package com.example.scoretastic;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class JoinEvent extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    TextView tvHost, tvSports, tvLocation, tvDate, tvTime, tvDescription, tvTotalPlayersJoined,tvJoin,btSeePlayers;
    Spinner dropDownSports;
    private DatabaseReference databaseReference;
    private FirebaseDatabase firebaseDatabase;
    DatabaseReference myReference;
    ArrayList<DataSnapshot> eventArray = new ArrayList();
    String sportsSet;
    int key;
    long maxId = 0;
    long maxIdJ = 0;
    ArrayList<String> spinnerArray = new ArrayList<>();
    Button btJoin;
    JoinEventData object = new JoinEventData();
    String uid;
    DatabaseReference userData;
    DatabaseReference userDataAll;
    ArrayList<DataSnapshot> userInfo;
    ArrayList<DataSnapshot> allUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join_event);
        userInfo = new ArrayList<>();
        allUser = new ArrayList<>();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            uid = user.getUid();
        }
        databaseReference = firebaseDatabase.getInstance().getReference("CreateEvent");
        myReference = firebaseDatabase.getInstance().getReference("JoinEvent");
        userData = firebaseDatabase.getInstance().getReference("UserData");
        userDataAll = firebaseDatabase.getInstance().getReference("UserData");
        tvDate = findViewById(R.id.tvDate);
        tvHost = findViewById(R.id.tvHost);
        tvSports = findViewById(R.id.tvSports);
        tvLocation = findViewById(R.id.tvLocation);
        tvTime = findViewById(R.id.tvTime);
        tvDescription = findViewById(R.id.tvDescription);
        tvTotalPlayersJoined = findViewById(R.id.tvPlayersJoined);
        tvJoin = findViewById(R.id.tvJoin);
        btJoin = findViewById(R.id.btJoin);
        btSeePlayers = findViewById(R.id.btSeePlayers);

        key = getIntent().getIntExtra("Event key",-1);
        key = key-1;

        userDataAll.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot ds : dataSnapshot.getChildren()){
                    allUser.add(ds);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                showData(dataSnapshot);
                spinnerData();
                maxId=(dataSnapshot.getChildrenCount());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        myReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                showData(dataSnapshot);
                spinnerData();
                maxIdJ=(dataSnapshot.getChildrenCount());
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


        btSeePlayers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),JoinedPlayersInfoRecycler.class);
                intent.putExtra("Event key", key+1);
                startActivity(intent);
            }
        });


        btJoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String keyString = String.valueOf(key+1);
                object.setEventKey(key+1);
                object.setUserId(uid);
                int subscribe = Integer.parseInt(userInfo.get(0).child("subscribed").getValue().toString().trim());
                subscribe++;
                userData.child(userInfo.get(0).child("id").getValue().toString().trim()).child("subscribed").setValue(subscribe);

                int playerCheck = Integer.parseInt(eventArray.get(key).child("totalPlayers").getValue().toString().trim());
                if(playerCheck == 0){
                    Toast.makeText(getApplicationContext(),"All the spots are filled, SORRY!",Toast.LENGTH_LONG).show();
                    finish();
                }
                else{
                    if(eventArray.get(key).child("sports").getValue().equals("Football")){
                        if(object.getPosition().equals("Attacker")){
                            int attacker = Integer.parseInt(eventArray.get(key).child("attacker").getValue().toString().trim());
                            if(attacker == 0){
                                Toast.makeText(getApplicationContext(),"You cannot join event for this position, Please select another one",Toast.LENGTH_LONG).show();
                                finish();
                            }
                            else{
                                attacker--;
                                int totalPlayers = Integer.parseInt(eventArray.get(key).child("totalPlayers").getValue().toString().trim());
                                totalPlayers--;
                                databaseReference.child(keyString).child("attacker").setValue(attacker);
                                databaseReference.child(keyString).child("totalPlayers").setValue(totalPlayers);
                                myReference.child(String.valueOf(maxIdJ+1)).setValue(object);
                                Toast.makeText(getApplicationContext(),"Event Joined",Toast.LENGTH_SHORT).show();
                                finish();
                            }
                        }
                        else if(object.getPosition().equals("Midfielder")){
                            int midfielder = Integer.parseInt(eventArray.get(key).child("midfielder").getValue().toString().trim());
                            if(midfielder == 0){
                                Toast.makeText(getApplicationContext(),"You cannot join event for this position, Please select another one",Toast.LENGTH_LONG).show();
                                finish();
                            }
                            else{
                                midfielder--;
                                int totalPlayers = Integer.parseInt(eventArray.get(key).child("totalPlayers").getValue().toString().trim());
                                totalPlayers--;
                                databaseReference.child(keyString).child("midfielder").setValue(midfielder);
                                databaseReference.child(keyString).child("totalPlayers").setValue(totalPlayers);
                                myReference.child(String.valueOf(maxIdJ+1)).setValue(object);
                                Toast.makeText(getApplicationContext(),"Event Joined",Toast.LENGTH_SHORT).show();
                                finish();
                            }
                        }
                        else if(object.getPosition().equals("Goal Keeper")){
                            int keeper = Integer.parseInt(eventArray.get(key).child("keeper").getValue().toString().trim());
                            if(keeper == 0){
                                Toast.makeText(getApplicationContext(),"You cannot join event for this position, Please select another one",Toast.LENGTH_LONG).show();
                                finish();
                            }
                            else{
                                keeper--;
                                int totalPlayers = Integer.parseInt(eventArray.get(key).child("totalPlayers").getValue().toString().trim());
                                totalPlayers--;
                                databaseReference.child(keyString).child("keeper").setValue(keeper);
                                databaseReference.child(keyString).child("totalPlayers").setValue(totalPlayers);
                                myReference.child(String.valueOf(maxIdJ+1)).setValue(object);
                                Toast.makeText(getApplicationContext(),"Event Joined",Toast.LENGTH_SHORT).show();
                                finish();
                            }
                        }
                        else if(object.getPosition().equals("Defender")){
                            int defender = Integer.parseInt(eventArray.get(key).child("defender").getValue().toString().trim());
                            if(defender == 0){
                                Toast.makeText(getApplicationContext(),"You cannot join event for this position, Please select another one",Toast.LENGTH_LONG).show();
                                finish();
                            }
                            else{
                                defender--;
                                int totalPlayers = Integer.parseInt(eventArray.get(key).child("totalPlayers").getValue().toString().trim());
                                totalPlayers--;
                                databaseReference.child(keyString).child("defender").setValue(defender);
                                databaseReference.child(keyString).child("totalPlayers").setValue(totalPlayers);
                                myReference.child(String.valueOf(maxIdJ+1)).setValue(object);
                                Toast.makeText(getApplicationContext(),"Event Joined",Toast.LENGTH_SHORT).show();
                                finish();
                            }

                        }
                    }
                    else if(eventArray.get(key).child("sports").getValue().equals("Cricket")){

                        if(object.getPosition().equals("Batsman")){
                            int batsman = Integer.parseInt(eventArray.get(key).child("batsman").getValue().toString().trim().trim());
                            if(batsman == 0){
                                Toast.makeText(getApplicationContext(),"You cannot join event for this position, Please select another one",Toast.LENGTH_LONG).show();
                                finish();
                            }
                            else{
                                batsman--;
                                int totalPlayers = Integer.parseInt(eventArray.get(key).child("totalPlayers").getValue().toString().trim());
                                totalPlayers--;
                                databaseReference.child(keyString).child("batsman").setValue(batsman);
                                databaseReference.child(keyString).child("totalPlayers").setValue(totalPlayers);
                                myReference.child(String.valueOf(maxIdJ+1)).setValue(object);
                                Toast.makeText(getApplicationContext(),"Event Joined",Toast.LENGTH_SHORT).show();
                                finish();
                            }
                        }
                        else if(object.getPosition().equals("Bowler")){
                            int bowlers = Integer.parseInt(eventArray.get(key).child("bowlers").getValue().toString().trim());
                            if(bowlers == 0){
                                Toast.makeText(getApplicationContext(),"You cannot join event for this position, Please select another one",Toast.LENGTH_LONG).show();
                                finish();
                            }
                            else{
                                bowlers--;
                                int totalPlayers = Integer.parseInt(eventArray.get(key).child("totalPlayers").getValue().toString().trim());
                                totalPlayers--;
                                databaseReference.child(keyString).child("bowlers").setValue(bowlers);
                                databaseReference.child(keyString).child("totalPlayers").setValue(totalPlayers);
                                myReference.child(String.valueOf(maxIdJ+1)).setValue(object);
                                Toast.makeText(getApplicationContext(),"Event Joined",Toast.LENGTH_SHORT).show();
                                finish();
                            }
                        }
                        else if(object.getPosition().equals("Wicket Keeper")){
                            int wicketKeeper = Integer.parseInt(eventArray.get(key).child("wicketKeeper").getValue().toString().trim());
                            if(wicketKeeper == 0){
                                Toast.makeText(getApplicationContext(),"You cannot join event for this position, Please select another one",Toast.LENGTH_LONG).show();
                                finish();
                            }
                            else{
                                wicketKeeper--;
                                int totalPlayers = Integer.parseInt(eventArray.get(key).child("totalPlayers").getValue().toString().trim());
                                totalPlayers--;
                                databaseReference.child(keyString).child("wicketKeeper").setValue(wicketKeeper);
                                databaseReference.child(keyString).child("totalPlayers").setValue(totalPlayers);
                                myReference.child(String.valueOf(maxIdJ+1)).setValue(object);
                                Toast.makeText(getApplicationContext(),"Event Joined",Toast.LENGTH_SHORT).show();
                                finish();
                            }
                        }
                        else if(object.getPosition().equals("All Rounder")){
                            int allRounder = Integer.parseInt(eventArray.get(key).child("allRounder").getValue().toString().trim()) ;
                            if(allRounder == 0){
                                Toast.makeText(getApplicationContext(),"You cannot join event for this position, Please select another one",Toast.LENGTH_LONG).show();
                                finish();
                            }
                            else{
                                allRounder--;
                                int totalPlayers = Integer.parseInt(eventArray.get(key).child("totalPlayers").getValue().toString().trim());
                                totalPlayers--;
                                databaseReference.child(keyString).child("allRounder").setValue(allRounder);
                                databaseReference.child(keyString).child("totalPlayers").setValue(totalPlayers);
                                myReference.child(String.valueOf(maxIdJ+1)).setValue(object);
                                Toast.makeText(getApplicationContext(),"Event Joined",Toast.LENGTH_SHORT).show();
                                finish();
                            }
                        }
                    }
                    else{
                        databaseReference.child(keyString).child("totalPlayers").setValue(0);
                        myReference.child(String.valueOf(maxIdJ+1)).setValue(object);
                        Toast.makeText(getApplicationContext(),"Event Joined",Toast.LENGTH_SHORT).show();
                        finish();
                    }
                }

            }

        });



    }

    public void showData(DataSnapshot dataSnapshot){
        for(DataSnapshot ds : dataSnapshot.getChildren()){
            eventArray.add(ds);
        }
        DataSnapshot eventKey = eventArray.get(key);

        if (eventKey != null){

            tvDate.setText(eventKey.child("day").getValue().toString() + "/" + eventKey.child("month").getValue().toString() + "/" + eventKey.child("year").getValue().toString());
            tvLocation.setText(eventKey.child("resultLocation").getValue().toString());
            tvTime.setText(eventKey.child("timeHour").getValue().toString() +":" + eventKey.child("timeMinute").getValue().toString());
            tvDescription.setText(eventKey.child("description").getValue().toString());
            tvTotalPlayersJoined.setText(eventKey.child("totalPlayers").getValue().toString());
            tvSports.setText(eventKey.child("sports").getValue().toString());
            sportsSet = eventKey.child("sports").getValue().toString().trim();
            for(int i = 0; i<allUser.size();i++){
                if(allUser.get(i).child("userId").getValue().toString().trim().equals(eventKey.child("uid").getValue().toString().trim())){
                    tvHost.setText(allUser.get(i).child("name").getValue().toString().trim());
                }
            }
        }
    }

    public void spinnerData(){
        if(sportsSet.equals("Football")){
            dropDownSports = findViewById(R.id.dropDownSports);
            dropDownSports.setOnItemSelectedListener(this);
            spinnerArray.add("Attacker");
            spinnerArray.add("Midfielder");
            spinnerArray.add("Defender");
            spinnerArray.add("Goal Keeper");

            ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, spinnerArray);
            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            dropDownSports.setAdapter(dataAdapter);
        }
        else if(sportsSet.equals("Cricket")){
            dropDownSports = findViewById(R.id.dropDownSports);
            dropDownSports.setOnItemSelectedListener(this);
            spinnerArray.add("Batsman");
            spinnerArray.add("Wicket Keeper");
            spinnerArray.add("Bowler");
            spinnerArray.add("All Rounder");
            ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, spinnerArray);
            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            dropDownSports.setAdapter(dataAdapter);
        }
        else if(sportsSet.equals("Tennis")||
                sportsSet.equals("Badminton")||
                sportsSet.equals("Squash")){
            dropDownSports = findViewById(R.id.dropDownSports);
            tvJoin.setVisibility(View.INVISIBLE);
            dropDownSports.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        String item = adapterView.getItemAtPosition(i).toString();

        if(item.equalsIgnoreCase("Attacker")){
            object.setPosition("Attacker");
        }
        else if(item.equalsIgnoreCase("Midfielder")){
            object.setPosition("Midfielder");
        }
        else if(item.equalsIgnoreCase("Defender")){
            object.setPosition("Defender");
        }
        else if(item.equalsIgnoreCase("Goal Keeper")){
            object.setPosition("Goal Keeper");
        }
        else if(item.equalsIgnoreCase("Batsman")){
            object.setPosition("Batsman");
        }
        else if(item.equalsIgnoreCase("Bowler")){
            object.setPosition("Bowler");
        }
        else if(item.equalsIgnoreCase("Wicket Keeper")){
            object.setPosition("Wicket Keeper");
        }
        else if(item.equalsIgnoreCase("All Rounder")){
            object.setPosition("All Rounder");
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}