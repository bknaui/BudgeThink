package com.myapp.apangcatan.spendingdiary;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

import com.myapp.apangcatan.spendingdiary.adapter.ExpensesAdapter;


public class RecyclerSwipeCallback extends ItemTouchHelper.SimpleCallback {

    private ExpensesAdapter expensesAdapter;
    private Drawable icon;
    private final ColorDrawable background;

    public RecyclerSwipeCallback(Context context, ExpensesAdapter expensesAdapter) {
        super(0, ItemTouchHelper.LEFT);
        this.expensesAdapter = expensesAdapter;


        background = new ColorDrawable(Color.TRANSPARENT);
        icon = ContextCompat.getDrawable(context, android.R.drawable.ic_delete);
    }

    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        return false;
    }

    @Override
    public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);

//
//        View itemView = viewHolder.itemView;
//
//        int iconMargin = (itemView.getHeight() - icon.getIntrinsicHeight()) / 2;
//        int iconTop = itemView.getTop() + (itemView.getHeight() - icon.getIntrinsicHeight()) / 2;
//        int iconBottom = iconTop + icon.getIntrinsicHeight();
//
//        if (dX < 20) {
//
//            int iconLeft = itemView.getRight() - iconMargin - icon.getIntrinsicWidth();
//            int iconRight = itemView.getRight() - iconMargin;
//
//            icon.setBounds(iconLeft, iconTop, iconRight, iconBottom);
//
//            background.setBounds(itemView.getRight() + ((int) dX),
//                    itemView.getTop(), itemView.getRight(), itemView.getBottom());
//
//            itemView.setTranslationX(dX / 3);
//        } else { // view is unSwiped
//            background.setBounds(0, 0, 0, 0);
//        }
//
//        background.draw(c);
//        icon.draw(c);

    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {

        expensesAdapter.deleteItem(viewHolder.getAdapterPosition());

    }

}
