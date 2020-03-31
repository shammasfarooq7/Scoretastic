package com.example.scoretastic;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Main extends AppCompatActivity {

    Button home,createEvent,myEvents,profile;
    FrameLayout frameLayout;
    LinearLayout linearLayout;
    Home objectHome = new Home();
    CreateEvent objectCreate = new CreateEvent();
    MyEvents objectEvent = new MyEvents();
    Profile objectProfile = new Profile();
    String userId;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if(user!=null){
            userId = user.getUid();
        }
        initializeViews();


    }


    private void initializeViews () {

            //toolbar1 = findViewById(R.id.toolbar1);
            home = findViewById(R.id.home);
            createEvent = findViewById(R.id.create);
            myEvents = findViewById(R.id.myevents);
            profile = findViewById(R.id.profile);
            frameLayout = findViewById(R.id.frameLayout);
            linearLayout = findViewById(R.id.toolbar);


            home.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, objectHome).commit();
                }
            });

            createEvent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, objectCreate).commit();
                }
            });

            myEvents.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, objectEvent).commit();
                }
            });

            profile.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, objectProfile).commit();
                }
            });

            getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, objectHome).commit();



    }
}
