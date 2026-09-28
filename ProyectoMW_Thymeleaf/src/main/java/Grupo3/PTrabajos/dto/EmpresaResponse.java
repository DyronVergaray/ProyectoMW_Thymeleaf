package Grupo3.PTrabajos.dto;

import java.util.List;

// RF-06: respuesta con el perfil/contacto de la empresa
public class EmpresaResponse {
    private final Long idEmpresa;
    private final String nombreEmpresa;
    private final String rubro;
    private final String ruc;
    private final String telefono;
    private final String correoEmpresarial;
    private final String redesSociales;
    // Datos generales del perfil (RF-06), usados por la vista Thymeleaf
    // "perfil.html": descripcion, mision, vision, foto de perfil, banner,
    // direccion y horario de atencion.
    private final String descripcion;
    private final String mision;
    private final String vision;
    private final String fotoPerfil;
    private final String banner;
    private final String direccion;
    private final String horarioAtencion;
    // Lista de integrantes del "Personal principal" de la empresa (RF-06).
    // La cantidad de tarjetas que pinta la vista depende del tamaño de esta lista.
    private final List<PersonalEmpresaRequest> personal;

    private EmpresaResponse(Builder builder) {
        this.idEmpresa = builder.idEmpresa;
        this.nombreEmpresa = builder.nombreEmpresa;
        this.rubro = builder.rubro;
        this.ruc = builder.ruc;
        this.telefono = builder.telefono;
        this.correoEmpresarial = builder.correoEmpresarial;
        this.redesSociales = builder.redesSociales;
        this.descripcion = builder.descripcion;
        this.mision = builder.mision;
        this.vision = builder.vision;
        this.fotoPerfil = builder.fotoPerfil;
        this.banner = builder.banner;
        this.direccion = builder.direccion;
        this.horarioAtencion = builder.horarioAtencion;
        this.personal = builder.personal;
    }

    public Long getIdEmpresa() {
        return idEmpresa;
    }

    public String getNombreEmpresa() {
        return nombreEmpresa;
    }

    public String getRubro() {
        return rubro;
    }

    public String getRuc() {
        return ruc;
    }

    public String getTelefono() {
        return telefono;
    }

    public String getCorreoEmpresarial() {
        return correoEmpresarial;
    }

    public String getRedesSociales() {
        return redesSociales;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public String getMision() {
        return mision;
    }

    public String getVision() {
        return vision;
    }

    public String getFotoPerfil() {
        return fotoPerfil;
    }

    public String getBanner() {
        return banner;
    }

    public String getDireccion() {
        return direccion;
    }

    public String getHorarioAtencion() {
        return horarioAtencion;
    }

    public List<PersonalEmpresaRequest> getPersonal() {
        return personal;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private Long idEmpresa;
        private String nombreEmpresa;
        private String rubro;
        private String ruc;
        private String telefono;
        private String correoEmpresarial;
        private String redesSociales;
        private String descripcion;
        private String mision;
        private String vision;
        private String fotoPerfil;
        private String banner;
        private String direccion;
        private String horarioAtencion;
        private List<PersonalEmpresaRequest> personal;

        public Builder idEmpresa(Long idEmpresa) {
            this.idEmpresa = idEmpresa;
            return this;
        }

        public Builder nombreEmpresa(String nombreEmpresa) {
            this.nombreEmpresa = nombreEmpresa;
            return this;
        }

        public Builder rubro(String rubro) {
            this.rubro = rubro;
            return this;
        }

        public Builder ruc(String ruc) {
            this.ruc = ruc;
            return this;
        }

        public Builder telefono(String telefono) {
            this.telefono = telefono;
            return this;
        }

        public Builder correoEmpresarial(String correoEmpresarial) {
            this.correoEmpresarial = correoEmpresarial;
            return this;
        }

        public Builder redesSociales(String redesSociales) {
            this.redesSociales = redesSociales;
            return this;
        }

        public Builder descripcion(String descripcion) {
            this.descripcion = descripcion;
            return this;
        }

        public Builder mision(String mision) {
            this.mision = mision;
            return this;
        }

        public Builder vision(String vision) {
            this.vision = vision;
            return this;
        }

        public Builder fotoPerfil(String fotoPerfil) {
            this.fotoPerfil = fotoPerfil;
            return this;
        }

        public Builder banner(String banner) {
            this.banner = banner;
            return this;
        }

        public Builder direccion(String direccion) {
            this.direccion = direccion;
            return this;
        }

        public Builder horarioAtencion(String horarioAtencion) {
            this.horarioAtencion = horarioAtencion;
            return this;
        }

        public Builder personal(List<PersonalEmpresaRequest> personal) {
            this.personal = personal;
            return this;
        }

        public EmpresaResponse build() {
            return new EmpresaResponse(this);
        }
    }
}
