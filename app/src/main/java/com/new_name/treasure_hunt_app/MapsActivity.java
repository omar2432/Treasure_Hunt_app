package com.new_name.treasure_hunt_app;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.PendingIntent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.new_name.treasure_hunt_app.ui.GeofenceHelper;
import com.new_name.treasure_hunt_app.ui.PermissionUtils;
import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofencingClient;
import com.google.android.gms.location.GeofencingRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

import static com.new_name.treasure_hunt_app.ui.StartActivity.Geo;
import static com.new_name.treasure_hunt_app.ui.StartActivity.mgeo_detailsViewModel;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    public static int count=1;
    int Radius=80;

  //  private Geo_details Geo;
  //  private Geo_detailsViewModel mgeo_detailsViewModel;


    private static final String TAG = "MapsActivity";
    private GoogleMap mMap;
    private GeofencingClient geofencingClient;
    private GeofenceHelper geofenceHelper;
    private int Fine_Location_AccessRequestCode=1001;
    private int BackGround_Location_AccessRequestCode=1002;
    private Boolean NeedtoShowRequestPermissionRationale = true;
    LatLng current_treasure_latlng;

    String GEOFENC_ID="Geo_Id";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);


        //  GET VIEWMODEL
      //  mgeo_detailsViewModel = ViewModelProviders.of(this).get(Geo_detailsViewModel.class);
        //      GET CURRENT GEO DETAILS
        Geo = mgeo_detailsViewModel.getGeo_details("g"+count);


        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        geofencingClient= LocationServices.getGeofencingClient(this);
        geofenceHelper=new GeofenceHelper(this);





        while (Geo==null) {

        }


        if (Geo!=null) {
            double lat = Double.parseDouble(Geo.getLat());
            double lng = Double.parseDouble(Geo.getLng());

            current_treasure_latlng = new LatLng(lat, lng);

            //  Toast.makeText(this," current_treasure_latlng ",Toast.LENGTH_SHORT).show();
        }


        //  count++;

    }


    @Override
    public void onBackPressed() {

    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;


        //  LatLng new_cairo = new LatLng(30.03, 31.46);
        //  mMap.addMarker(new MarkerOptions().position(new_cairo).title("New Cairo"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(current_treasure_latlng));


        Log.d("loco", "onMapReady: ");

        enableUserLocation();




    }


    private void try_Adding_Geofence(LatLng latLng){

        addMarker(latLng);
      //   addCircle(latLng,Radius);    // Adds Circle Around treasure
        addGeofence(latLng,Radius);
    }


    private void enableBackgroundLocation(){

        if(Build.VERSION.SDK_INT>=29){

            //  WE NEED BACKGROUND PERMISSION

            if(ContextCompat.checkSelfPermission(this,Manifest.permission.ACCESS_BACKGROUND_LOCATION)
                    ==PackageManager.PERMISSION_GRANTED){

                //  IF WE HAVE PERMISSION
                try_Adding_Geofence(current_treasure_latlng);

            }else {
                //     WILL WE SHOW RATONALE ????
                if(ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.ACCESS_BACKGROUND_LOCATION)){

                    // SHOW RATIONALE
                    //      ASK FOR BACKGROUND PERMISSION
                    ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_BACKGROUND_LOCATION}
                            ,BackGround_Location_AccessRequestCode);

                }else {
                    //      ASK FOR BACKGROUND PERMISSION
                    ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_BACKGROUND_LOCATION}
                            ,BackGround_Location_AccessRequestCode);
                }

            }


        }else {
            // SDK Less than 29
            try_Adding_Geofence(current_treasure_latlng);

        }

    }



    @SuppressLint("MissingPermission")
    private void enableUserLocation(){



        if (PermissionUtils.isAccessFineLocationGranted(this)) {
            if (PermissionUtils.isLocationEnabled(this)) {
                mMap.setMyLocationEnabled(true);  // ****
                enableBackgroundLocation();
            } else  {
                PermissionUtils.showGPSNotEnabledDialog(this);
            }
        } else {
            PermissionUtils.requestAccessFineLocationPermission(this, Fine_Location_AccessRequestCode);
        }




     /*   if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)==
                PackageManager.PERMISSION_GRANTED){

            mMap.setMyLocationEnabled(true);    //*************

        }else {


            //  Ask for permission
            if(ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)){

                // SHOW DIAOLG
                ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        Fine_Location_AccessRequestCode);


            }else{
                ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        Fine_Location_AccessRequestCode);

            }

        }
*/

    }



    @SuppressLint("MissingPermission")
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

       /* if(requestCode==Fine_Location_AccessRequestCode){
            if(grantResults.length>0 && grantResults[0]== PackageManager.PERMISSION_GRANTED ){

                // WE HAVE PERMISSION
                mMap.setMyLocationEnabled(true);      /************
            }else {

                // not granted
            }

        }
*/



        if (requestCode == Fine_Location_AccessRequestCode) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                if (PermissionUtils.isLocationEnabled(this)) {
                    mMap.setMyLocationEnabled(true);       //*************
                    enableBackgroundLocation();
                } else {
                    PermissionUtils.showGPSNotEnabledDialog(this);
                }

            } else {

                // permission denied, boo!
                // Explain to the user that the feature is unavailable because
                // the features requires a permission that the user has denied.
                // At the same time, respect the user's decision. Don't link to
                // system settings in an effort to convince the user to change
                // their decision.
                if (NeedtoShowRequestPermissionRationale) {
                    PermissionUtils.ShowRequestPermissionRationale(this, this, Fine_Location_AccessRequestCode);
                    NeedtoShowRequestPermissionRationale = false;
                } else {

                    Toast.makeText(this, "Permission Not Granted", Toast.LENGTH_LONG).show();

                }

            }



        }




        if(requestCode==BackGround_Location_AccessRequestCode){
            if(grantResults.length>0 && grantResults[0]== PackageManager.PERMISSION_GRANTED ){

                // WE HAVE PERMISSION
                try_Adding_Geofence(current_treasure_latlng);    //********//*********//
            }else {

                // not granted
                Toast.makeText(this, "BackGround Location not granted cannot add geofences", Toast.LENGTH_LONG).show();

            }

        }




    }


    @SuppressLint("MissingPermission")
    private void addGeofence(LatLng latlng, float radius){
       // Log.d("loco", "Geofence LatLng  " + latlng +"      "+ radius );
        Geofence geofence=geofenceHelper.getGeofence(GEOFENC_ID,latlng,radius,Geofence.GEOFENCE_TRANSITION_ENTER);
        GeofencingRequest geofencingRequest=geofenceHelper.getGeofencingRequest(geofence);
        PendingIntent pendingIntent=geofenceHelper.getPendingIntent();

        geofencingClient.addGeofences(geofencingRequest,pendingIntent)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d("loco", "Geofence added  " );
                        Log.d(TAG, "Geofence added");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d("loco", "Geofence OnFailure  " );
                        String errorMessage=geofenceHelper.getErrorString(e);
                        Log.d(TAG, "onFailure: "+errorMessage);

                    }
                });



    }


    private void addMarker(LatLng latLng){
        MarkerOptions markerOptions=new MarkerOptions().position(latLng);
        markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.chest));
        mMap.addMarker(markerOptions);

    }


    private void addCircle(LatLng latLng,float radius){

        CircleOptions circleOptions=new CircleOptions();
        circleOptions.center(latLng);
        circleOptions.radius(radius);
        circleOptions.strokeColor(Color.argb(255,0,0,0));
        //  circleOptions.fillColor(Color.argb(255,255,0,0));
        circleOptions.strokeWidth(2);
        mMap.addCircle(circleOptions);


    }



}