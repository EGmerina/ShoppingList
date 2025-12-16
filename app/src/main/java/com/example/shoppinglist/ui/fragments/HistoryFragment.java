package com.example.shoppinglist.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.shoppinglist.data.ShoppingList;
import com.example.shoppinglist.ui.MainActivity;
import com.example.shoppinglist.R;
import com.example.shoppinglist.ui.adapters.ShoppingListAdapter;
import com.example.shoppinglist.viewmodel.ShoppingViewModel;

import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import java.util.ArrayList;

public class HistoryFragment extends Fragment {
    private ShoppingViewModel viewModel;
    private ShoppingListAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_history, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel = new ViewModelProvider(requireActivity()).get(ShoppingViewModel.class);

        setupListView(view);
        setupButtons(view);

        viewModel.getTemplateLists(requireContext()).observe(getViewLifecycleOwner(), lists -> {
            adapter.clear();
            if (lists != null) {
                adapter.addAll(lists);
            }
            adapter.notifyDataSetChanged();
        });
    }

    private void setupListView(View view) {
        ListView listView = view.findViewById(R.id.history_list_view);

        adapter = new ShoppingListAdapter(requireContext(), new ArrayList<>());
        listView.setAdapter(adapter);

        listView.setOnItemClickListener((parent, v, position, id) -> {

            ShoppingList list = adapter.getItem(position);
            viewModel.selectList(list);
            getParentFragmentManager().beginTransaction()
                    .setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
                    .replace(R.id.fragment_container, new ViewListFragment())
                    .addToBackStack(null)
                    .commit();
        });
    }

    private void setupButtons(View view) {
        ImageButton btnMenu = view.findViewById(R.id.btn_menu);
        btnMenu.setOnClickListener(v -> {
            if (getActivity() instanceof MainActivity) {
                ((MainActivity) getActivity()).openDrawer();
            }
        });

    }
}
