package com.example.shoppinglist.ui.fragments;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;

import androidx.fragment.app.Fragment;

import com.example.shoppinglist.ui.MainActivity;
import com.example.shoppinglist.R;

public class HistoryFragment extends Fragment {
    @Override
    public android.view.View onCreateView(android.view.LayoutInflater inflater, android.view.ViewGroup container, android.os.Bundle savedInstanceState) {
        android.view.View view = inflater.inflate(R.layout.fragment_history, container, false);

        android.widget.ListView listView = view.findViewById(R.id.history_list_view);

        ImageButton btnMenu = view.findViewById(R.id.btn_menu);

        btnMenu.setOnClickListener(v -> {
            if (getActivity() instanceof MainActivity) {
                ((MainActivity) getActivity()).openDrawer();
            }
        });



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

        return view;
    }


}
