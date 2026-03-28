package com.example.realtime_currency_converter.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.Map;

@Controller
public class UnitConverterController {

    @GetMapping("/unit-converter")
    public String unitConverter(Model model) {
        model.addAttribute("type", "length");
        model.addAttribute("amount", 1.0);
        return "unit-converter";
    }

    @PostMapping("/unit-convert")
    public String convert(
            @RequestParam String type,
            @RequestParam String from,
            @RequestParam String to,
            @RequestParam double amount,
            Model model) {
        
        double result = performConversion(type, from, to, amount);
        
        model.addAttribute("type", type);
        model.addAttribute("from", from);
        model.addAttribute("to", to);
        model.addAttribute("amount", amount);
        model.addAttribute("result", result);
        model.addAttribute("showResult", true);
        
        return "unit-converter";
    }

    private double performConversion(String type, String from, String to, double amount) {
        if (from.equals(to)) return amount;

        switch (type) {
            case "length": return convertLength(from, to, amount);
            case "weight": return convertWeight(from, to, amount);
            case "temperature": return convertTemperature(from, to, amount);
            case "volume": return convertVolume(from, to, amount);
            case "area": return convertArea(from, to, amount);
            case "time": return convertTime(from, to, amount);
            default: return 0;
        }
    }

    private double convertLength(String from, String to, double amount) {
        Map<String, Double> toMeters = new HashMap<>();
        toMeters.put("mm", 0.001);
        toMeters.put("cm", 0.01);
        toMeters.put("m", 1.0);
        toMeters.put("km", 1000.0);
        toMeters.put("in", 0.0254);
        toMeters.put("ft", 0.3048);
        toMeters.put("yd", 0.9144);
        toMeters.put("mi", 1609.34);

        double meters = amount * toMeters.get(from);
        return meters / toMeters.get(to);
    }

    private double convertWeight(String from, String to, double amount) {
        Map<String, Double> toKg = new HashMap<>();
        toKg.put("mg", 0.000001);
        toKg.put("g", 0.001);
        toKg.put("kg", 1.0);
        toKg.put("oz", 0.0283495);
        toKg.put("lb", 0.453592);

        double kg = amount * toKg.get(from);
        return kg / toKg.get(to);
    }

    private double convertTemperature(String from, String to, double amount) {
        double celsius;
        if (from.equals("c")) celsius = amount;
        else if (from.equals("f")) celsius = (amount - 32) * 5/9;
        else celsius = amount - 273.15; // Kelvin

        if (to.equals("c")) return celsius;
        else if (to.equals("f")) return (celsius * 9/5) + 32;
        else return celsius + 273.15; // Kelvin
    }

    private double convertVolume(String from, String to, double amount) {
        Map<String, Double> toLiter = new HashMap<>();
        toLiter.put("ml", 0.001);
        toLiter.put("l", 1.0);
        toLiter.put("gal", 3.78541);
        toLiter.put("qt", 0.946353);

        double l = amount * toLiter.get(from);
        return l / toLiter.get(to);
    }

    private double convertArea(String from, String to, double amount) {
        Map<String, Double> toSqMeter = new HashMap<>();
        toSqMeter.put("sqmm", 0.000001);
        toSqMeter.put("sqcm", 0.0001);
        toSqMeter.put("sqm", 1.0);
        toSqMeter.put("sqkm", 1000000.0);
        toSqMeter.put("acre", 4046.86);

        double sqm = amount * toSqMeter.get(from);
        return sqm / toSqMeter.get(to);
    }

    private double convertTime(String from, String to, double amount) {
        Map<String, Double> toSec = new HashMap<>();
        toSec.put("sec", 1.0);
        toSec.put("min", 60.0);
        toSec.put("hr", 3600.0);
        toSec.put("day", 86400.0);

        double sec = amount * toSec.get(from);
        return sec / toSec.get(to);
    }
}
