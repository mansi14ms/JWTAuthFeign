package com.currencyconversionservice.currencyconversion.controller;

import com.currencyconversionservice.currencyconversion.model.CurrencyConversionBean;
import com.currencyconversionservice.currencyconversion.service.CurrencyExchangeServiceProxy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@RestController
public class CurrencyCoversionController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private CurrencyExchangeServiceProxy currencyExchangeServiceProxy;

    //Testing
    @GetMapping("/currencyCoversion")
    public String name() { return "Hello Coversion service";}

    //UsingRestTemplate
    @GetMapping("/currency-converter/from/{currency_from}/to/{currency_to}/quantity/{quantity}")
    public Long convertCurrency(@PathVariable String currency_from, @PathVariable String currency_to,
                                                  @PathVariable BigDecimal quantity) {

        Map<String, String> uriVariables = new HashMap<>();
        uriVariables.put("from", currency_from);
        uriVariables.put("to", currency_to);

       Long exchangeValue;
       Long responseLong = new RestTemplate().getForObject(
                "http://localhost:8000/convert/{from}/{to}", Long.class,
                uriVariables);

       if(responseLong == null) { return -1L; }


       //Haven't removed these code for future refernce purpose.
       /* ResponseEntity<CurrencyConversionBean> responseEntity = new RestTemplate().getForEntity(
                "http://localhost:8000/convert/{from}/{to}", CurrencyConversionBean.class,
                uriVariables);

        CurrencyConversionBean response = responseEntity.getBody();

        return new CurrencyConversionBean(response.getId(), currency_from, currency_to, response.getConversionMultiple(), quantity,
               quantity.multiply(response.getConversionMultiple()));
        */
        return responseLong*quantity.longValue();
    }

    //UsingFeignClient
    @GetMapping("/currency-converter-feign/from/{from}/to/{to}/quantity/{quantity}")
    public Long convertCurrencyFeign(@PathVariable String from, @PathVariable String to,
                                                       @PathVariable BigDecimal quantity) {

        Long response = currencyExchangeServiceProxy.retrieveExchangeValue(from, to);
        logger.info("{}", response);

        if(response == null) { return -1L; }

        return response*quantity.longValue();
    }
}
