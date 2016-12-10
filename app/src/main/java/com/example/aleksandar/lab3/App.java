package com.example.aleksandar.lab3;

import android.app.Application;

import com.example.aleksandar.lab3.Model.DaoMaster;
import com.example.aleksandar.lab3.Model.DaoMaster.DevOpenHelper;
import com.example.aleksandar.lab3.Model.DaoSession;

import org.greenrobot.greendao.database.Database;

/**
 * Created by Aleksandar on 10.12.2016.
 */

public class App extends Application {
    private DaoSession mDaoSession;

    @Override
    public void onCreate() {
        super.onCreate();
        DevOpenHelper devOpenHelper = new DevOpenHelper(this, "mapLocations-db");
        Database db = devOpenHelper.getWritableDb();
        mDaoSession = new DaoMaster(db).newSession();
    }

    public DaoSession getDaoSession() {
        return mDaoSession;
    }
}
