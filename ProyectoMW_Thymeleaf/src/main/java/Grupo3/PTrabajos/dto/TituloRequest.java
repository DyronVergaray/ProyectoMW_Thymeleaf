package Grupo3.PTrabajos.dto;

import jakarta.validation.constraints.NotBlank;

// RF-06: Personalizar perfil -> agregar Titulo
public class TituloRequest {

    @NotBlank(message = "La entidad es obligatoria")
    private String entidad;

    @NotBlank(message = "El nombre del titulo es obligatorio")
    private String nombreTitulo;

    private Boolean descargable;

    public String getEntidad() {
        return entidad;
    }

    public void setEntidad(String entidad) {
        this.entidad = entidad;
    }

    public String getNombreTitulo() {
        return nombreTitulo;
    }

    public void setNombreTitulo(String nombreTitulo) {
        this.nombreTitulo = nombreTitulo;
    }

    public Boolean getDescargable() {
        return descargable;
    }

    public void setDescargable(Boolean descargable) {
        this.descargable = descargable;
    }
}
