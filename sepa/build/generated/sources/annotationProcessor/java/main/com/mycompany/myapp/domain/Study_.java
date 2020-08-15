package com.mycompany.myapp.domain;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Study.class)
public abstract class Study_ {

	public static volatile SingularAttribute<Study, String> name;
	public static volatile SingularAttribute<Study, Long> id;
	public static volatile SingularAttribute<Study, String> resolution;
	public static volatile SingularAttribute<Study, String> legislation;

	public static final String NAME = "name";
	public static final String ID = "id";
	public static final String RESOLUTION = "resolution";
	public static final String LEGISLATION = "legislation";

}

