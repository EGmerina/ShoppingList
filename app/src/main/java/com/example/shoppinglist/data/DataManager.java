package com.example.shoppinglist.data;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class DataManager {
    private static final String FILE_TEMPLATES = "template_lists.json";
    private static final String FILE_HISTORY = "history_lists.json";

    private static void saveTo(Context context, List<ShoppingList> lists, String fileName) {
        Gson gson = new Gson();
        String json = gson.toJson(lists);
        try (FileOutputStream fos = context.openFileOutput(fileName, Context.MODE_PRIVATE)) {
            fos.write(json.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static List<ShoppingList> loadFrom(Context context, String fileName) {
        Gson gson = new Gson();

        try (FileInputStream fis = context.openFileInput(fileName);
             InputStreamReader isr = new InputStreamReader(fis);
             BufferedReader reader = new BufferedReader(isr)) {

            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
            String jsonString = sb.toString();

            Type listType = new TypeToken<ArrayList<ShoppingList>>() {
            }.getType();
            List<ShoppingList> loadedList = gson.fromJson(jsonString, listType);
            if (loadedList == null) {
                return new ArrayList<ShoppingList>();
            }
            return loadedList;

        } catch (IOException e) {
            return new ArrayList<ShoppingList>();
        }
    }

    public static void saveTemplate(Context context, List<ShoppingList> lists) {
        saveTo(context, lists, FILE_TEMPLATES);
    }

    public static List<ShoppingList> loadActive(Context context) {
        return loadFrom(context, FILE_TEMPLATES);
    }

    public static void saveHistory(Context context, List<ShoppingList> lists) {
        saveTo(context, lists, FILE_HISTORY);
    }

    public static List<ShoppingList> loadHistory(Context context) {
        return loadFrom(context, FILE_HISTORY);
    }

}
