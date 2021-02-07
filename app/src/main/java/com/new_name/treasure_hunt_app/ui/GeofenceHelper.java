package com.new_name.treasure_hunt_app.ui;

import android.app.PendingIntent;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;

import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofenceStatusCodes;
import com.google.android.gms.location.GeofencingRequest;
import com.google.android.gms.maps.model.LatLng;

public class GeofenceHelper extends ContextWrapper {

    public static final String TAG ="GeofencingHelper";
    public int request_code=555;
    PendingIntent pendingIntent;

    public GeofenceHelper(Context base) {
        super(base);
    }


    public GeofencingRequest getGeofencingRequest(Geofence geofence){

        return new GeofencingRequest.Builder()
                .addGeofence(geofence)
                .setInitialTrigger(GeofencingRequest.INITIAL_TRIGGER_ENTER)
                .build();
    }

    public Geofence getGeofence (String id, LatLng latlng, float radius, int transitionTypes){

        return new Geofence.Builder()
                .setCircularRegion(latlng.latitude,latlng.longitude,radius)
                .setRequestId(id)
                .setTransitionTypes(transitionTypes)
                .setLoiteringDelay(7000)
                .setExpirationDuration(Geofence.NEVER_EXPIRE)
                .build();
    }

    public PendingIntent getPendingIntent(){

        if(pendingIntent!= null){
            return pendingIntent;
        }

        Intent intent=new Intent(this,GeofenceBrodcastReciver.class);
        pendingIntent= PendingIntent.getBroadcast(this,request_code,intent,PendingIntent.FLAG_UPDATE_CURRENT);

        return pendingIntent;
    }


    public String getErrorString(Exception e){

        if(e instanceof ApiException){

            ApiException apiexception=(ApiException) e;
            switch (apiexception.getStatusCode()){

                case GeofenceStatusCodes
                        .GEOFENCE_NOT_AVAILABLE:
                    return "GEOFENCE_NOT_AVAILABLE";
                case GeofenceStatusCodes
                        .GEOFENCE_TOO_MANY_GEOFENCES:
                    return "GEOFENCE_TOO_MANY_GEOFENCES";
                case GeofenceStatusCodes
                        .GEOFENCE_TOO_MANY_PENDING_INTENTS:
                    return "GEOFENCE_TOO_MANY_PENDING_INTENTS";

            }
        }
        return e.getLocalizedMessage();
    }


}
