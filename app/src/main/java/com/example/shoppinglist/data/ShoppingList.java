package com.example.shoppinglist.data;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class ShoppingList {
    public String title;
    public ArrayList<String> items;
    public String date = null;

    public ShoppingList() {
        items = new ArrayList<>();
    }

    public ShoppingList(ShoppingList copy) {
        this.title = copy.title;
        this.items = new ArrayList<>(copy.items);

        Date now = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy HH:mm", Locale.getDefault());
        String dateString = formatter.format(now);
        this.date = dateString;
    }

    public ShoppingList(ArrayList<String> items, String title) {
        this.title = title;
        this.items = new ArrayList<>(items);

        Date now = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy HH:mm", Locale.getDefault());
        String dateString = formatter.format(now);
        this.date = dateString;
    }
}
