package com.example.shoppinglist;

import androidx.fragment.app.Fragment;

public class MainListFragment extends Fragment {
    @Override
    public android.view.View onCreateView(android.view.LayoutInflater inflater, android.view.ViewGroup container, android.os.Bundle savedInstanceState) {
        android.view.View view = inflater.inflate(R.layout.fragment_main_list, container, false);

        android.widget.ListView listView = view.findViewById(R.id.main_list_view);
        java.util.List<ShoppingItem> items = new java.util.ArrayList<>();
        items.add(new ShoppingItem("List for Maria-ra", "12-12-2025", android.graphics.Color.parseColor("#98D8C1")));
        items.add(new ShoppingItem("Weekend BBQ", "13-12-2025", android.graphics.Color.YELLOW));

        listView.setAdapter(new ShoppingAdapter(getContext(), items));
        return view;
    }
}
