package com.backend.abrazamente.controller;

import com.backend.abrazamente.dto.UsuarioRequestDTO;
import com.backend.abrazamente.dto.UsuarioResponseDTO;
import com.backend.abrazamente.dto.UsuarioUpdateRequestDTO;
import com.backend.abrazamente.service.UsuarioService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    private final UsuarioService service;

    @PostMapping
    public ResponseEntity<UsuarioResponseDTO> crearUsuario(@Valid @RequestBody UsuarioRequestDTO request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.crearUsuario(request));
    }

    @GetMapping
    public ResponseEntity<List<UsuarioResponseDTO>> obtenerUsuarios() {
        return ResponseEntity.ok(service.obtenerUsuarios());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioResponseDTO> usuarioById(@PathVariable Integer id) {
        return ResponseEntity.ok(service.usuarioById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UsuarioResponseDTO> actualizarUsuario(
            @PathVariable Integer id,
            @Valid @RequestBody UsuarioUpdateRequestDTO request,
            Authentication authentication) {
        UsuarioResponseDTO objetivo = service.usuarioById(id);
        boolean esAdmin = authentication.getAuthorities().stream()
                .anyMatch(authority -> "ROLE_ADMIN".equals(authority.getAuthority()));
        if (!esAdmin && !objetivo.email().equalsIgnoreCase(authentication.getName())) {
            throw new AccessDeniedException("No puedes modificar el perfil de otro usuario");
        }
        return ResponseEntity.ok(service.actualizarUsuario(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarUsuario(@PathVariable Integer id) {
        service.eliminarUsuario(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/nombres/{nombre}")
    public ResponseEntity<List<UsuarioResponseDTO>> listaPorNombres(@PathVariable String nombre) {
        return ResponseEntity.ok(service.buscarByNombre(nombre));
    }

    @GetMapping("/ciudades/{ciudad}")
    public ResponseEntity<List<UsuarioResponseDTO>> listaPorCiudad(@PathVariable String ciudad) {
        return ResponseEntity.ok(service.findByCiudad(ciudad));
    }
}
