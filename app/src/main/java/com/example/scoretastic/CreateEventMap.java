package com.example.scoretastic;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.maps.MapView;

public class CreateEventMap extends AppCompatActivity {


    TextView textView;
    EditText editText;
    MapView mapView;
    Button btcl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_event_map);


        textView = findViewById(R.id.textView);
        editText = findViewById(R.id.editText);
        mapView = findViewById(R.id.mapView);
        btcl = findViewById(R.id.btcl);


        btcl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                openCreateEvent();

            }
        });





    }


    public void openCreateEvent ()
    {
        Intent intent = new Intent(this,CreateEvent.class);
        startActivity(intent);

       //getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, objectCreate).commit();

    }


}
