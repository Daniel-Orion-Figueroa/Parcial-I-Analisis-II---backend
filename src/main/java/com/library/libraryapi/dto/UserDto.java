package com.library.libraryapi.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    private Long id;
    private String name;
    private String email;
    private String tipoUsuario;
    private String fechaRegistro;
    
    // Constructor desde entidad
    public UserDto(com.library.libraryapi.models.User user) {
        this.id = user.getId();
        this.name = user.getName();
        this.email = user.getEmail();
        this.tipoUsuario = user.getTipoUsuario() != null ? user.getTipoUsuario().toString() : null;
        this.fechaRegistro = user.getFechaRegistro() != null ? user.getFechaRegistro().toString() : null;
    }
}
