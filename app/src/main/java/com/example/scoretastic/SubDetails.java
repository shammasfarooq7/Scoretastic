package com.example.scoretastic;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class SubDetails extends AppCompatActivity {
    TextView tvHost, tvSports, tvLocation, tvDate, tvTime, tvDescription, tvTotalPlayersJoined,tvYourPosition;
    Button btLeave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub_details);
        tvDate = findViewById(R.id.tvDate);
        tvHost = findViewById(R.id.tvHost);
        tvSports = findViewById(R.id.tvSports);
        tvLocation = findViewById(R.id.tvLocation);
        tvTime = findViewById(R.id.tvTime);
        tvDescription = findViewById(R.id.tvDescription);
        tvTotalPlayersJoined = findViewById(R.id.tvPlayersJoined);
        tvYourPosition = findViewById(R.id.tvYourPosition);
        btLeave = findViewById(R.id.btLeave);

    }
}
