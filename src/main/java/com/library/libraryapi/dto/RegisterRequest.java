package com.library.libraryapi.dto;

import com.library.libraryapi.models.TipoUsuario;
import lombok.Data;

@Data
public class RegisterRequest {
    private String name;
    private String email;
    private String password;
    private TipoUsuario tipoUsuario;
}