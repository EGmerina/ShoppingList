package com.example.shoppinglist;

import androidx.fragment.app.Fragment;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;

public class DiagramFragment extends Fragment {
    @Override
    public android.view.View onCreateView(android.view.LayoutInflater inflater, android.view.ViewGroup container, android.os.Bundle savedInstanceState) {
        android.view.View view = inflater.inflate(R.layout.fragment_diagram, container, false);

        PieChart pieChart = view.findViewById(R.id.pie_chart);
        java.util.List<PieEntry> entries = new java.util.ArrayList<>();
        entries.add(new PieEntry(40f, "Food"));
        entries.add(new PieEntry(30f, "Drinks"));
        entries.add(new PieEntry(20f, "Other"));

        PieDataSet dataSet = new PieDataSet(entries, "");
        dataSet.setColors(new int[]{android.graphics.Color.CYAN, android.graphics.Color.MAGENTA, android.graphics.Color.YELLOW});

        PieData data = new PieData(dataSet);
        pieChart.setData(data);
        pieChart.getDescription().setEnabled(false);
        pieChart.animateY(1000); // Анимация появления
        pieChart.invalidate();

        return view;
    }
}
