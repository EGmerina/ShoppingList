package com.example.shoppinglist.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.shoppinglist.R;
import com.example.shoppinglist.data.ShoppingList;
import com.example.shoppinglist.ui.adapters.ProductAdapter;
import com.example.shoppinglist.viewmodel.ShoppingViewModel;

import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class EditListFragment extends Fragment {

    private ShoppingViewModel viewModel;

    private ProductAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_edit_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = new ViewModelProvider(requireActivity()).get(ShoppingViewModel.class);
        setupListView(view);
        setupButtons(view);
    }

    private void setupListView(View view) {
        ListView listView = view.findViewById(R.id.items_edit_list_view);
        listView.setAdapter(adapter);
        viewModel.getSelectedList().observe(getViewLifecycleOwner(), shoppingList -> {
            if (shoppingList != null) {
                EditText titleEdit = view.findViewById(R.id.et_title);
                titleEdit.setText(shoppingList.title);
                if (shoppingList.items == null) {
                    shoppingList.items = new ArrayList<>();
                }
                if (shoppingList.items.isEmpty()) {
                    shoppingList.items.add("");
                }
                adapter = new ProductAdapter(requireContext(), shoppingList.items);
                listView.setAdapter(adapter);
            }
        });
    }

    private void setupButtons(View view) {

        ImageButton btnBack = view.findViewById(R.id.btn_back);
        btnBack.setOnClickListener(v -> {
            if (getParentFragmentManager() != null) {
                getParentFragmentManager().popBackStack();
            }
        });

        ImageButton btnDone = view.findViewById(R.id.btn_done);
        btnDone.setOnClickListener(v -> {

            if (adapter != null) {
                // 1. Берем продукты
                List<String> currentItems = adapter.getAllItems();

                // 2. Получаем текущий редактируемый объект
                ShoppingList currentList = viewModel.getSelectedList().getValue();

                if (currentList != null) {
                    // Обновляем данные в объекте
                    EditText etTitle = view.findViewById(R.id.et_title);
                    String newTitle = etTitle.getText().toString();

                    // Если названия нет, дадим дефолтное, чтобы не терять список
                    if (newTitle.trim().isEmpty()) {
                        newTitle = "Новый список";
                    }
                    currentList.title = newTitle;
                    currentList.items = (ArrayList<String>) currentItems; // Каст (ArrayList) не обязателен

                    // --- ГЛАВНОЕ ИСПРАВЛЕНИЕ НИЖЕ ---

                    // 3. Получаем общий список всех шаблонов
                    List<ShoppingList> allLists = viewModel.getTemplateLists(requireContext()).getValue();

                    // 4. Проверяем: если нашего списка нет внутри общего списка, добавляем его
                    if (allLists != null && !allLists.contains(currentList)) {
                        viewModel.addTemplateList(requireContext(), currentList);
                    } else {
                        // Если он уже там есть, просто сохраняем обновления
                        viewModel.updateTemplateList(requireContext());
                    }
                }
            }
        });

        ImageButton btnDelete = view.findViewById(R.id.btn_delete);
        btnDelete.setOnClickListener(v -> {
            viewModel.deleteTemplateList(getContext(), viewModel.getSelectedList().getValue());
            getParentFragmentManager().popBackStack();
        });

    }
}
