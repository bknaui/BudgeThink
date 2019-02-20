package com.myapp.apangcatan.spendingdiary.contract;

import android.app.Dialog;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.myapp.apangcatan.spendingdiary.model.ExpenseModel;
import com.myapp.apangcatan.spendingdiary.model.ExpenseModelDao;

import java.util.List;

public class ExpenseContract {

    public interface ExpenseView {
        void displayExpenses(List<ExpenseModel> list);

        void onSetDialogDateSuccess(TextView date, String value);

        void addExpense();

        void onDeleteSuccess(String message);
    }

    public interface ExpensePresenter {
        void loadExpenses();

        void clearExpenses(ExpenseClearCallback expenseClearCallback);

        void deleteExpense(ExpenseModel expenseModel);

        void setDialogDate(String date, View view);

        void saveExpenseToLocal(Dialog dialog, ExpenseDialogCallback expenseDialogCallback);

    }

    public interface ExpenseClearCallback {
        void onClearSuccess(String message);
    }

    public interface ExpenseDialogCallback {
        void setDescriptionRequireMessage(EditText editTextDialogDescription);

        void setNameRequireMessage(EditText editTextDialogName);

        void setDateRequireMessage(TextView textViewDialogDate);

        void setAmountRequireMessage(EditText editTextDialogAmount);

        void onSaveSuccess();
    }
}

