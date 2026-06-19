package com.empresa.empleados.DTO;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmpleadoRequest {

    @NotBlank(message = "El nombre es obligatorio")
    private String nombres;

    @NotBlank(message = "El apellido es obligatorio")
    private String apellidos;

    @NotBlank(message = "El email es obligatorio")
    @Email(message = "El email no tiene un formato valido")
    private String email;

    private String telefono;

    @NotBlank(message = "El cargo es obligatorio")
    private String cargo;

    @NotBlank(message = "El departamento es obligatorio")
    private String departamento;

    @NotNull(message = "El salario es obligatorio")
    @Positive(message = "El salario debe ser positivo")
    private Double salario;

    @NotNull(message = "La fecha de contratacion es obligatoria")
    private LocalDate fechaContratacion;
}