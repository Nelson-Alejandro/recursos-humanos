package com.empresa.empleados.Entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "empleados")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Empleado {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El nombre es obligatorio")
    @Column(nullable = false)
    private String nombres;

    @NotBlank(message = "El apellido es obligatorio")
    @Column(nullable = false)
    private String apellidos;

    @NotBlank(message = "El email es obligatorio")
    @Email(message = "El email no tiene un formato valido")
    @Column(nullable = false, unique = true)
    private String email;

    private String telefono;

    @NotBlank(message = "El cargo es obligatorio")
    @Column(nullable = false)
    private String cargo;

    @NotBlank(message = "El departamento es obligatorio")
    @Column(nullable = false)
    private String departamento;

    @NotNull(message = "El salario es obligatorio")
    @Positive(message = "El salario debe ser positivo")
    @Column(nullable = false)
    private Double salario;

    @NotNull(message = "La fecha de contratacion es obligatoria")
    @Column(name = "fecha_contratacion", nullable = false)
    private LocalDate fechaContratacion;

    @Column(nullable = false)
    @Builder.Default
    private Boolean activo = true;
}