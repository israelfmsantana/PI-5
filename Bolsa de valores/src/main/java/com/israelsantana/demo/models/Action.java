package com.israelsantana.demo.models;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = Action.TABLE_NAME)
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Action {

    public static final String TABLE_NAME = "actions";
    
    @Id
    @Column(name = "id", unique = true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", length = 100, nullable = false)
    @Size(min = 2, max = 100)
    @NotBlank
    private String name;

    @Column(name = "date", length = 100)
    @Size(min = 0, max = 100)
    @NotBlank
    private String date;

    @Column(name = "open", length = 100)
    @Size(min = 0, max = 100)
    @NotBlank
    private String open;

    @Column(name = "high", length = 100)
    @Size(min = 0, max = 100)
    @NotBlank
    private String high;

    @Column(name = "low", length = 100)
    @Size(min = 0, max = 100)
    @NotBlank
    private String low;

    @Column(name = "close", length = 100)
    @Size(min = 0, max = 100)
    @NotBlank
    private String close;

    @Column(name = "adjClose", length = 100)
    @Size(min = 0, max = 100)
    @NotBlank
    private String adjClose;

    @Column(name = "volume", length = 50)
    @Size(min = 0, max = 100)
    @NotBlank
    private String volume;


    @OneToMany(mappedBy = "action")
    @JsonProperty(access = Access.WRITE_ONLY)
    private List<Portfolio> portfolios = new ArrayList<Portfolio>();


    @Override
    public String toString() {
        return "StockData{" +
                "name='" + name + '\'' +
                ", date='" + date + '\'' +
                ", open='" + open + '\'' +
                ", high='" + high + '\'' +
                ", low='" + low + '\'' +
                ", close='" + close + '\'' +
                ", adjClose='" + adjClose + '\'' +
                ", volume='" + volume + '\'' +
                '}';
    }
}
