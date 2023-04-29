package org.aston.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

@Configuration
@PropertySource(value = "classpath:application.properties")
public class DBConfig {

    @Value("${db_driver}")
    private String driver;
    @Value("${db_username}")
    private String userName;
    @Value("${db_password}")
    private String password;
    @Value("${db_url}")
    private String url;

    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource db = new DriverManagerDataSource();
        db.setDriverClassName(driver);
        db.setUsername(userName);
        db.setPassword(password);
        db.setUrl(url);
        return db;
    }
}
