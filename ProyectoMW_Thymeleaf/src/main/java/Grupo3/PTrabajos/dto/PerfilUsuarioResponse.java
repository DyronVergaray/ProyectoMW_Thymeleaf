package Grupo3.PTrabajos.dto;

// RF-03: datos basicos visibles segun el rol autenticado
public class PerfilUsuarioResponse {
    private final Long idUsuario;
    private final String nombreCompleto;
    private final String correo;
    private final String rol;
    private final boolean activo;

    private PerfilUsuarioResponse(Builder builder) {
        this.idUsuario = builder.idUsuario;
        this.nombreCompleto = builder.nombreCompleto;
        this.correo = builder.correo;
        this.rol = builder.rol;
        this.activo = builder.activo;
    }

    public Long getIdUsuario() {
        return idUsuario;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public String getCorreo() {
        return correo;
    }

    public String getRol() {
        return rol;
    }

    public boolean isActivo() {
        return activo;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private Long idUsuario;
        private String nombreCompleto;
        private String correo;
        private String rol;
        private boolean activo;

        public Builder idUsuario(Long idUsuario) {
            this.idUsuario = idUsuario;
            return this;
        }

        public Builder nombreCompleto(String nombreCompleto) {
            this.nombreCompleto = nombreCompleto;
            return this;
        }

        public Builder correo(String correo) {
            this.correo = correo;
            return this;
        }

        public Builder rol(String rol) {
            this.rol = rol;
            return this;
        }

        public Builder activo(boolean activo) {
            this.activo = activo;
            return this;
        }

        public PerfilUsuarioResponse build() {
            return new PerfilUsuarioResponse(this);
        }
    }
}
