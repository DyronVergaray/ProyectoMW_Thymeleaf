package Grupo3.PTrabajos.dto;

import java.util.List;

// RF-05: Filtros para busqueda de trabajo (por area/puesto y requisitos)
public class FiltroOfertaRequest {
    private String puesto;           
    private List<String> requisitos; 

    public String getPuesto() {
        return puesto;
    }

    public void setPuesto(String puesto) {
        this.puesto = puesto;
    }

    public List<String> getRequisitos() {
        return requisitos;
    }

    public void setRequisitos(List<String> requisitos) {
        this.requisitos = requisitos;
    }
}
