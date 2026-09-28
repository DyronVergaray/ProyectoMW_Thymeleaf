package Grupo3.PTrabajos.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import Grupo3.PTrabajos.controller.AuthController.RegistroUsuario;
import Grupo3.PTrabajos.controller.PerfilEstudianteController.PerfilEstudianteInterno;

/**
 * Vista Thymeleaf de la pagina del estudiante (estudiante.html). El nombre y
 * la descripcion que se muestran cambian segun lo que llegue en la URL:
 *
 *   - Uso simple (sin datos guardados):
 *     /estudiante?nombre=Juan Perez&descripcion=Estudiante de Sistemas...
 *
 *   - Uso con datos reales (RF-06, cuando el estudiante ya se registro con
 *     /api/auth/register y personalizo su perfil con /api/perfil/estudiante/..):
 *     /estudiante?idEstudiante=1
 *     En este caso el nombre, la descripcion, los titulos y la experiencia
 *     laboral se cargan de lo guardado en memoria, ignorando "nombre" y
 *     "descripcion" si se enviaran junto con el id.
 */
@Controller
public class EstudianteViewController {

    @GetMapping({"/estudiante", "/estudiante.html"})
    public String verEstudiante(
            @RequestParam(required = false) Long idEstudiante,
            @RequestParam(defaultValue = "Vergaray Gutierrez, Dyron") String nombre,
            @RequestParam(defaultValue = "Aun no completaste tu descripcion. Cuentanos sobre ti, tus intereses y tus objetivos profesionales.") String descripcion,
            Model model) {

        RegistroUsuario estudiante = buscarEstudiantePorId(idEstudiante);

        if (estudiante != null) {
            PerfilEstudianteInterno perfil = PerfilEstudianteController.perfilesEstudiante.get(idEstudiante);

            model.addAttribute("nombre", estudiante.nombreCompleto());
            model.addAttribute("descripcion", perfil != null ? perfil.descripcion : null);
            model.addAttribute("titulos", perfil != null ? perfil.titulos : List.of());
            model.addAttribute("experiencias", perfil != null ? perfil.experiencias : List.of());
        } else {
            // Sin idEstudiante (o no encontrado): se usa lo que venga por la URL
            model.addAttribute("nombre", nombre);
            model.addAttribute("descripcion", descripcion);
            model.addAttribute("titulos", List.of());
            model.addAttribute("experiencias", List.of());
        }

        return "estudiante";
    }

    private RegistroUsuario buscarEstudiantePorId(Long idEstudiante) {
        if (idEstudiante == null) {
            return null;
        }
        return AuthController.usuariosPorId.values().stream()
                .filter(u -> "POSTULANTE".equals(u.rol) && idEstudiante.equals(u.idEstudiante))
                .findFirst()
                .orElse(null);
    }
}
