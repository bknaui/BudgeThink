package com.myapp.apangcatan.spendingdiary;

import android.app.Application;

import com.myapp.apangcatan.spendingdiary.model.DaoMaster;
import com.myapp.apangcatan.spendingdiary.model.DaoSession;

public class ExpenseApplication extends Application {
    private DaoSession mDaoSession;

    @Override
    public void onCreate() {
        super.onCreate();
        mDaoSession = new DaoMaster(
                new DaoMaster.DevOpenHelper(this, "expense.db").getWritableDb()).newSession();

    }

    public DaoSession getDaoSession() {
        return mDaoSession;
    }
}
