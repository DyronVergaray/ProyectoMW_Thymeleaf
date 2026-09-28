package Grupo3.PTrabajos.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import Grupo3.PTrabajos.controller.AuthController.RegistroUsuario;
import Grupo3.PTrabajos.dto.PerfilUsuarioResponse;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    // ———————————————— RF-03: cada usuario solo accede a su propia info ————————————————
    // Disponible para cualquier rol autenticado (Postulante, Empresa o
    // Administrador); el usuario se obtiene del token, no de la URL.
    @GetMapping("/me")
    public ResponseEntity<PerfilUsuarioResponse> obtenerPerfilPropio(@RequestHeader("Authorization") String authorization) {
        RegistroUsuario usuario = AuthController.usuarioAutenticado(authorization);

        return ResponseEntity.ok(PerfilUsuarioResponse.builder()
                .idUsuario(usuario.idUsuario)
                .nombreCompleto(usuario.nombreCompleto())
                .correo(usuario.correo)
                .rol(usuario.rol)
                .activo(usuario.activo)
                .build());
    }
}
