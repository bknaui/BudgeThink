package com.myapp.apangcatan.spendingdiary.view.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.myapp.apangcatan.spendingdiary.R;

import java.util.ArrayList;
import java.util.List;

public class SettingsActivity extends AppCompatActivity {

    Toolbar toolbar;
    private PieChart pieChart;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_layout);
        pieChart = findViewById(R.id.barchart_statistics);

        Description description = pieChart.getDescription();
        description.setEnabled(false);

//        Legend legend = pieChart.getLegend();
//        legend.setEnabled(false);

        List<PieEntry> entries = new ArrayList<>();
        entries.add(new PieEntry(150, "BALANCE"));
        entries.add(new PieEntry(23, "FOOD"));
        entries.add(new PieEntry(500, "TRANSPORT"));
        entries.add(new PieEntry(68, "OTHERS"));

        int[] pieDataSetColors = {Color.rgb(244,
                67, 54), Color.rgb(21, 101, 192), Color.rgb(67, 160, 71), Color.YELLOW};
        PieDataSet set = new PieDataSet(entries, "");
        set.setColors(pieDataSetColors);
        set.setValueTextColor(Color.WHITE);
        set.setValueTextSize(12);

        PieData data = new PieData(set);


        pieChart.setDrawSliceText(false);
        pieChart.setData(data);
        pieChart.invalidate(); // refresh

        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Budget Settings");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return false;
        }
        return super.onOptionsItemSelected(item);
    }
}

