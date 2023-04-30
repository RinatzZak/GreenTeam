package org.aston.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aston.model.Currency;
import org.aston.parser.ParseXML;
import org.aston.repository.CurrencyRepository;
import org.aston.service.CurrencyService;
import org.aston.util.exception.CurrencyNotFoundException;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class CurrencyServiceImpl implements CurrencyService {


    private final CurrencyRepository repository;
    private final ParseXML parseXML;

    /**
     * Method for save parsing list of currencies
     * Parsing starts at midnight every day
     */
    @Override
    @Scheduled(cron = "0 0 1 * * *")
    public void saveAll() {
        List<Currency> currencies = parseXML.parseCurrenciesFromCBR("https://cbr.ru/scripts/XML_daily.asp");
        repository.saveAll(currencies);
    }


    /**
     * Method for getting currency by its char code
     *
     * @param charCode - incoming currency code
     * @return {@link Currency}
     */
    @Override
    public Currency getByCharCode(String charCode) {
        Currency currency = repository.getCurrencyByCharCodeAndDate(charCode, LocalDate.now());
        if (currency == null) {
            throw new CurrencyNotFoundException();
        }
        return currency;
    }
}
