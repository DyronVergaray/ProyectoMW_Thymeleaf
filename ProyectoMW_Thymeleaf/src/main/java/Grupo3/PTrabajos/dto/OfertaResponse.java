package Grupo3.PTrabajos.dto;

import java.time.LocalDate;
import java.util.List;

// Respuesta de oferta usada por RF-04 y RF-05
public class OfertaResponse {
    private final Long idOferta;
    private final String nombreEmpresa;
    private final String puesto;
    private final Double sueldo;
    private final String estado;
    private final LocalDate fechaInicio;
    private final LocalDate fechaFin;
    private final List<String> requisitos;

    private OfertaResponse(Builder builder) {
        this.idOferta = builder.idOferta;
        this.nombreEmpresa = builder.nombreEmpresa;
        this.puesto = builder.puesto;
        this.sueldo = builder.sueldo;
        this.estado = builder.estado;
        this.fechaInicio = builder.fechaInicio;
        this.fechaFin = builder.fechaFin;
        this.requisitos = builder.requisitos;
    }

    public Long getIdOferta() {
        return idOferta;
    }

    public String getNombreEmpresa() {
        return nombreEmpresa;
    }

    public String getPuesto() {
        return puesto;
    }

    public Double getSueldo() {
        return sueldo;
    }

    public String getEstado() {
        return estado;
    }

    public LocalDate getFechaInicio() {
        return fechaInicio;
    }

    public LocalDate getFechaFin() {
        return fechaFin;
    }

    public List<String> getRequisitos() {
        return requisitos;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private Long idOferta;
        private String nombreEmpresa;
        private String puesto;
        private Double sueldo;
        private String estado;
        private LocalDate fechaInicio;
        private LocalDate fechaFin;
        private List<String> requisitos;

        public Builder idOferta(Long idOferta) {
            this.idOferta = idOferta;
            return this;
        }

        public Builder nombreEmpresa(String nombreEmpresa) {
            this.nombreEmpresa = nombreEmpresa;
            return this;
        }

        public Builder puesto(String puesto) {
            this.puesto = puesto;
            return this;
        }

        public Builder sueldo(Double sueldo) {
            this.sueldo = sueldo;
            return this;
        }

        public Builder estado(String estado) {
            this.estado = estado;
            return this;
        }

        public Builder fechaInicio(LocalDate fechaInicio) {
            this.fechaInicio = fechaInicio;
            return this;
        }

        public Builder fechaFin(LocalDate fechaFin) {
            this.fechaFin = fechaFin;
            return this;
        }

        public Builder requisitos(List<String> requisitos) {
            this.requisitos = requisitos;
            return this;
        }

        public OfertaResponse build() {
            return new OfertaResponse(this);
        }
    }
}
