package org.aston.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

@Configuration
public class DBConfig {

    @Value("${spring.database.driver-class-name}")
    private String driver;
    @Value("${spring.database.username}")
    private String userName;
    @Value("${spring.database.passsword}")
    private String password;
    @Value("${spring.database.url}")
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
