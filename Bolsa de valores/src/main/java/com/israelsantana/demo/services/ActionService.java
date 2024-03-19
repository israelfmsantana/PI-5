package com.israelsantana.demo.services;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.israelsantana.demo.models.Action;
import com.israelsantana.demo.models.dto.ActionCreateDTO;
import com.israelsantana.demo.repositories.ActionRepository;
import com.israelsantana.demo.services.exceptions.ObjectNotFoundException;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

import javax.validation.Valid;


@Service
public class ActionService {

    @Autowired
    private ActionRepository actionRepository;

    public List<Action> obterDadosDaPagina(String url, String symbol) {
        List<Action> stockDataList = new ArrayList<>();

        try {
            Document document = Jsoup.connect(url).get();
            Elements rows = document.select("tbody tr");

            SimpleDateFormat dateFormat = new SimpleDateFormat("MMM dd, yyyy", Locale.ENGLISH);

            for (Element row : rows) {
                Action stockData = new Action();
                Elements columns = row.select("td");

                // Parse a data da coluna 0
                Date date = dateFormat.parse(columns.get(0).text());

                Date startDate = dateFormat.parse("Mar 1, 2024");
                Date endDate = dateFormat.parse("Mar 12, 2024");

                if (date.after(startDate) && date.before(endDate) || date.equals(startDate) || date.equals(endDate)) {
                    stockData.setName(symbol);
                    stockData.setDate(columns.get(0).text());
                    stockData.setOpen(columns.get(1).text());
                    stockData.setHigh(columns.get(2).text());
                    stockData.setLow(columns.get(3).text());
                    stockData.setClose(columns.get(4).text());
                    stockData.setAdjClose(columns.get(5).text());
                    stockData.setVolume(columns.get(6).text());

                    stockDataList.add(stockData);
                }
            }
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
        createList(stockDataList);
        return stockDataList;
    }



    public String obterHtmlDaPagina(String url) {
        try {
            Document document = Jsoup.connect(url).get();
            return document.html();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }


    public List<Action> findAll() {
        List<Action> actions = this.actionRepository.findAll();
        return actions;
    }



    @Transactional
    public List<Action> createList( List<Action> objs) {

        for(Action obj : objs) {
            obj.setId(null);
            this.actionRepository.save(obj);
        }
        return objs;
    }


    public Action fromDTO(@Valid ActionCreateDTO obj) {
        Action action = new Action();
        action.setAdjClose(obj.getAdjClose());
        action.setClose(obj.getClose());
        action.setDate(obj.getDate());
        action.setHigh(obj.getHigh());
        action.setLow(obj.getLow());
        action.setName(obj.getName());
        action.setOpen(obj.getOpen());
        action.setVolume(obj.getVolume());
        return action;
    }


    public Action findById(Long id) {
        Optional<Action> action = this.actionRepository.findById(id);
        return action.orElseThrow(() -> new ObjectNotFoundException(
                "Action not found! Id: " + id + ", Type: " + Action.class.getName()));
    }
}

