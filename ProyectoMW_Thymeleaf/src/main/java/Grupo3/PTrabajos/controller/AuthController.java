package Grupo3.PTrabajos.controller;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import Grupo3.PTrabajos.dto.AuthResponse;
import Grupo3.PTrabajos.dto.LoginRequest;
import Grupo3.PTrabajos.dto.RegisterRequest;
import jakarta.validation.Valid;

/* 
 * AuthController hace de "almacen" en memoria de los usuarios y de
 * las sesiones, porque el resto de controllers (Admin, Usuario, Oferta,
 * Perfil) necesitan consultar quien esta autenticado y con que rol.
 */
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    // ———————————————— Almacenamiento en memoria (compartido con el resto de controllers) ————————————————
    static final Map<Long, RegistroUsuario> usuariosPorId = new ConcurrentHashMap<>();
    static final Map<String, Long> idPorCorreo = new ConcurrentHashMap<>();
    static final Map<String, Long> sesiones = new ConcurrentHashMap<>(); // token -> idUsuario

    private static final AtomicLong SECUENCIA_USUARIO = new AtomicLong(1);
    private static final AtomicLong SECUENCIA_EMPRESA = new AtomicLong(1);
    private static final AtomicLong SECUENCIA_ESTUDIANTE = new AtomicLong(1);

    static {
        // Usuario ADMINISTRADOR de prueba (RF-03), ya que RF-01 solo permite
        // autoregistrarse como POSTULANTE o EMPRESA.
        RegistroUsuario admin = new RegistroUsuario();
        admin.idUsuario = SECUENCIA_USUARIO.getAndIncrement();
        admin.nombre = "Admin";
        admin.apellidoPaterno = "Empleate";
        admin.correo = "admin@empleate.com";
        admin.dni = "00000000";
        admin.password = "admin123";
        admin.rol = "ADMINISTRADOR";
        admin.activo = true;
        usuariosPorId.put(admin.idUsuario, admin);
        idPorCorreo.put(admin.correo, admin.idUsuario);

        // Estudiante (POSTULANTE) de prueba
        RegistroUsuario estudiante = new RegistroUsuario();
        estudiante.idUsuario = SECUENCIA_USUARIO.getAndIncrement();
        estudiante.nombre = "Dyron";
        estudiante.apellidoPaterno = "Vergaray";
        estudiante.apellidoMaterno = "Gutierrez";
        estudiante.correo = "dyron@mail.com";
        estudiante.dni = "11111111";
        estudiante.password = "clave123";
        estudiante.rol = "POSTULANTE";
        estudiante.activo = true;
        estudiante.idEstudiante = SECUENCIA_ESTUDIANTE.getAndIncrement();
        usuariosPorId.put(estudiante.idUsuario, estudiante);
        idPorCorreo.put(estudiante.correo, estudiante.idUsuario);

        // Estudiante (POSTULANTE) de prueba #2
        RegistroUsuario estudiante2 = new RegistroUsuario();
        estudiante2.idUsuario = SECUENCIA_USUARIO.getAndIncrement();
        estudiante2.nombre = "Maria Fernanda";
        estudiante2.apellidoPaterno = "Rojas";
        estudiante2.apellidoMaterno = "Castillo";
        estudiante2.correo = "maria@mail.com";
        estudiante2.dni = "11111112";
        estudiante2.password = "clave123";
        estudiante2.rol = "POSTULANTE";
        estudiante2.activo = true;
        estudiante2.idEstudiante = SECUENCIA_ESTUDIANTE.getAndIncrement();
        usuariosPorId.put(estudiante2.idUsuario, estudiante2);
        idPorCorreo.put(estudiante2.correo, estudiante2.idUsuario);

        // Estudiante (POSTULANTE) de prueba #3
        RegistroUsuario estudiante3 = new RegistroUsuario();
        estudiante3.idUsuario = SECUENCIA_USUARIO.getAndIncrement();
        estudiante3.nombre = "Carlos Alberto";
        estudiante3.apellidoPaterno = "Mendoza";
        estudiante3.apellidoMaterno = "Quispe";
        estudiante3.correo = "carlos@mail.com";
        estudiante3.dni = "11111113";
        estudiante3.password = "clave123";
        estudiante3.rol = "POSTULANTE";
        estudiante3.activo = true;
        estudiante3.idEstudiante = SECUENCIA_ESTUDIANTE.getAndIncrement();
        usuariosPorId.put(estudiante3.idUsuario, estudiante3);
        idPorCorreo.put(estudiante3.correo, estudiante3.idUsuario);

        // Estudiante (POSTULANTE) de prueba #4
        RegistroUsuario estudiante4 = new RegistroUsuario();
        estudiante4.idUsuario = SECUENCIA_USUARIO.getAndIncrement();
        estudiante4.nombre = "Valeria Nicole";
        estudiante4.apellidoPaterno = "Huaman";
        estudiante4.apellidoMaterno = "Torres";
        estudiante4.correo = "valeria@mail.com";
        estudiante4.dni = "11111114";
        estudiante4.password = "clave123";
        estudiante4.rol = "POSTULANTE";
        estudiante4.activo = true;
        estudiante4.idEstudiante = SECUENCIA_ESTUDIANTE.getAndIncrement();
        usuariosPorId.put(estudiante4.idUsuario, estudiante4);
        idPorCorreo.put(estudiante4.correo, estudiante4.idUsuario);

        // Empresa (EMPRESA) de prueba, simetrica al estudiante de prueba de
        // arriba: sirve para probar la vista Thymeleaf del lado empresa con
        // /perfil?idEmpresa=1 (ver EmpresaViewController).
        RegistroUsuario empresa = new RegistroUsuario();
        empresa.idUsuario = SECUENCIA_USUARIO.getAndIncrement();
        empresa.nombre = "Elena";
        empresa.apellidoPaterno = "Salazar";
        empresa.correo = "empresa@empleate.com";
        empresa.dni = "22222222";
        empresa.password = "empresa123";
        empresa.rol = "EMPRESA";
        empresa.activo = true;
        empresa.idEmpresa = SECUENCIA_EMPRESA.getAndIncrement();
        empresa.nombreEmpresa = "Pepito Industries";
        empresa.rubro = "Industrial";
        empresa.ruc = "20123456789";
        usuariosPorId.put(empresa.idUsuario, empresa);
        idPorCorreo.put(empresa.correo, empresa.idUsuario);

        RegistroUsuario empresa2 = new RegistroUsuario();
        empresa2.idUsuario = SECUENCIA_USUARIO.getAndIncrement();
        empresa2.nombre = "Empresa";
        empresa2.apellidoPaterno = "TechNova";
        empresa2.correo = "contacto@technovasolutions.com";
        empresa2.dni = "22222223";
        empresa2.password = "empresa123";
        empresa2.rol = "EMPRESA";
        empresa2.activo = true;
        empresa2.idEmpresa = SECUENCIA_EMPRESA.getAndIncrement();
        empresa2.nombreEmpresa = "TechNova Solutions";
        empresa2.rubro = "Tecnologia";
        empresa2.ruc = "20123456790";
        usuariosPorId.put(empresa2.idUsuario, empresa2);
        idPorCorreo.put(empresa2.correo, empresa2.idUsuario);
 
        RegistroUsuario empresa3 = new RegistroUsuario();
        empresa3.idUsuario = SECUENCIA_USUARIO.getAndIncrement();
        empresa3.nombre = "Empresa";
        empresa3.apellidoPaterno = "Inka Digital";
        empresa3.correo = "contacto@inkadigital.com";
        empresa3.dni = "22222224";
        empresa3.password = "empresa123";
        empresa3.rol = "EMPRESA";
        empresa3.activo = true;
        empresa3.idEmpresa = SECUENCIA_EMPRESA.getAndIncrement();
        empresa3.nombreEmpresa = "Inka Digital";
        empresa3.rubro = "Marketing y Diseño";
        empresa3.ruc = "20123456791";
        usuariosPorId.put(empresa3.idUsuario, empresa3);
        idPorCorreo.put(empresa3.correo, empresa3.idUsuario);
 
        RegistroUsuario empresa4 = new RegistroUsuario();
        empresa4.idUsuario = SECUENCIA_USUARIO.getAndIncrement();
        empresa4.nombre = "Empresa";
        empresa4.apellidoPaterno = "Soluciones Andinas";
        empresa4.correo = "contacto@solucionesandinas.com";
        empresa4.dni = "22222225";
        empresa4.password = "empresa123";
        empresa4.rol = "EMPRESA";
        empresa4.activo = true;
        empresa4.idEmpresa = SECUENCIA_EMPRESA.getAndIncrement();
        empresa4.nombreEmpresa = "Soluciones Andinas";
        empresa4.rubro = "Consultoria";
        empresa4.ruc = "20123456792";
        usuariosPorId.put(empresa4.idUsuario, empresa4);
        idPorCorreo.put(empresa4.correo, empresa4.idUsuario);
 
        RegistroUsuario empresa5 = new RegistroUsuario();
        empresa5.idUsuario = SECUENCIA_USUARIO.getAndIncrement();
        empresa5.nombre = "Empresa";
        empresa5.apellidoPaterno = "Grupo Nexa";
        empresa5.correo = "contacto@gruponexa.com";
        empresa5.dni = "22222226";
        empresa5.password = "empresa123";
        empresa5.rol = "EMPRESA";
        empresa5.activo = true;
        empresa5.idEmpresa = SECUENCIA_EMPRESA.getAndIncrement();
        empresa5.nombreEmpresa = "Grupo Nexa";
        empresa5.rubro = "Comercializacion";
        empresa5.ruc = "20123456793";
        usuariosPorId.put(empresa5.idUsuario, empresa5);
        idPorCorreo.put(empresa5.correo, empresa5.idUsuario);
    }

    // Representacion interna de un usuario registrado (equivalente en memoria
    // a las tablas Usuario + Rol + Credenciales + Estudiante/Empresa del ER).
    static class RegistroUsuario {
        Long idUsuario;
        String nombre;
        String apellidoPaterno;
        String apellidoMaterno;
        String correo;
        String dni;
        String password;
        String rol; // POSTULANTE, EMPRESA o ADMINISTRADOR
        boolean activo = true;
        Long idEstudiante;
        Long idEmpresa;
        String nombreEmpresa;
        String rubro;
        String ruc;

        String nombreCompleto() {
            StringBuilder sb = new StringBuilder(nombre).append(" ").append(apellidoPaterno);
            if (apellidoMaterno != null && !apellidoMaterno.isBlank()) {
                sb.append(" ").append(apellidoMaterno);
            }
            return sb.toString();
        }
    }

    /** Busca al usuario autenticado a partir del header "Authorization: Bearer &lt;token&gt;". */
    static RegistroUsuario usuarioAutenticado(String authorization) {
        if (authorization == null || !authorization.startsWith("Bearer ")) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Falta el token (Authorization: Bearer <token>)");
        }
        String token = authorization.substring("Bearer ".length()).trim();
        Long idUsuario = sesiones.get(token);
        if (idUsuario == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Token invalido o expirado");
        }
        RegistroUsuario usuario = usuariosPorId.get(idUsuario);
        if (usuario == null || !usuario.activo) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "La cuenta esta desactivada");
        }
        return usuario;
    }

    /** Exige que el usuario autenticado tenga uno de los roles indicados (equivalente a @PreAuthorize). */
    static RegistroUsuario exigirRol(String authorization, String... rolesPermitidos) {
        RegistroUsuario usuario = usuarioAutenticado(authorization);
        for (String rol : rolesPermitidos) {
            if (rol.equals(usuario.rol)) {
                return usuario;
            }
        }
        throw new ResponseStatusException(HttpStatus.FORBIDDEN, "No tienes permisos para esta accion");
    }

    // ———————————————— RF-01: Registro de usuarios ————————————————
    // El sistema permite registrar usuarios con sus datos basicos y el rol
    // correspondiente (POSTULANTE o EMPRESA).
    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@Valid @RequestBody RegisterRequest request) {
        String rol = request.getRol() == null ? "" : request.getRol().trim().toUpperCase();
        if (!rol.equals("POSTULANTE") && !rol.equals("EMPRESA")) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El rol debe ser POSTULANTE o EMPRESA");
        }
        if (idPorCorreo.containsKey(request.getCorreo())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Ya existe un usuario registrado con ese correo");
        }
        if (rol.equals("EMPRESA") && (request.getNombreEmpresa() == null || request.getNombreEmpresa().isBlank())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El nombre de la empresa es obligatorio");
        }

        RegistroUsuario usuario = new RegistroUsuario();
        usuario.idUsuario = SECUENCIA_USUARIO.getAndIncrement();
        usuario.nombre = request.getNombre();
        usuario.apellidoPaterno = request.getApellidoPaterno();
        usuario.apellidoMaterno = request.getApellidoMaterno();
        usuario.correo = request.getCorreo();
        usuario.dni = request.getDni();
        usuario.password = request.getPassword();
        usuario.rol = rol;
        usuario.activo = true;

        if (rol.equals("POSTULANTE")) {
            usuario.idEstudiante = SECUENCIA_ESTUDIANTE.getAndIncrement();
        } else {
            usuario.idEmpresa = SECUENCIA_EMPRESA.getAndIncrement();
            usuario.nombreEmpresa = request.getNombreEmpresa();
            usuario.rubro = request.getRubro();
            usuario.ruc = request.getRuc();
        }

        usuariosPorId.put(usuario.idUsuario, usuario);
        idPorCorreo.put(usuario.correo, usuario.idUsuario);

        String token = UUID.randomUUID().toString();
        sesiones.put(token, usuario.idUsuario);

        return ResponseEntity.ok(AuthResponse.builder()
                .token(token)
                .idUsuario(usuario.idUsuario)
                .nombre(usuario.nombreCompleto())
                .rol(usuario.rol)
                .idEmpresa(usuario.idEmpresa)
                .idEstudiante(usuario.idEstudiante)
                .build());
    }

    // ———————————————— RF-02: Inicio de sesion ————————————————
    // Con credenciales correctas se genera una sesion (token); con
    // credenciales incorrectas se rechaza el acceso.
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest request) {
        Long idUsuario = idPorCorreo.get(request.getCorreo());
        RegistroUsuario usuario = idUsuario == null ? null : usuariosPorId.get(idUsuario);

        if (usuario == null || !usuario.password.equals(request.getPassword())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Correo o contraseña incorrectos");
        }
        if (!usuario.activo) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "La cuenta esta desactivada");
        }

        String token = UUID.randomUUID().toString();
        sesiones.put(token, usuario.idUsuario);

        return ResponseEntity.ok(AuthResponse.builder()
                .token(token)
                .idUsuario(usuario.idUsuario)
                .nombre(usuario.nombreCompleto())
                .rol(usuario.rol)
                .idEmpresa(usuario.idEmpresa)
                .idEstudiante(usuario.idEstudiante)
                .build());
    }
}
