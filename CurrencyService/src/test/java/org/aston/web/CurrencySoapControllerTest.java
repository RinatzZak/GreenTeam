package org.aston.web;

import org.aston.model.Currency;
import org.aston.service.CurrencyService;
import org.aston.util.exception.BadRequestForCharCodeException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CurrencySoapControllerTest {

    @Mock
    private CurrencyService currencyService;


    @InjectMocks
    private CurrencySoapController currencySoapController;


    @Test
    void getByCharCode() {
        String charCode = "USD";
        Currency currency = new Currency();
        currency.setCharCode(charCode);
        currency.setValue("74,2");
        when(currencyService.getByCharCode(charCode)).thenReturn(currency);

        Currency result = currencySoapController.getByCharCode(charCode);

        assertEquals(currency, result);
        verify(currencyService).getByCharCode(charCode);
    }

    @Test
    void convertToRubFromAnother() {
        String charCode = "USD";
        String value = "100";
        Currency currency = new Currency();
        currency.setCharCode(charCode);
        currency.setValue("74,2");
        when(currencyService.getByCharCode(charCode)).thenReturn(currency);

        String result = currencySoapController.convertToRubFromAnother(charCode, value);

        assertEquals("7420.00", result.replace(",", "."));
        verify(currencyService).getByCharCode(charCode);
    }

    @Test
    void convertToUSDOrEURFromRub() {
        String charCode = "USD";
        String valueOfRub = "7420.00";
        Currency currency = new Currency();
        currency.setCharCode(charCode);
        currency.setValue("74,2");
        when(currencyService.getByCharCode(charCode)).thenReturn(currency);

        String result = currencySoapController.convertToUSDOrEURFromRub(charCode, valueOfRub);

        assertEquals("100.00", result.replace(",", "."));
        verify(currencyService).getByCharCode(charCode);
    }

    @Test
    void testConvertToUSDOrEURFromRub_withInvalidCharCode() {

        String charCode = "invalid";
        String valueOfRub = "7420.00";

        assertThrows(BadRequestForCharCodeException.class, () -> currencySoapController.convertToUSDOrEURFromRub(charCode, valueOfRub));
    }
}