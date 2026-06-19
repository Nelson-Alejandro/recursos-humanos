package com.empresa.empleados.Service;

import com.empresa.empleados.DTO.LoginRequest;
import com.empresa.empleados.DTO.LoginResponse;
import com.empresa.empleados.Entities.Usuario;
import com.empresa.empleados.Repository.UsuarioRepository;
import com.empresa.empleados.Security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final UsuarioRepository usuarioRepository;
    private final JwtUtil jwtUtil;

    public LoginResponse login(LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
        );

        Usuario usuario = usuarioRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        String token = jwtUtil.generateToken(usuario.getUsername(), usuario.getRol().name());

        return LoginResponse.builder()
                .token(token)
                .username(usuario.getUsername())
                .rol(usuario.getRol().name())
                .build();
    }
}