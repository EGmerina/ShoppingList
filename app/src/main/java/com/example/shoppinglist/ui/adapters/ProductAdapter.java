package com.example.shoppinglist.ui.adapters;

import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.widget.CheckBox;

import androidx.core.content.ContextCompat;

import com.example.shoppinglist.R;

public class ProductAdapter extends android.widget.ArrayAdapter<String> {
    public ProductAdapter(android.content.Context context, java.util.List<String> items) {
        super(context, 0, items);
    }

    @Override
    public android.view.View getView(int position, android.view.View convertView, android.view.ViewGroup parent) {
        if (convertView == null) {
            convertView = android.view.LayoutInflater.from(getContext()).inflate(R.layout.product_item, parent, false);
        }
        String product = getItem(position);
        if (product == null) {
            System.out.println("product not found");
            return convertView;
        }
        CheckBox checkBox = convertView.findViewById(R.id.item_checkbox);
        android.widget.EditText data = convertView.findViewById(R.id.item_text);
        checkBox.setOnCheckedChangeListener(null);

        data.setText(product);


        // Вешаем новый слушатель на галочку
        checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {

            if (isChecked) {
                checkBox.setPaintFlags(checkBox.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            } else {
                checkBox.setPaintFlags(checkBox.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
            }
        });

        return convertView;
    }
}
