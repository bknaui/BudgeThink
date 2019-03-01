package com.myapp.apangcatan.spendingdiary.contract;

import android.app.Dialog;
import android.widget.EditText;
import android.widget.TextView;

import com.github.mikephil.charting.data.PieData;
import com.myapp.apangcatan.spendingdiary.model.BudgetPlanModelDao;
import com.myapp.apangcatan.spendingdiary.model.ExpenseModelDao;

public class SettingsContract {

    public interface SettingsView {
        void showExpensesGraph(PieData pieDat);

        void setGraphPercentage(String percentage);

        void setStatisticsTotalExpense(String text);

        void setStatisticsRemainingBalance(String text);

        void setStatisticsBudget(String text);

        void setBudgetPlanText(String plan);

        void setBudgetPlanAmountText(String amount);

        void setBudgetDatesText(String dates);


        void onSuccess();

        void addPlan();

        ExpenseModelDao getExpenseModelDao();

        BudgetPlanModelDao getBudgetPlanModelDao();


    }

    public interface SettingsPresenter {
        void loadExpensesByBudget(Long budgetPlanId);

        void addBudgetPlan(Dialog dialog, SettingsDialogCallback settingsDialogCallback);


    }

    public interface SettingsDialogCallback {

        void setPlanRequireMessage();

        void setDateRequireMessage();

        void setAmountRequireMessage();

        void onSaveSuccess();
    }
}
