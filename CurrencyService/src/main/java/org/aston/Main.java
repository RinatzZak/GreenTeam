package org.aston;

import org.aston.config.ApplicationConfig;
import org.aston.config.DBConfig;
import org.aston.model.Currency;
import org.aston.service.CurrencyService;
import org.aston.service.impl.CurrencyServiceImpl;
import org.aston.util.exception.CurrencyNotFoundException;
import org.aston.web.CurrencySoapControllerImpl;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {
    public static void main(String[] args) {
        Currency currency = null;
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(ApplicationConfig.class, DBConfig.class);
        CurrencyService service = context.getBean(CurrencyServiceImpl.class);
        try {
            currency = service.getByCharCode("USD");
        } catch (CurrencyNotFoundException ignore) {
            service.saveAll();
            currency = service.getByCharCode("USD");
        }
        System.out.println(currency.getCharCode() + " - " + currency.getValue() + " - " + currency.getDateOfCreation() );
        CurrencySoapControllerImpl controller = new CurrencySoapControllerImpl(service);
        controller.publishEndpoint("http://localhost:8082/currency");
    }
}