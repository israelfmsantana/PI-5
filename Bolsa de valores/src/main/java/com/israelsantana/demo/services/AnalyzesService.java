package com.israelsantana.demo.services;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.israelsantana.demo.models.Action;
import com.israelsantana.demo.models.Analyzes;

@Service
public class AnalyzesService {

    @Autowired
    private ActionService actionService;

    public String classifyStocks() {
        List<Analyzes> actionsList = getStockData();
        double investimentoInicial = 100;


        Map<String, List<Analyzes>> groupedStockData = actionsList.stream()
                .collect(Collectors.groupingBy(Analyzes::getName));

        StringBuilder result = new StringBuilder();


        for (List<Analyzes> group : groupedStockData.values()) {
            double valorAcumuladoHoje = investimentoInicial;


            List<Analyzes> todayData = group.stream()
                    .filter(stockData -> stockData.getDate().equals("Mar 12, 2024"))  // Substitua "Mar 12, 2024" pela data de hoje
                    .collect(Collectors.toList());

            if (!todayData.isEmpty()) {
                for (Analyzes stockData : todayData) {
                    stockData.calculateDailyReturn();
                    valorAcumuladoHoje *= 1 + stockData.getDailyReturn() / 100;
                }

                Analyzes diaCompraHoje = todayData.stream().min(Comparator.comparingDouble(Analyzes::getDailyReturn)).orElse(null);

                if (diaCompraHoje != null) {
                    result.append("Ação ").append(diaCompraHoje.getName()).append(" hoje (Mar 12, 2024): ")
                            .append("Retorno: ").append(diaCompraHoje.getDailyReturn()).append("%\n");


                    List<Analyzes> filteredGroup = group.stream()
                            .filter(stockData -> !stockData.getDate().equals("Mar 12, 2024"))
                            .collect(Collectors.toList());

                    double valorAcumuladoCompra = investimentoInicial;

                    for (Analyzes stockData : filteredGroup) {
                        stockData.calculateDailyReturn(); 
                        valorAcumuladoCompra *= 1 + stockData.getDailyReturn() / 100;
                    }


                    Analyzes diaCompra = filteredGroup.stream().min(Comparator.comparingDouble(Analyzes::getDailyReturn)).orElse(null);

                    if (diaCompra != null) {
                        result.append("Melhor dia para comprar ").append(diaCompra.getName()).append(": ")
                                .append("Retorno: ").append(diaCompra.getDailyReturn()).append("%\n\n").append("Valor acumulado Hoje: ").append(valorAcumuladoHoje).append("   Valor acumulado Compra: ").append(valorAcumuladoCompra).append("%\n");

                        if (valorAcumuladoHoje > valorAcumuladoCompra) {
                            result.append("Classificação: ").append(diaCompra.getName()).append(" mais próximo\n\n\n\n");
                        } else {
                            result.append("Classificação: ").append(diaCompra.getName()).append(" mais distante\n\n\n\n");
                        }
                    }
                }
            }
        }

        return result.toString();
    }

    public String calculatedAnalist () {

        List<Analyzes> actionsList = getStockData();
        double investimentoInicial = 100;

        // Agrupar ações pelo nome
        Map<String, List<Analyzes>> groupedStockData = actionsList.stream()
                .collect(Collectors.groupingBy(Analyzes::getName));

        StringBuilder result = new StringBuilder();

        // Calcular para cada grupo de ações
        for (List<Analyzes> group : groupedStockData.values()) {
            double valorAcumulado = investimentoInicial;

            // Filtrar as ações com a data de hoje
            List<Analyzes> filteredGroup = group.stream()
                    .filter(stockData -> !stockData.getDate().equals("Mar 12, 2024"))  // Substitua "Mar 12, 2024" pela data de hoje
                    .collect(Collectors.toList());

            // Calcular retorno diário (% de mudança)
            for (Analyzes stockData : filteredGroup) {
                stockData.calculateDailyReturn();
            }

            // Calcular o valor ao longo do tempo para o grupo
            for (Analyzes stockData : filteredGroup) {
                valorAcumulado *= 1 + stockData.getDailyReturn() / 100;
                stockData.setValorAcumulado(valorAcumulado);
            }

            // Encontrar o dia de compra e venda para o grupo
            Analyzes diaCompra = filteredGroup.stream().min(Comparator.comparingDouble(Analyzes::getDailyReturn)).orElse(null);
            Analyzes diaVenda = filteredGroup.stream().max(Comparator.comparingDouble(Analyzes::getDailyReturn)).orElse(null);

            // Exibir resultados para o grupo
            if (diaCompra != null) {
                result.append("Melhor dia para comprar ").append(diaCompra.getName()).append(": ").append(diaCompra.getDate())
                        .append(" com retorno de: ").append(diaCompra.getDailyReturn()).append("%\n");
            }
            if (diaVenda != null) {
                result.append("Melhor dia para vender ").append(diaVenda.getName()).append(": ").append(diaVenda.getDate())
                        .append(" com retorno de: ").append(diaVenda.getDailyReturn()).append("%\n");
            }
            result.append("Valor acumulado ao longo do tempo para ").append(filteredGroup.get(0).getName()).append(": ")
                    .append(filteredGroup.get(filteredGroup.size() - 1).getValorAcumulado()).append("\n\n\n");
        }

        return result.toString();
    }



    private List<Analyzes> getStockData() {
        List<Analyzes> stockDataList = new ArrayList<>();

        List<Action> actions = this.actionService.findAll();
        for(Action obj : actions) {
            stockDataList.add(this.fromDTO(obj));
        }
        
        return stockDataList;
    }



    public Analyzes fromDTO(@Valid Action obj) {
        Analyzes stockData = new Analyzes();
        stockData.setName(obj.getName());;
        stockData.setDate(obj.getDate());
        
        obj.setClose(obj.getClose().replace(",", ""));
        stockData.setClose(Double.parseDouble(obj.getClose()));

        obj.setOpen(obj.getOpen().replace(",", ""));
        stockData.setOpen(Double.parseDouble(obj.getOpen()));

        return stockData;
    }
}
