package com.marini.service;

import java.util.List;

import com.marini.dao.WishlistDao;
import com.marini.model.Pokemon;

public class WishlistService {
    WishlistDao wishlistDao = new WishlistDao();

    public boolean addToWishlist(int national_number, int userId) {
        return wishlistDao.addToWishlist(national_number, userId);
    }

    public List<Pokemon> getAllWishlistPokemon(int userId) {
        return wishlistDao.getAllWishlistPokemon(userId);
    }

    public boolean removeFromWishlist(int national_number, int userId) {
        return wishlistDao.removeFromWishlist(national_number, userId);
    }

}
