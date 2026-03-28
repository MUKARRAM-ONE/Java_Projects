package com.example.realtime_currency_converter.controller;

import com.example.realtime_currency_converter.service.CurrencyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class CurrencyController {

    private final CurrencyService currencyService;

    @GetMapping("/dashboard")
    public String dashboard(Model model, Principal principal) {
        String base = "USD";
        Map<String, Double> rates = currencyService.getTopCurrencies(base);
        model.addAttribute("username", principal.getName());
        model.addAttribute("base", base);
        model.addAttribute("rates", rates);
        return "dashboard";
    }

    @GetMapping("/converter")
    public String converter(Model model) {
        model.addAttribute("from", "USD");
        model.addAttribute("to", "EUR");
        model.addAttribute("amount", 1.0);
        model.addAttribute("currencies", currencyService.getSupportedCurrencies());
        return "converter";
    }

    @PostMapping("/convert")
    public String convert(
            @RequestParam String from,
            @RequestParam String to,
            @RequestParam double amount,
            Model model) {
        double result = currencyService.convert(from, to, amount);
        model.addAttribute("from", from);
        model.addAttribute("to", to);
        model.addAttribute("amount", amount);
        model.addAttribute("result", result);
        model.addAttribute("showResult", true);
        model.addAttribute("currencies", currencyService.getSupportedCurrencies());
        return "converter";
    }
}
