package com.microservicios.workenado.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.microservicios.workenado.model.Usuario;


@Repository

public interface UsuarioRepository extends JpaRepository<Usuario,Long> {

    
} 
