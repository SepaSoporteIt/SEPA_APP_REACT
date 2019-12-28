package com.mycompany.myapp.domain;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;

/**
 * A Study.
 */
@Entity
@Table(name = "study")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "study")
public class Study implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @org.springframework.data.elasticsearch.annotations.Field(type = FieldType.Keyword)
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @NotNull
    @Column(name = "resolution", nullable = false)
    private String resolution;

    @NotNull
    @Column(name = "legislation", nullable = false)
    private String legislation;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Study name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getResolution() {
        return resolution;
    }

    public Study resolution(String resolution) {
        this.resolution = resolution;
        return this;
    }

    public void setResolution(String resolution) {
        this.resolution = resolution;
    }

    public String getLegislation() {
        return legislation;
    }

    public Study legislation(String legislation) {
        this.legislation = legislation;
        return this;
    }

    public void setLegislation(String legislation) {
        this.legislation = legislation;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Study)) {
            return false;
        }
        return id != null && id.equals(((Study) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Study{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", resolution='" + getResolution() + "'" +
            ", legislation='" + getLegislation() + "'" +
            "}";
    }
}
