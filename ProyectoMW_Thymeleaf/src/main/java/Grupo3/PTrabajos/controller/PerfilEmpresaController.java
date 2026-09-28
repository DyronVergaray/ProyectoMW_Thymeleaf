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
import Grupo3.PTrabajos.dto.ActualizarPerfilEmpresaRequest;
import Grupo3.PTrabajos.dto.ContactoEmpresaRequest;
import Grupo3.PTrabajos.dto.EmpresaResponse;
import Grupo3.PTrabajos.dto.PersonalEmpresaRequest;
import jakarta.validation.Valid;

// ———————————————— RF-06: Personalizar perfil de la empresa ————————————————
// El sistema permite a la empresa personalizar su perfil publico
// (descripcion, mision, vision, foto de perfil, banner, direccion, horario
// de atencion, contacto y personal principal). Todo se guarda en memoria
// (no hay model/repository), indexado por el idEmpresa que entrega
// AuthController al registrarse. 
@RestController
@RequestMapping("/api/perfil")
public class PerfilEmpresaController {

        // idEmpresa -> datos de perfil + contacto (equivalente en memoria a
        // Perfil_Empresa/Contacto_Empresa).
        static final Map<Long, PerfilEmpresaInterno> perfilesEmpresa = new ConcurrentHashMap<>();

        static {
                // Perfil de prueba para la empresa con idEmpresa = 1 (la empresa
                // "Pepito Industries" que se crea en AuthController), asi la vista
                // Thymeleaf "perfil.html" se puede probar con /perfil?idEmpresa=1.
                PerfilEmpresaInterno perfilEmpresa = new PerfilEmpresaInterno();
                perfilEmpresa.descripcion = "En Pepito Industries nos dedicamos a la innovacion y el desarrollo de "
                                + "soluciones industriales de vanguardia, comprometidos con la calidad y la sostenibilidad.";
                perfilEmpresa.mision = "Proveer productos y servicios de alta calidad que mejoren la vida de las "
                                + "personas, manteniendo un fuerte compromiso con el medio ambiente y fomentando el "
                                + "desarrollo profesional de todos nuestros colaboradores.";
                perfilEmpresa.vision = "Ser reconocidos para el 2030 como el socio estrategico preferido en la "
                                + "industria manufacturera a nivel regional, destacando por nuestra tecnologia e "
                                + "innovacion responsable.";
                perfilEmpresa.fotoPerfil = "/img/logo.png";
                perfilEmpresa.banner = "/img/Prueba.png";
                perfilEmpresa.direccion = "Av. Principal 1234, Parque Industrial, Lima, Peru.";
                perfilEmpresa.horarioAtencion = "Lunes a Viernes de 8:00 am - 6:00 pm.";
                perfilEmpresa.telefono = "+51 01 234 5678";
                perfilEmpresa.correoEmpresarial = "contacto@pepitoindustries.com";
                perfilEmpresa.personal.add(nuevoPersonal("Carlos Mendoza", "Director General", "/img/logo.png"));
                perfilEmpresa.personal.add(nuevoPersonal("Elena Salazar", "CEO & Fundadora", "/img/Prueba.png"));
                perfilEmpresa.personal.add(nuevoPersonal("Luis Ramirez", "COO", "/img/logo.png"));
                perfilEmpresa.personal.add(nuevoPersonal("Ana Gomez", "RR.HH", "/img/Prueba.png"));
                perfilesEmpresa.put(1L, perfilEmpresa);

                // Perfil de prueba para la empresa con idEmpresa = 2
                PerfilEmpresaInterno perfilEmpresa2 = new PerfilEmpresaInterno();
                perfilEmpresa2.descripcion = "Empresa dedicada al desarrollo de soluciones tecnologicas, "
                                + "servicios digitales y soporte informatico para pequeñas y medianas empresas.";
                perfilEmpresa2.mision = "Impulsar la transformacion digital de las pequeñas y medianas empresas "
                                + "mediante soluciones tecnologicas accesibles, confiables y a la medida de cada cliente.";
                perfilEmpresa2.vision = "Convertirnos en el aliado tecnologico de referencia para las pymes de la "
                                + "region, liderando la adopcion de tecnologia con soporte cercano y de calidad.";
                perfilEmpresa2.fotoPerfil = "/img/logo.png";
                perfilEmpresa2.banner = "/img/Prueba.png";
                perfilEmpresa2.direccion = "Av. Tecnologia 245, Miraflores, Lima, Peru.";
                perfilEmpresa2.horarioAtencion = "Lunes a Viernes de 9:00 am - 6:00 pm.";
                perfilEmpresa2.telefono = "+51 01 345 6789";
                perfilEmpresa2.correoEmpresarial = "contacto@technovasolutions.com";
                perfilEmpresa2.redesSociales = "@TechNovaSolutions";
                perfilEmpresa2.personal.add(nuevoPersonal("Marco Villanueva", "Gerente General", "/img/logo.png"));
                perfilEmpresa2.personal.add(nuevoPersonal("Diana Flores", "Jefa de Soporte", "/img/Prueba.png"));
                perfilesEmpresa.put(2L, perfilEmpresa2);

                // Perfil de prueba para la empresa con idEmpresa = 3
                PerfilEmpresaInterno perfilEmpresa3 = new PerfilEmpresaInterno();
                perfilEmpresa3.descripcion = "Inka Digital ofrece servicios de marketing digital, diseño grafico "
                                + "y desarrollo de plataformas web para empresas y emprendimientos.";
                perfilEmpresa3.mision = "Ayudar a marcas y emprendimientos a crecer en el mundo digital mediante "
                                + "estrategias de marketing, diseño y desarrollo web creativas y efectivas.";
                perfilEmpresa3.vision = "Ser la agencia digital preferida de los emprendedores peruanos, reconocida "
                                + "por su creatividad, cercania y resultados medibles.";
                perfilEmpresa3.fotoPerfil = "/img/logo.png";
                perfilEmpresa3.banner = "/img/Prueba.png";
                perfilEmpresa3.direccion = "Jr. Los Innovadores 567, San Miguel, Lima, Peru.";
                perfilEmpresa3.horarioAtencion = "Lunes a Sabado de 8:00 am - 5:00 pm.";
                perfilEmpresa3.telefono = "+51 01 456 7890";
                perfilEmpresa3.correoEmpresarial = "contacto@inkadigital.com";
                perfilEmpresa3.redesSociales = "@InkaDigital";
                perfilEmpresa3.personal.add(nuevoPersonal("Camila Rojas", "Directora Creativa", "/img/Prueba.png"));
                perfilEmpresa3.personal.add(nuevoPersonal("Jose Huaman", "Desarrollador Web", "/img/logo.png"));
                perfilesEmpresa.put(3L, perfilEmpresa3);

                // Perfil de prueba para la empresa con idEmpresa = 4
                PerfilEmpresaInterno perfilEmpresa4 = new PerfilEmpresaInterno();
                perfilEmpresa4.descripcion = "Soluciones Andinas brinda servicios de consultoria empresarial, "
                                + "gestion de proyectos y asesoramiento para organizaciones de diferentes sectores.";
                perfilEmpresa4.mision = "Acompañar a las organizaciones en la mejora de su gestion y sus proyectos, "
                                + "brindando asesoria especializada, etica y orientada a resultados sostenibles.";
                perfilEmpresa4.vision = "Ser reconocidos como la consultora de referencia en la region andina para "
                                + "la gestion de proyectos y la mejora organizacional.";
                perfilEmpresa4.fotoPerfil = "/img/logo.png";
                perfilEmpresa4.banner = "/img/Prueba.png";
                perfilEmpresa4.direccion = "Av. Los Andes 890, Santiago de Surco, Lima, Peru.";
                perfilEmpresa4.horarioAtencion = "Lunes a Viernes de 8:30 am - 5:30 pm.";
                perfilEmpresa4.telefono = "+51 01 567 8901";
                perfilEmpresa4.correoEmpresarial = "contacto@solucionesandinas.com";
                perfilEmpresa4.redesSociales = "@SolucionesAndinas";
                perfilEmpresa4.personal.add(nuevoPersonal("Patricia Zevallos", "Socia Consultora", "/img/Prueba.png"));
                perfilesEmpresa.put(4L, perfilEmpresa4);

                // Perfil de prueba para la empresa con idEmpresa = 5
                PerfilEmpresaInterno perfilEmpresa5 = new PerfilEmpresaInterno();
                perfilEmpresa5.descripcion = "Grupo Nexa es una empresa orientada a la comercializacion de productos "
                                + "tecnologicos y servicios especializados para clientes y empresas.";
                perfilEmpresa5.mision = "Ofrecer productos tecnologicos de calidad y un servicio especializado que "
                                + "genere valor real para nuestros clientes y socios comerciales.";
                perfilEmpresa5.vision = "Consolidarnos como uno de los principales distribuidores de tecnologia del "
                                + "pais, destacando por la confianza y el servicio postventa.";
                perfilEmpresa5.fotoPerfil = "/img/logo.png";
                perfilEmpresa5.banner = "/img/Prueba.png";
                perfilEmpresa5.direccion = "Av. Industrial 123, Los Olivos, Lima, Peru.";
                perfilEmpresa5.horarioAtencion = "Lunes a Viernes de 9:00 am - 6:00 pm.";
                perfilEmpresa5.telefono = "+51 01 678 9012";
                perfilEmpresa5.correoEmpresarial = "contacto@gruponexa.com";
                perfilEmpresa5.redesSociales = "@GrupoNexa";
                perfilEmpresa5.personal.add(nuevoPersonal("Renato Palacios", "Gerente Comercial", "/img/logo.png"));
                perfilesEmpresa.put(5L, perfilEmpresa5);
        }

        // Helper solo para armar el personal de prueba de los bloques estaticos de
        // arriba.
        private static PersonalEmpresaRequest nuevoPersonal(String nombre, String cargo, String foto) {
                PersonalEmpresaRequest personal = new PersonalEmpresaRequest();
                personal.setNombre(nombre);
                personal.setCargo(cargo);
                personal.setFoto(foto);
                return personal;
        }

        // Equivalente en memoria a Perfil_Empresa + Contacto_Empresa.
        static class PerfilEmpresaInterno {
                String descripcion;
                String mision;
                String vision;
                String fotoPerfil;
                String banner;
                String direccion;
                String horarioAtencion;
                String telefono;
                String correoEmpresarial;
                String redesSociales;
                // Personal principal de la empresa (RF-06). La vista Thymeleaf
                // "perfil.html" recorre esta lista con th:each, asi que la cantidad
                // de tarjetas que se muestran depende de cuantos elementos tenga.
                List<PersonalEmpresaRequest> personal = new ArrayList<>();
        }

        private PerfilEmpresaInterno perfilEmpresaDe(Long idEmpresa) {
                return perfilesEmpresa.computeIfAbsent(idEmpresa, k -> new PerfilEmpresaInterno());
        }

        private RegistroUsuario exigirEmpresaDueno(String authorization, Long idEmpresa) {
                RegistroUsuario usuario = AuthController.exigirRol(authorization, "EMPRESA");
                if (!usuario.idEmpresa.equals(idEmpresa)) {
                        throw new ResponseStatusException(HttpStatus.FORBIDDEN,
                                        "Solo puedes editar el perfil de tu propia empresa");
                }
                return usuario;
        }

        // Construye el EmpresaResponse a partir de los datos fijos del registro
        // (nombreEmpresa, rubro, ruc) y de los datos de perfil guardados en
        // PerfilEmpresaInterno. Usa el Builder propio de EmpresaResponse, sin
        // depender de ninguna clase del lado estudiante.
        private EmpresaResponse aResponseEmpresa(RegistroUsuario empresa, PerfilEmpresaInterno perfil) {
                return EmpresaResponse.builder()
                                .idEmpresa(empresa.idEmpresa)
                                .nombreEmpresa(empresa.nombreEmpresa)
                                .rubro(empresa.rubro)
                                .ruc(empresa.ruc)
                                .telefono(perfil.telefono)
                                .correoEmpresarial(perfil.correoEmpresarial)
                                .redesSociales(perfil.redesSociales)
                                .descripcion(perfil.descripcion)
                                .mision(perfil.mision)
                                .vision(perfil.vision)
                                .fotoPerfil(perfil.fotoPerfil)
                                .banner(perfil.banner)
                                .direccion(perfil.direccion)
                                .horarioAtencion(perfil.horarioAtencion)
                                .personal(perfil.personal)
                                .build();
        }

        // RF-06: datos generales del perfil de la empresa (descripcion, mision,
        // vision, foto de perfil, banner, direccion, horario de atencion).
        @PostMapping("/empresa/{idEmpresa}")
        public ResponseEntity<EmpresaResponse> actualizarPerfilEmpresa(
                        @RequestHeader("Authorization") String authorization,
                        @PathVariable Long idEmpresa,
                        @RequestBody ActualizarPerfilEmpresaRequest request) {
                RegistroUsuario usuario = exigirEmpresaDueno(authorization, idEmpresa);
                PerfilEmpresaInterno perfil = perfilEmpresaDe(idEmpresa);
                perfil.descripcion = request.getDescripcion();
                perfil.mision = request.getMision();
                perfil.vision = request.getVision();
                perfil.fotoPerfil = request.getFotoPerfil();
                perfil.banner = request.getBanner();
                perfil.direccion = request.getDireccion();
                perfil.horarioAtencion = request.getHorarioAtencion();
                return ResponseEntity.ok(aResponseEmpresa(usuario, perfil));
        }

        // RF-06: personalizar el contacto de la empresa (telefono, correo empresarial,
        // redes sociales)
        @PostMapping("/empresa/{idEmpresa}/contacto")
        public ResponseEntity<EmpresaResponse> actualizarContactoEmpresa(
                        @RequestHeader("Authorization") String authorization,
                        @PathVariable Long idEmpresa,
                        @RequestBody ContactoEmpresaRequest request) {
                RegistroUsuario usuario = exigirEmpresaDueno(authorization, idEmpresa);
                PerfilEmpresaInterno perfil = perfilEmpresaDe(idEmpresa);
                perfil.telefono = request.getTelefono();
                perfil.correoEmpresarial = request.getCorreoEmpresarial();
                perfil.redesSociales = request.getRedesSociales();
                return ResponseEntity.ok(aResponseEmpresa(usuario, perfil));
        }

        // RF-06: agregar un integrante al "Personal principal" de la empresa.
        // Cada llamada suma un elemento a la lista, y la vista Thymeleaf pinta
        // tantas tarjetas como elementos haya.
        @PostMapping("/empresa/{idEmpresa}/personal")
        public ResponseEntity<EmpresaResponse> agregarPersonalEmpresa(
                        @RequestHeader("Authorization") String authorization,
                        @PathVariable Long idEmpresa,
                        @Valid @RequestBody PersonalEmpresaRequest request) {
                RegistroUsuario usuario = exigirEmpresaDueno(authorization, idEmpresa);
                PerfilEmpresaInterno perfil = perfilEmpresaDe(idEmpresa);
                perfil.personal.add(request);
                return ResponseEntity.ok(aResponseEmpresa(usuario, perfil));
        }
}
