package com.example.shoppinglist;

import androidx.fragment.app.Fragment;

public class HistoryFragment extends Fragment {
    @Override
    public android.view.View onCreateView(android.view.LayoutInflater inflater, android.view.ViewGroup container, android.os.Bundle savedInstanceState) {
        android.view.View view = inflater.inflate(R.layout.fragment_history, container, false);

        android.widget.ListView listView = view.findViewById(R.id.history_list_view);
        java.util.List<ShoppingItem> items = new java.util.ArrayList<>();
        items.add(new ShoppingItem("Archived: Party", "01-11-2025", android.graphics.Color.LTGRAY));

        listView.setAdapter(new ShoppingAdapter(getContext(), items));
        return view;
    }
}
