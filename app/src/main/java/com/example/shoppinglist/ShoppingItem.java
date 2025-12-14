package com.example.shoppinglist;

public class ShoppingItem {
    String title;
    String date;
    int color; // например, Color.RED

    public ShoppingItem(String title, String date, int color) {
        this.title = title;
        this.date = date;
        this.color = color;
    }
}
