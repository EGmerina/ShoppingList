package com.example.shoppinglist.viewmodel;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.shoppinglist.data.DataManager;
import com.example.shoppinglist.data.ShoppingList;

import java.util.List;

public class ShoppingViewModel extends ViewModel {
    private MutableLiveData<List<ShoppingList>> templateLists = null;
    private MutableLiveData<List<ShoppingList>> historyLists = null;
    private final MutableLiveData<ShoppingList> selectedList = new MutableLiveData<>();

    public void selectList(ShoppingList list) {
        selectedList.setValue(list);
    }

    public LiveData<ShoppingList> getSelectedList() {
        return selectedList;
    }

    public LiveData<List<ShoppingList>> getTemplateLists(Context context) {
        if (templateLists == null) {
            templateLists = new MutableLiveData<>();
            templateLists.setValue(DataManager.loadActive(context));
        }
        return templateLists;
    }

    public LiveData<List<ShoppingList>> getHistoryLists(Context context) {
        if (historyLists == null) {
            historyLists = new MutableLiveData<>();
            historyLists.setValue(DataManager.loadHistory(context));
        }
        return historyLists;
    }


    public void addTemplateList(Context context, ShoppingList list) {
        List<ShoppingList> current = templateLists.getValue();
        if (current != null) {
            current.add(list);
            templateLists.setValue(current);
            DataManager.saveTemplate(context, current);
        }
    }

    public void addHistoryList(Context context, ShoppingList copyList) {
        if (historyLists == null) getHistoryLists(context);
        List<ShoppingList> history = historyLists.getValue();

        history.add(0, copyList);
        historyLists.setValue(history);
        DataManager.saveHistory(context, history);

    }

    public void updateTemplateList(Context context) {
        DataManager.saveTemplate(context, templateLists.getValue());
        templateLists.setValue(templateLists.getValue());
    }

    public void deleteTemplateList(Context context, ShoppingList listToDelete) {
        List<ShoppingList> current = templateLists.getValue();

        if (current != null) {
            current.remove(listToDelete);
            templateLists.setValue(current);
            DataManager.saveTemplate(context, current);
        }
    }

    public void deleteHistoryList(Context context, ShoppingList listToDelete) {
        List<ShoppingList> current = historyLists.getValue();

        if (current != null) {
            current.remove(listToDelete);
            historyLists.setValue(current);
            DataManager.saveHistory(context, current);
        }
    }
}
