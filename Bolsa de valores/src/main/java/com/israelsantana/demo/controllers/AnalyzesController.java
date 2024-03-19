package com.israelsantana.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.israelsantana.demo.services.AnalyzesService;

@RestController
@RequestMapping("/analyzes")
@Validated
public class AnalyzesController {

    @Autowired
    private AnalyzesService analyzesService;
    
    @GetMapping("/calculated")
    public String GetAnalist() {
        return this.analyzesService.calculatedAnalist();
    }

    @GetMapping("/classify")
    public String GetClassify() {
        return this.analyzesService.classifyStocks();
    }
}
