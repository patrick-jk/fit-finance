package com.fitfinance;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan
public class FitFinanceApplication {

    public static void main(String[] args) {
        SpringApplication.run(FitFinanceApplication.class, args);
    }
}
