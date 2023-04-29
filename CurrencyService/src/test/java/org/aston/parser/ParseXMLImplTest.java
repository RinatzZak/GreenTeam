package org.aston.parser;

import org.aston.model.Currency;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.List;

class ParseXMLImplTest {

    @Test
    void parseCurrenciesFromCBR() throws IOException, URISyntaxException {
        URI uri = ClassLoader.getSystemResource("XML_daily.xml").toURI();
        String ratesXml = Files.readString(Paths.get(uri));

        ParseXML parseXML = new ParseXMLImpl();
        List<Currency> currencies = parseXML.parseCurrenciesFromCBR(ratesXml);

        Assertions.assertEquals(currencies.size(),43);
        Assertions.assertEquals(currencies.get(4), getBelRubRate());
        Assertions.assertTrue(currencies.contains(getJPYRate()));
        Assertions.assertTrue(currencies.contains(getUSDRate()));
    }
    Currency getUSDRate() {
        return Currency.builder()
                .id(null)
                .numCode("840")
                .charCode("USD")
                .nominal("1")
                .name("Доллар США")
                .value("81,2745")
                .dateOfCreation(LocalDate.parse("2023-04-25"))
                .build();
    }

    Currency getJPYRate() {
        return Currency.builder()
                .numCode("392")
                .charCode("JPY")
                .nominal("100")
                .name("Японских иен")
                .value("60,6164")
                .dateOfCreation(LocalDate.now())
                .build();
    }

    Currency getBelRubRate() {
        return Currency.builder()
                .numCode("933")
                .charCode("BYN")
                .nominal("1")
                .name("Белорусский рубль")
                .value("27,6660")
                .dateOfCreation(LocalDate.now())
                .build();
    }
}