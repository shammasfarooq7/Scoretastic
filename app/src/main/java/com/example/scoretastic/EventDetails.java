package com.example.scoretastic;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class
EventDetails extends AppCompatActivity {

    TextView etSports,etHost,etLocation,etTime,etTotalPlayers,etPosition,etConfirmation;
    Button btBack,btLeave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_details);
        etSports = findViewById(R.id.etSports);
        etHost = findViewById(R.id.etHost);
        etLocation = findViewById(R.id.etLocation);
        etTime = findViewById(R.id.tvMarkerTime);
        etTotalPlayers = findViewById(R.id.etTotalPlayers);
        etPosition = findViewById(R.id.etPosition);
        etConfirmation = findViewById(R.id.etConfirmation);
        btBack = findViewById(R.id.btBack);
        btLeave = findViewById(R.id.btLeave);

        btBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        btLeave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }
}
