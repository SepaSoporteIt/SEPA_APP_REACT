package com.mycompany.myapp.domain;

import com.mycompany.myapp.domain.enumeration.Status;
import java.time.LocalDate;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Expiration.class)
public abstract class Expiration_ {

	public static volatile SingularAttribute<Expiration, Study> study;
	public static volatile SingularAttribute<Expiration, String> comments;
	public static volatile SingularAttribute<Expiration, String> uniqueCode;
	public static volatile SingularAttribute<Expiration, LocalDate> endDate;
	public static volatile SingularAttribute<Expiration, String> responsible;
	public static volatile SingularAttribute<Expiration, Company> company;
	public static volatile SingularAttribute<Expiration, Long> id;
	public static volatile SingularAttribute<Expiration, Employee> employee;
	public static volatile SingularAttribute<Expiration, LocalDate> startDate;
	public static volatile SingularAttribute<Expiration, Status> status;
	public static volatile SingularAttribute<Expiration, Boolean> isCompleted;

	public static final String STUDY = "study";
	public static final String COMMENTS = "comments";
	public static final String UNIQUE_CODE = "uniqueCode";
	public static final String END_DATE = "endDate";
	public static final String RESPONSIBLE = "responsible";
	public static final String COMPANY = "company";
	public static final String ID = "id";
	public static final String EMPLOYEE = "employee";
	public static final String START_DATE = "startDate";
	public static final String STATUS = "status";
	public static final String IS_COMPLETED = "isCompleted";

}

