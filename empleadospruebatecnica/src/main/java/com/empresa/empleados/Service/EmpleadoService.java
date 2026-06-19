package com.empresa.empleados.Service;

import com.empresa.empleados.DTO.EmpleadoRequest;
import com.empresa.empleados.DTO.EmpleadoResponse;
import com.empresa.empleados.Entities.Empleado;
import com.empresa.empleados.Repository.EmpleadoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EmpleadoService {

    private final EmpleadoRepository empleadoRepository;

    public List<EmpleadoResponse> listarTodos() {
        return empleadoRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    public EmpleadoResponse buscarPorId(Long id) {
        Empleado empleado = empleadoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Empleado no encontrado con id: " + id));
        return mapToResponse(empleado);
    }

    public EmpleadoResponse crear(EmpleadoRequest request) {
        if (empleadoRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Ya existe un empleado con ese email");
        }

        Empleado empleado = Empleado.builder()
                .nombres(request.getNombres())
                .apellidos(request.getApellidos())
                .email(request.getEmail())
                .telefono(request.getTelefono())
                .cargo(request.getCargo())
                .departamento(request.getDepartamento())
                .salario(request.getSalario())
                .fechaContratacion(request.getFechaContratacion())
                .activo(true)
                .build();

        return mapToResponse(empleadoRepository.save(empleado));
    }

    public EmpleadoResponse actualizar(Long id, EmpleadoRequest request) {
        Empleado empleado = empleadoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Empleado no encontrado con id: " + id));

        empleado.setNombres(request.getNombres());
        empleado.setApellidos(request.getApellidos());
        empleado.setEmail(request.getEmail());
        empleado.setTelefono(request.getTelefono());
        empleado.setCargo(request.getCargo());
        empleado.setDepartamento(request.getDepartamento());
        empleado.setSalario(request.getSalario());
        empleado.setFechaContratacion(request.getFechaContratacion());

        return mapToResponse(empleadoRepository.save(empleado));
    }

    public void eliminar(Long id) {
        Empleado empleado = empleadoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Empleado no encontrado con id: " + id));

        // Borrado logico en vez de fisico (buena practica)
        empleado.setActivo(false);
        empleadoRepository.save(empleado);
    }

    private EmpleadoResponse mapToResponse(Empleado empleado) {
        return EmpleadoResponse.builder()
                .id(empleado.getId())
                .nombres(empleado.getNombres())
                .apellidos(empleado.getApellidos())
                .email(empleado.getEmail())
                .telefono(empleado.getTelefono())
                .cargo(empleado.getCargo())
                .departamento(empleado.getDepartamento())
                .salario(empleado.getSalario())
                .fechaContratacion(empleado.getFechaContratacion())
                .activo(empleado.getActivo())
                .build();
    }
}