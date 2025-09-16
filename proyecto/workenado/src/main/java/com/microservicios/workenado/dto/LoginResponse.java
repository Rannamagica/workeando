package com.microservicios.workenado.dto;

import com.microservicios.workenado.model.Rol;

import lombok.Data;

@Data
public class LoginResponse{

    private Long id;
    private String username;
    private String correo;
    private String nombres;
    private String apellido;
    private Rol rol;
}
