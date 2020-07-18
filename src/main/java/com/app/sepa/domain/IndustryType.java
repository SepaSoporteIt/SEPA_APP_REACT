package com.app.sepa.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

/**
 * A IndustryType.
 */
@Entity
@Table(name = "industry_type")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class IndustryType implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @NotNull
    @Column(name = "ciiu", nullable = false)
    private String ciiu;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public IndustryType name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCiiu() {
        return ciiu;
    }

    public IndustryType ciiu(String ciiu) {
        this.ciiu = ciiu;
        return this;
    }

    public void setCiiu(String ciiu) {
        this.ciiu = ciiu;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof IndustryType)) {
            return false;
        }
        return id != null && id.equals(((IndustryType) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "IndustryType{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", ciiu='" + getCiiu() + "'" +
            "}";
    }
}
