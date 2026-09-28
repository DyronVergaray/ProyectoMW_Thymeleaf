package Grupo3.PTrabajos.dto;

// Respuesta comun para RF-01 (registro) y RF-02 (login)
public class AuthResponse {
    private final String token;
    private final Long idUsuario;
    private final String nombre;
    private final String rol;
    // Se completa solo cuando corresponde (segun el rol registrado)
    private final Long idEmpresa;
    private final Long idEstudiante;

    private AuthResponse(Builder builder) {
        this.token = builder.token;
        this.idUsuario = builder.idUsuario;
        this.nombre = builder.nombre;
        this.rol = builder.rol;
        this.idEmpresa = builder.idEmpresa;
        this.idEstudiante = builder.idEstudiante;
    }

    public String getToken() {
        return token;
    }

    public Long getIdUsuario() {
        return idUsuario;
    }

    public String getNombre() {
        return nombre;
    }

    public String getRol() {
        return rol;
    }

    public Long getIdEmpresa() {
        return idEmpresa;
    }

    public Long getIdEstudiante() {
        return idEstudiante;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String token;
        private Long idUsuario;
        private String nombre;
        private String rol;
        private Long idEmpresa;
        private Long idEstudiante;

        public Builder token(String token) {
            this.token = token;
            return this;
        }

        public Builder idUsuario(Long idUsuario) {
            this.idUsuario = idUsuario;
            return this;
        }

        public Builder nombre(String nombre) {
            this.nombre = nombre;
            return this;
        }

        public Builder rol(String rol) {
            this.rol = rol;
            return this;
        }

        public Builder idEmpresa(Long idEmpresa) {
            this.idEmpresa = idEmpresa;
            return this;
        }

        public Builder idEstudiante(Long idEstudiante) {
            this.idEstudiante = idEstudiante;
            return this;
        }

        public AuthResponse build() {
            return new AuthResponse(this);
        }
    }
}
