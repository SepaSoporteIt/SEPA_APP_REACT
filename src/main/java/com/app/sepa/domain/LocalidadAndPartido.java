package com.app.sepa.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;

/**
 * A LocalidadAndPartido.
 */
@Entity
@Table(name = "localidad_and_partido")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class LocalidadAndPartido implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "localidad")
    private String localidad;

    @Column(name = "partido")
    private String partido;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLocalidad() {
        return localidad;
    }

    public LocalidadAndPartido localidad(String localidad) {
        this.localidad = localidad;
        return this;
    }

    public void setLocalidad(String localidad) {
        this.localidad = localidad;
    }

    public String getPartido() {
        return partido;
    }

    public LocalidadAndPartido partido(String partido) {
        this.partido = partido;
        return this;
    }

    public void setPartido(String partido) {
        this.partido = partido;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof LocalidadAndPartido)) {
            return false;
        }
        return id != null && id.equals(((LocalidadAndPartido) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "LocalidadAndPartido{" +
            "id=" + getId() +
            ", localidad='" + getLocalidad() + "'" +
            ", partido='" + getPartido() + "'" +
            "}";
    }
}
