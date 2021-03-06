package com.example.scoretastic;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class TeamMatchDetails extends AppCompatActivity {
    TextView tvHost,tvSports,tvVariation,tvVenue,tvLocation,tvDate,tvTime,tvDescription,btGoogleMap;
    Button btMessage;
    int key;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private DatabaseReference userReference;
    String uid,hostId,hostName;
    String location;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team_match_details);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            uid = user.getUid();
        }
        tvHost = findViewById(R.id.tvHost);
        tvSports = findViewById(R.id.tvSports);
        tvVariation = findViewById(R.id.tvVariation);
        tvVenue = findViewById(R.id.tvVenue);
        tvLocation = findViewById(R.id.tvLocation);
        tvDate = findViewById(R.id.tvDate);
        tvTime = findViewById(R.id.tvTime);
        tvDescription = findViewById(R.id.tvDescription);
        btGoogleMap = findViewById(R.id.btGoogleMap);
        btMessage = findViewById(R.id.btMessage);
        key = getIntent().getIntExtra("Event key",-1);
        databaseReference = firebaseDatabase.getInstance().getReference("TeamCreateEvent");
        userReference = firebaseDatabase.getInstance().getReference("UserData");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                showData(dataSnapshot);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        userReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                userShowData(dataSnapshot);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        btGoogleMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri gmmIntentUri = Uri.parse(location);
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                mapIntent.setPackage("com.google.android.apps.maps");
                if (mapIntent.resolveActivity(getPackageManager()) != null) {
                    startActivity(mapIntent);
                }
            }
        });
        btMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),Messenger.class);
                intent.putExtra("Host Id", hostId);
                intent.putExtra("Host Name", hostName);
                intent.putExtra("User Id", uid);
                startActivity(intent);
            }
        });
    }

    public void showData(DataSnapshot dataSnapshot){
        for(DataSnapshot ds : dataSnapshot.getChildren()){
            int id = Integer.parseInt(ds.child("id").getValue().toString().trim());
            if(id == key){
                tvDate.setText(ds.child("day").getValue().toString() + "/" + ds.child("month").getValue().toString() + "/" + ds.child("year").getValue().toString());
                tvLocation.setText(ds.child("resultLocation").getValue().toString());
                tvTime.setText(ds.child("timeHour").getValue().toString() +":" + ds.child("timeMinute").getValue().toString());
                tvDescription.setText(ds.child("description").getValue().toString());
                tvSports.setText(ds.child("sports").getValue().toString().trim());
                tvVariation.setText(ds.child("variation").getValue().toString().trim());
                tvVenue.setText(ds.child("venue").getValue().toString().trim());
                String lat = ds.child("resultLat").getValue().toString().trim();
                String lng = ds.child("resultLng").getValue().toString().trim();
                location = "google.navigation:q=" + lat + ","  + lng;
                hostId = ds.child("uid").getValue().toString().trim();

            }
        }
    }
    public void userShowData(DataSnapshot dataSnapshot){
        for(DataSnapshot ds : dataSnapshot.getChildren()){
            String id = ds.child("userId").getValue().toString().trim();
            if(id.equals(hostId)){
                tvHost.setText(ds.child("name").getValue().toString().trim());
                hostName = ds.child("name").getValue().toString().trim();
            }
        }
    }

}