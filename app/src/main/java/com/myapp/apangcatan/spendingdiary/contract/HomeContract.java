package com.myapp.apangcatan.spendingdiary.contract;

import android.support.v4.app.Fragment;

import com.myapp.apangcatan.spendingdiary.view.fragment.ExpenseFragment;
import com.myapp.apangcatan.spendingdiary.view.fragment.EventFragment;
import com.myapp.apangcatan.spendingdiary.view.activity.SettingsActivity;
import com.myapp.apangcatan.spendingdiary.view.fragment.StatisticsFragment;

public class HomeContract {
    public interface HomeView {
        void setFragment(Fragment fragment,String toolbar);

        ExpenseFragment getExpenseFragment();

        StatisticsFragment getStatisticsFragment();

        EventFragment getEventFragment();

        SettingsActivity getSettingsActivity();

    }

    public interface HomePresnter {
        void navigateToFragment(int menuItemId);
    }
}
