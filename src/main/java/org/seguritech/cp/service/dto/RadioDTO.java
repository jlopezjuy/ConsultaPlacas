package org.seguritech.cp.service.dto;


import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the Radio entity.
 */
public class RadioDTO implements Serializable {

    private Long id;

    @NotNull
    private String descripcion;

    @NotNull
    private String tipoDeRadio;

    private Long marcaId;

    private String marcaDescripcion;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getTipoDeRadio() {
        return tipoDeRadio;
    }

    public void setTipoDeRadio(String tipoDeRadio) {
        this.tipoDeRadio = tipoDeRadio;
    }

    public Long getMarcaId() {
        return marcaId;
    }

    public void setMarcaId(Long marcaId) {
        this.marcaId = marcaId;
    }

    public String getMarcaDescripcion() {
        return marcaDescripcion;
    }

    public void setMarcaDescripcion(String marcaDescripcion) {
        this.marcaDescripcion = marcaDescripcion;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        RadioDTO radioDTO = (RadioDTO) o;
        if(radioDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), radioDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "RadioDTO{" +
            "id=" + getId() +
            ", descripcion='" + getDescripcion() + "'" +
            ", tipoDeRadio='" + getTipoDeRadio() + "'" +
            "}";
    }
}
