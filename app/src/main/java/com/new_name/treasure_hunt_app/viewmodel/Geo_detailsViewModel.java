package com.new_name.treasure_hunt_app.viewmodel;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.new_name.treasure_hunt_app.model.Geo_details;
import com.new_name.treasure_hunt_app.model.Geo_detailsRepository;

public class Geo_detailsViewModel extends AndroidViewModel {


    private Geo_detailsRepository mRepository;
    private Geo_details mgeo_details;

    public Geo_detailsViewModel(@NonNull Application application) {
        super(application);
        mRepository=new Geo_detailsRepository(application);
    }

    public Geo_details getGeo_details(String geoId){
        Log.d("loco", "  VIEWMODEL     getGeo_details: ");
        mgeo_details=mRepository.getGeo_details(geoId);
        return mgeo_details;
    }

    public void insert(Geo_details geo_details) { mRepository.insert(geo_details); }


}
