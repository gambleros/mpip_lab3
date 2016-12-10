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
import com.example.aleksandar.lab3.Model.DaoSession;
import com.example.aleksandar.lab3.Model.MapLocation;
import com.example.aleksandar.lab3.Model.MapLocationDao;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.gson.Gson;

/**
 * Created by Aleksandar on 06.12.2016.
 */

public class MapsService extends IntentService {

    private static final String TAG = "MapsService";

    private static final int INTERVAL = 1000 * 60;

    public static final String ACTION_NOTIFICATION =
            "com.example.aleksandar.lab3.NOTIFY_DATA_CHANGE";
    public static final String PREM_PRIVATE =
            "com.example.aleksandar.lab3.PRIVATE";

    public static Intent newIntent(Context context) {
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
        } else {
            alarmManager.cancel(pi);
            pi.cancel();
        }
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (!isNetworkAvailableAndConnected()) {
            return;
        }
        GoogleApiClient googleApiClient = null;
        DaoSession daoSession = null;
        try {
            googleApiClient = new GoogleApiClient.Builder(this)
                    .addApi(LocationServices.API)
                    .build();

            googleApiClient.blockingConnect();

            Location loc = LocationServices.FusedLocationApi
                    .getLastLocation(googleApiClient);

            MapLab mL = MapLab.get(this);
            if (mL.getLocation() == null || mL.getLocation().distanceTo(loc) >= 2) {
//                Log.i("Log", "Hello1");
                mL.setLocation(loc);
            } else {
//                Log.i("Log", "Hello2");
                return;
            }

//            daoSession=((App)getApplication()).getDaoSession();
            daoSession = MapLab.getDao(this);

            MapLocationDao mapLocationDao = daoSession.getMapLocationDao();
            mapLocationDao.deleteAll();
            //TODO replace location string
//            String location = String.format("location=%.2f,%.2f", 41.99, 21.42);
            String location = String.format("location=%.2f,%.2f", loc.getLatitude(), loc.getLongitude());

            String json = new Fetch()
                    .getUrlString("https://maps.googleapis.com/maps/api/place/nearbysearch/json?" + location + "&key=AIzaSyD1qNrBFoW5ZZzxDb6i0Mm5sr-cxYynbXI&radius=5000&types=restaurant");

            Gson gson = new Gson();

            Maps mMapsList = gson.fromJson(json, Maps.class);

            for (Result r : mMapsList.results) {
                Log.i("Log", r.name);
                com.example.aleksandar.lab3.Data.Location dbloc = r.geometry.location;
                MapLocation tmpLoc = new MapLocation(r.name, r.rating, r.vicinity, dbloc.lat, dbloc.lng);
                mapLocationDao.insert(tmpLoc);
            }

            mL.setMaps(mapLocationDao.queryBuilder().list());
            sendBroadcast(new Intent(ACTION_NOTIFICATION),PREM_PRIVATE);

        } catch (SecurityException e) {
            Log.e(TAG, Log.getStackTraceString(e));
        } catch (Exception e) {
            Log.e(TAG, Log.getStackTraceString(e));
        } finally {
            googleApiClient.disconnect();
        }
    }

    @Override
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);

    }

    private boolean isNetworkAvailableAndConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        boolean isNetworkAvailable = cm.getActiveNetworkInfo() != null;
        boolean isNetworkConnected = isNetworkAvailable &&
                cm.getActiveNetworkInfo().isConnected();
        return isNetworkConnected;
    }

    public MapsService() {
        super(TAG);
    }

}
