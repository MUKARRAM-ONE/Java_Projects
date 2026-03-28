package com.example.realtime_currency_converter.model;

import java.util.List;
import java.util.Map;

public record ExchangeRateResponse(
    String result,
    String base_code,
    Map<String, Double> conversion_rates,
    List<List<String>> supported_codes,
    String time_last_update_utc
) {}
