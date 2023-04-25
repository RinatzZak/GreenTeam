package org.aston.repository;

import org.aston.model.Currency;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CurrencyRepository extends JpaRepository<Currency, Integer> {
    Currency getCurrencyByCharCode(String charCode);
}
