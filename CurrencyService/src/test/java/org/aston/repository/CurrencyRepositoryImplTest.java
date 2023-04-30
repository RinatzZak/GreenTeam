package org.aston.repository;

import org.aston.config.PostgresTestContainerConfig;
import org.aston.model.Currency;
import org.aston.parser.ParseXML;
import org.aston.parser.ParseXMLImpl;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ContextConfiguration(classes = PostgresTestContainerConfig.class)
@ExtendWith(SpringExtension.class)
class CurrencyRepositoryImplTest {

    @Autowired
    private SessionFactory sessionFactory;

    @Mock
    private ParseXML parseXML;

    @Autowired
    private CurrencyRepository repository;

    @BeforeEach
    public void setUp() throws Exception {
        parseXML = mock(ParseXMLImpl.class);
    }

    @Test
    void testGetCurrencyByCharCodeAndDate() {
        Currency USD = Currency.builder()
                .id(1)
                .numCode("840")
                .charCode("USD")
                .nominal("1")
                .name("Доллар США")
                .value("80,5093")
                .dateOfCreation(LocalDate.parse("2023-04-29"))
                .build();
        Session session = sessionFactory.openSession();
        session.save(USD);
        Currency result = repository.getCurrencyByCharCodeAndDate("USD", LocalDate.parse("2023-04-29"));
        System.out.println(result);
        Assertions.assertEquals(USD.getCharCode(), result.getCharCode());
    }

    @Test
    void testSaveAll() throws Exception {
        List<Currency> currencies = new ArrayList<>();
        currencies.add(Currency.builder()
                .numCode("123")
                .charCode("ABC")
                .nominal("1")
                .name("Test Currency")
                .value("1.0")
                .dateOfCreation(LocalDate.now())
                .build());
        when(parseXML.parseCurrenciesFromCBR("XML_daily.xml")).thenReturn(currencies);

        repository.saveAll(currencies);

        try (Session session = sessionFactory.openSession()) {
            Query<Currency> query = session.createQuery(
                    "SELECT c FROM Currency c WHERE c.charCode = :charCode AND c.dateOfCreation = :date", Currency.class);
            query.setParameter("charCode", "ABC");
            query.setParameter("date", LocalDate.now());
            List<Currency> results = query.list();
            Assertions.assertNotNull(results);
            Assertions.assertEquals("ABC", results.get(0).getCharCode());
        }
    }
}