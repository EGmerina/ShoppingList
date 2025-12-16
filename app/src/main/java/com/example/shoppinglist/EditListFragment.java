package com.example.shoppinglist;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import java.util.ArrayList;

public class EditListFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_edit_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Кнопка Назад
        ImageButton btnBack = view.findViewById(R.id.btn_back);
        btnBack.setOnClickListener(v -> {
            if (getParentFragmentManager() != null) {
                getParentFragmentManager().popBackStack();
            }
        });

        // Кнопка Готово (сохранение)
        ImageButton btnDone = view.findViewById(R.id.btn_done);
        btnDone.setOnClickListener(v -> {
            // Тут логика сохранения
            getParentFragmentManager().popBackStack();
        });

        ImageButton btnDelete = view.findViewById(R.id.btn_delete);
        btnDone.setOnClickListener(v -> {
            // Тут логика удаления
            getParentFragmentManager().popBackStack();
        });

        // Заполнение списка (item 1, item 2...)
        ListView listView = view.findViewById(R.id.items_list_view);
        ArrayList<String> items = new ArrayList<>();
        items.add("item 1");
        items.add("item 2");
        items.add("item 3");
        items.add("item 4");

        // Используем простой адаптер для демонстрации
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), R.layout.product_item, R.id.item_text, items);
        listView.setAdapter(adapter);
    }
}
