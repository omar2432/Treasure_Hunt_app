package com.new_name.treasure_hunt_app.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;


@Entity(tableName = "geofence_details_table")
public class Geo_details{


    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "id")
    private String Geo_id;

    /*  @NonNull
      @ColumnInfo(name = "latLng")
      private LatLng latLng;
  */
    @NonNull
    @ColumnInfo(name = "lat")
    private String lat;

    @NonNull
    @ColumnInfo(name = "lng")
    private String lng;

    @NonNull
    @ColumnInfo(name = "description")
    private String Geo_location_description;

    @NonNull
    @ColumnInfo(name = "riddle")
    private String riddle;

    @NonNull
    @ColumnInfo(name = "answer")
    private String answer;

    @NonNull
    @ColumnInfo(name = "answered")
    private boolean answered;

    @NonNull
    @ColumnInfo(name = "gold")
    private int gold;


    public Geo_details(@NonNull String Geo_id,@NonNull String lat,@NonNull String lng,
                       @NonNull String Geo_location_description,@NonNull String riddle,
                       @NonNull String answer,@NonNull boolean answered,@NonNull int gold) {

        this.Geo_id = Geo_id;
        this.Geo_location_description=Geo_location_description;
        this.riddle=riddle;
        this.lat=lat;
        this.lng=lng;
        this.answer=answer;
        this.answered=answered;
        this.gold=gold;

    }


    public String getGeo_id(){return this.Geo_id;}
    public String getLat(){return this.lat;}
    public String getLng(){return this.lng;}
    public String getGeo_location_description(){return this.Geo_location_description;}
    public String getRiddle(){return this.riddle;}
    public String getAnswer(){return this.answer;}
    public boolean getAnswered(){return this.answered;}
    public int getGold(){return this.gold;}




}
