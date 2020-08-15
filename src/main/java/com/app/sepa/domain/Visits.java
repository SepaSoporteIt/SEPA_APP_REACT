package com.app.sepa.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * A Visits.
 */
@Entity
@Table(name = "visits")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Visits implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "employee")
    private String employee;

    @Column(name = "visit_date")
    private LocalDate visit_date;

    @Column(name = "comments")
    private String comments;

    @OneToOne(optional = false)
    @NotNull
    @JoinColumn(unique = true)
    private Company company;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmployee() {
        return employee;
    }

    public Visits employee(String employee) {
        this.employee = employee;
        return this;
    }

    public void setEmployee(String employee) {
        this.employee = employee;
    }

    public LocalDate getVisit_date() {
        return visit_date;
    }

    public Visits visit_date(LocalDate visit_date) {
        this.visit_date = visit_date;
        return this;
    }

    public void setVisit_date(LocalDate visit_date) {
        this.visit_date = visit_date;
    }

    public String getComments() {
        return comments;
    }

    public Visits comments(String comments) {
        this.comments = comments;
        return this;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public Company getCompany() {
        return company;
    }

    public Visits company(Company company) {
        this.company = company;
        return this;
    }

    public void setCompany(Company company) {
        this.company = company;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Visits)) {
            return false;
        }
        return id != null && id.equals(((Visits) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Visits{" +
            "id=" + getId() +
            ", employee='" + getEmployee() + "'" +
            ", visit_date='" + getVisit_date() + "'" +
            ", comments='" + getComments() + "'" +
            "}";
    }
}
