package com.borbely.pokemon.dto;

import com.borbely.pokemon.model.Pokemon;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReviewDto {

    private int id;
    private String title;
    private String content;
    private int stars;
//    private Pokemon pokemon;

}
