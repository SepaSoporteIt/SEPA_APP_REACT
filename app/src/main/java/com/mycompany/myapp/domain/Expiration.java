package com.mycompany.myapp.domain;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.Instant;

/**
 * A Expiration.
 */
@Entity
@Table(name = "expiration")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Expiration implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "company_id")
    private Integer companyId;

    @Column(name = "employee_id")
    private Integer employeeId;

    @Column(name = "study_id")
    private Integer studyId;

    @Column(name = "start_date")
    private Instant startDate;

    @Column(name = "end_date")
    private Instant endDate;

    @Column(name = "status")
    private String status;

    @Column(name = "comments")
    private Long comments;

    @OneToOne
    @JoinColumn(unique = true)
    private Company company;

    @OneToOne
    @JoinColumn(unique = true)
    private Employee employee;

    @OneToOne
    @JoinColumn(unique = true)
    private Study study;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public Expiration companyId(Integer companyId) {
        this.companyId = companyId;
        return this;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public Integer getEmployeeId() {
        return employeeId;
    }

    public Expiration employeeId(Integer employeeId) {
        this.employeeId = employeeId;
        return this;
    }

    public void setEmployeeId(Integer employeeId) {
        this.employeeId = employeeId;
    }

    public Integer getStudyId() {
        return studyId;
    }

    public Expiration studyId(Integer studyId) {
        this.studyId = studyId;
        return this;
    }

    public void setStudyId(Integer studyId) {
        this.studyId = studyId;
    }

    public Instant getStartDate() {
        return startDate;
    }

    public Expiration startDate(Instant startDate) {
        this.startDate = startDate;
        return this;
    }

    public void setStartDate(Instant startDate) {
        this.startDate = startDate;
    }

    public Instant getEndDate() {
        return endDate;
    }

    public Expiration endDate(Instant endDate) {
        this.endDate = endDate;
        return this;
    }

    public void setEndDate(Instant endDate) {
        this.endDate = endDate;
    }

    public String getStatus() {
        return status;
    }

    public Expiration status(String status) {
        this.status = status;
        return this;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getComments() {
        return comments;
    }

    public Expiration comments(Long comments) {
        this.comments = comments;
        return this;
    }

    public void setComments(Long comments) {
        this.comments = comments;
    }

    public Company getCompany() {
        return company;
    }

    public Expiration company(Company company) {
        this.company = company;
        return this;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public Employee getEmployee() {
        return employee;
    }

    public Expiration employee(Employee employee) {
        this.employee = employee;
        return this;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public Study getStudy() {
        return study;
    }

    public Expiration study(Study study) {
        this.study = study;
        return this;
    }

    public void setStudy(Study study) {
        this.study = study;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Expiration)) {
            return false;
        }
        return id != null && id.equals(((Expiration) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Expiration{" +
            "id=" + getId() +
            ", companyId=" + getCompanyId() +
            ", employeeId=" + getEmployeeId() +
            ", studyId=" + getStudyId() +
            ", startDate='" + getStartDate() + "'" +
            ", endDate='" + getEndDate() + "'" +
            ", status='" + getStatus() + "'" +
            ", comments=" + getComments() +
            "}";
    }
}
