package com.example.realtime_currency_converter;

import com.example.realtime_currency_converter.service.CurrencyService;
import com.example.realtime_currency_converter.model.CurrencyInfo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CurrencyConverterDebugTests {

    @Autowired
    private CurrencyService currencyService;

    @Test
    void testGetSupportedCurrencies() {
        List<CurrencyInfo> currencies = currencyService.getSupportedCurrencies();
        assertNotNull(currencies);
        assertFalse(currencies.isEmpty(), "Currencies list should not be empty");
        
        // Check for USD
        boolean hasUsd = currencies.stream().anyMatch(c -> c.code().equals("USD"));
        assertTrue(hasUsd, "Should contain USD");
    }

    @Test
    void testConversion() {
        double result = currencyService.convert("USD", "EUR", 100.0);
        assertTrue(result > 0, "Conversion result should be greater than 0");
    }
}
