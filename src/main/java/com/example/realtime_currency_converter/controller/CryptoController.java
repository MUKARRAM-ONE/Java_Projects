package com.example.realtime_currency_converter.controller;

import com.example.realtime_currency_converter.service.CryptoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class CryptoController {

    private final CryptoService cryptoService;

    @GetMapping("/crypto-converter")
    public String cryptoConverter(Model model) {
        model.addAttribute("from", "BTC");
        model.addAttribute("to", "ETH");
        model.addAttribute("amount", 1.0);
        model.addAttribute("cryptos", cryptoService.getSupportedCrypto());
        return "crypto-converter";
    }

    @PostMapping("/crypto-convert")
    public String convert(
            @RequestParam String from,
            @RequestParam String to,
            @RequestParam double amount,
            Model model) {
        double result = cryptoService.convert(from, to, amount);
        model.addAttribute("from", from);
        model.addAttribute("to", to);
        model.addAttribute("amount", amount);
        model.addAttribute("result", result);
        model.addAttribute("showResult", true);
        model.addAttribute("cryptos", cryptoService.getSupportedCrypto());
        return "crypto-converter";
    }
}
