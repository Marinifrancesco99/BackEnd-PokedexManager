package com.marini.service;

import com.marini.dao.UsersDao;
import com.marini.model.Users;

public class UsersService {
    private UsersDao userService = new UsersDao();

    public Users getUserByusername (String username) {
        return userService.getUserByusername(username);
    }

    public boolean createUser(Users user) {
        return userService.createUser(user);
    }

    public boolean deleteUser (int id) {
        return userService.deleteUser(id);
    }
}
