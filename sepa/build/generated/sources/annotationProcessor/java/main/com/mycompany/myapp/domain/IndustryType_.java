package com.mycompany.myapp.domain;

import java.time.Instant;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(IndustryType.class)
public abstract class IndustryType_ {

	public static volatile SingularAttribute<IndustryType, Instant> createdAt;
	public static volatile SingularAttribute<IndustryType, String> name;
	public static volatile SingularAttribute<IndustryType, Long> id;
	public static volatile SingularAttribute<IndustryType, String> ciiu;
	public static volatile SingularAttribute<IndustryType, Instant> updatedAt;

	public static final String CREATED_AT = "createdAt";
	public static final String NAME = "name";
	public static final String ID = "id";
	public static final String CIIU = "ciiu";
	public static final String UPDATED_AT = "updatedAt";

}

