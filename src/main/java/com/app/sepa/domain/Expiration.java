package com.app.sepa.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.LocalDate;

import com.app.sepa.domain.enumeration.Status;

/**
 * A Expiration.
 */
@Entity
@Table(name = "expiration")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Expiration implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private Status status;

    @Column(name = "comments")
    private String comments;

    @Column(name = "unique_code")
    private String uniqueCode;

    @Column(name = "responsible")
    private String responsible;

    @Column(name = "is_completed")
    private Boolean isCompleted;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = "expirations", allowSetters = true)
    private Company company;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = "expirations", allowSetters = true)
    private Employee employee;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = "expirations", allowSetters = true)
    private Study study;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public Expiration startDate(LocalDate startDate) {
        this.startDate = startDate;
        return this;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public Expiration endDate(LocalDate endDate) {
        this.endDate = endDate;
        return this;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public Status getStatus() {
        return status;
    }

    public Expiration status(Status status) {
        this.status = status;
        return this;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getComments() {
        return comments;
    }

    public Expiration comments(String comments) {
        this.comments = comments;
        return this;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getUniqueCode() {
        return uniqueCode;
    }

    public Expiration uniqueCode(String uniqueCode) {
        this.uniqueCode = uniqueCode;
        return this;
    }

    public void setUniqueCode(String uniqueCode) {
        this.uniqueCode = uniqueCode;
    }

    public String getResponsible() {
        return responsible;
    }

    public Expiration responsible(String responsible) {
        this.responsible = responsible;
        return this;
    }

    public void setResponsible(String responsible) {
        this.responsible = responsible;
    }

    public Boolean isIsCompleted() {
        return isCompleted;
    }

    public Expiration isCompleted(Boolean isCompleted) {
        this.isCompleted = isCompleted;
        return this;
    }

    public void setIsCompleted(Boolean isCompleted) {
        this.isCompleted = isCompleted;
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
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

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

    // prettier-ignore
    @Override
    public String toString() {
        return "Expiration{" +
            "id=" + getId() +
            ", startDate='" + getStartDate() + "'" +
            ", endDate='" + getEndDate() + "'" +
            ", status='" + getStatus() + "'" +
            ", comments='" + getComments() + "'" +
            ", uniqueCode='" + getUniqueCode() + "'" +
            ", responsible='" + getResponsible() + "'" +
            ", isCompleted='" + isIsCompleted() + "'" +
            "}";
    }

    public void checkDate()
    {
        LocalDate actualEndDate = getEndDate();
        if (actualEndDate == null)
            return;
            
        LocalDate actualWarningDate = actualEndDate.minusDays(30);
    
        Status status;
        status = getStatus();
        
        if (status == Status.VIGENTE || status == Status.A_VENCER)
            return;
        
        if (actualEndDate.isBefore(LocalDate.now()))
        {
            setStatus(Status.VENCIDO);
        }
        else if (actualWarningDate.isBefore(LocalDate.now()))
        {
            setStatus(Status.A_VENCER);
        }
    }

    public void generateUniqueCode()
    {
        Company company;
        Employee employee;
        Study study;
        LocalDate startDate;

        company = getCompany();
        employee = getEmployee();
        study = getStudy();
        startDate = getStartDate();
        
        if (company == null || employee == null || study == null || startDate == null)
        {
            setUniqueCode("Faltan datos");
            return;
        }
        
        String initialDate = startDate.toString();
        String companyId = company.getId().toString();
        String employeeId = employee.getId().toString();
        String studyId = study.getId().toString();
        
        setUniqueCode(companyId+"-"+employeeId+"-"+studyId+"-"+initialDate);
    }

    public void setExpirationResponsible()
    {
        Company company;
        Employee employee;

        company = getCompany();
        if (company == null)
            return;
        
        employee = company.getEmployee();
        if (employee == null)
            return;
        setResponsible(employee.getName() + " " + employee.getSurname());
    }
}
