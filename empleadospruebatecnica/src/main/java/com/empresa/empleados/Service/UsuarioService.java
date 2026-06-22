package com.empresa.empleados.Service;

import com.empresa.empleados.DTO.UsuarioRequest;
import com.empresa.empleados.DTO.UsuarioResponse;
import com.empresa.empleados.Entities.Rol;
import com.empresa.empleados.Entities.Usuario;
import com.empresa.empleados.Repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public List<UsuarioResponse> listarRRHH() {
        return usuarioRepository.findByRol(Rol.RRHH)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    public UsuarioResponse crear(UsuarioRequest request) {
        if (usuarioRepository.findByUsername(request.getUsername()).isPresent()) {
            throw new RuntimeException("El nombre de usuario ya existe");
        }
        if (request.getPassword() == null || request.getPassword().isBlank()) {
            throw new RuntimeException("La contraseña es obligatoria al crear un usuario");
        }
        if (request.getRol() != Rol.RRHH) {
            throw new RuntimeException("Solo se pueden crear usuarios con rol RRHH desde aquí");
        }

        Usuario usuario = Usuario.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .rol(Rol.RRHH)
                .activo(true)
                .build();

        return toResponse(usuarioRepository.save(usuario));
    }

    public UsuarioResponse actualizar(Long id, UsuarioRequest request) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        if (usuario.getRol() != Rol.RRHH) {
            throw new RuntimeException("Solo se pueden editar usuarios con rol RRHH");
        }

        if (!usuario.getUsername().equals(request.getUsername())
                && usuarioRepository.findByUsername(request.getUsername()).isPresent()) {
            throw new RuntimeException("El nombre de usuario ya existe");
        }

        usuario.setUsername(request.getUsername());
        if (request.getPassword() != null && !request.getPassword().isBlank()) {
            usuario.setPassword(passwordEncoder.encode(request.getPassword()));
        }

        return toResponse(usuarioRepository.save(usuario));
    }

    public void eliminar(Long id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        if (usuario.getRol() != Rol.RRHH) {
            throw new RuntimeException("Solo se pueden eliminar usuarios con rol RRHH");
        }

        usuarioRepository.delete(usuario);
    }

    private UsuarioResponse toResponse(Usuario usuario) {
        return UsuarioResponse.builder()
                .id(usuario.getId())
                .username(usuario.getUsername())
                .rol(usuario.getRol())
                .activo(usuario.isActivo())
                .build();
    }
}