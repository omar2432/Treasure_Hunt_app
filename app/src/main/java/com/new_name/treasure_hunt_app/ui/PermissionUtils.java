package com.new_name.treasure_hunt_app.ui;

import android.Manifest;
import android.app.Activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.provider.Settings;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.new_name.treasure_hunt_app.R;

public class PermissionUtils {


    /**
     * Function to request permission from the user
     */
    public static void requestAccessFineLocationPermission(Activity AppCompatActivity, int MY_PERMISSIONS_REQUEST_LOCATION){
        ActivityCompat.requestPermissions(AppCompatActivity,
                new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                MY_PERMISSIONS_REQUEST_LOCATION);
    }


    /**
     * Function to check if the location permissions are granted or not
     */
    public static Boolean isAccessFineLocationGranted(Context context){
        return (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION ) == PackageManager.PERMISSION_GRANTED);
    }

    /**
     * Function to check if location of the device is enabled or not
     */
    public static Boolean  isLocationEnabled(Context context){
        LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        return (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
                || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER));
    }

    /**
     * Function to show the "enable GPS" Dialog box
     */
    public static void showGPSNotEnabledDialog(final Context context) {
        new AlertDialog.Builder(context)
                .setTitle(R.string.enable_gps)
                .setMessage(R.string.required_for_this_app)
                .setCancelable(false)
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //Prompt the user once explanation has been shown
                        context.startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                    }
                })
                .create()
                .show();

    }


    /**
     * Function to show the Request Permission Rationale Dialog box
     */
    public static void ShowRequestPermissionRationale(Context context, final Activity AppCompatActivity, final int MY_PERMISSIONS_REQUEST_LOCATION) {
        new AlertDialog.Builder(context)
                .setTitle(R.string.show_rational)
                .setMessage(R.string.msg_body)
                .setCancelable(false)
                .setPositiveButton(R.string.permission, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //Prompt the user once explanation has been shown
                        requestAccessFineLocationPermission(AppCompatActivity,  MY_PERMISSIONS_REQUEST_LOCATION);
                    }
                })
                .create()
                .show();

    }

}

