package com.example.aleksandar.lab3;

import android.app.AlarmManager;
import android.app.IntentService;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.net.ConnectivityManager;
import android.os.SystemClock;
import android.util.Log;

import com.example.aleksandar.lab3.Data.Maps;
import com.example.aleksandar.lab3.Data.Result;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.gson.Gson;

/**
 * Created by Aleksandar on 06.12.2016.
 */

public class MapsService extends IntentService implements LocationListener{
    private static final String TAG = "MapsService";
    private Location mCurrentLocation;
    private static GoogleApiClient mClient;
//    private static Context mContext;

    private static final int INTERVAL = 1000 * 2;

    public static Intent newIntent(Context context) {
//        mContext=context;
        return new Intent(context, MapsService.class);
    }

    public static void setServiceAlarm(Context context, boolean isOn) {
        Intent i = MapsService.newIntent(context);
        PendingIntent pi = PendingIntent.getService(context, 0, i, 0);

        AlarmManager alarmManager = (AlarmManager) context.
                getSystemService(Context.ALARM_SERVICE);
        if (isOn) {
            alarmManager.setInexactRepeating(AlarmManager.ELAPSED_REALTIME,
                    SystemClock.elapsedRealtime(), INTERVAL, pi);
            mClient=new GoogleApiClient.Builder(context)
                    .addApi(LocationServices.API)
                    .build();
            mClient.connect();

        } else {
            alarmManager.cancel(pi);
            pi.cancel();
            mClient.disconnect();
        }
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (!isNetworkAvailableAndConnected()) {
            return;
        }
        try {
            findLocation();
            //Log.i("Log",mCurrentLocation.toString());
            MapLab mL = MapLab.get(this);
            if (mL.isFlag()) {
//                Log.i("Log", "Hello1");
                stopSelf();
                return;
            } else {
                mCurrentLocation=mL.getLocation();
            }
//            String location = String.format("location=%.2f,%.2f", 41.99, 21.42);
            String location = String.format("location=%.2f,%.2f", mCurrentLocation.getLatitude(), mCurrentLocation.getLongitude());
            String json = new Fetch()
                    .getUrlString("https://maps.googleapis.com/maps/api/place/nearbysearch/json?" + location + "&key=AIzaSyD1qNrBFoW5ZZzxDb6i0Mm5sr-cxYynbXI&radius=5000&types=restaurant");
            Gson gson = new Gson();
            Maps mMapsList = gson.fromJson(json, Maps.class);
            //Todo fill db with values
            mL.setMaps(mMapsList);
            for (Result r : mMapsList.results) {
                Log.i("Log", r.name);
            }


        } catch (Exception e) {

        }
        //Log.i("Log", "" + mL.getActiveFragment());
    }

    @Override
    public void onCreate() {
        super.onCreate();
//        /Log.i("Log","Create");
//        mClient=new GoogleApiClient.Builder(this)
//                .addApi(LocationServices.API)
//                .build();
    }

    @Override
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);

    }

    @Override
    public void onDestroy() {
//        super.onDestroy();
//        Log.i("Log","Destroy");
//        mClient.disconnect();
    }

    private boolean isNetworkAvailableAndConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        boolean isNetworkAvailable = cm.getActiveNetworkInfo() != null;
        boolean isNetworkConnected = isNetworkAvailable &&
                cm.getActiveNetworkInfo().isConnected();
        return isNetworkConnected;
    }

    private void findLocation() {

        LocationRequest request = LocationRequest.create()
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                .setNumUpdates(1)
                .setInterval(0);
        try {
            LocationServices.FusedLocationApi
                    .requestLocationUpdates(mClient, request,this);
        } catch (SecurityException e) {
            e.printStackTrace();
        }
    }

    public MapsService() {
        super(TAG);
    }


    @Override
    public void onLocationChanged(Location location) {
        MapLab mapLab=MapLab.get(this);
        Location tmp=mapLab.getLocation();
        if(tmp==null){
            mapLab.setLocation(location);
        }
        else if(tmp.distanceTo(location)<2){
            mapLab.setFlag(true);
            return;
        }
        else{
            mapLab.setLocation(location);
            mapLab.setFlag(false);
        }


    }
}
