package com.currencyconversionservice.currencyconversion.model;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

public class CurrencyConversionBean {
    private Long id;
    private String currency_from;
    private String currency_to;
    private BigDecimal conversionMultiple;
    private BigDecimal quantity;
    private BigDecimal totalCalculatedAmount;

    public CurrencyConversionBean() {

    }

    public CurrencyConversionBean(Long id, String currency_from, String currency_to, BigDecimal conversionMultiple, BigDecimal quantity,
                                  BigDecimal totalCalculatedAmount) {
        super();
        this.id = id;
        this.currency_from = currency_from;
        this.currency_to = currency_to;
        this.conversionMultiple = conversionMultiple;
        this.quantity = quantity;
        this.totalCalculatedAmount = totalCalculatedAmount;

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCurrency_from() {
        return currency_from;
    }

    public void setCurrency_from(String currency_from) {
        this.currency_from = currency_from;
    }

    public String getCurrency_to() {
        return currency_to;
    }

    public void setCurrency_to(String currency_to) {
        this.currency_to = currency_to;
    }

    public BigDecimal getConversionMultiple() {
        return conversionMultiple;
    }

    public void setConversionMultiple(BigDecimal conversionMultiple) {
        this.conversionMultiple = conversionMultiple;
    }

    public BigDecimal getQuantity() {
        return quantity;
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getTotalCalculatedAmount() {
        return totalCalculatedAmount;
    }

    public void setTotalCalculatedAmount(BigDecimal totalCalculatedAmount) {
        this.totalCalculatedAmount = totalCalculatedAmount;
    }


}
