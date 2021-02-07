package com.new_name.treasure_hunt_app.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.new_name.treasure_hunt_app.R;

import static com.new_name.treasure_hunt_app.MapsActivity.count;

public class Winner extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_winner);

        count=0;

    }

    @Override
    public void onBackPressed() {

    }
}