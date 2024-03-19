package com.israelsantana.demo.controllers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.israelsantana.demo.models.Action;
import com.israelsantana.demo.services.ActionService;

@RestController
@RequestMapping("/webScraping")
@Validated
public class ActionController {
    
    @Autowired
    private ActionService actionsService;

    @GetMapping()
    public List<Action> getHtml() {
        List<String> symbols = Arrays.asList("%5EBVSP", "PETR4.SA", "%5EGSPC");
        // List<String> symbols = Arrays.asList("%5EBVSP");

        List<Action> result = new ArrayList<>();

        for (String symbol : symbols) {
            String url = "https://finance.yahoo.com/quote/" + symbol + "/history";

            String formattedSymbol = symbol.replace("%5E", "^").replace(".SA", "");
            result.addAll(actionsService.obterDadosDaPagina(url, formattedSymbol));
        }

        return result;
    }
}
