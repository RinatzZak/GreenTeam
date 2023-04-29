package org.aston.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.aston.model.Currency;
import org.aston.parser.ParseXML;
import org.aston.repository.CurrencyRepository;
import org.aston.service.CurrencyService;
import org.aston.util.exception.CurrencyNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class CurrencyServiceImpl implements CurrencyService {

    @Autowired
    private ParseXML parseXML;

    @Autowired
    private CurrencyRepository repository;

    public CurrencyServiceImpl() {
    }


    @Scheduled(cron = "0 0 0 * * *")
    public void saveAll() {
        List<Currency> currencies = parseXML.parseCurrenciesFromCBR("https://cbr.ru/scripts/XML_daily.asp");
        repository.saveAll(currencies);
    }


    public Currency getByCharCode(String charCode) {
        Currency currency = repository.getCurrencyByCharCode(charCode);
        if (currency == null) {
            throw new CurrencyNotFoundException();
        }
        return currency;
    }
}
