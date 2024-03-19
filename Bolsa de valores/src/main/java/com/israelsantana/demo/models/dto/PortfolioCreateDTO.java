package com.israelsantana.demo.models.dto;

import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PortfolioCreateDTO {
    
    @NotNull
    private long userId;

    @NotNull
    private long actionId;
}
