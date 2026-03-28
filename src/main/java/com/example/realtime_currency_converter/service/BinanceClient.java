package com.example.realtime_currency_converter.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class BinanceClient {

    private final RestClient restClient;

    public BinanceClient() {
        this.restClient = RestClient.create("https://api.binance.com/api/v3");
    }

    public double getPrice(String symbol) {
        try {
            Map<String, String> response = restClient.get()
                .uri("/ticker/price?symbol=" + symbol)
                .retrieve()
                .body(Map.class);
            return Double.parseDouble(response.get("price"));
        } catch (Exception e) {
            return 0;
        }
    }

    public List<Map<String, String>> getAllPrices() {
        return restClient.get()
            .uri("/ticker/price")
            .retrieve()
            .body(List.class);
    }
}
