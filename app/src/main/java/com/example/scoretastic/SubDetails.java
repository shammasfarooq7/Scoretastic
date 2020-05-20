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

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class SubDetails extends AppCompatActivity {
    TextView tvHost, tvSports, tvLocation, tvDate, tvTime, tvDescription, tvTotalPlayersJoined,tvYourPosition;
    Button btLeave;
    int key;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    String uid;
    ArrayList<DataSnapshot> eventArray = new ArrayList();
    ArrayList<DataSnapshot> subArray = new ArrayList();
    TextView btGoogleMap;
    String location;

    DatabaseReference userData;
    ArrayList<DataSnapshot> userInfo;
    String position;
    private DatabaseReference joinReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub_details);
        userData = firebaseDatabase.getInstance().getReference("UserData");
        userInfo = new ArrayList<>();
        btGoogleMap = findViewById(R.id.btGoogleMap);
        tvDate = findViewById(R.id.tvDate);
        tvHost = findViewById(R.id.tvHost);
        tvSports = findViewById(R.id.tvSports);
        tvLocation = findViewById(R.id.tvLocation);
        tvTime = findViewById(R.id.tvTime);
        tvDescription = findViewById(R.id.tvDescription);
        tvTotalPlayersJoined = findViewById(R.id.tvPlayersJoined);
        tvYourPosition = findViewById(R.id.tvYourPosition);
        btLeave = findViewById(R.id.btLeave);
        key = getIntent().getIntExtra("Event key Sub",-1);
        position = getIntent().getStringExtra("Position");
        databaseReference = firebaseDatabase.getInstance().getReference("CreateEvent");
        joinReference = firebaseDatabase.getInstance().getReference("JoinEvent");
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            uid = user.getUid();
        }
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                showData(dataSnapshot);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        joinReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                joinShowData(dataSnapshot);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        userData.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                userInfo.clear();
                for(DataSnapshot ds : dataSnapshot.getChildren()){
                    String currentUser = ds.child("userId").getValue().toString().trim();
                    if(currentUser.equals(uid.trim())){
                        userInfo.add(ds);
                    }
                }
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



        btLeave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int subscribe = Integer.parseInt(userInfo.get(0).child("subscribed").getValue().toString().trim());
                subscribe--;
                userData.child(userInfo.get(0).child("id").getValue().toString().trim()).child("subscribed").setValue(subscribe);

                joinReference.child(subArray.get(0).getKey()).child("userId").setValue(0);
                if(subArray.get(0).hasChild("position")){
                    if(subArray.get(0).child("position").getValue().toString().trim().equals("Attacker")){
                        String keyEvent = subArray.get(0).child("eventKey").getValue().toString().trim();
                        DataSnapshot ds = eventArray.get(key-1);
                        int totalPlayers = Integer.parseInt(ds.child("totalPlayers").getValue().toString().trim());
                        int position = Integer.parseInt(ds.child("attacker").getValue().toString().trim());
                        totalPlayers++;
                        position++;
                        databaseReference.child(keyEvent).child("totalPlayers").setValue(totalPlayers);
                        databaseReference.child(keyEvent).child("attacker").setValue(position);
                        Toast.makeText(getApplicationContext(),"Event Unsubscribed",Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(), Main.class);
                        startActivity(intent);
                    }
                    else if(subArray.get(0).child("position").getValue().toString().trim().equals("Midfielder")){
                        String keyEvent = subArray.get(0).child("eventKey").getValue().toString().trim();
                        DataSnapshot ds = eventArray.get(key-1);
                        int totalPlayers = Integer.parseInt(ds.child("totalPlayers").getValue().toString().trim());
                        int position = Integer.parseInt(ds.child("midfielder").getValue().toString().trim());
                        totalPlayers++;
                        position++;
                        databaseReference.child(keyEvent).child("totalPlayers").setValue(totalPlayers);
                        databaseReference.child(keyEvent).child("midfielder").setValue(position);
                        Toast.makeText(getApplicationContext(),"Event Unsubscribed",Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(), Main.class);
                        startActivity(intent);
                    }
                    else if(subArray.get(0).child("position").getValue().toString().trim().equals("Goal Keeper")){
                        String keyEvent = subArray.get(0).child("eventKey").getValue().toString().trim();
                        DataSnapshot ds = eventArray.get(key-1);
                        int totalPlayers = Integer.parseInt(ds.child("totalPlayers").getValue().toString().trim());
                        int position = Integer.parseInt(ds.child("keeper").getValue().toString().trim());
                        totalPlayers++;
                        position++;
                        databaseReference.child(keyEvent).child("totalPlayers").setValue(totalPlayers);
                        databaseReference.child(keyEvent).child("keeper").setValue(position);
                        Toast.makeText(getApplicationContext(),"Event Unsubscribed",Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(), Main.class);
                        startActivity(intent);
                    }
                    else if(subArray.get(0).child("position").getValue().toString().trim().equals("Defender")){
                        String keyEvent = subArray.get(0).child("eventKey").getValue().toString().trim();
                        DataSnapshot ds = eventArray.get(key-1);
                        int totalPlayers = Integer.parseInt(ds.child("totalPlayers").getValue().toString().trim());
                        int position = Integer.parseInt(ds.child("defender").getValue().toString().trim());
                        totalPlayers++;
                        position++;
                        databaseReference.child(keyEvent).child("totalPlayers").setValue(totalPlayers);
                        databaseReference.child(keyEvent).child("defender").setValue(position);
                        Toast.makeText(getApplicationContext(),"Event Unsubscribed",Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(), Main.class);
                        startActivity(intent);

                    }
                    else if(subArray.get(0).child("position").getValue().toString().trim().equals("Batsman")){
                        String keyEvent = subArray.get(0).child("eventKey").getValue().toString().trim();
                        DataSnapshot ds = eventArray.get(key-1);
                        int totalPlayers = Integer.parseInt(ds.child("totalPlayers").getValue().toString().trim());
                        int position = Integer.parseInt(ds.child("batsman").getValue().toString().trim());
                        totalPlayers++;
                        position++;
                        databaseReference.child(keyEvent).child("totalPlayers").setValue(totalPlayers);
                        databaseReference.child(keyEvent).child("batsman").setValue(position);
                        Toast.makeText(getApplicationContext(),"Event Unsubscribed",Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(), Main.class);
                        startActivity(intent);
                    }
                    else if(subArray.get(0).child("position").getValue().toString().trim().equals("Bowler")){
                        String keyEvent = subArray.get(0).child("eventKey").getValue().toString().trim();
                        DataSnapshot ds = eventArray.get(key-1);
                        int totalPlayers = Integer.parseInt(ds.child("totalPlayers").getValue().toString().trim());
                        int position = Integer.parseInt(ds.child("bowlers").getValue().toString().trim());
                        totalPlayers++;
                        position++;
                        databaseReference.child(keyEvent).child("totalPlayers").setValue(totalPlayers);
                        databaseReference.child(keyEvent).child("bowlers").setValue(position);
                        Toast.makeText(getApplicationContext(),"Event Unsubscribed",Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(), Main.class);
                        startActivity(intent);

                    }
                    else if(subArray.get(0).child("position").getValue().toString().trim().equals("Wicket Keeper")){
                        String keyEvent = subArray.get(0).child("eventKey").getValue().toString().trim();
                        DataSnapshot ds = eventArray.get(key-1);
                        int totalPlayers = Integer.parseInt(ds.child("totalPlayers").getValue().toString().trim());
                        int position = Integer.parseInt(ds.child("wicketKeeper").getValue().toString().trim());
                        totalPlayers++;
                        position++;
                        databaseReference.child(keyEvent).child("totalPlayers").setValue(totalPlayers);
                        databaseReference.child(keyEvent).child("wicketKeeper").setValue(position);
                        Toast.makeText(getApplicationContext(),"Event Unsubscribed",Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(), Main.class);
                        startActivity(intent);

                    }
                    else if(subArray.get(0).child("position").getValue().toString().trim().equals("All Rounder")){
                        String keyEvent = subArray.get(0).child("eventKey").getValue().toString().trim();
                        DataSnapshot ds = eventArray.get(key-1);
                        int totalPlayers = Integer.parseInt(ds.child("totalPlayers").getValue().toString().trim());
                        int position = Integer.parseInt(ds.child("allRounder").getValue().toString().trim());
                        totalPlayers++;
                        position++;
                        databaseReference.child(keyEvent).child("totalPlayers").setValue(totalPlayers);
                        databaseReference.child(keyEvent).child("allRounder").setValue(position);
                        Toast.makeText(getApplicationContext(),"Event Unsubscribed",Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(), Main.class);
                        startActivity(intent);

                    }

                }
                else{
                    String keyEvent = subArray.get(0).child("eventKey").getValue().toString().trim();
                    databaseReference.child(keyEvent).child("totalPlayers").setValue(1);
                    Toast.makeText(getApplicationContext(),"Event Unsubscribed",Toast.LENGTH_SHORT).show();
                    finish();
                    /*Intent intent = new Intent(getApplicationContext(), Main.class);
                    startActivity(intent);*/
                }
            }
        });

    }

    public void showData(DataSnapshot dataSnapshot){
        for(DataSnapshot ds : dataSnapshot.getChildren()){
            eventArray.add(ds);
        }
        DataSnapshot eventKey = eventArray.get(key-1);
        if(eventKey!=null){
            tvHost.setText(eventKey.getKey());
            tvDate.setText(eventKey.child("day").getValue().toString() + "/" + eventKey.child("month").getValue().toString() + "/" + eventKey.child("year").getValue().toString());
            tvLocation.setText(eventKey.child("resultLocation").getValue().toString());
            tvTime.setText(eventKey.child("timeHour").getValue().toString() +":" + eventKey.child("timeMinute").getValue().toString());
            tvDescription.setText(eventKey.child("description").getValue().toString());
            tvTotalPlayersJoined.setText(eventKey.child("totalPlayers").getValue().toString());
            tvSports.setText(eventKey.child("sports").getValue().toString());
            tvYourPosition.setText(position);
            String lat = eventKey.child("resultLat").getValue().toString().trim();
            String lng = eventKey.child("resultLng").getValue().toString().trim();
            location = "google.navigation:q=" + lat + ","  + lng;
        }
    }

    public void joinShowData(DataSnapshot dataSnapshot){
        for(DataSnapshot ds : dataSnapshot.getChildren()){
            String currentUser = ds.child("userId").getValue().toString().trim();
            int key1 = Integer.parseInt(ds.child("eventKey").getValue().toString().trim());
            if(currentUser.trim().equals(uid.trim()) && (key1 == key)) {
                subArray.add(ds);
            }
        }
    }

}
