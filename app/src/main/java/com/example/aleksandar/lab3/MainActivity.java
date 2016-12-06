package com.example.aleksandar.lab3;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.aleksandar.lab3.Data.Maps;

public class MainActivity extends SingleFragmentActivity {
    public static final String LIST_FRAGMENT="ListFragment";
    public static final String MAPS_FRAGMENT="MapsFragment";
    public static final String SETTINGS_FRAGMENT="SettingsFragment";

    @Override
    protected Fragment createFragment() {
        return activeFragment();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MapsService.setServiceAlarm(this,true);
    }

    @Override
    protected void onStop() {
        super.onStop();
        MapsService.setServiceAlarm(this,false);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_item_settings:
                replaceFragment(new SettingsFragment(),SETTINGS_FRAGMENT);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        MapLab mapLab = MapLab.get(this);
        String ActiveFragment = mapLab.getActiveFragment();
        //TODO if not in settings call super.onBackPressed();
        Fragment f = activeFragment();
        replaceFragment(f, ActiveFragment);
    }

    private Fragment activeFragment() {
        MapLab mapLab = MapLab.get(this);
        String ActiveFragment = mapLab.getActiveFragment();
        Fragment f;
        if (ActiveFragment.equals(LIST_FRAGMENT))
            f = new LocationListFragment();
        else
            f = new MapsFragment();
        return f;
    }
}
