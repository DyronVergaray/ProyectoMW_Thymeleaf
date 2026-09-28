package Grupo3.PTrabajos.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import Grupo3.PTrabajos.controller.AuthController.RegistroUsuario;
import Grupo3.PTrabajos.dto.ActualizarPerfilEstudianteRequest;
import Grupo3.PTrabajos.dto.EstudianteResponse;
import Grupo3.PTrabajos.dto.ExperienciaLaboralRequest;
import Grupo3.PTrabajos.dto.TituloRequest;
import jakarta.validation.Valid;

// ———————————————— RF-06: Personalizar perfil del estudiante ————————————————
// El sistema permite al postulante personalizar su perfil con su
// informacion profesional (descripcion, nivel educativo, telefono, titulos
// y experiencia laboral). Todo se guarda en memoria (no hay
// model/repository), indexado por el idEstudiante que entrega
// AuthController al registrarse. El lado EMPRESA vive por separado en
// PerfilEmpresaController: cada rol tiene su propio almacen en memoria y su
// propio constructor de respuesta, sin compartir clases entre ambos.
@RestController
@RequestMapping("/api/perfil")
public class PerfilEstudianteController {

    // idEstudiante -> datos de perfil (equivalente en memoria a Perfil_Estudiante)
    static final Map<Long, PerfilEstudianteInterno> perfilesEstudiante = new ConcurrentHashMap<>();

    static {
        // Perfil de prueba para el estudiante con idEstudiante = 1
        // (el estudiante "Dyron" que se crea en AuthController).
        PerfilEstudianteInterno perfil = new PerfilEstudianteInterno();
        perfil.descripcion = "Estudiante de Ingenieria de Sistemas, interesado en desarrollo backend.";
        perfil.nivelEducativo = "Universitario";
        perfil.telefono = "987654321";

        TituloRequest titulo = new TituloRequest();
        titulo.setEntidad("Google");
        titulo.setNombreTitulo("Cloud Camp");
        titulo.setDescargable(true);
        perfil.titulos.add(titulo);

        ExperienciaLaboralRequest experiencia = new ExperienciaLaboralRequest();
        experiencia.setEntidad("BBVA");
        experiencia.setNombrePuesto("Software Developer");
        experiencia.setPeriodo("2021-2025");
        perfil.experiencias.add(experiencia);

        perfilesEstudiante.put(1L, perfil);

        // Perfil de prueba para el estudiante con idEstudiante = 2
        // (la estudiante "Maria Fernanda" que se crea en AuthController).
        PerfilEstudianteInterno perfil2 = new PerfilEstudianteInterno();
        perfil2.descripcion = "Estudiante de Ingenieria de Software, interesada en desarrollo frontend y diseño de interfaces.";
        perfil2.nivelEducativo = "Universitario";
        perfil2.telefono = "987654322";

        TituloRequest titulo2 = new TituloRequest();
        titulo2.setEntidad("Microsoft");
        titulo2.setNombreTitulo("Learn Student Ambassador");
        titulo2.setDescargable(true);
        perfil2.titulos.add(titulo2);

        ExperienciaLaboralRequest experiencia2 = new ExperienciaLaboralRequest();
        experiencia2.setEntidad("Interbank");
        experiencia2.setNombrePuesto("Frontend Developer Practicante");
        experiencia2.setPeriodo("2023-2024");
        perfil2.experiencias.add(experiencia2);

        perfilesEstudiante.put(2L, perfil2);

        // Perfil de prueba para el estudiante con idEstudiante = 3
        // (el estudiante "Carlos Alberto" que se crea en AuthController).
        PerfilEstudianteInterno perfil3 = new PerfilEstudianteInterno();
        perfil3.descripcion = "Estudiante de Ciencias de la Computacion, apasionado por el analisis de datos y la inteligencia artificial.";
        perfil3.nivelEducativo = "Universitario";
        perfil3.telefono = "987654323";

        TituloRequest titulo3 = new TituloRequest();
        titulo3.setEntidad("AWS");
        titulo3.setNombreTitulo("Cloud Practitioner");
        titulo3.setDescargable(true);
        perfil3.titulos.add(titulo3);

        ExperienciaLaboralRequest experiencia3 = new ExperienciaLaboralRequest();
        experiencia3.setEntidad("Rappi");
        experiencia3.setNombrePuesto("Data Analyst Practicante");
        experiencia3.setPeriodo("2022-2023");
        perfil3.experiencias.add(experiencia3);

        perfilesEstudiante.put(3L, perfil3);

        // Perfil de prueba para el estudiante con idEstudiante = 4
        // (la estudiante "Valeria Nicole" que se crea en AuthController).
        PerfilEstudianteInterno perfil4 = new PerfilEstudianteInterno();
        perfil4.descripcion = "Estudiante de Ingenieria Industrial, enfocada en mejora de procesos y gestion de proyectos.";
        perfil4.nivelEducativo = "Universitario";
        perfil4.telefono = "987654324";

        TituloRequest titulo4 = new TituloRequest();
        titulo4.setEntidad("PMI");
        titulo4.setNombreTitulo("Project Management Ready");
        titulo4.setDescargable(false);
        perfil4.titulos.add(titulo4);

        ExperienciaLaboralRequest experiencia4 = new ExperienciaLaboralRequest();
        experiencia4.setEntidad("Backus");
        experiencia4.setNombrePuesto("Analista de Procesos Practicante");
        experiencia4.setPeriodo("2023-2024");
        perfil4.experiencias.add(experiencia4);

        perfilesEstudiante.put(4L, perfil4);
    }

    static class PerfilEstudianteInterno {
        String descripcion;
        String nivelEducativo;
        String telefono;
        List<TituloRequest> titulos = new ArrayList<>();
        List<ExperienciaLaboralRequest> experiencias = new ArrayList<>();
    }

    private PerfilEstudianteInterno perfilDe(Long idEstudiante) {
        return perfilesEstudiante.computeIfAbsent(idEstudiante, k -> new PerfilEstudianteInterno());
    }

    private EstudianteResponse aResponse(RegistroUsuario estudiante, PerfilEstudianteInterno perfil) {
        return EstudianteResponse.builder()
                .idEstudiante(estudiante.idEstudiante)
                .nombreCompleto(estudiante.nombreCompleto())
                .correo(estudiante.correo)
                .telefono(perfil.telefono)
                .descripcion(perfil.descripcion)
                .nivelEducativo(perfil.nivelEducativo)
                .titulos(perfil.titulos)
                .experiencias(perfil.experiencias)
                .build();
    }

    private RegistroUsuario exigirEstudianteDueno(String authorization, Long idEstudiante) {
        RegistroUsuario usuario = AuthController.exigirRol(authorization, "POSTULANTE");
        if (!usuario.idEstudiante.equals(idEstudiante)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Solo puedes editar tu propio perfil");
        }
        return usuario;
    }

    // RF-06: datos generales del perfil del estudiante (descripcion, nivel educativo, telefono)
    @PostMapping("/estudiante/{idEstudiante}")
    public ResponseEntity<EstudianteResponse> actualizarPerfilEstudiante(@RequestHeader("Authorization") String authorization,
                                                                          @PathVariable Long idEstudiante,
                                                                          @RequestBody ActualizarPerfilEstudianteRequest request) {
        RegistroUsuario usuario = exigirEstudianteDueno(authorization, idEstudiante);
        PerfilEstudianteInterno perfil = perfilDe(idEstudiante);
        perfil.descripcion = request.getDescripcion();
        perfil.nivelEducativo = request.getNivelEducativo();
        perfil.telefono = request.getTelefono();
        return ResponseEntity.ok(aResponse(usuario, perfil));
    }

    // RF-06: agregar un Titulo al perfil del estudiante
    @PostMapping("/estudiante/{idEstudiante}/titulos")
    public ResponseEntity<EstudianteResponse> agregarTitulo(@RequestHeader("Authorization") String authorization,
                                                              @PathVariable Long idEstudiante,
                                                              @Valid @RequestBody TituloRequest request) {
        RegistroUsuario usuario = exigirEstudianteDueno(authorization, idEstudiante);
        if (request.getDescargable() == null) {
            request.setDescargable(Boolean.FALSE);
        }
        PerfilEstudianteInterno perfil = perfilDe(idEstudiante);
        perfil.titulos.add(request);
        return ResponseEntity.ok(aResponse(usuario, perfil));
    }

    // RF-06: agregar una Experiencia Laboral al perfil del estudiante
    @PostMapping("/estudiante/{idEstudiante}/experiencia")
    public ResponseEntity<EstudianteResponse> agregarExperienciaLaboral(@RequestHeader("Authorization") String authorization,
                                                                         @PathVariable Long idEstudiante,
                                                                         @Valid @RequestBody ExperienciaLaboralRequest request) {
        RegistroUsuario usuario = exigirEstudianteDueno(authorization, idEstudiante);
        PerfilEstudianteInterno perfil = perfilDe(idEstudiante);
        perfil.experiencias.add(request);
        return ResponseEntity.ok(aResponse(usuario, perfil));
    }
}
