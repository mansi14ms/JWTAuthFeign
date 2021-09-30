package com.currencyexchangeservice.currencyexchange.service;

import com.currencyexchangeservice.currencyexchange.model.ExchangeRate;
import com.currencyexchangeservice.currencyexchange.repository.ExchangeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CurrencyExchangeServices {

    @Autowired
    private ExchangeRepository exchangeRepository;


    public Long getCurrencyExchangeValue(String from, String to) {

        Long exchangeRate = exchangeRepository.findByCurrency_FromAndCurrency_To(from, to);

    //  Question -> how to solve without using nativeQuery
    //    ExchangeRate exchg = exchangeRepository.findByCurrencyFromAndCurrencyTo(from, to);
        return exchangeRate;

    }
}
