package com.example.shoppinglist;

public class ShoppingList {
    private String title;
    private String date;
    private int color; // Для цветных кружочков

    public ShoppingList(String title, String date, int color) {
        this.title = title;
        this.date = date;
        this.color = color;
    }
    // геттеры...
}