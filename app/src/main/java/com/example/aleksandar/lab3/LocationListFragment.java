package com.example.aleksandar.lab3;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.aleksandar.lab3.Data.Maps;
import com.example.aleksandar.lab3.Model.MapLocation;

import java.util.List;

/**
 * Created by Aleksandar on 06.12.2016.
 */

public class LocationListFragment extends Fragment {
    private RecyclerView mRecyclerView;
    private LocationAdapter mLocationAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_list, container, false);
        mRecyclerView = (RecyclerView) v.findViewById(R.id.location_recycle_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        updateUI();

        return v;
    }

    private void updateUI() {
        MapLab mapLab = MapLab.get(getActivity());
        List<MapLocation> maps = mapLab.getMaps();

        mLocationAdapter = new LocationAdapter(maps);
        mRecyclerView.setAdapter(mLocationAdapter);
    }
}
