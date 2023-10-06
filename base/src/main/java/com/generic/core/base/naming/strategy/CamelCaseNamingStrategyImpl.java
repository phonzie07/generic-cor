package com.generic.core.base.naming.strategy;

import org.hibernate.boot.model.naming.Identifier;
import org.hibernate.engine.jdbc.env.spi.JdbcEnvironment;

import java.io.Serializable;

public class CamelCaseNamingStrategyImpl extends org.springframework.boot.orm.jpa.hibernate.SpringPhysicalNamingStrategy implements Serializable {

    public static final long serialVersionUID = 1L;

    @Override
    public Identifier toPhysicalColumnName(Identifier name, JdbcEnvironment jdbcEnvironment) {
        return jdbcEnvironment.getIdentifierHelper().toIdentifier(name.getText().toLowerCase(), name.isQuoted());
    }

    @Override
    public Identifier toPhysicalTableName(Identifier name, JdbcEnvironment jdbcEnvironment) {
        return jdbcEnvironment.getIdentifierHelper().toIdentifier(name.getText().toLowerCase(), name.isQuoted());
    }
}