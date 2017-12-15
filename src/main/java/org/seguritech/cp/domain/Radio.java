package org.seguritech.cp.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

import org.seguritech.cp.domain.enumeration.Permiso;


/**
 * A Radio.
 */
@Entity
@Table(name = "radio")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Radio implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long issi;

    @NotNull
    @Column(name = "descripcion", nullable = false)
    private String descripcion;

    @Enumerated(EnumType.STRING)
    @Column(name = "permiso")
    private Permiso permiso;

    @NotNull
    @Column(name = "id_radio", nullable = false)
    private String idRadio;

    @ManyToOne(optional = false)
    @NotNull
    private Marca marca;

    @ManyToOne(optional = false)
    @NotNull
    private Municipio municipio;

    @ManyToOne(optional = false)
    @NotNull
    private Corporacion corporacion;

    @ManyToOne(optional = false)
    @NotNull
    private TipoRadio tipoRadio;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getIssi() {
        return issi;
    }

    public void setIssi(Long issi) {
        this.issi = issi;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public Radio descripcion(String descripcion) {
        this.descripcion = descripcion;
        return this;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Permiso getPermiso() {
        return permiso;
    }

    public Radio permiso(Permiso permiso) {
        this.permiso = permiso;
        return this;
    }

    public void setPermiso(Permiso permiso) {
        this.permiso = permiso;
    }

    public String getIdRadio() {
        return idRadio;
    }

    public Radio idRadio(String idRadio) {
        this.idRadio = idRadio;
        return this;
    }

    public void setIdRadio(String idRadio) {
        this.idRadio = idRadio;
    }

    public Marca getMarca() {
        return marca;
    }

    public Radio marca(Marca marca) {
        this.marca = marca;
        return this;
    }

    public void setMarca(Marca marca) {
        this.marca = marca;
    }

    public Municipio getMunicipio() {
        return municipio;
    }

    public Radio municipio(Municipio municipio) {
        this.municipio = municipio;
        return this;
    }

    public void setMunicipio(Municipio municipio) {
        this.municipio = municipio;
    }

    public Corporacion getCorporacion() {
        return corporacion;
    }

    public Radio corporacion(Corporacion corporacion) {
        this.corporacion = corporacion;
        return this;
    }

    public void setCorporacion(Corporacion corporacion) {
        this.corporacion = corporacion;
    }

    public TipoRadio getTipoRadio() {
        return tipoRadio;
    }

    public Radio tipoRadio(TipoRadio tipoRadio) {
        this.tipoRadio = tipoRadio;
        return this;
    }

    public void setTipoRadio(TipoRadio tipoRadio) {
        this.tipoRadio = tipoRadio;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Radio radio = (Radio) o;
        if (radio.getIssi() == null || getIssi() == null) {
            return false;
        }
        return Objects.equals(getIssi(), radio.getIssi());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getIssi());
    }

    @Override
    public String toString() {
        return "Radio{" +
            "id=" + getIssi() +
            ", descripcion='" + getDescripcion() + "'" +
            ", permiso='" + getPermiso() + "'" +
            ", idRadio='" + getIdRadio() + "'" +
            "}";
    }
}
