package com.new_name.treasure_hunt_app.ui;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofencingEvent;

import java.util.List;

public class GeofenceBrodcastReciver extends BroadcastReceiver {

    private static final String TAG = "GeofenceBrodcastReciver";
    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.
        //  Toast.makeText(context," YOU FOUND TREASUREEE ",Toast.LENGTH_LONG).show();
        Log.d("loco", "onReceive:          " );
        GeofencingEvent geofencingEvent=GeofencingEvent.fromIntent(intent);

        if(geofencingEvent.hasError()){
            Log.d(TAG, "onReceive: Error reciving geofence event ");
            return;
        }

        List<Geofence> geofenceList=geofencingEvent.getTriggeringGeofences();

        for (Geofence geofence: geofenceList){

            Log.d(TAG, "onReceive: " +geofence.getRequestId());
        }

        int transitionType = geofencingEvent.getGeofenceTransition();

        switch (transitionType){
            case Geofence.GEOFENCE_TRANSITION_ENTER:

                Log.d("loco", "onReceive:    GEOFENCE_TRANSITION_ENTER       " );
                //              geofenceList.get(0).getRequestId();   //    REQUEST ID IS THE GEOFENCE ID

                Toast.makeText(context, " YOU FOUND TREASUREEE ", Toast.LENGTH_LONG).show();

                //                        GOING TO A NEW ACTIVITY LAYOUT

                Intent i = new Intent();
                i.setClassName(context.getPackageName(), MainActivity.class.getName());
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                i.putExtra("key", geofenceList.get(0).getRequestId());

                context.startActivity(i );



                break;
            case Geofence.GEOFENCE_TRANSITION_DWELL:
                Toast.makeText(context, "GEOFENCE_TRANSITION_DWELL", Toast.LENGTH_LONG).show();
                break;
            case Geofence.GEOFENCE_TRANSITION_EXIT:
                Toast.makeText(context, "GEOFENCE_TRANSITION_EXIT", Toast.LENGTH_LONG).show();
                break;

        }





    }
}