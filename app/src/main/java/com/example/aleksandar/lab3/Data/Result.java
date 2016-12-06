package com.example.aleksandar.lab3.Data;

/**
 * Created by Aleksandar on 06.12.2016.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Result {

    @SerializedName("geometry")
    @Expose
    public Geometry geometry;
    @SerializedName("name")
    @Expose
    public String name;
    @SerializedName("rating")
    @Expose
    public Double rating;
    @SerializedName("vicinity")
    @Expose
    public String vicinity;

}
