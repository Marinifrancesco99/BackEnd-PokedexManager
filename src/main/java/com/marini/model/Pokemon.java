package com.marini.model;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Pokemon {
    @NonNull
    private int national_number;

    private String gen;

    private String english_name;

    private String primary_type;

    private String secondary_type;

    private String classification;

    private double percent_male;

    private double percent_female;

    private double height_m;

    private double weight_kg;

    private int capture_rate;

    private int hp;

    private int attack;

    private int defense;

    private int speed;

    private String abilities_0;

    private String abilities_1;

    private String abilities_special;

    private int is_sublegendary;

    private int is_legendary;

    private int is_mythical;

    private String evochain_0;

    private String evochain_2;

    private String evochain_4;

    private String mega_evolution;

    private String description;

}
