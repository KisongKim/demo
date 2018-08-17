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
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Exchange rate from xe.com
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "XE_EXCHANGE_RATE")
public class XeExchangeRate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "BASE", length = 3, nullable = false)
    private String base;

    @Column(name = "TERM", length = 3, nullable = false)
    private String term;

    @Column(name = "RATE", nullable = false)
    private BigDecimal rate;

    @Column(name = "CREATED_TIME", nullable = false)
    private LocalDateTime createdTime;

}
