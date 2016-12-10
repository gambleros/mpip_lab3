package com.example.aleksandar.lab3.Model;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by Aleksandar on 10.12.2016.
 */

@Entity
public class MapLocation {
    @Id(autoincrement = true)
    private Long id;

    private String name;
    private Double rating;
    private String vicinity;

    private Double latitude;
    private Double longitude;

    public MapLocation(String name, Double rating, String vicinity, Double latitude, Double longitude) {
        this.name = name;
        this.rating = rating;
        this.vicinity = vicinity;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    @Generated(hash = 50491896)
    public MapLocation(Long id, String name, Double rating, String vicinity,
            Double latitude, Double longitude) {
        this.id = id;
        this.name = name;
        this.rating = rating;
        this.vicinity = vicinity;
        this.latitude = latitude;
        this.longitude = longitude;
    }
    @Generated(hash = 1041917947)
    public MapLocation() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public Double getRating() {
        return this.rating;
    }
    public void setRating(Double rating) {
        this.rating = rating;
    }
    public String getVicinity() {
        return this.vicinity;
    }
    public void setVicinity(String vicinity) {
        this.vicinity = vicinity;
    }
    public Double getLatitude() {
        return this.latitude;
    }
    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }
    public Double getLongitude() {
        return this.longitude;
    }
    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }
}
