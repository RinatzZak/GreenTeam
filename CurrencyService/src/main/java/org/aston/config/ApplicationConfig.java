package org.aston.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = {"org.aston.repository", "org.aston.service", "org.aston.parser"})
public class ApplicationConfig {

}
