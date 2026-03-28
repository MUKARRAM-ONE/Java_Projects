package com.example.realtime_currency_converter.service;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class CryptoService {

    private final BinanceClient binanceClient;

    private static final List<String> POPULAR_CRYPTO = List.of(
        "BTC", "ETH", "BNB", "SOL", "XRP", "ADA", "DOGE", "DOT", "MATIC", "LINK"
    );

    @Cacheable(value = "crypto-prices")
    public double getPriceInUsdt(String symbol) {
        if (symbol.equals("USDT")) return 1.0;
        return binanceClient.getPrice(symbol + "USDT");
    }

    public List<String> getSupportedCrypto() {
        return POPULAR_CRYPTO;
    }

    public double convert(String from, String to, double amount) {
        if (from.equals(to)) return amount;
        
        double fromPrice = getPriceInUsdt(from);
        double toPrice = getPriceInUsdt(to);

        if (fromPrice == 0 || toPrice == 0) return 0;

        return (amount * fromPrice) / toPrice;
    }
}
