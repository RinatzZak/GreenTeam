package org.aston.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.aston.model.Currency;
import org.aston.parser.ParseXML;
import org.aston.parser.ParseXMLImpl;
import org.aston.repository.CurrencyRepository;
import org.aston.service.CurrencyService;
import org.aston.util.exception.CurrencyNotFoundException;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class CurrencyServiceImpl implements CurrencyService {
    private final ParseXML parseXML;

    private CurrencyRepository repository;


    public CurrencyServiceImpl(ParseXML parseXML, CurrencyRepository repository) {
        this.parseXML = parseXML;
        this.repository = repository;
    }

    public CurrencyServiceImpl() {
        this.parseXML = new ParseXMLImpl();
    }

    @Scheduled(cron = "0 0 0 * * *")
    public void saveAll() {
        List<Currency> currencies = parseXML.parseCurrenciesFromCBR();
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
