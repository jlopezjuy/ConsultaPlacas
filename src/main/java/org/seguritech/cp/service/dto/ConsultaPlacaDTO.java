package org.seguritech.cp.service.dto;


import java.time.LocalDate;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the ConsultaPlaca entity.
 */
public class ConsultaPlacaDTO implements Serializable {

    private Long id;

    @NotNull
    private String responsable;

    @NotNull
    private LocalDate fecha;

    @NotNull
    private String consulta;

    @NotNull
    private String metodo;

    @NotNull
    private Boolean estado;

    @NotNull
    private String resultado;

    @NotNull
    private String coordenadas;

    private Long radioIssi;

    private String radioDescripcion;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getResponsable() {
        return responsable;
    }

    public void setResponsable(String responsable) {
        this.responsable = responsable;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public String getConsulta() {
        return consulta;
    }

    public void setConsulta(String consulta) {
        this.consulta = consulta;
    }

    public String getMetodo() {
        return metodo;
    }

    public void setMetodo(String metodo) {
        this.metodo = metodo;
    }

    public Boolean isEstado() {
        return estado;
    }

    public Boolean getEstado(){
        return estado;
    }

    public void setEstado(Boolean estado) {
        this.estado = estado;
    }

    public String getResultado() {
        return resultado;
    }

    public void setResultado(String resultado) {
        this.resultado = resultado;
    }

    public String getCoordenadas() {
        return coordenadas;
    }

    public void setCoordenadas(String coordenadas) {
        this.coordenadas = coordenadas;
    }

    public Long getRadioIssi() {
        return radioIssi;
    }

    public void setRadioIssi(Long radioIssi) {
        this.radioIssi = radioIssi;
    }

    public String getRadioDescripcion() {
        return radioDescripcion;
    }

    public void setRadioDescripcion(String radioDescripcion) {
        this.radioDescripcion = radioDescripcion;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ConsultaPlacaDTO consultaPlacaDTO = (ConsultaPlacaDTO) o;
        if(consultaPlacaDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), consultaPlacaDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ConsultaPlacaDTO{" +
            "id=" + getId() +
            ", responsable='" + getResponsable() + "'" +
            ", fecha='" + getFecha() + "'" +
            ", consulta='" + getConsulta() + "'" +
            ", metodo='" + getMetodo() + "'" +
            ", estado='" + isEstado() + "'" +
            ", resultado='" + getResultado() + "'" +
            ", coordenadas='" + getCoordenadas() + "'" +
            "}";
    }
}
