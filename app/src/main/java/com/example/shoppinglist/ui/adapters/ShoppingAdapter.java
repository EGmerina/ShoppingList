package com.example.shoppinglist.ui.adapters;

import com.example.shoppinglist.R;

public class ShoppingAdapter extends android.widget.ArrayAdapter<ShoppingItem>{
    public ShoppingAdapter(android.content.Context context, java.util.List<ShoppingItem> items) {
        super(context, 0, items);
    }

    @Override
    public android.view.View getView(int position, android.view.View convertView, android.view.ViewGroup parent) {
        if (convertView == null) {
            convertView = android.view.LayoutInflater.from(getContext()).inflate(R.layout.item_shopping_list, parent, false);
        }
        ShoppingItem item = getItem(position);
        android.view.View colorCircle = convertView.findViewById(R.id.color_indicator);
        android.widget.TextView title = convertView.findViewById(R.id.tv_title);
        android.widget.TextView date = convertView.findViewById(R.id.tv_date);

        title.setText(item.title);
        date.setText(item.date);
        // Установка цвета кружка (нужен drawable-ресурс circle_shape)
        colorCircle.getBackground().setColorFilter(item.color, android.graphics.PorterDuff.Mode.SRC_IN);

        return convertView;
    }
}
