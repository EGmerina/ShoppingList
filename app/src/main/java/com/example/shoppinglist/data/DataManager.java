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
    private static final String FILE_NAME = "shopping_lists_data.json";

    /**
     * Метод для сохранения списка списков
     *
     * @param context       нужен для доступа к файловой системе
     * @param shoppingLists список, который хотим сохранить
     */
    public static void saveLists(Context context, List<ShoppingList> shoppingLists) {
        Gson gson = new Gson();
        String jsonString = gson.toJson(shoppingLists);
        try (FileOutputStream fos = context.openFileOutput(FILE_NAME, Context.MODE_PRIVATE)) {
            fos.write(jsonString.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Метод для чтения данных при запуске
     *
     * @return возвращает сохраненный список или пустой список, если файла нет
     */
    public static List<ShoppingList> loadLists(Context context) {
        Gson gson = new Gson();

        try (FileInputStream fis = context.openFileInput(FILE_NAME);
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
}
