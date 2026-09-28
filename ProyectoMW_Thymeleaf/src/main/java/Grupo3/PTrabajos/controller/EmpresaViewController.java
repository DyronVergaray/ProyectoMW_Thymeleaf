package Grupo3.PTrabajos.controller;

import java.util.Collections;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import Grupo3.PTrabajos.controller.AuthController.RegistroUsuario;
import Grupo3.PTrabajos.controller.PerfilEmpresaController.PerfilEmpresaInterno;

/**
 * Vista Thymeleaf de la pagina de la empresa (perfil.html). Es el mismo
 * mecanismo que EstudianteViewController pero del lado EMPRESA: el nombre,
 * la descripcion, la foto de perfil y el banner que se muestran cambian
 * segun lo que llegue en la URL:
 *
 *   - Uso simple (sin datos guardados):
 *     /perfil?nombreEmpresa=Mi Empresa&descripcion=Somos una empresa...
 *
 *   - Uso con datos reales (RF-06, cuando la empresa ya se registro con
 *     /api/auth/register y personalizo su perfil con /api/perfil/empresa/..):
 *     /perfil?idEmpresa=1
 *     En este caso el nombre de la empresa, el rubro, la descripcion, la
 *     mision, la vision, la foto de perfil, el banner, la direccion, el
 *     telefono, el correo, el horario de atencion y el "Personal principal"
 *     (lista, puede tener cualquier cantidad de integrantes) se cargan de lo
 *     guardado en memoria, ignorando los parametros de la URL si se enviaran
 *     junto con el id.
 *
 * El nombre de empresa y la foto de perfil que arma este metodo tambien los
 * usa el navbar (fragmento "navbar.html", incluido con th:replace dentro de
 * perfil.html), asi que arriba a la derecha se ve el mismo nombre y la misma
 * foto que en la tarjeta principal.
 */
@Controller
public class EmpresaViewController {

    @GetMapping({"/perfil", "/perfil.html"})
    public String verPerfilEmpresa(
            @RequestParam(required = false) Long idEmpresa,
            @RequestParam(defaultValue = "Pepito Industries") String nombreEmpresa,
            @RequestParam(defaultValue = "Aun no completaste la descripcion de tu empresa. Cuentanos a que se dedica y que la hace unica.") String descripcion,
            @RequestParam(defaultValue = "/img/logo.png") String fotoPerfil,
            @RequestParam(defaultValue = "/img/Prueba.png") String banner,
            Model model) {

        RegistroUsuario empresa = buscarEmpresaPorId(idEmpresa);

        if (empresa != null) {
            PerfilEmpresaInterno perfil = PerfilEmpresaController.perfilesEmpresa.get(idEmpresa);

            // Nombre y rubro salen del registro (RF-01/RF-06 no los vuelve a
            // pedir); el resto sale del perfil personalizado (RF-06).
            model.addAttribute("nombreEmpresa", empresa.nombreEmpresa);
            model.addAttribute("rubro", empresa.rubro);
            model.addAttribute("descripcion", perfil != null ? perfil.descripcion : null);
            model.addAttribute("mision", perfil != null ? perfil.mision : null);
            model.addAttribute("vision", perfil != null ? perfil.vision : null);
            model.addAttribute("fotoPerfil", perfil != null && perfil.fotoPerfil != null ? perfil.fotoPerfil : "/img/logo.png");
            model.addAttribute("banner", perfil != null && perfil.banner != null ? perfil.banner : "/img/Prueba.png");
            model.addAttribute("direccion", perfil != null ? perfil.direccion : null);
            model.addAttribute("telefono", perfil != null ? perfil.telefono : null);
            model.addAttribute("correoEmpresarial", perfil != null ? perfil.correoEmpresarial : null);
            model.addAttribute("horarioAtencion", perfil != null ? perfil.horarioAtencion : null);
            // "Personal principal": la vista recorre esta lista con th:each,
            // asi que muestra tantas tarjetas como integrantes tenga.
            model.addAttribute("personal", perfil != null ? perfil.personal : Collections.emptyList());
        } else {
            // Sin idEmpresa (o no encontrada): se usa lo que venga por la URL
            // y no hay datos de contacto ni personal guardados todavia.
            model.addAttribute("nombreEmpresa", nombreEmpresa);
            model.addAttribute("rubro", null);
            model.addAttribute("descripcion", descripcion);
            model.addAttribute("mision", null);
            model.addAttribute("vision", null);
            model.addAttribute("fotoPerfil", fotoPerfil);
            model.addAttribute("banner", banner);
            model.addAttribute("direccion", null);
            model.addAttribute("telefono", null);
            model.addAttribute("correoEmpresarial", null);
            model.addAttribute("horarioAtencion", null);
            model.addAttribute("personal", Collections.emptyList());
        }

        return "perfil";
    }

    private RegistroUsuario buscarEmpresaPorId(Long idEmpresa) {
        if (idEmpresa == null) {
            return null;
        }
        return AuthController.usuariosPorId.values().stream()
                .filter(u -> "EMPRESA".equals(u.rol) && idEmpresa.equals(u.idEmpresa))
                .findFirst()
                .orElse(null);
    }
}
