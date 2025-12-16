package com.example.shoppinglist.ui.adapters;

import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;

import androidx.core.content.ContextCompat;

import com.example.shoppinglist.R;
import com.example.shoppinglist.data.ShoppingList;

public class ShoppingListAdapter extends android.widget.ArrayAdapter<ShoppingList> {

    private final int[] colorResIds = {
            R.color.secondary_pink,
            R.color.secondary_green,
            R.color.secondary_blue,
            R.color.secondary_orange,
            R.color.secondary_red
    };

    public ShoppingListAdapter(android.content.Context context, java.util.List<ShoppingList> items) {
        super(context, 0, items);
    }

    @Override
    public android.view.View getView(int position, android.view.View convertView, android.view.ViewGroup parent) {
        if (convertView == null) {
            convertView = android.view.LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);
        }
        ShoppingList list = getItem(position);
        if (list == null) {
            System.out.println("list not found");
            return convertView;
        }

        android.view.View colorCircle = convertView.findViewById(R.id.color_indicator);
        android.widget.TextView title = convertView.findViewById(R.id.tv_title);
        android.widget.TextView date = convertView.findViewById(R.id.tv_date);

        title.setText(list.title);

        if (list.date != null) {
            date.setText(list.date);
        }

        int hash = list.title.hashCode(); //выбираем цвет по хэшу
        int index = Math.abs(hash) % colorResIds.length;
        int colorRes = colorResIds[index];
        int resolvedColor = ContextCompat.getColor(getContext(), colorRes);

        Drawable background = colorCircle.getBackground();
        if (background != null) {
            background.mutate().setColorFilter(resolvedColor, PorterDuff.Mode.SRC_IN);
        }

        return convertView;
    }
}
