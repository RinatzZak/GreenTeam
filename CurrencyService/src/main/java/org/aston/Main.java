package org.aston;

import org.aston.config.ApplicationConfig;
import org.aston.config.DBConfig;
import org.aston.model.Currency;
import org.aston.parser.ParseXML;
import org.aston.parser.ParseXMLImpl;
import org.aston.service.CurrencyService;
import org.aston.service.impl.CurrencyServiceImpl;
import org.aston.web.CurrencySoapController;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.sql.SQLException;

public class Main {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(ApplicationConfig.class, DBConfig.class);
        CurrencyService service = context.getBean(CurrencyServiceImpl.class);
        service.saveAll();
        Currency currency = service.getByCharCode("USD");
        System.out.println(currency.getCharCode() + " - " + currency.getValue() + " - " + currency.getDateOfCreation() );
        CurrencySoapController controller = new CurrencySoapController(service);
        controller.publishEndpoint("http://localhost:8082/currency");
    }
}