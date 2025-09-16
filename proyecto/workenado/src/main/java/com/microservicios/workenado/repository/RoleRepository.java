package com.microservicios.workenado.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.microservicios.workenado.model.Rol;

@Repository

public interface RoleRepository extends JpaRepository<Rol, Long> {
    
}
