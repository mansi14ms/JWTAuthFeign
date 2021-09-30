package com.currencyexchangeservice.currencyexchange.batch;

import com.currencyexchangeservice.currencyexchange.model.ExchangeRate;
import com.currencyexchangeservice.currencyexchange.repository.ExchangeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DBWriter implements ItemWriter<ExchangeRate> {

    Logger logger = LoggerFactory.getLogger(DBWriter.class);

    @Autowired
    ExchangeRepository exchangeRepository;

    @Override
    public void write(List<? extends ExchangeRate> exchangeRate) throws Exception {

        logger.info("Saving Data = " + exchangeRate);
        exchangeRepository.saveAll(exchangeRate);

    }
}
