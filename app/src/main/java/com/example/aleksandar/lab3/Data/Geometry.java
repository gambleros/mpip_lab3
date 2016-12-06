package com.example.aleksandar.lab3.Data;

/**
 * Created by Aleksandar on 06.12.2016.
 */

import android.location.Location;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Geometry {

    @SerializedName("location")
    @Expose
    public com.example.aleksandar.lab3.Data.Location location;

}