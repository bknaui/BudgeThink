package com.myapp.apangcatan.spendingdiary.view.activity;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.myapp.apangcatan.spendingdiary.ExpenseApplication;
import com.myapp.apangcatan.spendingdiary.R;
import com.myapp.apangcatan.spendingdiary.contract.ExpenseContract;
import com.myapp.apangcatan.spendingdiary.contract.SettingsContract;
import com.myapp.apangcatan.spendingdiary.model.BudgetPlanModel;
import com.myapp.apangcatan.spendingdiary.model.BudgetPlanModelDao;
import com.myapp.apangcatan.spendingdiary.model.ExpenseModelDao;
import com.myapp.apangcatan.spendingdiary.presenter.SettingsPresenter;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.util.Calendar;

public class SettingsActivity extends AppCompatActivity implements View.OnClickListener, SettingsContract.SettingsView {
    private Toolbar toolbar;
    private PieChart pieChart;
    private SettingsPresenter settingsPresenter;
    private ExpenseModelDao expenseModelDao;
    private BudgetPlanModelDao budgetPlanModelDao;
    private TextView statisticsBudget;
    private TextView statisticsTotalExpenses;
    private TextView statisticsRemainingBalance;
    private CardView cardCreateContainer;

    private BudgetPlanModel budgetPlanModel;

    private TextView txtviewBudgetPlan;
    private TextView txtviewBudgetAmount;
    private TextView txtviewBudgetDate;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_layout);

        pieChart = findViewById(R.id.barchart_statistics);
        toolbar = findViewById(R.id.toolbar);
        statisticsBudget = findViewById(R.id.barchart_statistics_budget);
        statisticsTotalExpenses = findViewById(R.id.barchart_statistics_total_expenses);
        statisticsRemainingBalance = findViewById(R.id.barchart_statistics_remaining_balance);
        cardCreateContainer = findViewById(R.id.cardview_create_budget);

        txtviewBudgetPlan = findViewById(R.id.settings_current_budget_value);
        txtviewBudgetAmount = findViewById(R.id.settings_current_amount_value);
        txtviewBudgetDate = findViewById(R.id.settings_current_inclusive_value);

        toolbar.setTitle("Budget Settings");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        pieChart.setCenterTextSize(25);

        Description description = pieChart.getDescription();
        description.setEnabled(false);

        Legend legend = pieChart.getLegend();
        legend.setEnabled(false);

        expenseModelDao = ((ExpenseApplication) getApplicationContext()).getDaoSession().getExpenseModelDao();
        budgetPlanModelDao = ((ExpenseApplication) getApplicationContext()).getDaoSession().getBudgetPlanModelDao();

        budgetPlanModel = budgetPlanModelDao.queryBuilder().where(BudgetPlanModelDao.Properties.Selected.eq(true)).unique();

        settingsPresenter = new SettingsPresenter(this);

        if (budgetPlanModel != null) {
            settingsPresenter.loadExpensesByBudget(budgetPlanModel.getId());
        }

        findViewById(R.id.edtxt_settings_budget_inclusive_date).setOnClickListener(this);
        findViewById(R.id.txtview_add_budget_plan).setOnClickListener(this);
        findViewById(R.id.btn_add_budget_plan).setOnClickListener(this);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_settings, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return false;
            case R.id.add_plan:
                addPlan();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.edtxt_settings_budget_inclusive_date:
                break;
            case R.id.txtview_add_budget_plan:
                cardCreateContainer.setVisibility(View.VISIBLE);
                break;

        }
    }

    @Override
    public void addPlan() {
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_add_plan);

        dialog.findViewById(R.id.dialog_plan_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                settingsPresenter.addBudgetPlan(dialog, new SettingsContract.SettingsDialogCallback() {

                    @Override
                    public void setDateRequireMessage() {
                        Toast.makeText(SettingsActivity.this, "Please specify inclusive dates", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void setPlanRequireMessage() {
                        EditText plan = dialog.findViewById(R.id.dialog_plan_description);
                        plan.setError("Required");
                        plan.requestFocus();
                    }

                    @Override
                    public void setAmountRequireMessage() {
                        EditText amount = dialog.findViewById(R.id.dialog_plan_amount);
                        amount.setError("Required");
                        amount.requestFocus();
                    }

                    @Override
                    public void onSaveSuccess() {
                        Toast.makeText(SettingsActivity.this, "New Plan created", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }
                });

            }
        });

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        dialog.show();
        dialog.getWindow().setAttributes(lp);
        dialog.show();
    }

    @Override
    public void showExpensesGraph(PieData pieData) {
        pieChart.setEntryLabelTextSize(0);
        pieChart.setData(pieData);
        pieChart.invalidate(); // refresh
    }

    @Override
    public void setGraphPercentage(String percentage) {
        pieChart.setCenterText(percentage);
    }

    @Override
    public void setStatisticsTotalExpense(String text) {
        statisticsTotalExpenses.setText(text);
    }

    @Override
    public void setStatisticsRemainingBalance(String text) {
        statisticsRemainingBalance.setText(text);
    }

    @Override
    public void setStatisticsBudget(String text) {
        statisticsBudget.setText(text);
    }

    @Override
    public void setBudgetPlanText(String plan) {
        txtviewBudgetPlan.setText(plan);
    }

    @Override
    public void setBudgetPlanAmountText(String amount) {
        txtviewBudgetAmount.setText(amount);
    }

    @Override
    public void setBudgetDatesText(String dates) {
        txtviewBudgetDate.setText(dates);
    }

    @Override
    public void onSuccess() {
        Toast.makeText(this, "New Budget Plan Added", Toast.LENGTH_SHORT).show();
        budgetPlanModel = budgetPlanModelDao.queryBuilder().where(BudgetPlanModelDao.Properties.Selected.eq(true)).unique();
        settingsPresenter.loadExpensesByBudget(budgetPlanModel.getId());
    }

    @Override
    public ExpenseModelDao getExpenseModelDao() {
        return expenseModelDao;
    }

    @Override
    public BudgetPlanModelDao getBudgetPlanModelDao() {
        return budgetPlanModelDao;
    }


}

