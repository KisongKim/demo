package com.example.demo.model;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.PreRemove;
import java.time.LocalDateTime;

/**
 * Base abstract class for entities that support soft delete.
 *
 * @author Kisong
 */
@MappedSuperclass
public abstract class AbstractSupportSoftDeleteEntity {

    @Column(name = "DELETED", nullable = false)
    protected Boolean deleted;

    @Column(name = "DELETED_TIME")
    protected LocalDateTime deletedTime;

    @PreRemove
    public void preRemove() {
        deleted = true;
        deletedTime = LocalDateTime.now();
    }

}
