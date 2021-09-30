package com.currencyconversionservice.currencyconversion.service;

import com.currencyconversionservice.currencyconversion.model.CurrencyConversionBean;
//import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name="currency-exchange-service")
//@RibbonClient(name="currency-exchange-service")
//@FeignClient(name="forex-service",url="localhost:8000")
public interface CurrencyExchangeServiceProxy {
    @GetMapping("/convert/{from}/{to}")
    public Long retrieveExchangeValue
            (@PathVariable("from") String from, @PathVariable("to") String to);
}