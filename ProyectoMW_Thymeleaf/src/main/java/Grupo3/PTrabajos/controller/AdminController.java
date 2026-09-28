package Grupo3.PTrabajos.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import Grupo3.PTrabajos.controller.AuthController.RegistroUsuario;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    // ———————————————— RF-03: Seguridad y autorizacion basada en roles ————————————————
    // Solo un usuario autenticado con rol ADMINISTRADOR puede desactivar
    // cuentas. Si el token no trae ese rol, se responde 403 (Forbidden)
    // antes de hacer cualquier cambio.
    @PostMapping("/usuarios/{idUsuario}/desactivar")
    public ResponseEntity<Void> desactivarUsuario(@RequestHeader("Authorization") String authorization,
                                                   @PathVariable Long idUsuario) {
        AuthController.exigirRol(authorization, "ADMINISTRADOR");

        RegistroUsuario usuario = AuthController.usuariosPorId.get(idUsuario);
        if (usuario == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No existe un usuario con ese id");
        }
        usuario.activo = false;
        return ResponseEntity.ok().build();
    }
}
