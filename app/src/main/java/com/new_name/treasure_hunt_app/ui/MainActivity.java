package com.new_name.treasure_hunt_app.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.new_name.treasure_hunt_app.MapsActivity;
import com.new_name.treasure_hunt_app.R;
import com.new_name.treasure_hunt_app.model.Geo_details;
import com.new_name.treasure_hunt_app.viewmodel.Geo_detailsViewModel;

import static com.new_name.treasure_hunt_app.MapsActivity.count;

public class MainActivity extends AppCompatActivity {

    private Geo_details Geo;
    private Geo_detailsViewModel mgeo_detailsViewModel;

    public static int score=0;
    private TextView Question_textView;
    private TextView Score_textView;
    EditText answer_text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //  GET VIEWMODEL
        mgeo_detailsViewModel = ViewModelProviders.of(this).get(Geo_detailsViewModel.class);
        //      GET CURRENT GEO DETAILS
        Geo = mgeo_detailsViewModel.getGeo_details("g"+count);


          Question_textView=findViewById(R.id.question);
          Question_textView.setText(Geo.getRiddle());

        Score_textView=findViewById(R.id.score);
        Score_textView.setText(Integer.toString(score));

        answer_text=findViewById(R.id.answer_text);
        Button button = (Button) findViewById(R.id.answer_button);

        button.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                      //  Toast.makeText(MainActivity.this.getApplicationContext(), " click "  +answer_text.getText() , Toast.LENGTH_LONG).show();

                        String Ans=answer_text.getText().toString().toLowerCase();


                        if (Ans.contains(Geo.getAnswer().toLowerCase())){
                            Toast.makeText(MainActivity.this.getApplicationContext(), " You Are Correct you got "  +Geo.getGold()+"  Gold" , Toast.LENGTH_LONG).show();
                            score+=Geo.getGold();
                            Score_textView.setText(Integer.toString(score));

                            count++;


                            if(count==6){
                                Intent myIntent = new Intent(MainActivity.this.getApplicationContext(), Winner.class);
                                startActivity(myIntent);

                            }else {

                                Intent myIntent = new Intent(MainActivity.this.getApplicationContext(), MapsActivity.class);
                                startActivity(myIntent);
                            }

                        }else {
                            Toast.makeText(MainActivity.this.getApplicationContext(), " Wrong answer " , Toast.LENGTH_SHORT).show();
                        }


                    }
                }
        );


        Toast.makeText(this, " Answer Question to get the gold ", Toast.LENGTH_LONG).show();

    }

    @Override
    public void onBackPressed() {

    }
}