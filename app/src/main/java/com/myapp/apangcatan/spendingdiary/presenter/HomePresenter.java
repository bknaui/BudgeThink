package com.myapp.apangcatan.spendingdiary.presenter;

import com.myapp.apangcatan.spendingdiary.R;
import com.myapp.apangcatan.spendingdiary.contract.HomeContract;

public class HomePresenter implements HomeContract.HomePresnter {
    HomeContract.HomeView homeView;

    public HomePresenter(HomeContract.HomeView homeView) {
        this.homeView = homeView;
    }

    @Override
    public void navigateToFragment(int menuItemId) {
        switch (menuItemId) {
            case R.id.menu_expense:
                homeView.setFragment(homeView.getExpenseFragment(),"Expenses");
                return;
            case R.id.menu_barchart:
                homeView.setFragment(homeView.getStatisticsFragment(),"Statistics");
                return;
            case R.id.menu_events:
                homeView.setFragment(homeView.getEventFragment(),"Events");
                return;
        }
    }

}
