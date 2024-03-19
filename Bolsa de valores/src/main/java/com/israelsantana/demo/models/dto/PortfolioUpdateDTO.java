package com.israelsantana.demo.models.dto;

import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PortfolioUpdateDTO {
    
    @NotNull
    private Long id;
    
    @NotNull
    private Long userId;

    @NotNull
    private Long actionId;
}
