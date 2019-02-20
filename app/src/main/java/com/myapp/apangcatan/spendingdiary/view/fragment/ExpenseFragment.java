package com.myapp.apangcatan.spendingdiary.view.fragment;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.myapp.apangcatan.spendingdiary.ExpenseApplication;
import com.myapp.apangcatan.spendingdiary.R;
import com.myapp.apangcatan.spendingdiary.RecyclerSwipeCallback;
import com.myapp.apangcatan.spendingdiary.adapter.ExpensesAdapter;
import com.myapp.apangcatan.spendingdiary.contract.ExpenseContract;
import com.myapp.apangcatan.spendingdiary.contract.SwipeCallBackk;
import com.myapp.apangcatan.spendingdiary.model.ExpenseModel;
import com.myapp.apangcatan.spendingdiary.model.ExpenseModelDao;
import com.myapp.apangcatan.spendingdiary.presenter.ExpensePresenter;
import com.myapp.apangcatan.spendingdiary.view.activity.SettingsActivity;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class ExpenseFragment extends Fragment implements ExpenseContract.ExpenseView {
    private RecyclerView recyclerView;
    private List<ExpenseModel> list = new ArrayList<>();
    private ExpensesAdapter expensesAdapter;
    private ExpensePresenter expensePresenter;
    private ExpenseModelDao expenseModelDao;
    private RecyclerSwipeCallback recyclerSwipeCallback;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.expenses_list, container, false);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        recyclerView = view.findViewById(R.id.recylcerview_expenses);
        recyclerView.setLayoutManager(linearLayoutManager);


        expenseModelDao = ((ExpenseApplication) getContext().getApplicationContext()).getDaoSession().getExpenseModelDao();

        expensesAdapter = new ExpensesAdapter(list, new SwipeCallBackk() {
            @Override
            public void onItemDelete(ExpenseModel expenseModel) {
               expensePresenter.deleteExpense(expenseModel);
            }
        });

        recyclerSwipeCallback = new RecyclerSwipeCallback(getContext(), expensesAdapter);

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(recyclerSwipeCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);
        recyclerView.setAdapter(expensesAdapter);

        expensePresenter = new ExpensePresenter(this, expenseModelDao);
        expensePresenter.loadExpenses();
        return view;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.clear_expense:
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setMessage("Are you sure you want to clear all expenses? \n\n\nNOTE: Clearing all expenses deletes all expenses history. ");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        expensePresenter.clearExpenses(new ExpenseContract.ExpenseClearCallback() {
                            @Override
                            public void onClearSuccess(String message) {
                                Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
                                expensePresenter.loadExpenses();
                            }
                        });
                    }
                });
                builder.setNegativeButton("Cancel", null);
                Dialog dialog = builder.create();
                dialog.show();
                return true;
            case R.id.add_expense:
                addExpense();
                return true;
            case R.id.menu_settings:
                Intent intent = new Intent(getContext(), SettingsActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_expense, menu);
    }

    @Override
    public void displayExpenses(List<ExpenseModel> list) {
        this.list.clear();
        this.list.addAll(list);
        expensesAdapter.notifyDataSetChanged();
    }

    @Override
    public void onSetDialogDateSuccess(TextView date, String value) {
        date.setText(value);
    }


    @Override
    public void addExpense() {
        final Calendar calendar = Calendar.getInstance();

        final Dialog dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.dialog_add_expense);
        dialog.findViewById(R.id.dialog_expense_date).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {

                DatePickerDialog datePickerDialog = DatePickerDialog.newInstance(new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
                        String date = year + "-" + String.format("%02d", monthOfYear + 1) + "-" + String.format("%02d", dayOfMonth);
                        expensePresenter.setDialogDate(date, v);
                    }
                }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DATE));

                datePickerDialog.show(getActivity().getFragmentManager(), "Specify Date");

            }
        });
        dialog.findViewById(R.id.dialog_expense_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                expensePresenter.saveExpenseToLocal(dialog, new ExpenseContract.ExpenseDialogCallback() {
                    @Override
                    public void setDescriptionRequireMessage(EditText editTextDialogDescription) {
                        editTextDialogDescription.setError("Required");
                        editTextDialogDescription.requestFocus();
                    }

                    @Override
                    public void setNameRequireMessage(EditText editTextDialogName) {
                        editTextDialogName.setError("Required");
                        editTextDialogName.requestFocus();
                    }

                    @Override
                    public void setDateRequireMessage(TextView textViewDialogDate) {
                        Toast.makeText(getContext(), "Please indicate date", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void setAmountRequireMessage(EditText editTextDialogAmount) {
                        editTextDialogAmount.setError("Required");
                        editTextDialogAmount.requestFocus();
                    }

                    @Override
                    public void onSaveSuccess() {
                        Toast.makeText(getContext(), "Successfully Addedd", Toast.LENGTH_SHORT).show();
                        expensePresenter.loadExpenses();
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
    public void onDeleteSuccess(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }
}
