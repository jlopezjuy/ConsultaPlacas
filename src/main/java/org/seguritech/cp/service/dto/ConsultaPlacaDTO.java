package org.seguritech.cp.service.dto;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the ConsultaPlaca entity.
 */
public class ConsultaPlacaDTO implements Serializable {

    private final Logger log = LoggerFactory.getLogger(ConsultaPlacaDTO.class);

    private Long id;

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

    private String radioResponsable;

    private String radioMunicipio;

    private String radioCorporacion;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getFecha() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        log.info(fecha.toString());
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

    public String getRadioResponsable() {
        return radioResponsable;
    }

    public void setRadioResponsable(String radioResponsable) {
        this.radioResponsable = radioResponsable;
    }

    public String getRadioMunicipio() {
        return radioMunicipio;
    }

    public void setRadioMunicipio(String radioMunicipio) {
        this.radioMunicipio = radioMunicipio;
    }

    public String getRadioCorporacion() {
        return radioCorporacion;
    }

    public void setRadioCorporacion(String radioCorporacion) {
        this.radioCorporacion = radioCorporacion;
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
            ", fecha='" + getFecha() + "'" +
            ", consulta='" + getConsulta() + "'" +
            ", metodo='" + getMetodo() + "'" +
            ", estado='" + isEstado() + "'" +
            ", resultado='" + getResultado() + "'" +
            ", coordenadas='" + getCoordenadas() + "'" +
            ", radio='" + getRadioIssi() + "'" +
            ", radio desc='" + getRadioDescripcion() + "'" +
            "}";
    }
}
