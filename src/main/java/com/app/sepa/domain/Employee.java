package com.app.sepa.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

/**
 * A Employee.
 */
@Entity
@Table(name = "employee")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Employee implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @NotNull
    @Column(name = "surname", nullable = false)
    private String surname;

    @Column(name = "email")
    private String email;

    @Column(name = "tlf")
    private String tlf;

    @Column(name = "is_internal")
    private Boolean isInternal;

    @NotNull
    @Column(name = "mat_number", nullable = false)
    private String matNumber;

    @NotNull
    @Column(name = "cuit", nullable = false)
    private String cuit;

    @Column(name = "address_direction")
    private String addressDirection;

    @Column(name = "address_number")
    private String addressNumber;

    @Column(name = "floor")
    private String floor;

    @Column(name = "departament")
    private String departament;

    @Column(name = "degree")
    private String degree;

    @Column(name = "especializacion")
    private String especializacion;

    @Column(name = "celular")
    private String celular;

    @Column(name = "comentario")
    private String comentario;

    @Column(name = "is_disabled")
    private Boolean isDisabled;

    @OneToOne
    @JoinColumn(unique = true)
    private Company company;

    @OneToOne
    @JoinColumn(unique = true)
    private LocalidadAndPartido localidadId;

    @OneToOne
    @JoinColumn(unique = true)
    private LocalidadAndPartido partidoId;

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

    public Employee name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public Employee surname(String surname) {
        this.surname = surname;
        return this;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getEmail() {
        return email;
    }

    public Employee email(String email) {
        this.email = email;
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTlf() {
        return tlf;
    }

    public Employee tlf(String tlf) {
        this.tlf = tlf;
        return this;
    }

    public void setTlf(String tlf) {
        this.tlf = tlf;
    }

    public Boolean isIsInternal() {
        return isInternal;
    }

    public Employee isInternal(Boolean isInternal) {
        this.isInternal = isInternal;
        return this;
    }

    public void setIsInternal(Boolean isInternal) {
        this.isInternal = isInternal;
    }

    public String getMatNumber() {
        return matNumber;
    }

    public Employee matNumber(String matNumber) {
        this.matNumber = matNumber;
        return this;
    }

    public void setMatNumber(String matNumber) {
        this.matNumber = matNumber;
    }

    public String getCuit() {
        return cuit;
    }

    public Employee cuit(String cuit) {
        this.cuit = cuit;
        return this;
    }

    public void setCuit(String cuit) {
        this.cuit = cuit;
    }

    public String getAddressDirection() {
        return addressDirection;
    }

    public Employee addressDirection(String addressDirection) {
        this.addressDirection = addressDirection;
        return this;
    }

    public void setAddressDirection(String addressDirection) {
        this.addressDirection = addressDirection;
    }

    public String getAddressNumber() {
        return addressNumber;
    }

    public Employee addressNumber(String addressNumber) {
        this.addressNumber = addressNumber;
        return this;
    }

    public void setAddressNumber(String addressNumber) {
        this.addressNumber = addressNumber;
    }

    public String getFloor() {
        return floor;
    }

    public Employee floor(String floor) {
        this.floor = floor;
        return this;
    }

    public void setFloor(String floor) {
        this.floor = floor;
    }

    public String getDepartament() {
        return departament;
    }

    public Employee departament(String departament) {
        this.departament = departament;
        return this;
    }

    public void setDepartament(String departament) {
        this.departament = departament;
    }

    public String getDegree() {
        return degree;
    }

    public Employee degree(String degree) {
        this.degree = degree;
        return this;
    }

    public void setDegree(String degree) {
        this.degree = degree;
    }

    public String getEspecializacion() {
        return especializacion;
    }

    public Employee especializacion(String especializacion) {
        this.especializacion = especializacion;
        return this;
    }

    public void setEspecializacion(String especializacion) {
        this.especializacion = especializacion;
    }

    public String getCelular() {
        return celular;
    }

    public Employee celular(String celular) {
        this.celular = celular;
        return this;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public String getComentario() {
        return comentario;
    }

    public Employee comentario(String comentario) {
        this.comentario = comentario;
        return this;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public Boolean isIsDisabled() {
        return isDisabled;
    }

    public Employee isDisabled(Boolean isDisabled) {
        this.isDisabled = isDisabled;
        return this;
    }

    public void setIsDisabled(Boolean isDisabled) {
        this.isDisabled = isDisabled;
    }

    public Company getCompany() {
        return company;
    }

    public Employee company(Company company) {
        this.company = company;
        return this;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public LocalidadAndPartido getLocalidadId() {
        return localidadId;
    }

    public Employee localidadId(LocalidadAndPartido localidadAndPartido) {
        this.localidadId = localidadAndPartido;
        return this;
    }

    public void setLocalidadId(LocalidadAndPartido localidadAndPartido) {
        this.localidadId = localidadAndPartido;
    }

    public LocalidadAndPartido getPartidoId() {
        return partidoId;
    }

    public Employee partidoId(LocalidadAndPartido localidadAndPartido) {
        this.partidoId = localidadAndPartido;
        return this;
    }

    public void setPartidoId(LocalidadAndPartido localidadAndPartido) {
        this.partidoId = localidadAndPartido;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Employee)) {
            return false;
        }
        return id != null && id.equals(((Employee) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Employee{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", surname='" + getSurname() + "'" +
            ", email='" + getEmail() + "'" +
            ", tlf='" + getTlf() + "'" +
            ", isInternal='" + isIsInternal() + "'" +
            ", matNumber='" + getMatNumber() + "'" +
            ", cuit='" + getCuit() + "'" +
            ", addressDirection='" + getAddressDirection() + "'" +
            ", addressNumber='" + getAddressNumber() + "'" +
            ", floor='" + getFloor() + "'" +
            ", departament='" + getDepartament() + "'" +
            ", degree='" + getDegree() + "'" +
            ", especializacion='" + getEspecializacion() + "'" +
            ", celular='" + getCelular() + "'" +
            ", comentario='" + getComentario() + "'" +
            ", isDisabled='" + isIsDisabled() + "'" +
            "}";
    }
}
