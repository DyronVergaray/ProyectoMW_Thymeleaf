package Grupo3.PTrabajos.dto;

import jakarta.validation.constraints.NotBlank;

// RF-06: agregar un integrante al "Personal principal" de la empresa
public class PersonalEmpresaRequest {

    @NotBlank(message = "El nombre es obligatorio")
    private String nombre;

    @NotBlank(message = "El cargo es obligatorio")
    private String cargo;

    // Ruta/URL de la foto de este integrante (ej: "/img/logo.png").
    // Si no se envia, la vista usa una foto por defecto.
    private String foto;

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }
}
