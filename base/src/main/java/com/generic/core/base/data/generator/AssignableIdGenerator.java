package com.generic.core.base.data.generator;

import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentityGenerator;
import com.generic.core.base.data.BaseEntity;

import java.io.Serializable;

/**
 * The type Assignable id generator.
 */
public class AssignableIdGenerator extends IdentityGenerator {

    @Override
    public Serializable generate( SharedSessionContractImplementor session, Object obj ) {
        if ( obj instanceof BaseEntity ) {
            BaseEntity baseEntity = ( BaseEntity ) obj;
            Serializable id = baseEntity.getId( );
            if ( id != null ) {
                return id;
            }
        }
        return super.generate( session, obj );
    }

}
