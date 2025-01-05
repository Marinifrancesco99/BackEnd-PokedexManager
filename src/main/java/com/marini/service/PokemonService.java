package com.marini.service;

import java.util.List;

import com.marini.dao.PokemonDao;
import com.marini.model.Pokemon;

public class PokemonService {
    private PokemonDao pokemonDao = new PokemonDao();

    public List<Pokemon> getAllPokemon (){
        return pokemonDao.getAllPokemon();
    }

    public Pokemon getByNationalNumber(int national_number){
        return pokemonDao.getByNationalNumber(national_number);
    }
}
