package com.example.shoppinglist.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.shoppinglist.R;
import com.example.shoppinglist.ui.adapters.ViewProductAdapter;
import com.example.shoppinglist.viewmodel.ShoppingViewModel;

import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

public class ViewHistoryListFragment extends Fragment {
    private ShoppingViewModel viewModel;

    private ViewProductAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_history_list_view, container, false);
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
                TextView title = view.findViewById(R.id.et_title);
                title.setText(shoppingList.title);
                adapter = new ViewProductAdapter(requireContext(), shoppingList.items);
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

        ImageButton btnDelete = view.findViewById(R.id.btn_delete);
        btnDelete.setOnClickListener(v -> {
            viewModel.deleteHistoryList(getContext(), viewModel.getSelectedList().getValue());
            getParentFragmentManager().popBackStack();
        });

    }

}
