package org.aston.service;

import org.aston.model.Currency;

public interface CurrencyService {
    void saveAll();
    Currency getByCharCode(String charCode);
}
