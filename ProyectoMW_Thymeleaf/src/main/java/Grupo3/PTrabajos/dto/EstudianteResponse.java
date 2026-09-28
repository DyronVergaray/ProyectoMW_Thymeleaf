package Grupo3.PTrabajos.dto;

import java.util.List;

// RF-06: respuesta con el perfil del estudiante (Titulos y Experiencia Laboral)
public class EstudianteResponse {
    private final Long idEstudiante;
    private final String nombreCompleto;
    private final String correo;
    private final String telefono;
    private final String descripcion;
    private final String nivelEducativo;
    private final List<TituloRequest> titulos;
    private final List<ExperienciaLaboralRequest> experiencias;

    private EstudianteResponse(Builder builder) {
        this.idEstudiante = builder.idEstudiante;
        this.nombreCompleto = builder.nombreCompleto;
        this.correo = builder.correo;
        this.telefono = builder.telefono;
        this.descripcion = builder.descripcion;
        this.nivelEducativo = builder.nivelEducativo;
        this.titulos = builder.titulos;
        this.experiencias = builder.experiencias;
    }

    public Long getIdEstudiante() {
        return idEstudiante;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public String getCorreo() {
        return correo;
    }

    public String getTelefono() {
        return telefono;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public String getNivelEducativo() {
        return nivelEducativo;
    }

    public List<TituloRequest> getTitulos() {
        return titulos;
    }

    public List<ExperienciaLaboralRequest> getExperiencias() {
        return experiencias;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private Long idEstudiante;
        private String nombreCompleto;
        private String correo;
        private String telefono;
        private String descripcion;
        private String nivelEducativo;
        private List<TituloRequest> titulos;
        private List<ExperienciaLaboralRequest> experiencias;

        public Builder idEstudiante(Long idEstudiante) {
            this.idEstudiante = idEstudiante;
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

        public Builder telefono(String telefono) {
            this.telefono = telefono;
            return this;
        }

        public Builder descripcion(String descripcion) {
            this.descripcion = descripcion;
            return this;
        }

        public Builder nivelEducativo(String nivelEducativo) {
            this.nivelEducativo = nivelEducativo;
            return this;
        }

        public Builder titulos(List<TituloRequest> titulos) {
            this.titulos = titulos;
            return this;
        }

        public Builder experiencias(List<ExperienciaLaboralRequest> experiencias) {
            this.experiencias = experiencias;
            return this;
        }

        public EstudianteResponse build() {
            return new EstudianteResponse(this);
        }
    }
}
