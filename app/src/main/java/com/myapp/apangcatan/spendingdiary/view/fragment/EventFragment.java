package com.myapp.apangcatan.spendingdiary.view.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.myapp.apangcatan.spendingdiary.R;
import com.myapp.apangcatan.spendingdiary.adapter.ExpensesAdapter;
import com.myapp.apangcatan.spendingdiary.model.ExpenseModel;

import java.util.ArrayList;
import java.util.List;

public class EventFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.event_layout, container, false);
        return view;
    }
}
