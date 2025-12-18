package com.example.shoppinglist.ui.adapters;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.EditText;

import com.example.shoppinglist.R;

import java.util.ArrayList;
import java.util.List;

public class EditProductAdapter extends ArrayAdapter<String> {

    private final Context context;
    private final List<String> items;

    // Переменная для управления фокусом
    private int focusPosition = -1;

    public EditProductAdapter(Context context, List<String> items) {
        super(context, 0, items);
        this.context = context;
        this.items = items;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.edit_product_item, parent, false);
            holder = new ViewHolder();
            holder.editText = convertView.findViewById(R.id.item_text);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        String currentText = getItem(position);

        // Снимаем слушатели перед изменением текста, чтобы не вызвать бесконечный цикл
        if (holder.textWatcher != null) {
            holder.editText.removeTextChangedListener(holder.textWatcher);
        }
        holder.editText.setOnEditorActionListener(null); // Сбрасываем старый слушатель

        holder.editText.setText(currentText);

        // --- ЛОГИКА ФОКУСА ---
        if (position == focusPosition) {
            holder.editText.post(() -> {
                holder.editText.requestFocus();
                holder.editText.setSelection(holder.editText.getText().length());
            });
            focusPosition = -1; // Сбрасываем флаг
        }
        // ---------------------

        holder.textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                items.set(position, s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {}
        };
        holder.editText.addTextChangedListener(holder.textWatcher);

        // --- ГЛАВНОЕ ИСПРАВЛЕНИЕ: OnEditorActionListener ---
        holder.editText.setOnEditorActionListener((v, actionId, event) -> {
            // Ловим нажатие "Далее" (Next) или "Готово" (Done), или чистого Enter (KEYCODE_ENTER)
            if (actionId == EditorInfo.IME_ACTION_NEXT ||
                    actionId == EditorInfo.IME_ACTION_DONE ||
                    (event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN)) {

                // Добавляем новую строку СРАЗУ ПОСЛЕ текущей
                int nextIndex = position + 1;
                items.add(nextIndex, "");

                // Говорим, что фокус должен быть на новой строке
                focusPosition = nextIndex;

                notifyDataSetChanged();
                return true; // Поглощаем событие, чтобы не было переноса строки внутри EditText
            }
            return false;
        });

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

    private static class ViewHolder {
        EditText editText;
        TextWatcher textWatcher;
    }
}