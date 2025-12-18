package com.example.shoppinglist.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.shoppinglist.R;
import com.example.shoppinglist.data.ShoppingList;
import com.example.shoppinglist.ui.adapters.ProductAdapter;
import com.example.shoppinglist.ui.adapters.ShoppingListAdapter;
import com.example.shoppinglist.viewmodel.ShoppingViewModel;

import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class ViewListFragment extends Fragment {
    private ShoppingViewModel viewModel;

    private ProductAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_list_view, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = new ViewModelProvider(requireActivity()).get(ShoppingViewModel.class);
        setupListView(view);
        setupButtons(view);
    }

    private void setupListView(View view) {
        ListView listView = view.findViewById(R.id.items_list_view);
        listView.setAdapter(adapter);
        viewModel.getSelectedList().observe(getViewLifecycleOwner(), shoppingList -> {
            if (shoppingList != null) {
                EditText titleEdit = view.findViewById(R.id.et_title);
                titleEdit.setText(shoppingList.title);
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


        Button btnFinishShopping = view.findViewById(R.id.finish_button);
        btnFinishShopping.setOnClickListener(v -> {

            if (adapter != null) {
                List<String> boughtItems = adapter.getCheckedItems();
                ShoppingList newList = new ShoppingList((ArrayList<String>) boughtItems, viewModel.getSelectedList().getValue().title);
                viewModel.addHistoryList(getContext(), newList);
                viewModel.updateTemplateList(getContext());
            }

            // viewModel.addHistoryList(getContext(), new ShoppingList(viewModel.getSelectedList().getValue())); //TODO заполнить список только отмеченными

            if (getParentFragmentManager() != null) {
                getParentFragmentManager().popBackStack();
            }
        });
    }

}
