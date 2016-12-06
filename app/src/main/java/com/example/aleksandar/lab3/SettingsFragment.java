package com.example.aleksandar.lab3;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;

import static com.example.aleksandar.lab3.MainActivity.LIST_FRAGMENT;
import static com.example.aleksandar.lab3.MainActivity.MAPS_FRAGMENT;

/**
 * Created by Aleksandar on 06.12.2016.
 */

public class SettingsFragment extends Fragment {
    private Switch mSwitch;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_settings,container,false);
        mSwitch=(Switch) view.findViewById(R.id.setting_maps_toggle);
        MapLab mapLab=MapLab.get(getActivity());
        if(mapLab.getActiveFragment().equals(MAPS_FRAGMENT))
            mSwitch.setChecked(true);
        mSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                MapLab mapLab=MapLab.get(getActivity());
                if(b){
                    mapLab.setActiveFragment(MAPS_FRAGMENT);
                }
                else mapLab.setActiveFragment(LIST_FRAGMENT);
//                Log.i("Log",mapLab.getActiveFragment());
            }
        });
        return view;
    }
}
