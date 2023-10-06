package com.generic.core.base.data;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

/**
 * The type Auditable entity.
 */
@Data
@MappedSuperclass
@EqualsAndHashCode( callSuper = true )
public class AuditableEntity extends BaseEntity {

    /**
     * The Modified by.
     */
    @Column
    protected Long modifiedBy = 0L;

    /**
     * The Modification date.
     */
    @Column
    @UpdateTimestamp
    protected LocalDateTime modifiedTime;

}
