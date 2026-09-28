package Grupo3.PTrabajos.dto;

// RF-06: Personalizar perfiles de usuarios (datos generales del estudiante)
public class ActualizarPerfilEstudianteRequest {
    private String descripcion;
    private String nivelEducativo;
    private String telefono;

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getNivelEducativo() {
        return nivelEducativo;
    }

    public void setNivelEducativo(String nivelEducativo) {
        this.nivelEducativo = nivelEducativo;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }
}
