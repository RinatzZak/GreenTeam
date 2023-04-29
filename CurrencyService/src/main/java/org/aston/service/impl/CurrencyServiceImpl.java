package org.aston.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aston.model.Currency;
import org.aston.parser.ParseXML;
import org.aston.repository.CurrencyRepository;
import org.aston.repository.CurrencyRepositoryImpl;
import org.aston.service.CurrencyService;
import org.aston.util.exception.CurrencyNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class CurrencyServiceImpl implements CurrencyService {


    private final CurrencyRepository repository;


    @Override
    @Scheduled(cron = "0 0 0 * * *")
    public void saveAll() {
        repository.saveAll();
    }

    @Override
    public Currency getByCharCode(String charCode) {
        Currency currency = repository.getCurrencyByCharCodeAndDate(charCode, LocalDate.now());
        if (currency == null) {
            throw new CurrencyNotFoundException();
        }
        return currency;
    }
}
