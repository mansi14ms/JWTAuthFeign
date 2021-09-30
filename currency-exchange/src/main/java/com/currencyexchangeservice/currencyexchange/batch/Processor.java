package com.currencyexchangeservice.currencyexchange.batch;

import com.currencyexchangeservice.currencyexchange.model.ExchangeRate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;



@Component
public class Processor implements ItemProcessor<ExchangeRate, ExchangeRate> {
    @Override
    public ExchangeRate process(ExchangeRate exchangeRate) throws Exception {

        Logger logger = LoggerFactory.getLogger(DBWriter.class);
        logger.info("In Processor");
        return exchangeRate;
    }
}
