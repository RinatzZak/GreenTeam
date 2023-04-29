package org.aston.config;

import org.aston.parser.ParseXML;
import org.aston.parser.ParseXMLImpl;
import org.aston.service.CurrencyService;
import org.aston.service.impl.CurrencyServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
@ComponentScan(basePackages = {"org.aston.repository"})
public class ApplicationConfig {

    @Bean
    @Primary
    CurrencyService currencyService() {
        return new CurrencyServiceImpl();
    }

    @Bean
    ParseXML parseXML() {
        return new ParseXMLImpl();
    }
}
