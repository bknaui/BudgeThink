package com.myapp.apangcatan.spendingdiary.view.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.myapp.apangcatan.spendingdiary.ExpenseApplication;
import com.myapp.apangcatan.spendingdiary.R;
import com.myapp.apangcatan.spendingdiary.adapter.ExpensesAdapter;
import com.myapp.apangcatan.spendingdiary.contract.StatisticContract;
import com.myapp.apangcatan.spendingdiary.model.ExpenseModel;
import com.myapp.apangcatan.spendingdiary.model.ExpenseModelDao;
import com.myapp.apangcatan.spendingdiary.presenter.StatisticPresenter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class StatisticsFragment extends Fragment implements StatisticContract.StatisticView {

    private PieChart pieChart;
    private StatisticPresenter statisticPresenter;
    private ExpenseModelDao expenseModelDao;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.statistics_chart_layout, container, false);
        pieChart = view.findViewById(R.id.barchart_statistics);

        Description description = pieChart.getDescription();
        description.setEnabled(false);

        expenseModelDao = ((ExpenseApplication) getContext().getApplicationContext()).getDaoSession().getExpenseModelDao();
        statisticPresenter = new StatisticPresenter(this, expenseModelDao);

        statisticPresenter.loadExpenses();
        return view;
    }

    @Override
    public void setPieData(PieData pieData) {
        pieChart.setDrawSliceText(false);
        pieChart.setData(pieData);
        pieChart.invalidate(); // refresh
    }
}
