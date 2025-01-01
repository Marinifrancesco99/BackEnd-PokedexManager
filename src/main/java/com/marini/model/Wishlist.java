package com.marini.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Wishlist {
    @NonNull
    private int id_wishlist;
    @NonNull
    private int national_number;
    @NonNull
    private int id_user;
}
