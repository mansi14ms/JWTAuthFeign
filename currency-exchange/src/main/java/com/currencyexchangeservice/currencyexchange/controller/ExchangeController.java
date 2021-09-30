package com.currencyexchangeservice.currencyexchange.controller;

import com.currencyexchangeservice.currencyexchange.model.ExchangeRate;
import com.currencyexchangeservice.currencyexchange.repository.ExchangeRepository;
import com.currencyexchangeservice.currencyexchange.service.CurrencyExchangeServices;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
public class ExchangeController {
    Logger logger = LoggerFactory.getLogger(ExchangeController.class);

    @Autowired
    private CurrencyExchangeServices currencyExchangeServices;

    @Autowired
    private ExchangeRepository exchangeRepository;

    @GetMapping("/ex")
    public String check() {
        this.logger.info("controller check");
        return "Exchange works well"; }

    @GetMapping("/convert/{from}/{to}")
    public Long currencyExchangeValue(@PathVariable String from, @PathVariable String to ) {
        this.logger.info("from= " + from);
        this.logger.info("to = " + to);
        return currencyExchangeServices.getCurrencyExchangeValue(from,to);

    }

    /*@GetMapping("/check/{id}")
    public String find(@PathVariable Long id)
    { Optional<ExchangeRate> exchg = exchangeRepository.findById(id);
    this.logger.info(String.valueOf(exchg));
    if(exchg.isEmpty())
        return "No";
    else
        return "Yes";
    }

     */
}
