package com.example.demo.model;

import com.example.demo.type.RemittanceOption;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.ResultCheckStyle;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Kisong
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = {"mtoServiceCenters"})
@EqualsAndHashCode(exclude = {"mtoServiceCenters"})
@Entity
@Table(name = "MTO", indexes = {
        @Index(columnList = "CODE", name = "MTO_IDX1"),
        @Index(columnList = "COUNTRY", name = "MTO_IDX2")
})
@Where(clause = "deleted = false")
public class Mto extends AbstractSupportSoftDeleteEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "CODE", length = 20, nullable = false, updatable = false)
    private String code;

    @Column(name = "COUNTRY", length = 2, nullable = false, updatable = false)
    private String country;

    @Column(name = "CURRENCY", length = 3, nullable = false, updatable = false)
    private String currency;

    @Column(name = "REMITTANCE_OPTION", length = 20, nullable = false, updatable = false)
    @Enumerated(EnumType.STRING)
    private RemittanceOption remittanceOption;

    @Column(name = "FEE_CURRENCY", length = 3, nullable = false, updatable = false)
    private String feeCurrency;

    @Column(name = "FEE", nullable = false)
    private BigDecimal fee;

    @Column(name = "EXCHANGE_SPREAD", nullable = false)
    private BigDecimal exchangeSpread;

    @Column(name = "EXPOSE", nullable = false)
    private Boolean expose;

    @Column(name = "MODIFIED_TIME", nullable = false)
    private LocalDateTime modifiedTime;

    @Column(name = "CREATED_TIME", nullable = false, updatable = false)
    private LocalDateTime createdTime;

    // one-to-many associate with mto-service-center, to support soft deletion in center use SQLDelete annotations.
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "mto", orphanRemoval = true)
    @SQLDelete(sql = "update MTO_SERVICE_CENTER set DELETED = 1 where MTO_SERVICE_CENTER.ID = ?", check = ResultCheckStyle.COUNT)
    private Set<MtoServiceCenter> mtoServiceCenters;

    /**
     * Gets the mto service centers that associate with this mto.
     *
     * @return list of mtoServiceCenters
     */
    public Set<MtoServiceCenter> getMtoServiceCenters() {
        if (mtoServiceCenters == null) {
            mtoServiceCenters = new HashSet<>();
        }
        return mtoServiceCenters;
    }

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
