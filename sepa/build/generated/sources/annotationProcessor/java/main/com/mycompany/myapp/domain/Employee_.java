package com.mycompany.myapp.domain;

import java.time.Instant;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Employee.class)
public abstract class Employee_ {

	public static volatile SingularAttribute<Employee, String> degree;
	public static volatile SingularAttribute<Employee, String> matNumber;
	public static volatile SingularAttribute<Employee, String> comentario;
	public static volatile SingularAttribute<Employee, Localidadandpartido> partidoId;
	public static volatile SingularAttribute<Employee, String> tlf;
	public static volatile SingularAttribute<Employee, Boolean> isInternal;
	public static volatile SingularAttribute<Employee, Instant> createdAt;
	public static volatile SingularAttribute<Employee, String> cuit;
	public static volatile SingularAttribute<Employee, String> addressDirection;
	public static volatile SingularAttribute<Employee, String> surname;
	public static volatile SingularAttribute<Employee, String> name;
	public static volatile SingularAttribute<Employee, String> addressNumber;
	public static volatile SingularAttribute<Employee, String> celular;
	public static volatile SingularAttribute<Employee, Long> id;
	public static volatile SingularAttribute<Employee, Boolean> isDisabled;
	public static volatile SingularAttribute<Employee, String> floor;
	public static volatile SingularAttribute<Employee, String> especializacion;
	public static volatile SingularAttribute<Employee, Localidadandpartido> localidadId;
	public static volatile SingularAttribute<Employee, String> email;
	public static volatile SingularAttribute<Employee, String> departament;
	public static volatile SingularAttribute<Employee, Instant> updatedAt;

	public static final String DEGREE = "degree";
	public static final String MAT_NUMBER = "matNumber";
	public static final String COMENTARIO = "comentario";
	public static final String PARTIDO_ID = "partidoId";
	public static final String TLF = "tlf";
	public static final String IS_INTERNAL = "isInternal";
	public static final String CREATED_AT = "createdAt";
	public static final String CUIT = "cuit";
	public static final String ADDRESS_DIRECTION = "addressDirection";
	public static final String SURNAME = "surname";
	public static final String NAME = "name";
	public static final String ADDRESS_NUMBER = "addressNumber";
	public static final String CELULAR = "celular";
	public static final String ID = "id";
	public static final String IS_DISABLED = "isDisabled";
	public static final String FLOOR = "floor";
	public static final String ESPECIALIZACION = "especializacion";
	public static final String LOCALIDAD_ID = "localidadId";
	public static final String EMAIL = "email";
	public static final String DEPARTAMENT = "departament";
	public static final String UPDATED_AT = "updatedAt";

}

