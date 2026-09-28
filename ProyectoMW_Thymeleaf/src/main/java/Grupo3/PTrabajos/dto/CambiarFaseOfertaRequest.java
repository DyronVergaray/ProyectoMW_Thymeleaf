package Grupo3.PTrabajos.dto;

import jakarta.validation.constraints.NotNull;

// RF-04: manejar las fases del anuncio de postulacion
public class CambiarFaseOfertaRequest {

    @NotNull(message = "El nuevo estado es obligatorio")
    private String nuevoEstado; // BORRADOR, PUBLICADA, EN_PROCESO, CERRADA, CANCELADA

    public String getNuevoEstado() {
        return nuevoEstado;
    }

    public void setNuevoEstado(String nuevoEstado) {
        this.nuevoEstado = nuevoEstado;
    }
}
