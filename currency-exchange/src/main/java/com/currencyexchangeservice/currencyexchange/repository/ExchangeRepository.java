package com.currencyexchangeservice.currencyexchange.repository;

import com.currencyexchangeservice.currencyexchange.model.ExchangeRate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ExchangeRepository extends JpaRepository<ExchangeRate, Long> {

    @Query(value = "select currency_exchange_value from exchange_rate where currency_from=?1 and currency_to=?2", nativeQuery = true)
    Long findByCurrency_FromAndCurrency_To(String from, String to);

    // ExchangeRate findByFromAndTo(String from, String to);
}
