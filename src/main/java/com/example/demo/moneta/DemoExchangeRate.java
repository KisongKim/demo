package com.example.demo.moneta;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.math.BigDecimal;

/**
 * Demo exchange rate
 *
 * @author Kisong
 */
public class DemoExchangeRate {

    private final String base;

    private final String term;

    private final BigDecimal rate;

    public DemoExchangeRate(final String base, final String term, final BigDecimal rate) {
        this.base = base;
        this.term = term;
        this.rate = rate;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }

    public String getBase() {
        return base;
    }

    public String getTerm() {
        return term;
    }

    public BigDecimal getRate() {
        return rate;
    }
}
