package com.microservicios.workenado.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.microservicios.workenado.model.Rol;
import com.microservicios.workenado.repository.RoleRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class ServiceRole {
    @Autowired
    private RoleRepository roleRepositoy;

    public List<Rol> bucarRol(){

        return roleRepositoy.findAll();
        
    }

    public Rol buscarPorId (Long id){
        return roleRepositoy.findById(id).orElseThrow(()-> new RuntimeException("Rol no encontrado"));

        
    }
}
