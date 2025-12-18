package com.example.shoppinglist.ui.adapters;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.SparseBooleanArray;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.shoppinglist.R;

import java.util.ArrayList;
import java.util.List;

// Теперь наследуемся от String
public class CheckProductAdapter extends ArrayAdapter<String> {

    private final Context context;
    private final List<String> items; // Список строк
    private final SparseBooleanArray checkedStates = new SparseBooleanArray();

    public CheckProductAdapter(Context context, List<String> items) {
        super(context, 0, items);
        this.context = context;
        this.items = items;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.check_product_item, parent, false);
            holder = new ViewHolder();
            holder.checkBox = convertView.findViewById(R.id.item_checkbox);
            holder.textView = convertView.findViewById(R.id.item_text);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        String currentText = getItem(position);

        // 1. Снимаем старые слушатели
        holder.checkBox.setOnCheckedChangeListener(null);
        if (holder.textWatcher != null) {
            holder.textView.removeTextChangedListener(holder.textWatcher);
        }
        holder.textView.setOnKeyListener(null);

        // 2. Устанавливаем данные
        holder.checkBox.setChecked(checkedStates.get(position, false));
        holder.textView.setText(currentText);

        // 3. Логика Чекбокса (осталась прежней)
        holder.checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                checkedStates.put(position, true);
            } else {
                checkedStates.delete(position);
            }
        });

        // 4. Логика ввода текста (ИЗМЕНИЛАСЬ)
        holder.textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // Мы не можем изменить String, поэтому мы обновляем элемент в списке по индексу
                items.set(position, s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {}
        };
        holder.textView.addTextChangedListener(holder.textWatcher);

        // 5. Логика Enter (добавляем пустую строку "")
        holder.textView.setOnKeyListener((v, keyCode, event) -> {
            if (event.getAction() == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                items.add(""); // Добавляем пустую строку
                notifyDataSetChanged();
                return true;
            }
            return false;
        });

        return convertView;
    }

    public List<String> getAllItems() {
        List<String> result = new ArrayList<>();
        for (String item : items) {
            // Проверяем, что строка не пустая и не состоит из одних пробелов
            if (item != null && !item.trim().isEmpty()) {
                result.add(item.trim());
            }
        }
        return result;
    }

    // Возвращаем список строк, которые были отмечены
    public List<String> getCheckedItems() {
        List<String> result = new ArrayList<>();
        for (int i = 0; i < items.size(); i++) {
            if (checkedStates.get(i)) {
                result.add(items.get(i));
            }
        }
        return result;
    }

    private static class ViewHolder {
        CheckBox checkBox;
        TextView textView;
        TextWatcher textWatcher;
    }
}