package com.microservicios.workenado.dto;

import com.microservicios.workenado.model.Rol;

import lombok.Data;

@Data

public class UsuarioDTO{

    private Long id;
    private String correo;
    private String nombres;
    private String apellido;
    private Rol rol;
    
}
