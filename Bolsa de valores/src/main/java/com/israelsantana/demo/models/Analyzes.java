package com.israelsantana.demo.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Analyzes {

    private int id;
    private String name;
    private String date;
    private double open;
    private double close;
    private double dailyReturn;
    private double valorAcumulado;

    // Construtores, getters e setters

    public void calculateDailyReturn() {
        this.dailyReturn = (this.close - this.open) / this.open * 100;
    }
}
