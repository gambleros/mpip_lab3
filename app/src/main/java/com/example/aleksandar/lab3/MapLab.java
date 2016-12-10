package com.example.aleksandar.lab3;

import android.content.Context;
import android.location.Location;
import android.preference.PreferenceManager;

import com.example.aleksandar.lab3.Data.Maps;
import com.example.aleksandar.lab3.Data.Result;
import com.example.aleksandar.lab3.Model.DaoMaster;
import com.example.aleksandar.lab3.Model.DaoSession;
import com.example.aleksandar.lab3.Model.MapLocation;
import com.example.aleksandar.lab3.Model.MapLocationDao;

import org.greenrobot.greendao.database.Database;

import java.util.ArrayList;
import java.util.List;

import static com.example.aleksandar.lab3.MainActivity.LIST_FRAGMENT;
import static com.example.aleksandar.lab3.MainActivity.MAPS_FRAGMENT;

/**
 * Created by Aleksandar on 06.12.2016.
 */

public class MapLab {
    private static MapLab sMapLab;

    private Location mLocation;
    private String mActiveFragment;

    private List<MapLocation> mMaps;
    private MapLocationDao mMapLocationDao;
    private DaoSession mDaoSession;

    private MapLab(Context context) {
        mActiveFragment = LIST_FRAGMENT;

//        mDaoSession = ((App) context.getApplicationContext()).getDaoSession();
        mDaoSession = getDao(context);
        mMapLocationDao = mDaoSession.getMapLocationDao();
        mMaps = mMapLocationDao.queryBuilder().list();
        mActiveFragment = SettingPreference.isMapOn(context) ? MAPS_FRAGMENT : LIST_FRAGMENT;
    }

    public static DaoSession getDao(Context context) {
        DaoMaster.DevOpenHelper devOpenHelper = new DaoMaster.DevOpenHelper(context, "mapLocations-db");
        Database db = devOpenHelper.getWritableDb();
        return new DaoMaster(db).newSession();
    }

    public static MapLab get(Context context) {
        if (sMapLab == null) {
            sMapLab = new MapLab(context);
        }
        return sMapLab;
    }

    public Location getLocation() {
        return mLocation;
    }

    public void setLocation(Location location) {
        mLocation = location;
    }

    public String getActiveFragment() {
        return mActiveFragment;
    }

    public void setActiveFragment(String activeFragment) {
        mActiveFragment = activeFragment;
    }

    public List<MapLocation> getMaps() {
        return mMaps;
    }

    public void setMaps(List<MapLocation> maps) {
        mMaps = maps;
    }

}
