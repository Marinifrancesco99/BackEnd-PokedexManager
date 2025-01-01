package com.marini.service;

import com.marini.dao.UsersDao;
import com.marini.model.Users;

public class UsersService {
    private UsersDao userService = new UsersDao();

    public Users getUserByusername (String username) {
        return userService.getUserByusername(username);
    }
}
