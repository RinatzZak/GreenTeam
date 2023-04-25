package org.aston.parser;

import org.aston.model.Currency;

import java.util.List;

public interface ParseXML {
    List<Currency> parseCurrenciesFromCBR();
}
