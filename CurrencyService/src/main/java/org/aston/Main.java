package org.aston;

import org.aston.config.ApplicationConfig;
import org.aston.config.DBConfig;
import org.aston.config.ParserConfig;
import org.aston.service.CurrencyService;
import org.aston.service.impl.CurrencyServiceImpl;
import org.aston.web.CurrencySoapController;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.sql.SQLException;

public class Main {

    public static void main(String[] args) throws SQLException {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(ApplicationConfig.class, DBConfig.class, ParserConfig.class);
        CurrencySoapController controller = new CurrencySoapController();
        controller.publishEndpoint("http://localhost:8083/currency");
        CurrencyService service = context.getBean(CurrencyServiceImpl.class);
        service.saveAll();
    }
}