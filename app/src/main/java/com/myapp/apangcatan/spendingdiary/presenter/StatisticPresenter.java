package com.myapp.apangcatan.spendingdiary.presenter;

import android.graphics.Color;

import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.myapp.apangcatan.spendingdiary.contract.StatisticContract;
import com.myapp.apangcatan.spendingdiary.model.ExpenseModel;
import com.myapp.apangcatan.spendingdiary.model.ExpenseModelDao;

import org.greenrobot.greendao.query.WhereCondition;

import java.util.ArrayList;
import java.util.List;

public class StatisticPresenter implements StatisticContract.StatisticPresenter {
    private StatisticContract.StatisticView statisticView;
    private ExpenseModelDao expenseModelDao;

    public StatisticPresenter(StatisticContract.StatisticView statisticView, ExpenseModelDao expenseModelDao) {
        this.statisticView = statisticView;
        this.expenseModelDao = expenseModelDao;
    }

    @Override
    public void loadExpenses() {

        float foodAmount = 0;
        float transportAmount = 0;
        float othersAmount = 0;


        List<PieEntry> entries = new ArrayList<>();
        entries.add(new PieEntry(foodAmount, "FOOD"));
        entries.add(new PieEntry(transportAmount, "TRANSPORT"));
        entries.add(new PieEntry(othersAmount, "OTHERS"));

        int[] pieDataSetColors = {Color.rgb(244,
                67, 54), Color.rgb(21, 101, 192), Color.rgb(67, 160, 71)};
        PieDataSet set = new PieDataSet(entries, "");
        set.setColors(pieDataSetColors);
        set.setValueTextColor(Color.WHITE);
        set.setValueTextSize(12);

        PieData data = new PieData(set);

        statisticView.setPieData(data);
    }
}
