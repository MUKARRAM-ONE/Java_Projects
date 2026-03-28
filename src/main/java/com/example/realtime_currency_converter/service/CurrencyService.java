package com.example.realtime_currency_converter.service;

import com.example.realtime_currency_converter.model.CurrencyInfo;
import com.example.realtime_currency_converter.model.ExchangeRateResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Currency;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CurrencyService {

    private final ExchangeRateClient exchangeRateClient;

    @Cacheable(value = "exchange-rates", key = "#baseCurrency")
    public ExchangeRateResponse getRates(String baseCurrency) {
        return exchangeRateClient.getLatestRates(baseCurrency);
    }

    @Cacheable(value = "supported-currencies")
    public List<CurrencyInfo> getSupportedCurrencies() {
        ExchangeRateResponse response = exchangeRateClient.getSupportedCodes();
        if (response != null && response.supported_codes() != null) {
            return response.supported_codes().stream()
                .map(list -> {
                    String code = list.get(0);
                    String name = list.get(1);
                    String symbol = getSymbol(code);
                    String flagUrl = "https://flagcdn.com/w40/" + code.substring(0, 2).toLowerCase() + ".png";
                    // Special case for some currencies where first 2 letters != country code
                    if (code.equals("EUR")) flagUrl = "https://flagcdn.com/w40/eu.png";
                    if (code.equals("USD")) flagUrl = "https://flagcdn.com/w40/us.png";
                    if (code.equals("GBP")) flagUrl = "https://flagcdn.com/w40/gb.png";
                    if (code.equals("JPY")) flagUrl = "https://flagcdn.com/w40/jp.png";
                    if (code.equals("INR")) flagUrl = "https://flagcdn.com/w40/in.png";
                    if (code.equals("PKR")) flagUrl = "https://flagcdn.com/w40/pk.png";
                    return new CurrencyInfo(code, name, symbol, flagUrl);
                })
                .sorted((a, b) -> a.code().compareTo(b.code()))
                .collect(Collectors.toList());
        }
        return List.of();
    }

    private String getSymbol(String code) {
        try {
            return Currency.getInstance(code).getSymbol();
        } catch (Exception e) {
            return code;
        }
    }

    public double convert(String from, String to, double amount) {
        System.out.println("Converting " + amount + " from " + from + " to " + to);
        ExchangeRateResponse rates = getRates(from);
        if (rates != null && rates.conversion_rates() != null) {
            Double rate = rates.conversion_rates().get(to);
            if (rate != null) {
                System.out.println("Rate found: " + rate);
                return amount * rate;
            } else {
                System.out.println("Rate NOT found for " + to);
            }
        } else {
            System.out.println("Rates or conversion_rates is NULL for " + from);
        }
        return 0;
    }

    public Map<String, Double> getTopCurrencies(String base) {
        List<String> topCodes = List.of("USD", "EUR", "GBP", "JPY", "AUD", "CAD", "CHF", "CNY", "INR", "PKR");
        ExchangeRateResponse rates = getRates(base);
        Map<String, Double> result = new HashMap<>();
        if (rates != null && rates.conversion_rates() != null) {
            for (String code : topCodes) {
                if (rates.conversion_rates().containsKey(code)) {
                    result.put(code, rates.conversion_rates().get(code));
                }
            }
        }
        return result;
    }

    public List<String> getAllCurrencies(String base) {
        ExchangeRateResponse rates = getRates(base);
        if (rates != null && rates.conversion_rates() != null) {
            return rates.conversion_rates().keySet().stream().sorted().toList();
        }
        return List.of();
    }
}
