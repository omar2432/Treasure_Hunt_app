package com.new_name.treasure_hunt_app.model;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

@Dao
public interface Geo_detailsDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Geo_details geo_details);

    @Query("DELETE FROM geofence_details_table")
    void deleteAll();

    @Query("SELECT * from geofence_details_table where id = :geo_id" )
    Geo_details getGeo_details(String geo_id);



}
