package com.myapp.apangcatan.spendingdiary.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.myapp.apangcatan.spendingdiary.R;
import com.myapp.apangcatan.spendingdiary.contract.SwipeCallBackk;
import com.myapp.apangcatan.spendingdiary.model.ExpenseModel;

import java.util.List;

public class ExpensesAdapter extends RecyclerView.Adapter<ExpensesAdapter.ExpensesViewHolder> {
    private List<ExpenseModel> list;
    private SwipeCallBackk swipeCallBackk;


    public ExpensesAdapter(List<ExpenseModel> list, SwipeCallBackk swipeCallBackk) {
        this.list = list;
        this.swipeCallBackk = swipeCallBackk;
    }

    @NonNull
    @Override
    public ExpensesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.expense_item_layout, parent, false);
        return new ExpensesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ExpensesViewHolder holder, int position) {

        holder.txtviewDescription.setText(list.get(position).getDescription());
        holder.txtviewName.setText(list.get(position).getItem());
        holder.txtviewDate.setText(list.get(position).getDate());
        holder.txtviewExpense.setText("P" + list.get(position).getAmount());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void deleteItem(int position) {
        swipeCallBackk.onItemDelete(list.get(position));
        list.remove(position);
        this.notifyItemRemoved(position);
    }

    class ExpensesViewHolder extends RecyclerView.ViewHolder {
        ImageView imgviewType;
        TextView txtviewDescription;
        TextView txtviewName;
        TextView txtviewDate;
        TextView txtviewExpense;

        public ExpensesViewHolder(View itemView) {
            super(itemView);
            this.txtviewDescription = itemView.findViewById(R.id.txtview_item_status);
            this.imgviewType = itemView.findViewById(R.id.imagview_expense_type);
            this.txtviewName = itemView.findViewById(R.id.txtview_item_name);
            this.txtviewDate = itemView.findViewById(R.id.txtview_item_date);
            this.txtviewExpense = itemView.findViewById(R.id.txtview_item_amount);
        }
    }


}
