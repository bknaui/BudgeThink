package com.myapp.apangcatan.spendingdiary.presenter;

import android.app.Dialog;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.myapp.apangcatan.spendingdiary.R;
import com.myapp.apangcatan.spendingdiary.contract.ExpenseContract;
import com.myapp.apangcatan.spendingdiary.model.BudgetPlanModel;
import com.myapp.apangcatan.spendingdiary.model.BudgetPlanModelDao;
import com.myapp.apangcatan.spendingdiary.model.ExpenseModel;
import com.myapp.apangcatan.spendingdiary.model.ExpenseModelDao;
import com.myapp.apangcatan.spendingdiary.utility.ValueFormatterUtility;

import java.util.List;

public class ExpensePresenter implements ExpenseContract.ExpensePresenter {
    private ExpenseContract.ExpenseView expenseView;
    private ExpenseModelDao expenseModelDao;

    public ExpensePresenter(ExpenseContract.ExpenseView expenseView) {
        this.expenseView = expenseView;
        this.expenseModelDao = expenseView.getExpenseModelDao();
    }

    @Override
    public void loadExpenses(long budgetPlanID) {
        List<ExpenseModel> list = expenseModelDao.queryBuilder().where(ExpenseModelDao.Properties.BudgetPlanId.eq(budgetPlanID)).list();
        Log.e("loadExpenses", list.size() + "");
        expenseView.displayExpenses(list);
    }

    @Override
    public void clearExpenses(ExpenseContract.ExpenseClearCallback expenseClearCallback) {
        expenseModelDao.deleteAll();
        expenseClearCallback.onClearSuccess("Expenses cleared");
    }

    @Override
    public void deleteExpense(ExpenseModel expenseModel) {
        expenseView.onDeleteSuccess(expenseModel.getItem() + " Deleted");
        expenseModelDao.delete(expenseModel);
    }


    @Override
    public void setDialogDate(String date, View view) {
        TextView textDate = (TextView) view;
        expenseView.onSetDialogDateSuccess(textDate, date);
    }

    @Override
    public void saveExpenseToLocal(Dialog dialog, ExpenseContract.ExpenseDialogCallback expenseDialogCallback) {

        EditText dialogDescription = dialog.findViewById(R.id.dialog_expense_description);
        EditText dialogName = dialog.findViewById(R.id.dialog_expense_name);

        TextView dialogDate = dialog.findViewById(R.id.dialog_expense_date);
        EditText dialogAmount = dialog.findViewById(R.id.dialog_expense_amount);

        String stringDescription = dialogDescription.getText().toString();
        String stringName = dialogName.getText().toString();

        String stringDate = dialogDate.getText().toString();
        String stringAmount = dialogAmount.getText().toString();

        if (stringDescription.trim().isEmpty()) {
            expenseDialogCallback.setDescriptionRequireMessage(dialogDescription);
            return;
        }
        if (stringName.trim().isEmpty()) {
            expenseDialogCallback.setNameRequireMessage(dialogName);
            return;
        }
        if (stringDate.trim().isEmpty()) {
            expenseDialogCallback.setDateRequireMessage(dialogDate);
            return;
        }
        if (stringAmount.trim().isEmpty()) {
            expenseDialogCallback.setAmountRequireMessage(dialogAmount);
            return;
        }

        ExpenseModel expenseModel = new ExpenseModel(null, stringDescription, stringName, stringDate, stringAmount, expenseView.getCurrentBudgetID());
        expenseModelDao.insert(expenseModel);

        expenseDialogCallback.onSaveSuccess();


    }

    @Override
    public void getCurrentRemainingBalance(Long budgetPlanId) {
        double total_expenses = 0;
        List<ExpenseModel> expenses = expenseModelDao.queryBuilder().where(ExpenseModelDao.Properties.BudgetPlanId.eq(budgetPlanId)).list();
        for (ExpenseModel data : expenses) {
            total_expenses += Double.parseDouble(data.getAmount());
        }
        BudgetPlanModelDao budgetPlanModelDao = expenseView.getBudgetPlanModelDao();
        BudgetPlanModel budgetPlanModel = budgetPlanModelDao.queryBuilder().where(BudgetPlanModelDao.Properties.Id.eq(budgetPlanId)).unique();

        double remaining_balance = Double.parseDouble(budgetPlanModel.getAmount()) - total_expenses;

        expenseView.setBalanceStatus("Balance : P"+ValueFormatterUtility.convertToCurrencyFormat(remaining_balance));

    }

}
