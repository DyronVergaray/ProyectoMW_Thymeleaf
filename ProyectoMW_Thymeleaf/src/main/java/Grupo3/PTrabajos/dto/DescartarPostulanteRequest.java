package Grupo3.PTrabajos.dto;

// RF-04: la empresa descarta postulantes de una oferta
public class DescartarPostulanteRequest {
    private String motivo; // opcional, motivo del descarte

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }
}
