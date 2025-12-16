package com.example.shoppinglist.viewmodel;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.shoppinglist.data.DataManager;
import com.example.shoppinglist.data.ShoppingList;

import java.util.List;

public class ShoppingViewModel extends ViewModel {
    private MutableLiveData<List<ShoppingList>> allLists = null;

    public LiveData<List<ShoppingList>> getAllLists(Context context) {
        if (allLists == null) {
            allLists = new MutableLiveData<>();
            allLists.setValue(DataManager.loadLists(context));
        }
        return allLists;
    }

    public void addList(Context context, ShoppingList newList) { //TODO проверка на null
        List<ShoppingList> current = allLists.getValue();
        if (current != null) {
            current.add(newList);
            allLists.setValue(current); // Уведомляем фрагменты
            DataManager.saveLists(context, current); // Сохраняем в JSON
        }
    }


    public void update(Context context) {
        DataManager.saveLists(context, allLists.getValue());
        allLists.setValue(allLists.getValue()); // Перерисовываем UI
    }
}
