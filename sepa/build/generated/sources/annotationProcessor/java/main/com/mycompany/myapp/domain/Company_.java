package com.mycompany.myapp.domain;

import java.time.Instant;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Company.class)
public abstract class Company_ {

	public static volatile SingularAttribute<Company, String> betweenStreets;
	public static volatile SingularAttribute<Company, String> postalCode;
	public static volatile SingularAttribute<Company, Boolean> isSubscripted;
	public static volatile SingularAttribute<Company, String> internalTlf;
	public static volatile SingularAttribute<Company, Employee> employee;
	public static volatile SingularAttribute<Company, Instant> createdAt;
	public static volatile SingularAttribute<Company, IndustryType> primIndustryTipe;
	public static volatile SingularAttribute<Company, String> habPrim;
	public static volatile SingularAttribute<Company, String> contact;
	public static volatile SingularAttribute<Company, String> cellphone;
	public static volatile SingularAttribute<Company, Long> id;
	public static volatile SingularAttribute<Company, Boolean> isDisabled;
	public static volatile SingularAttribute<Company, String> floor;
	public static volatile SingularAttribute<Company, String> email;
	public static volatile SingularAttribute<Company, String> departament;
	public static volatile SingularAttribute<Company, Instant> updatedAt;
	public static volatile SingularAttribute<Company, Integer> visitsQtyMin;
	public static volatile SingularAttribute<Company, IndustryType> secIndustryTipe;
	public static volatile SingularAttribute<Company, Localidadandpartido> partidoId;
	public static volatile SingularAttribute<Company, String> tlf;
	public static volatile SingularAttribute<Company, Integer> visitsQtyMax;
	public static volatile SingularAttribute<Company, String> addressDirection;
	public static volatile SingularAttribute<Company, String> cuit;
	public static volatile SingularAttribute<Company, String> habSec;
	public static volatile SingularAttribute<Company, String> name;
	public static volatile SingularAttribute<Company, String> addressNumber;
	public static volatile SingularAttribute<Company, String> comment;
	public static volatile SingularAttribute<Company, String> fantasyName;
	public static volatile SingularAttribute<Company, Localidadandpartido> localidadId;

	public static final String BETWEEN_STREETS = "betweenStreets";
	public static final String POSTAL_CODE = "postalCode";
	public static final String IS_SUBSCRIPTED = "isSubscripted";
	public static final String INTERNAL_TLF = "internalTlf";
	public static final String EMPLOYEE = "employee";
	public static final String CREATED_AT = "createdAt";
	public static final String PRIM_INDUSTRY_TIPE = "primIndustryTipe";
	public static final String HAB_PRIM = "habPrim";
	public static final String CONTACT = "contact";
	public static final String CELLPHONE = "cellphone";
	public static final String ID = "id";
	public static final String IS_DISABLED = "isDisabled";
	public static final String FLOOR = "floor";
	public static final String EMAIL = "email";
	public static final String DEPARTAMENT = "departament";
	public static final String UPDATED_AT = "updatedAt";
	public static final String VISITS_QTY_MIN = "visitsQtyMin";
	public static final String SEC_INDUSTRY_TIPE = "secIndustryTipe";
	public static final String PARTIDO_ID = "partidoId";
	public static final String TLF = "tlf";
	public static final String VISITS_QTY_MAX = "visitsQtyMax";
	public static final String ADDRESS_DIRECTION = "addressDirection";
	public static final String CUIT = "cuit";
	public static final String HAB_SEC = "habSec";
	public static final String NAME = "name";
	public static final String ADDRESS_NUMBER = "addressNumber";
	public static final String COMMENT = "comment";
	public static final String FANTASY_NAME = "fantasyName";
	public static final String LOCALIDAD_ID = "localidadId";

}

