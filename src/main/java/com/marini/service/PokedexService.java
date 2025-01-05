package com.marini.service;

import java.util.List;

import com.marini.dao.PokedexDao;
import com.marini.model.Pokemon;

public class PokedexService {
    private PokedexDao pokedexDao = new PokedexDao();

    public boolean addToPokedex (int national_number, int userId) {
        return pokedexDao.addToPokedex(national_number, userId);
    } 

    public List<Pokemon> getAllPokedexPokemon (int userId) {
        return pokedexDao.getAllPokedexPokemon(userId);
    } 
}
