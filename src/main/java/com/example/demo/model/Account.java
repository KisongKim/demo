package com.example.demo.model;

import com.example.demo.model.utils.CryptoConverter;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * User account entity.
 *
 * @author Kisong
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "ACCOUNT", indexes = {
        @Index(columnList = "MEMBER_ID", name = "ACCOUNT_IDX1")
})
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "MEMBER_ID", nullable = false)
    private Long memberId;

    @Column(name = "BANK_CODE", nullable = false)
    private String bankCode;

    @Column(name = "HOLDER_NAME", nullable = false)
    private String holderName;

    @Column(name = "NUMBER", nullable = false)
    @Convert(converter = CryptoConverter.class)
    private String number;

    @Column(name = "DELETED", nullable = false)
    private Boolean deleted;

    @Column(name = "DELETED_TIME")
    private LocalDateTime deletedTime;

    @Column(name = "MODIFIED_TIME", nullable = false)
    private LocalDateTime modifiedTime;

    @Column(name = "CREATED_TIME", nullable = false, updatable = false)
    private LocalDateTime createdTime;

}
