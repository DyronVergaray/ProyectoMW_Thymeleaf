package Grupo3.PTrabajos.dto;

// RF-06: Personalizar perfiles de empresas (datos generales de la empresa,
// equivalente al ActualizarPerfilEstudianteRequest pero del lado EMPRESA).
// Con esto se completa lo que se muestra en la vista Thymeleaf "perfil.html":
// descripcion, mision, vision, foto de perfil, banner, direccion y horario
// de atencion.
public class ActualizarPerfilEmpresaRequest {
    private String descripcion;
    private String mision;
    private String vision;
    private String fotoPerfil;
    private String banner;
    private String direccion;
    private String horarioAtencion;

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getMision() {
        return mision;
    }

    public void setMision(String mision) {
        this.mision = mision;
    }

    public String getVision() {
        return vision;
    }

    public void setVision(String vision) {
        this.vision = vision;
    }

    public String getFotoPerfil() {
        return fotoPerfil;
    }

    public void setFotoPerfil(String fotoPerfil) {
        this.fotoPerfil = fotoPerfil;
    }

    public String getBanner() {
        return banner;
    }

    public void setBanner(String banner) {
        this.banner = banner;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getHorarioAtencion() {
        return horarioAtencion;
    }

    public void setHorarioAtencion(String horarioAtencion) {
        this.horarioAtencion = horarioAtencion;
    }
}
