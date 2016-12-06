package com.example.aleksandar.lab3;

import android.content.Context;
import android.location.Location;

import com.example.aleksandar.lab3.Data.Maps;
import com.example.aleksandar.lab3.Data.Result;

import java.util.ArrayList;

import static com.example.aleksandar.lab3.MainActivity.LIST_FRAGMENT;

/**
 * Created by Aleksandar on 06.12.2016.
 */

public class MapLab {
    private static MapLab sMapLab;
    private android.location.Location mLocation;
    private String mActiveFragment;
    private boolean mFlag;
    private Maps mMaps;

    private MapLab(Context context) {
        mActiveFragment = LIST_FRAGMENT;
        mMaps = new Maps();
        mMaps.results = new ArrayList<>();
        Result r = new Result();
        r.name = "tmp";
        mMaps.results.add(r);
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

    public Maps getMaps() {
        return mMaps;
    }

    public void setMaps(Maps maps) {
        mMaps = maps;
    }

    public boolean isFlag() {
        return mFlag;
    }

    public void setFlag(boolean flag) {
        mFlag = flag;
    }
}
