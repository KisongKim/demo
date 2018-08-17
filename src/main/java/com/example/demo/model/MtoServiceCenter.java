package com.example.demo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.Where;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreRemove;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import java.time.LocalDateTime;

/**
 * @author Kisong
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = {"mto"})
@EqualsAndHashCode(exclude = {"mto"})
@Entity
@Table(name = "MTO_SERVICE_CENTER", indexes = {
        @Index(columnList = "SERVICE_CENTER_CODE", name = "MTO_SERVICE_CENTER_IDX1", unique = true)
})
@Where(clause = "deleted = false")
public class MtoServiceCenter extends AbstractSupportSoftDeleteEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @ManyToOne(targetEntity = Mto.class)
    @JoinColumn(name = "MTO_ID", referencedColumnName = "ID", nullable = false, updatable = false)
    private Mto mto;

    @Column(name = "DISPLAY_ORDER", nullable = false)
    private Integer displayOrder;

    @Column(name = "SERVICE_CENTER_CODE", length = 10, nullable = false, unique = true, updatable = false)
    private String serviceCenterCode;

    @Column(name = "LOCAL_CODE", length = 20, updatable = false)
    private String localCode;

    @Column(name = "DISPLAY_NAME", length = 100, nullable = false)
    private String displayName;

    @Column(name = "LOCAL_NAME", length = 200, updatable = false)
    private String localName;

    @Column(name = "MODIFIED_TIME", nullable = false)
    private LocalDateTime modifiedTime;

    @Column(name = "CREATED_TIME", nullable = false, updatable = false)
    private LocalDateTime createdTime;

    @PrePersist
    public void prePersist() {
        LocalDateTime now = LocalDateTime.now();
        modifiedTime = now;
        createdTime = now;
    }

    @PreUpdate
    public void preUpdate() {
        modifiedTime = LocalDateTime.now();
    }

}
