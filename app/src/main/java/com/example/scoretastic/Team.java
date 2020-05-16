package com.example.scoretastic;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

public class Team extends AppCompatActivity {
    Button home, matches, messages;
    FrameLayout frameLayout;
    LinearLayout linearLayout;
    TeamHome teamHome = new TeamHome();
    TeamMatches teamMatches = new TeamMatches();
    TeamMessages teamMessages = new TeamMessages();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team);
        home = findViewById(R.id.btHome);
        matches = findViewById(R.id.btMatches);
        messages = findViewById(R.id.btMessages);
        frameLayout = findViewById(R.id.frameLayoutT);
        linearLayout = findViewById(R.id.toolbar);

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getSupportFragmentManager().beginTransaction().replace(R.id.frameLayoutT, teamHome).commit();
            }
        });

        matches.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getSupportFragmentManager().beginTransaction().replace(R.id.frameLayoutT, teamMatches).commit();
            }
        });

        messages.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getSupportFragmentManager().beginTransaction().replace(R.id.frameLayoutT, teamMessages).commit();
            }
        });

        getSupportFragmentManager().beginTransaction().replace(R.id.frameLayoutT, teamHome).commit();
    }
}
