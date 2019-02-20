package com.myapp.apangcatan.spendingdiary.contract;

import com.github.mikephil.charting.data.PieData;

public class StatisticContract {
    public interface StatisticView {
        void setPieData(PieData pieData);

    }

    public interface StatisticPresenter {
        void loadExpenses();
    }
}
