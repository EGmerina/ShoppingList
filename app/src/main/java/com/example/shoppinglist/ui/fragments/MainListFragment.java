package com.example.shoppinglist.ui.fragments;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.shoppinglist.ui.MainActivity;
import com.example.shoppinglist.R;
import com.example.shoppinglist.viewmodel.ShoppingViewModel;
import com.example.shoppinglist.data.ShoppingList;

public class MainListFragment extends Fragment {
    private ShoppingViewModel viewModel;

    @Override
    public android.view.View onCreateView(android.view.LayoutInflater inflater, android.view.ViewGroup container, android.os.Bundle savedInstanceState) {
        android.view.View view = inflater.inflate(R.layout.fragment_main_list, container, false);


        ImageButton btnMenu = view.findViewById(R.id.btn_menu);

        btnMenu.setOnClickListener(v -> {
            if (getActivity() instanceof MainActivity) {
                ((MainActivity) getActivity()).openDrawer();
            }
        });


        ImageButton btnCreate = view.findViewById(R.id.create_list_button);

        btnCreate.setOnClickListener(v -> {

            ShoppingList newList = new ShoppingList();

            viewModel.addList(getContext(), newList); //TODO лучше потом добавлять
            int newIndex = viewModel.getAllLists(getContext()).getValue().size() - 1;
            openEditFragment(newIndex);
        });


        android.widget.ListView listView = view.findViewById(R.id.main_list_view);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                getParentFragmentManager().beginTransaction()
                        .setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
                        .replace(R.id.fragment_container, new ListViewFragment())
                        .addToBackStack(null)
                        .commit();
            }
        });

        viewModel = new ViewModelProvider(requireActivity()).get(ShoppingViewModel.class);

        viewModel.getAllLists(getContext()).observe(getViewLifecycleOwner(), lists -> {
            // Как только данные в JSON или памяти изменятся, этот код сработает сам
            adapter.clear();
            adapter.addAll(lists);
            adapter.notifyDataSetChanged();
        });

        return view;
    }
}
