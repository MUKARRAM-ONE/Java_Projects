package com.example.realtime_currency_converter.service;

import com.example.realtime_currency_converter.model.ExchangeRateResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
public class ExchangeRateClient {

    private final RestClient restClient;

    @Value("${app.exchange-rate-api.url}")
    private String apiUrl;

    @Value("${app.exchange-rate-api.key}")
    private String apiKey;

    public ExchangeRateClient() {
        this.restClient = RestClient.create();
    }

    public ExchangeRateResponse getLatestRates(String baseCurrency) {
        return restClient.get()
            .uri(apiUrl + "/" + apiKey + "/latest/" + baseCurrency)
            .retrieve()
            .body(ExchangeRateResponse.class);
    }

    public ExchangeRateResponse getSupportedCodes() {
        return restClient.get()
            .uri(apiUrl + "/" + apiKey + "/codes")
            .retrieve()
            .body(ExchangeRateResponse.class);
    }
}
