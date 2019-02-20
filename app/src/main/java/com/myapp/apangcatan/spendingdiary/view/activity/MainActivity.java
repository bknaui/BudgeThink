package com.myapp.apangcatan.spendingdiary.view.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.myapp.apangcatan.spendingdiary.R;
import com.myapp.apangcatan.spendingdiary.contract.HomeContract;
import com.myapp.apangcatan.spendingdiary.presenter.HomePresenter;
import com.myapp.apangcatan.spendingdiary.view.fragment.EventFragment;
import com.myapp.apangcatan.spendingdiary.view.fragment.ExpenseFragment;
import com.myapp.apangcatan.spendingdiary.view.fragment.StatisticsFragment;

public class MainActivity extends AppCompatActivity implements HomeContract.HomeView {
    private BottomNavigationView bottomNavigationView;
    private Toolbar toolbar;

    private ExpenseFragment expenseFragment = new ExpenseFragment();
    private StatisticsFragment statisticsFragment = new StatisticsFragment();
    private EventFragment eventFragment = new EventFragment();
    private SettingsActivity settingsActivity = new SettingsActivity();

    private HomePresenter homePresenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = findViewById(R.id.toolbar);
        bottomNavigationView = findViewById(R.id.navigation);

        setSupportActionBar(toolbar);

        homePresenter = new HomePresenter(this);
        homePresenter.navigateToFragment(R.id.menu_expense);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                homePresenter.navigateToFragment(item.getItemId());
                return false;
            }
        });
    }

    @Override
    public void setFragment(Fragment fragment, String toolbar) {

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.framelayout_home, fragment).commit();

        this.toolbar.setTitle(toolbar);
    }

    @Override
    public ExpenseFragment getExpenseFragment() {
        return expenseFragment;
    }

    @Override
    public StatisticsFragment getStatisticsFragment() {
        return statisticsFragment;
    }

    @Override
    public EventFragment getEventFragment() {
        return eventFragment;
    }

    @Override
    public SettingsActivity getSettingsActivity() {
        return settingsActivity;
    }

}
