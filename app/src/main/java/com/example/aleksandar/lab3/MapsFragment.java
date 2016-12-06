package com.example.aleksandar.lab3;

import android.location.Location;
import android.os.Bundle;

import com.example.aleksandar.lab3.Data.Maps;
import com.example.aleksandar.lab3.Data.Result;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.Map;

/**
 * Created by Aleksandar on 06.12.2016.
 */

public class MapsFragment extends SupportMapFragment {
    private GoogleApiClient mClient;
    private GoogleMap mMap;

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        mClient = new GoogleApiClient.Builder(getActivity())
                .addApi(LocationServices.API)
                .build();
        getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                mMap = googleMap;
                updateUI();
            }
        });
    }

    private void updateUI() {
        MapLab mapLab = MapLab.get(getActivity());
        Location mCurrentLocation = mapLab.getLocation();
        if (mMap == null || mCurrentLocation == null) {
            return;
        }
        Maps maps = mapLab.getMaps();
        LatLngBounds.Builder bounds = new LatLngBounds.Builder();
        mMap.clear();
        LatLng myPoint = new LatLng(mCurrentLocation.getLatitude(), mCurrentLocation.getLongitude());
        for (Result result : maps.results) {
            LatLng latLng = new LatLng(result.geometry.location.lat, result.geometry.location.lng);
            MarkerOptions markerOptions = new MarkerOptions()
                    .position(latLng).title(result.name)
                    .icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_pizza));
            bounds.include(latLng);
            mMap.addMarker(markerOptions);
        }
        MarkerOptions myMarker = new MarkerOptions().position(myPoint);
        mMap.addMarker(myMarker);
        LatLngBounds bound = bounds.include(myPoint).build();
        int margin = getResources().getDimensionPixelSize(R.dimen.map_insert_margin);
        CameraUpdate update = CameraUpdateFactory.newLatLngBounds(bound, margin);
        mMap.animateCamera(update);
    }

    @Override
    public void onStart() {
        super.onStart();
        mClient.connect();
    }

    @Override
    public void onStop() {
        super.onStop();
        mClient.disconnect();
    }
}
