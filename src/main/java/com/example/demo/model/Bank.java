package com.example.demo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;

/**
 * Bank entity
 *
 * @author Kisong
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "BANK")
public class Bank {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    /**
     * Domestic bank code of this bank
     */
    @Column(length = 3, name = "CODE", nullable = false, unique = true, updatable = false)
    private String code;

    /**
     * International swift code of this bank
     */
    @Column(length = 11, name = "SWIFT", nullable = false, unique = true, updatable = false)
    private String swift;

    @Column(length = 50, name = "NAME", nullable = false, updatable = false)
    private String name;

    /**
     * English name of this bank
     */
    @Column(length = 100, name = "NAME_IN_ENGLISH", nullable = false, updatable = false)
    private String nameInEnglish;

    /**
     * Display order in app
     */
    @Column(name = "DISPLAY_ORDER", nullable = false)
    private Integer displayOrder;

    @Column(name = "DELETED", nullable = false)
    private Boolean deleted;

    @Column(name = "DELETED_TIME")
    private LocalDateTime deletedTime;

    @Column(name = "MODIFIED_TIME", nullable = false)
    private LocalDateTime modifiedTime;

    @Column(name = "CREATED_TIME", nullable = false, updatable = false)
    private LocalDateTime createdTime;

}
