package com.microservicios.workenado.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.microservicios.workenado.dto.UsuarioDTO;
import com.microservicios.workenado.model.Rol;
import com.microservicios.workenado.model.Usuario;
import com.microservicios.workenado.service.ServiceRole;
import com.microservicios.workenado.service.ServiceUsuarios;


@RestController
@RequestMapping("/api/v1")
public class UsuarioController {
    

    @Autowired
    private ServiceUsuarios usuarioService;

    @Autowired
    private ServiceRole roleService;

    
    @GetMapping("/user")
    public List<UsuarioDTO> listarUsuarios() {
        return usuarioService.obetenerUsuarios();
    }

    
    @GetMapping("/roles")
    public ResponseEntity<List<Rol>> obtenerRoles() {
        List<Rol> roles = roleService.bucarRol();
        return roles.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(roles);
    }

    @PostMapping("/users")
    public ResponseEntity<?> crearUsuarios(@RequestBody Usuario usuario) {
        try {
            System.out.println("Password recibida: " + usuario.getPassword());

            Long roleId = usuario.getRol().getId();// ayuda

            Usuario nuevoUsuario = usuarioService.crearUsuario(
                usuario.getUsername(),
                usuario.getPassword(),
                usuario.getNombres(),
                usuario.getApellido(),
                usuario.getEmail(),
                roleId
            );

            return ResponseEntity.status(HttpStatus.CREATED).body(nuevoUsuario);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<?> eliminarUsuario(@PathVariable Long id) {
        try {
            usuarioService.eliminarUsuario(id);
            return ResponseEntity.ok("Usuario eliminado con éxito");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<?> buscarPorid(@PathVariable Long id) {
        try {
            Usuario usuario = usuarioService.buscarPorId(id);
            return ResponseEntity.ok(usuario);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/users/{id}")
    public ResponseEntity<?> editarUsuario(@PathVariable Long id, @RequestBody Usuario usuarioActualizado) {
        try {
            Usuario actualizado = usuarioService.actualizarUsuario(id, usuarioActualizado);
            return ResponseEntity.ok(actualizado);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
