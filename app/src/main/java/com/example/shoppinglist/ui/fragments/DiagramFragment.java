package com.example.shoppinglist.ui.fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.shoppinglist.R;
import com.example.shoppinglist.data.ShoppingList;
import com.example.shoppinglist.ui.MainActivity;
import com.example.shoppinglist.viewmodel.ShoppingViewModel;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DiagramFragment extends Fragment {

    private ShoppingViewModel viewModel;
    private PieChart pieChart;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_diagram, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel = new ViewModelProvider(requireActivity()).get(ShoppingViewModel.class);

        pieChart = view.findViewById(R.id.pie_chart);
        setupChart();

        ImageButton btnMenu = view.findViewById(R.id.btn_menu);
        btnMenu.setOnClickListener(v -> {
            if (getActivity() instanceof MainActivity) {
                ((MainActivity) getActivity()).openDrawer();
            }
        });

        viewModel.getHistoryLists(requireContext()).observe(getViewLifecycleOwner(), historyLists -> {
            if (historyLists != null && !historyLists.isEmpty()) {
                updateChartData(historyLists);
            }
        });
    }

    private void setupChart() {
        pieChart.setUsePercentValues(true);
        pieChart.getDescription().setEnabled(false);
        pieChart.setExtraOffsets(5, 10, 5, 5);
        pieChart.setDragDecelerationFrictionCoef(0.95f);
        pieChart.setDrawHoleEnabled(true);
        pieChart.setHoleColor(Color.WHITE);
        pieChart.setTransparentCircleRadius(61f);

        // Настройка текста НА графике (внутри круга)
        pieChart.setEntryLabelColor(Color.BLACK);
        pieChart.setEntryLabelTextSize(12f);

        // --- НОВЫЙ КОД ДЛЯ ЛЕГЕНДЫ (ПОДПИСИ ВНИЗУ) ---
        Legend legend = pieChart.getLegend();

// 1. Увеличиваем размер текста (по умолчанию около 10f)
        legend.setTextSize(16f);

// 2. Делаем так, чтобы текст переносился на новую строку, если не влезает
        legend.setWordWrapEnabled(true);

// 3. (Опционально) Увеличиваем размер цветных квадратиков, чтобы соответствовали тексту
        legend.setFormSize(16f);

// 4. (Опционально) Отступы между элементами легенды
        legend.setXEntrySpace(10f); // Расстояние по горизонтали
        legend.setYEntrySpace(5f);  // Расстояние по вертикали
    }

    private void updateChartData(List<ShoppingList> historyLists) {
        // --- АЛГОРИТМ ПОДСЧЕТА ---
        Map<String, Integer> productCountMap = new HashMap<>();

        for (ShoppingList list : historyLists) {
            for (String rawItem : list.items) {
                if (rawItem == null || rawItem.trim().isEmpty()) continue;

                // Нормализация: убираем пробелы по краям и переводим в нижний регистр
                String normalizedItem = rawItem.trim().toLowerCase();

                // Считаем количество
                if (productCountMap.containsKey(normalizedItem)) {
                    productCountMap.put(normalizedItem, productCountMap.get(normalizedItem) + 1);
                } else {
                    productCountMap.put(normalizedItem, 1);
                }
            }
        }

        // --- ПОДГОТОВКА ДАННЫХ ДЛЯ ГРАФИКА ---
        List<PieEntry> entries = new ArrayList<>();

        // Превращаем Map в PieEntry
        for (Map.Entry<String, Integer> entry : productCountMap.entrySet()) {
            // entry.getValue() - это количество (int), график сам переведет это в проценты
            // entry.getKey() - название товара
            entries.add(new PieEntry(entry.getValue(), entry.getKey()));
        }

        PieDataSet dataSet = new PieDataSet(entries, "");
        dataSet.setSliceSpace(3f); // Расстояние между дольками
        dataSet.setSelectionShift(5f); // Сдвиг при нажатии

        // --- ЦВЕТА ---
        // Используем готовые наборы цветов, так как товаров может быть много
        ArrayList<Integer> colors = new ArrayList<>();
        for (int c : ColorTemplate.MATERIAL_COLORS) colors.add(c);
        for (int c : ColorTemplate.JOYFUL_COLORS) colors.add(c);
        for (int c : ColorTemplate.COLORFUL_COLORS) colors.add(c);
        for (int c : ColorTemplate.LIBERTY_COLORS) colors.add(c);
        for (int c : ColorTemplate.PASTEL_COLORS) colors.add(c);
        dataSet.setColors(colors);

        // --- УСТАНОВКА ДАННЫХ ---
        PieData data = new PieData(dataSet);
        data.setValueFormatter(new PercentFormatter(pieChart)); // Форматирование в %
        data.setValueTextSize(15f);
        data.setValueTextColor(Color.BLACK);

        pieChart.setData(data);
        pieChart.highlightValues(null); // Убрать выделение при загрузке
        pieChart.invalidate(); // Обновить график
        pieChart.animateY(1400); // Анимация
    }
}