package Grupo3.PTrabajos.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

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
import Grupo3.PTrabajos.dto.CambiarFaseOfertaRequest;
import Grupo3.PTrabajos.dto.DescartarPostulanteRequest;
import Grupo3.PTrabajos.dto.FiltroOfertaRequest;
import Grupo3.PTrabajos.dto.OfertaRequest;
import Grupo3.PTrabajos.dto.OfertaResponse;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/ofertas")
public class OfertaController {

    private static final Set<String> ESTADOS_VALIDOS = Set.of(
            "BORRADOR", "PUBLICADA", "EN_PROCESO", "CERRADA", "CANCELADA");

    // Almacenamiento en memoria de las ofertas (reemplaza model.Oferta + OfertaRepository)
    private static final Map<Long, OfertaInterna> ofertas = new ConcurrentHashMap<>();

    private static final Map<Long, Set<Long>> postulantesDescartados = new ConcurrentHashMap<>();
    private static final AtomicLong secuenciaOferta = new AtomicLong(1);

    private static class OfertaInterna {
        Long idOferta;
        Long idEmpresa;
        String nombreEmpresa;
        String puesto;
        Double sueldo;
        Integer horas;
        String frecuenciaPagos;
        String bonos;
        String gratificaciones;
        Boolean seguroVida;
        String vacaciones;
        String otros;
        LocalDate fechaInicio;
        LocalDate fechaFin;
        String estado = "BORRADOR";
        List<String> requisitos = new ArrayList<>();
    }

    private OfertaResponse aResponse(OfertaInterna o) {
        return OfertaResponse.builder()
                .idOferta(o.idOferta)
                .nombreEmpresa(o.nombreEmpresa)
                .puesto(o.puesto)
                .sueldo(o.sueldo)
                .estado(o.estado)
                .fechaInicio(o.fechaInicio)
                .fechaFin(o.fechaFin)
                .requisitos(o.requisitos)
                .build();
    }

    // ———————————————— RF-04: Anuncios de Trabajo (crear anuncio) ————————————————
    // Solo una cuenta con rol EMPRESA puede publicar anuncios de trabajo, y
    // solo sobre su propia empresa (idEmpresa del token).
    @PostMapping
    public ResponseEntity<OfertaResponse> crearOferta(@RequestHeader("Authorization") String authorization,
                                                        @Valid @RequestBody OfertaRequest request) {
        RegistroUsuario empresa = AuthController.exigirRol(authorization, "EMPRESA");
        if (!empresa.idEmpresa.equals(request.getIdEmpresa())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Solo puedes crear ofertas para tu propia empresa");
        }

        OfertaInterna oferta = new OfertaInterna();
        oferta.idOferta = secuenciaOferta.getAndIncrement();
        oferta.idEmpresa = empresa.idEmpresa;
        oferta.nombreEmpresa = empresa.nombreEmpresa;
        oferta.puesto = request.getPuesto();
        oferta.sueldo = request.getSueldo();
        oferta.horas = request.getHoras();
        oferta.frecuenciaPagos = request.getFrecuenciaPagos();
        oferta.bonos = request.getBonos();
        oferta.gratificaciones = request.getGratificaciones();
        oferta.seguroVida = request.getSeguroVida();
        oferta.vacaciones = request.getVacaciones();
        oferta.otros = request.getOtros();
        oferta.fechaInicio = request.getFechaInicio();
        oferta.fechaFin = request.getFechaFin();
        if (request.getRequisitos() != null) {
            oferta.requisitos = new ArrayList<>(request.getRequisitos());
        }

        ofertas.put(oferta.idOferta, oferta);
        return ResponseEntity.ok(aResponse(oferta));
    }

    // ———————————————— RF-04: manejar las fases del anuncio de postulacion ————————————————
    // La empresa mueve la oferta entre sus fases: BORRADOR, PUBLICADA,
    // EN_PROCESO, CERRADA o CANCELADA.
    @PostMapping("/{idOferta}/fase")
    public ResponseEntity<OfertaResponse> cambiarFase(@RequestHeader("Authorization") String authorization,
                                                        @PathVariable Long idOferta,
                                                        @Valid @RequestBody CambiarFaseOfertaRequest request) {
        RegistroUsuario empresa = AuthController.exigirRol(authorization, "EMPRESA");
        OfertaInterna oferta = obtenerOfertaDeLaEmpresa(idOferta, empresa);

        String nuevoEstado = request.getNuevoEstado() == null ? "" : request.getNuevoEstado().trim().toUpperCase();
        if (!ESTADOS_VALIDOS.contains(nuevoEstado)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Estado invalido. Debe ser uno de: " + ESTADOS_VALIDOS);
        }
        oferta.estado = nuevoEstado;
        return ResponseEntity.ok(aResponse(oferta));
    }

    // ———————————————— RF-04: la empresa descarta postulantes ————————————————
    @PostMapping("/{idOferta}/postulaciones/{idPostulacion}/descartar")
    public ResponseEntity<Void> descartarPostulante(@RequestHeader("Authorization") String authorization,
                                                      @PathVariable Long idOferta,
                                                      @PathVariable Long idPostulacion,
                                                      @RequestBody(required = false) DescartarPostulanteRequest request) {
        RegistroUsuario empresa = AuthController.exigirRol(authorization, "EMPRESA");
        obtenerOfertaDeLaEmpresa(idOferta, empresa);

        postulantesDescartados.computeIfAbsent(idOferta, k -> new HashSet<>()).add(idPostulacion);
        return ResponseEntity.ok().build();
    }

    // ———————————————— RF-05: Filtros para busqueda de trabajo ————————————————
    // El postulante filtra los anuncios de empleo (publicados) por
    // area/puesto y por requisitos. Se usa POST porque el filtro viaja en
    // el body (puede incluir una lista de requisitos).
    @PostMapping("/buscar")
    public ResponseEntity<List<OfertaResponse>> buscarOfertas(@RequestHeader("Authorization") String authorization,
                                                                @RequestBody(required = false) FiltroOfertaRequest filtro) {
        AuthController.exigirRol(authorization, "POSTULANTE");

        String puesto = (filtro != null && filtro.getPuesto() != null) ? filtro.getPuesto().trim().toLowerCase() : null;
        List<String> requisitos = (filtro != null) ? filtro.getRequisitos() : null;

        List<OfertaResponse> resultado = ofertas.values().stream()
                .filter(o -> "PUBLICADA".equals(o.estado))
                .filter(o -> puesto == null || puesto.isBlank() || o.puesto.toLowerCase().contains(puesto))
                .filter(o -> {
                    if (requisitos == null || requisitos.isEmpty()) {
                        return true;
                    }
                    Set<String> requisitosOferta = o.requisitos.stream()
                            .map(String::toLowerCase)
                            .collect(java.util.stream.Collectors.toSet());
                    return requisitos.stream().map(String::toLowerCase).allMatch(requisitosOferta::contains);
                })
                .map(this::aResponse)
                .toList();

        return ResponseEntity.ok(resultado);
    }

    private OfertaInterna obtenerOfertaDeLaEmpresa(Long idOferta, RegistroUsuario empresa) {
        OfertaInterna oferta = ofertas.get(idOferta);
        if (oferta == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No existe una oferta con ese id");
        }
        if (!oferta.idEmpresa.equals(empresa.idEmpresa)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Esta oferta no pertenece a tu empresa");
        }
        return oferta;
    }
}
