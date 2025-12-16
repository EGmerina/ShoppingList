package com.example.shoppinglist.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

import com.example.shoppinglist.R;

import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import java.util.ArrayList;

public class ListViewFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_list_view, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ImageButton btnBack = view.findViewById(R.id.btn_back);
        btnBack.setOnClickListener(v -> {
            if (getParentFragmentManager() != null) {
                getParentFragmentManager().popBackStack();
            }
        });

        ImageButton btnEdit = view.findViewById(R.id.btn_edit);
        btnEdit.setOnClickListener(v -> {
            getParentFragmentManager().beginTransaction()
                    .setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
                    .replace(R.id.fragment_container, new EditListFragment())
                    .addToBackStack(null)
                    .commit();
        });

        ImageButton btnFinishShopping = view.findViewById(R.id.finish_button);
        btnFinishShopping.setOnClickListener(v -> {
            // здесь сохранение списка
            if (getParentFragmentManager() != null) {
                getParentFragmentManager().popBackStack();
            }
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
