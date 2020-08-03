package com.example.scoretastic;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class TeamHostMatchDetails extends AppCompatActivity {
    TextView tvHost,tvSports,tvVariation,tvVenue,tvLocation,tvDate,tvTime,tvDescription,btGoogleMap;
    Button btDelete;
    int key;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private DatabaseReference userEventReference;
    String location;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team_host_match_details);
        tvHost = findViewById(R.id.tvHost);
        tvSports = findViewById(R.id.tvSports);
        tvVariation = findViewById(R.id.tvVariation);
        tvVenue = findViewById(R.id.tvVenue);
        tvLocation = findViewById(R.id.tvLocation);
        tvDate = findViewById(R.id.tvDate);
        tvTime = findViewById(R.id.tvTime);
        tvDescription = findViewById(R.id.tvDescription);
        btGoogleMap = findViewById(R.id.btGoogleMap);
        btDelete = findViewById(R.id.btDelete);
        key = getIntent().getIntExtra("Key",-1);
        databaseReference = firebaseDatabase.getInstance().getReference("TeamCreateEvent");
        userEventReference = firebaseDatabase.getInstance().getReference("TeamUserEvent");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                showData(dataSnapshot);
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
        btDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                databaseReference.child(String.valueOf(key)).child("status").setValue(0);
                userEventReference.child(String.valueOf(key)).child("uid").setValue(0);
                Toast.makeText(getApplicationContext(),"Event Deleted",Toast.LENGTH_SHORT).show();
                finish();
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
                tvHost.setText("You");
            }
        }
    }
}