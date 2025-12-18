package com.example.shoppinglist.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.shoppinglist.R;
import com.example.shoppinglist.data.ShoppingList;
import com.example.shoppinglist.ui.adapters.CheckProductAdapter;
import com.example.shoppinglist.ui.adapters.EditProductAdapter;
import com.example.shoppinglist.viewmodel.ShoppingViewModel;

import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class EditListFragment extends Fragment {

    private ShoppingViewModel viewModel;

    private EditProductAdapter adapter;

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
                adapter = new EditProductAdapter(requireContext(), shoppingList.items);
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

            if (adapter == null) return;

            // 1. Сначала собираем все данные
            List<String> currentItems = adapter.getAllItems();
            EditText etTitle = view.findViewById(R.id.et_title);
            String newTitle = etTitle.getText().toString().trim(); // .trim() убирает пробелы по краям

            // ПРОВЕРКА №1: Список не должен быть пустым
            if (currentItems.isEmpty()) {
                showErrorDialog("The list is empty! Add at least one product.");
                return; // Останавливаем выполнение, не сохраняем и не выходим
            }

            // ПРОВЕРКА №2: Название не должно быть пустым
            if (newTitle.isEmpty()) {
                showErrorDialog("Please enter a list title.");
                return;
            }

            // ПРОВЕРКА №3: Уникальность имени
            ShoppingList currentList = viewModel.getSelectedList().getValue();
            List<ShoppingList> allLists = viewModel.getTemplateLists(requireContext()).getValue();

            boolean isNameTaken = false;
            if (allLists != null) {
                for (ShoppingList list : allLists) {
                    // Если имя совпадает (игнорируя регистр) И это не тот же самый список, который мы сейчас редактируем
                    if (list.title.equalsIgnoreCase(newTitle) && list != currentList) {
                        isNameTaken = true;
                        break;
                    }
                }
            }

            if (isNameTaken) {
                showErrorDialog("A list with this name already exists. Please think of a different name.");
                return;
            }

            // --- ЕСЛИ ВСЕ ПРОВЕРКИ ПРОЙДЕНЫ, СОХРАНЯЕМ ---

            if (currentList != null) {
                currentList.title = newTitle;
                currentList.items = (java.util.ArrayList<String>) currentItems;

                // Если списка еще нет в базе (новый) - добавляем, иначе обновляем
                if (allLists != null && !allLists.contains(currentList)) {
                    viewModel.addTemplateList(requireContext(), currentList);
                } else {
                    viewModel.updateTemplateList(requireContext());
                }
            }

            // Выходим назад
            if (getParentFragmentManager() != null) {
                getParentFragmentManager().popBackStack();
            }
        });

        ImageButton btnDelete = view.findViewById(R.id.btn_delete);
        btnDelete.setOnClickListener(v -> {
            viewModel.deleteTemplateList(getContext(), viewModel.getSelectedList().getValue());
            getParentFragmentManager().popBackStack("start menu", 0);
        });

    }

    private void showErrorDialog(String message) {
        new androidx.appcompat.app.AlertDialog.Builder(requireContext())
                .setTitle("Note")
                .setMessage(message)
                .setPositiveButton("OK", null) // Кнопка, чтобы просто закрыть диалог
                .show();
    }
}
