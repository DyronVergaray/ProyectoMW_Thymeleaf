package Grupo3.PTrabajos.dto;

import java.time.LocalDate;
import java.util.List;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

// RF-04: Anuncios de trabajo (creacion de la oferta)
public class OfertaRequest {

    @NotNull(message = "El id de la empresa es obligatorio")
    private Long idEmpresa;

    @NotBlank(message = "El puesto es obligatorio")
    private String puesto;

    private Double sueldo;
    private Integer horas;
    private String frecuenciaPagos;
    private String bonos;
    private String gratificaciones;
    private Boolean seguroVida;
    private String vacaciones;
    private String otros;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;

    // Nombres de los requisitos (RF-05 filtra ofertas por estos requisitos)
    private List<String> requisitos;

    public Long getIdEmpresa() {
        return idEmpresa;
    }

    public void setIdEmpresa(Long idEmpresa) {
        this.idEmpresa = idEmpresa;
    }

    public String getPuesto() {
        return puesto;
    }

    public void setPuesto(String puesto) {
        this.puesto = puesto;
    }

    public Double getSueldo() {
        return sueldo;
    }

    public void setSueldo(Double sueldo) {
        this.sueldo = sueldo;
    }

    public Integer getHoras() {
        return horas;
    }

    public void setHoras(Integer horas) {
        this.horas = horas;
    }

    public String getFrecuenciaPagos() {
        return frecuenciaPagos;
    }

    public void setFrecuenciaPagos(String frecuenciaPagos) {
        this.frecuenciaPagos = frecuenciaPagos;
    }

    public String getBonos() {
        return bonos;
    }

    public void setBonos(String bonos) {
        this.bonos = bonos;
    }

    public String getGratificaciones() {
        return gratificaciones;
    }

    public void setGratificaciones(String gratificaciones) {
        this.gratificaciones = gratificaciones;
    }

    public Boolean getSeguroVida() {
        return seguroVida;
    }

    public void setSeguroVida(Boolean seguroVida) {
        this.seguroVida = seguroVida;
    }

    public String getVacaciones() {
        return vacaciones;
    }

    public void setVacaciones(String vacaciones) {
        this.vacaciones = vacaciones;
    }

    public String getOtros() {
        return otros;
    }

    public void setOtros(String otros) {
        this.otros = otros;
    }

    public LocalDate getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(LocalDate fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public LocalDate getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(LocalDate fechaFin) {
        this.fechaFin = fechaFin;
    }

    public List<String> getRequisitos() {
        return requisitos;
    }

    public void setRequisitos(List<String> requisitos) {
        this.requisitos = requisitos;
    }
}
