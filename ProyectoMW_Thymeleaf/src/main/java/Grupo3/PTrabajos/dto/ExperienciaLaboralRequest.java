package Grupo3.PTrabajos.dto;

import jakarta.validation.constraints.NotBlank;

// RF-06: Personalizar perfil -> agregar Experiencia Laboral
public class ExperienciaLaboralRequest {

    @NotBlank(message = "La entidad es obligatoria")
    private String entidad;

    @NotBlank(message = "El nombre del puesto es obligatorio")
    private String nombrePuesto;

    private String periodo;

    public String getEntidad() {
        return entidad;
    }

    public void setEntidad(String entidad) {
        this.entidad = entidad;
    }

    public String getNombrePuesto() {
        return nombrePuesto;
    }

    public void setNombrePuesto(String nombrePuesto) {
        this.nombrePuesto = nombrePuesto;
    }

    public String getPeriodo() {
        return periodo;
    }

    public void setPeriodo(String periodo) {
        this.periodo = periodo;
    }
}
