package com.currencyexchangeservice.currencyexchange.model;

import javax.persistence.*;

@Entity
@Table(name ="exchange_rate")
public class ExchangeRate {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String currency_from;
    @Column
    private String currency_to;
    @Column
    private Long currency_exchange_value;

    public ExchangeRate() {

    }

    public ExchangeRate(Long id, String currency_from, String currency_to, Long currency_exchange_value ) {
        this.id = id;
        this.currency_from = currency_from;
        this.currency_to = currency_to;
        this.currency_exchange_value = currency_exchange_value;
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

    public Long getCurrency_exchange_value() {
        return currency_exchange_value;
    }

    public void setCurrency_exchange_value(Long currency_exchange_value) {
        this.currency_exchange_value = currency_exchange_value;
    }

    @Override
    public String toString() {
        return "ExchangeRate{" +
                "id=" + id +
                ", currency_from='" + currency_from + '\'' +
                ", currency_to='" + currency_to + '\'' +
                ", currency_exchange_value=" + currency_exchange_value +
                '}';
    }
}
