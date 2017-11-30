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

    @ManyToOne(optional = false)
    @NotNull
    private Marca marca;

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
            "}";
    }
}
