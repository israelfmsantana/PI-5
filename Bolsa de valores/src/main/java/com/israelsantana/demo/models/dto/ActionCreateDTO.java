package com.israelsantana.demo.models.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ActionCreateDTO {

    @Size(min = 2, max = 100)
    @NotBlank
    private String name;

    
    @Size(min = 2, max = 100)
    @NotBlank
    private String date;

   
    @Size(min = 2, max = 100)
    @NotBlank
    private String open;

    
    @Size(min = 2, max = 100)
    @NotBlank
    private String high;

    
    @Size(min = 2, max = 100)
    @NotBlank
    private String low;

    
    @Size(min = 2, max = 100)
    @NotBlank
    private String close;

    
    @Size(min = 2, max = 100)
    @NotBlank
    private String adjClose;

    
    @Size(min = 2, max = 100)
    @NotBlank
    private String volume;
}
