package org.aston.repository;

import org.aston.model.Currency;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;


public interface CurrencyRepository{
    Currency getCurrencyByCharCodeAndDate(String charCode, LocalDate date);
    void saveAll(List<Currency> currencies);
}
