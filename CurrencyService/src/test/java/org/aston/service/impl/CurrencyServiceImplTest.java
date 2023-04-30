package org.aston.service.impl;

import org.aston.model.Currency;
import org.aston.parser.ParseXML;
import org.aston.repository.CurrencyRepository;
import org.aston.repository.CurrencyRepositoryImpl;
import org.aston.util.exception.CurrencyNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;


class CurrencyServiceImplTest {

    private final CurrencyRepository repository = mock(CurrencyRepositoryImpl.class);

    @InjectMocks
    private CurrencyServiceImpl service;
    @Mock
    private ParseXML parseXML;


    @BeforeEach
    void setUp() {
        parseXML = mock(ParseXML.class);
        service = new CurrencyServiceImpl(repository, parseXML);
    }

    @Test
    public void testGetByCharCodeSuccess() {
        String charCode = "USD";
        Currency expectedCurrency = new Currency();
        expectedCurrency.setCharCode(charCode);

        when(repository.getCurrencyByCharCodeAndDate(eq(charCode), any())).thenReturn(expectedCurrency);

        Currency result = service.getByCharCode(charCode);

        assertEquals(expectedCurrency, result);
    }

    @Test
    public void testGetByCharCodeNotFound() {
        String charCode = "GBP";

        when(repository.getCurrencyByCharCodeAndDate(eq(charCode), any())).thenReturn(null);

        Assertions.assertThrows(CurrencyNotFoundException.class, () -> service.getByCharCode(charCode));
    }

    @Test
    void saveAll() {
        List<Currency> currencies = new ArrayList<>();
        currencies.add(Currency.builder()
                .numCode("123")
                .charCode("ABC")
                .nominal("1")
                .name("Test Currency")
                .value("1.0")
                .dateOfCreation(LocalDate.now())
                .build());

        when(parseXML.parseCurrenciesFromCBR("https://cbr.ru/scripts/XML_daily.asp")).thenReturn(currencies);
        service.saveAll();
        verify(repository, times(1)).saveAll(currencies);
    }
}