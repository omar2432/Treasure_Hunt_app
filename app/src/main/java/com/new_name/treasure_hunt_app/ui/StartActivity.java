package com.new_name.treasure_hunt_app.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.new_name.treasure_hunt_app.MapsActivity;
import com.new_name.treasure_hunt_app.R;
import com.new_name.treasure_hunt_app.model.Geo_details;
import com.new_name.treasure_hunt_app.viewmodel.Geo_detailsViewModel;

import static com.new_name.treasure_hunt_app.MapsActivity.count;

public class StartActivity extends AppCompatActivity {


    public static Geo_details Geo;
    public static Geo_detailsViewModel mgeo_detailsViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);


        //  GET VIEWMODEL
        mgeo_detailsViewModel = ViewModelProviders.of(this).get(Geo_detailsViewModel.class);
        //      GET CURRENT GEO DETAILS
        Geo = mgeo_detailsViewModel.getGeo_details("g"+count);


        Button button = (Button) findViewById(R.id.start_button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(StartActivity.this.getApplicationContext(), MapsActivity.class);
                startActivity(myIntent);

            }
        });


    }
}