package com.empresa.empleados.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmpleadoResponse {

    private Long id;
    private String nombres;
    private String apellidos;
    private String email;
    private String telefono;
    private String cargo;
    private String departamento;
    private Double salario;
    private LocalDate fechaContratacion;
    private Boolean activo;
}