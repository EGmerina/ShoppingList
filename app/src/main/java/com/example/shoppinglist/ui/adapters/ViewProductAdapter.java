package com.example.shoppinglist.ui.adapters;

import android.content.Context;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import com.example.shoppinglist.R;
import java.util.ArrayList;
import java.util.List;

public class ViewProductAdapter extends ArrayAdapter<String> {

    private final Context context;
    private final List<String> items;

    public ViewProductAdapter(Context context, List<String> items) {
        super(context, 0, items);
        this.context = context;
        this.items = items;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = android.view.LayoutInflater.from(getContext()).inflate(R.layout.view_product_item, parent, false);
        }
        String text = getItem(position);
        if (text == null) {
            System.out.println("text not found");
            return convertView;
        }

        android.widget.TextView textView = convertView.findViewById(R.id.item_text);

        textView.setText(text);

        return convertView;
    }

    public List<String> getAllItems() {
        List<String> result = new ArrayList<>();
        for (String item : items) {
            if (item != null && !item.trim().isEmpty()) {
                result.add(item.trim());
            }
        }
        return result;
    }

}