package com.app.sepa.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;

/**
 * A Company.
 */
@Entity
@Table(name = "company")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Company implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "email")
    private String email;

    @NotNull
    @Column(name = "address_direction", nullable = false)
    private String addressDirection;

    @NotNull
    @Column(name = "address_number", nullable = false)
    private String addressNumber;

    @Column(name = "floor")
    private String floor;

    @Column(name = "departament")
    private String departament;

    @NotNull
    @Column(name = "cuit", nullable = false)
    private String cuit;

    @Column(name = "is_subscripted")
    private Boolean isSubscripted;

    @Column(name = "fantasy_name")
    private String fantasyName;

    @Column(name = "postal_code")
    private String postalCode;

    @Column(name = "tlf")
    private String tlf;

    @Column(name = "internal_tlf")
    private String internalTlf;

    @Column(name = "contact")
    private String contact;

    @Column(name = "cellphone")
    private String cellphone;

    @Column(name = "visits_qty_min")
    private Integer visitsQtyMin;

    @Column(name = "visits_qty_max")
    private Integer visitsQtyMax;

    @Column(name = "hab_prim")
    private String habPrim;

    @Column(name = "hab_sec")
    private String habSec;

    @Lob
    @Column(name = "comment")
    private String comment;

    @Column(name = "legislation_id")
    private Integer legislationId;

    @Column(name = "solicitador_id")
    private Integer solicitadorId;

    @Column(name = "ambito_id")
    private Integer ambitoId;

    @Column(name = "autoridad_id")
    private Integer autoridadId;

    @Column(name = "frecuency_type_id")
    private Integer frecuencyTypeId;

    @Column(name = "created_at")
    private Instant createdAt;

    @Column(name = "updated_at")
    private Instant updatedAt;

    @OneToOne
    @JoinColumn(unique = true)
    private Employee employee;

    @OneToOne
    @JoinColumn(unique = true)
    private IndustryType primIndustryTipe;

    @OneToOne
    @JoinColumn(unique = true)
    private IndustryType secIndustryTipe;

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

    public Company name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public Company email(String email) {
        this.email = email;
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddressDirection() {
        return addressDirection;
    }

    public Company addressDirection(String addressDirection) {
        this.addressDirection = addressDirection;
        return this;
    }

    public void setAddressDirection(String addressDirection) {
        this.addressDirection = addressDirection;
    }

    public String getAddressNumber() {
        return addressNumber;
    }

    public Company addressNumber(String addressNumber) {
        this.addressNumber = addressNumber;
        return this;
    }

    public void setAddressNumber(String addressNumber) {
        this.addressNumber = addressNumber;
    }

    public String getFloor() {
        return floor;
    }

    public Company floor(String floor) {
        this.floor = floor;
        return this;
    }

    public void setFloor(String floor) {
        this.floor = floor;
    }

    public String getDepartament() {
        return departament;
    }

    public Company departament(String departament) {
        this.departament = departament;
        return this;
    }

    public void setDepartament(String departament) {
        this.departament = departament;
    }

    public String getCuit() {
        return cuit;
    }

    public Company cuit(String cuit) {
        this.cuit = cuit;
        return this;
    }

    public void setCuit(String cuit) {
        this.cuit = cuit;
    }

    public Boolean isIsSubscripted() {
        return isSubscripted;
    }

    public Company isSubscripted(Boolean isSubscripted) {
        this.isSubscripted = isSubscripted;
        return this;
    }

    public void setIsSubscripted(Boolean isSubscripted) {
        this.isSubscripted = isSubscripted;
    }

    public String getFantasyName() {
        return fantasyName;
    }

    public Company fantasyName(String fantasyName) {
        this.fantasyName = fantasyName;
        return this;
    }

    public void setFantasyName(String fantasyName) {
        this.fantasyName = fantasyName;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public Company postalCode(String postalCode) {
        this.postalCode = postalCode;
        return this;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getTlf() {
        return tlf;
    }

    public Company tlf(String tlf) {
        this.tlf = tlf;
        return this;
    }

    public void setTlf(String tlf) {
        this.tlf = tlf;
    }

    public String getInternalTlf() {
        return internalTlf;
    }

    public Company internalTlf(String internalTlf) {
        this.internalTlf = internalTlf;
        return this;
    }

    public void setInternalTlf(String internalTlf) {
        this.internalTlf = internalTlf;
    }

    public String getContact() {
        return contact;
    }

    public Company contact(String contact) {
        this.contact = contact;
        return this;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getCellphone() {
        return cellphone;
    }

    public Company cellphone(String cellphone) {
        this.cellphone = cellphone;
        return this;
    }

    public void setCellphone(String cellphone) {
        this.cellphone = cellphone;
    }

    public Integer getVisitsQtyMin() {
        return visitsQtyMin;
    }

    public Company visitsQtyMin(Integer visitsQtyMin) {
        this.visitsQtyMin = visitsQtyMin;
        return this;
    }

    public void setVisitsQtyMin(Integer visitsQtyMin) {
        this.visitsQtyMin = visitsQtyMin;
    }

    public Integer getVisitsQtyMax() {
        return visitsQtyMax;
    }

    public Company visitsQtyMax(Integer visitsQtyMax) {
        this.visitsQtyMax = visitsQtyMax;
        return this;
    }

    public void setVisitsQtyMax(Integer visitsQtyMax) {
        this.visitsQtyMax = visitsQtyMax;
    }

    public String getHabPrim() {
        return habPrim;
    }

    public Company habPrim(String habPrim) {
        this.habPrim = habPrim;
        return this;
    }

    public void setHabPrim(String habPrim) {
        this.habPrim = habPrim;
    }

    public String getHabSec() {
        return habSec;
    }

    public Company habSec(String habSec) {
        this.habSec = habSec;
        return this;
    }

    public void setHabSec(String habSec) {
        this.habSec = habSec;
    }

    public String getComment() {
        return comment;
    }

    public Company comment(String comment) {
        this.comment = comment;
        return this;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Integer getLegislationId() {
        return legislationId;
    }

    public Company legislationId(Integer legislationId) {
        this.legislationId = legislationId;
        return this;
    }

    public void setLegislationId(Integer legislationId) {
        this.legislationId = legislationId;
    }

    public Integer getSolicitadorId() {
        return solicitadorId;
    }

    public Company solicitadorId(Integer solicitadorId) {
        this.solicitadorId = solicitadorId;
        return this;
    }

    public void setSolicitadorId(Integer solicitadorId) {
        this.solicitadorId = solicitadorId;
    }

    public Integer getAmbitoId() {
        return ambitoId;
    }

    public Company ambitoId(Integer ambitoId) {
        this.ambitoId = ambitoId;
        return this;
    }

    public void setAmbitoId(Integer ambitoId) {
        this.ambitoId = ambitoId;
    }

    public Integer getAutoridadId() {
        return autoridadId;
    }

    public Company autoridadId(Integer autoridadId) {
        this.autoridadId = autoridadId;
        return this;
    }

    public void setAutoridadId(Integer autoridadId) {
        this.autoridadId = autoridadId;
    }

    public Integer getFrecuencyTypeId() {
        return frecuencyTypeId;
    }

    public Company frecuencyTypeId(Integer frecuencyTypeId) {
        this.frecuencyTypeId = frecuencyTypeId;
        return this;
    }

    public void setFrecuencyTypeId(Integer frecuencyTypeId) {
        this.frecuencyTypeId = frecuencyTypeId;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public Company createdAt(Instant createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public Company updatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
        return this;
    }

    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Employee getEmployee() {
        return employee;
    }

    public Company employee(Employee employee) {
        this.employee = employee;
        return this;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public IndustryType getPrimIndustryTipe() {
        return primIndustryTipe;
    }

    public Company primIndustryTipe(IndustryType industryType) {
        this.primIndustryTipe = industryType;
        return this;
    }

    public void setPrimIndustryTipe(IndustryType industryType) {
        this.primIndustryTipe = industryType;
    }

    public IndustryType getSecIndustryTipe() {
        return secIndustryTipe;
    }

    public Company secIndustryTipe(IndustryType industryType) {
        this.secIndustryTipe = industryType;
        return this;
    }

    public void setSecIndustryTipe(IndustryType industryType) {
        this.secIndustryTipe = industryType;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Company)) {
            return false;
        }
        return id != null && id.equals(((Company) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Company{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", email='" + getEmail() + "'" +
            ", addressDirection='" + getAddressDirection() + "'" +
            ", addressNumber='" + getAddressNumber() + "'" +
            ", floor='" + getFloor() + "'" +
            ", departament='" + getDepartament() + "'" +
            ", cuit='" + getCuit() + "'" +
            ", isSubscripted='" + isIsSubscripted() + "'" +
            ", fantasyName='" + getFantasyName() + "'" +
            ", postalCode='" + getPostalCode() + "'" +
            ", tlf='" + getTlf() + "'" +
            ", internalTlf='" + getInternalTlf() + "'" +
            ", contact='" + getContact() + "'" +
            ", cellphone='" + getCellphone() + "'" +
            ", visitsQtyMin=" + getVisitsQtyMin() +
            ", visitsQtyMax=" + getVisitsQtyMax() +
            ", habPrim='" + getHabPrim() + "'" +
            ", habSec='" + getHabSec() + "'" +
            ", comment='" + getComment() + "'" +
            ", legislationId=" + getLegislationId() +
            ", solicitadorId=" + getSolicitadorId() +
            ", ambitoId=" + getAmbitoId() +
            ", autoridadId=" + getAutoridadId() +
            ", frecuencyTypeId=" + getFrecuencyTypeId() +
            ", createdAt='" + getCreatedAt() + "'" +
            ", updatedAt='" + getUpdatedAt() + "'" +
            "}";
    }
}
