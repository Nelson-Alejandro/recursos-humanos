package com.empresa.empleados.DTO;

import com.empresa.empleados.Entities.Rol;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UsuarioRequest {
    @NotBlank(message = "El username es obligatorio")
    private String username;

    private String password; // opcional al editar

    @NotNull(message = "El rol es obligatorio")
    private Rol rol;
}