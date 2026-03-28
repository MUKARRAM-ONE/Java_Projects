package com.example.realtime_currency_converter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@SpringBootApplication
@EnableCaching
public class RealtimeCurrencyConverterApplication {

    public static void main(String[] args) {
        SpringApplication.run(RealtimeCurrencyConverterApplication.class, args);
    }

}
