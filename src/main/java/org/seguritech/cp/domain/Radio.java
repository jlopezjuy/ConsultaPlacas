package org.seguritech.cp.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;


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
    private Long id;

    @NotNull
    @Column(name = "descripcion", nullable = false)
    private String descripcion;

    @NotNull
    @Column(name = "tipo_de_radio", nullable = false)
    private String tipoDeRadio;

    @NotNull
    @Column(name = "permiso", nullable = false)
    private String permiso;

    @ManyToOne(optional = false)
    @NotNull
    private Marca marca;

    @ManyToOne(optional = false)
    @NotNull
    private Municipio municipio;

    @ManyToOne(optional = false)
    @NotNull
    private Corporacion corporacion;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getTipoDeRadio() {
        return tipoDeRadio;
    }

    public Radio tipoDeRadio(String tipoDeRadio) {
        this.tipoDeRadio = tipoDeRadio;
        return this;
    }

    public void setTipoDeRadio(String tipoDeRadio) {
        this.tipoDeRadio = tipoDeRadio;
    }

    public String getPermiso() {
        return permiso;
    }

    public Radio permiso(String permiso) {
        this.permiso = permiso;
        return this;
    }

    public void setPermiso(String permiso) {
        this.permiso = permiso;
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
        if (radio.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), radio.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Radio{" +
            "id=" + getId() +
            ", descripcion='" + getDescripcion() + "'" +
            ", tipoDeRadio='" + getTipoDeRadio() + "'" +
            ", permiso='" + getPermiso() + "'" +
            "}";
    }
}
