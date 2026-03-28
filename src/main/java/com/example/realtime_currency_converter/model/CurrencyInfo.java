package com.example.realtime_currency_converter.model;

public record CurrencyInfo(
    String code,
    String name,
    String symbol,
    String flagUrl
) {}
