package com.microservicios.workenado.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.microservicios.workenado.dto.LoginResponse;
import com.microservicios.workenado.dto.UsuarioDTO;
import com.microservicios.workenado.model.Rol;
import com.microservicios.workenado.model.Usuario;
import com.microservicios.workenado.repository.RoleRepository;
import com.microservicios.workenado.repository.UsuarioRepository;

import jakarta.transaction.Transactional;
import jakarta.validation.constraints.Email;

@Service
@Transactional

public class ServiceUsuarios {
    
    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired 
    private PasswordEncoder passwordEncoder;

    public List<UsuarioDTO> obetenerUsuarios(){

        return usuarioRepository.findAll()
                .stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    private UsuarioDTO convertirADTO(Usuario usuario) {
        UsuarioDTO dto = new UsuarioDTO();
        dto.setId(usuario.getId());
        dto.setCorreo(usuario.getEmail());
        dto.setNombres(usuario.getNombres());
        dto.setApellido(usuario.getApellido());
        dto.setRol(usuario.getRol());
        return dto;
    }
    
    public Usuario crearUsuario(String username, String password, String nombres, String apellido, String correo,
            Long roleId) {
        if (password == null || password.isBlank()) {
            throw new RuntimeException("La contraseña no puede ser nula ni vacía");
        }

        Rol role = roleRepository.findById(roleId).orElseThrow(() -> new RuntimeException("Rol no encontrado"));

        Usuario user = new Usuario();
        user.setPassword(passwordEncoder.encode(password));

        user.setUsername(username);
        user.setEmail(correo);
        user.setRol(role);
        user.setNombres(nombres);
        user.setApellido(apellido);

        return usuarioRepository.save(user);
    }
    public void eliminarUsuario(Long userId) {

        Usuario usuario = usuarioRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("usuario no encontrado" + userId));

        usuarioRepository.delete(usuario);
    }

    public Usuario buscarPorId(Long id) {
        return usuarioRepository.findById(id).get();
    }

    public Usuario actualizarUsuario(Long id, Usuario usuarioActualizado) {
        Usuario usuarioExistente = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        usuarioExistente.setUsername(usuarioActualizado.getUsername());
        
        usuarioExistente.setEmail(usuarioActualizado.getEmail());                                                              
        usuarioExistente.setNombres(usuarioActualizado.getNombres());
        usuarioExistente.setApellido(usuarioActualizado.getApellido());
        usuarioExistente.setRol(usuarioActualizado.getRol());

        // Solo actualiza la contraseña si viene una nueva
        if (usuarioActualizado.getPassword() != null && !usuarioActualizado.getPassword().isBlank()) {
            usuarioExistente.setPassword(passwordEncoder.encode(usuarioActualizado.getPassword()));
        }

        return usuarioRepository.save(usuarioExistente);
    }

    public LoginResponse autenticarUsuario(String username, String password) {
        Usuario usuario = usuarioRepository.findAll()
                .stream()
                .filter(u -> u.getUsername().equals(username))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        if (!passwordEncoder.matches(password, usuario.getPassword())) {
            throw new RuntimeException("Contraseña incorrecta");
        }

        LoginResponse response = new LoginResponse();
        response.setId(usuario.getId());
        response.setUsername(usuario.getUsername());
        response.setCorreo(usuario.getEmail());
        response.setNombres(usuario.getNombres());
        response.setApellido(usuario.getApellido());
        response.setRol(usuario.getRol());

        return response;
    }
}


