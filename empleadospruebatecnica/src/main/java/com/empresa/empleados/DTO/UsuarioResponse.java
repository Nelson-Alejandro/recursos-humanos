package com.empresa.empleados.DTO;

import com.empresa.empleados.Entities.Rol;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UsuarioResponse {
    private Long id;
    private String username;
    private Rol rol;
    private boolean activo;
}