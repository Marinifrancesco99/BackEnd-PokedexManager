package com.marini.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class Users {
    @NonNull
    private int id_user;
    @NonNull
    private String username;
    @NonNull
    private String email;
    @NonNull
    private String password;
}
