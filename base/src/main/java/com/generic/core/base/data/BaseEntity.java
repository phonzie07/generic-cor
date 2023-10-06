package com.generic.core.base.data;

import lombok.Data;
import org.apache.poi.ss.formula.functions.T;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.time.LocalDateTime;

import static javax.persistence.GenerationType.IDENTITY;

/**
 * The type Base entity.
 */
@Data
@MappedSuperclass
public class BaseEntity<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * The Id.
     */
    @Id
    @Column( updatable = false, nullable = false )
    @GenericGenerator( name = "assigned-identity",
            strategy = "com.generic.core.base.data.generator.AssignableIdGenerator" )
    @GeneratedValue( generator = "assigned-identity", strategy = IDENTITY )
    protected Long id;

    /**
     * The Creator id.
     */
    @Column( updatable = false )
    protected Long createdBy = 0L;

    /**
     * The Created time.
     */
    @UpdateTimestamp
    @Column( updatable = false )
    protected LocalDateTime createdTime;

    public void updateEntity(T tmpEntity) throws IllegalAccessException {
        Field fields[] = this.getClass().getDeclaredFields();

        for (Field f : fields) {
            if (f.get(tmpEntity) != null) {
                f.set(this, f.get(tmpEntity));
            }
        }
    }

}
