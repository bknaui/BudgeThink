package com.myapp.apangcatan.spendingdiary.presenter;

import android.app.Dialog;
import android.graphics.Color;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.myapp.apangcatan.spendingdiary.R;
import com.myapp.apangcatan.spendingdiary.contract.SettingsContract;
import com.myapp.apangcatan.spendingdiary.model.BudgetPlanModel;
import com.myapp.apangcatan.spendingdiary.model.BudgetPlanModelDao;
import com.myapp.apangcatan.spendingdiary.model.ExpenseModel;
import com.myapp.apangcatan.spendingdiary.model.ExpenseModelDao;
import com.myapp.apangcatan.spendingdiary.utility.ValueFormatterUtility;

import java.util.ArrayList;
import java.util.List;

public class SettingsPresenter implements SettingsContract.SettingsPresenter {
    private SettingsContract.SettingsView settingsView;
    private ExpenseModelDao expenseModelDao;

    public SettingsPresenter(SettingsContract.SettingsView settingsView) {
        this.settingsView = settingsView;
        this.expenseModelDao = settingsView.getExpenseModelDao();
    }

    @Override
    public void loadExpensesByBudget(Long budgetPlanId) {

        BudgetPlanModelDao budgetPlanModelDao = settingsView.getBudgetPlanModelDao();
        BudgetPlanModel budgetPlanModel = budgetPlanModelDao.queryBuilder().where(BudgetPlanModelDao.Properties.Id.eq(budgetPlanId)).unique();

        if (budgetPlanModel != null) {
            String budget_name = budgetPlanModel.getDescription();
            String budget_date = budgetPlanModel.getFromDate() + "-" + budgetPlanModel.getToDate();
            double budget_amount = Double.parseDouble(budgetPlanModel.getAmount());
            double total_expenses = 0;


            List<ExpenseModel> expenseModel = expenseModelDao.queryBuilder().where(ExpenseModelDao.Properties.BudgetPlanId.eq(budgetPlanId)).list();

            for (ExpenseModel data : expenseModel) {
                total_expenses += Float.parseFloat(data.getAmount());
            }

            double balance = budget_amount - total_expenses;

            double percentage_expenses = (total_expenses / budget_amount) * 100;
            if (Double.isInfinite(percentage_expenses)) {
                percentage_expenses = 0;
            }
            double percentage_balance = 100 - percentage_expenses;
            if (Double.isInfinite(percentage_balance)) {
                percentage_expenses = 0;
            }
            String percentage_text = (int) percentage_expenses + "%";

            List<PieEntry> entries = new ArrayList<>();
            entries.add(new PieEntry((float) percentage_balance, "BALANCE"));
            entries.add(new PieEntry((float) percentage_expenses, "EXPENSES"));

            int expenses_color = Color.rgb(244, 67, 54);
            int balance_color = Color.rgb(215, 215, 215);

            int[] pieDataSetColors = {balance_color, expenses_color};

            PieDataSet set = new PieDataSet(entries, "");
            set.setColors(pieDataSetColors);
            set.setValueTextSize(0);

            PieData data = new PieData(set);

            settingsView.setGraphPercentage(percentage_text);
            settingsView.setStatisticsTotalExpense("Expenses: " + ValueFormatterUtility.convertToCurrencyFormat(total_expenses));
            settingsView.setStatisticsRemainingBalance("Balance: " + ValueFormatterUtility.convertToCurrencyFormat(balance));
            settingsView.setStatisticsBudget("Budget: " + ValueFormatterUtility.convertToCurrencyFormat(budget_amount));
            settingsView.showExpensesGraph(data);


            settingsView.setBudgetPlanText(budget_name);
            settingsView.setBudgetPlanAmountText("P" + ValueFormatterUtility.convertToCurrencyFormat(budget_amount));
            settingsView.setBudgetDatesText(budget_date);
        }
    }

    @Override
    public void addBudgetPlan(Dialog dialog, SettingsContract.SettingsDialogCallback settingsDialogCallback) {
        EditText dialog_plan_description = dialog.findViewById(R.id.dialog_plan_description);
        EditText dialog_plan_amount = dialog.findViewById(R.id.dialog_plan_amount);
        TextView dialog_plan_date = dialog.findViewById(R.id.dialog_plan_date);

        String plan = dialog_plan_description.getText().toString().trim();
        String date = dialog_plan_date.getText().toString().trim();
        String amount = dialog_plan_amount.getText().toString().trim();

        if (plan.isEmpty()) {
            settingsDialogCallback.setPlanRequireMessage();
            return;
        }
        if (date.isEmpty()) {
            settingsDialogCallback.setDateRequireMessage();
            return;
        }
        if (amount.isEmpty()) {
            settingsDialogCallback.setAmountRequireMessage();
            return;
        }

        String from = "01/01/2019";
        String to = "01/31/2019";

        BudgetPlanModel budgetPlanModel = new BudgetPlanModel(null, plan, from, to, amount, false);
        budgetPlanModel.setSelected(true);

        settingsView.getBudgetPlanModelDao().insert(budgetPlanModel);
        settingsDialogCallback.onSaveSuccess();
    }
/*
    @Override
    public void addBudgetPlan() {
        if (validateBudgetForms()) {
            String plan = settingsView.getBudgetPlan().getText().toString();
            String from = "01/01/2019";
            String to = "01/31/2019";

            String amount = settingsView.getBudgetAmount().getText().toString();

            BudgetPlanModel budgetPlanModel = new BudgetPlanModel(null, plan, from, to, amount, true);
            budgetPlanModel.setSelected(true);

            settingsView.getBudgetPlanModelDao().insert(budgetPlanModel);
            settingsView.onSuccess();
        }
    }
    */


}
