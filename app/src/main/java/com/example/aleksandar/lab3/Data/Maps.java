package com.example.aleksandar.lab3.Data;

/**
 * Created by Aleksandar on 06.12.2016.
 */

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Maps {

    @SerializedName("results")
    @Expose
    public List<Result> results = null;

}