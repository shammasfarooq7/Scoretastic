package com.example.scoretastic;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

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

    TextView tvHost, tvSports, tvLocation, tvDate, tvTime, tvDescription, tvTotalPlayersJoined,tvJoin;
    Spinner dropDownSports;
    private DatabaseReference databaseReference;
    private FirebaseDatabase firebaseDatabase;
    DatabaseReference myReference;
    ArrayList<DataSnapshot> eventArray = new ArrayList();
    String sportsSet;
    int key;
    ArrayList<String> spinnerArray = new ArrayList<>();
    Button btJoin;
    JoinEventData object = new JoinEventData();
    String uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join_event);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            uid = user.getUid();
        }
        databaseReference = firebaseDatabase.getInstance().getReference("CreateEvent");
        myReference = firebaseDatabase.getInstance().getReference("JoinEvent");
        tvDate = findViewById(R.id.tvDate);
        tvHost = findViewById(R.id.tvHost);
        tvSports = findViewById(R.id.tvSports);
        tvLocation = findViewById(R.id.tvLocation);
        tvTime = findViewById(R.id.tvTime);
        tvDescription = findViewById(R.id.tvDescription);
        tvTotalPlayersJoined = findViewById(R.id.tvTotalPlayer);
        tvJoin = findViewById(R.id.tvJoin);
        btJoin = findViewById(R.id.btJoin);

        key = getIntent().getIntExtra("Event key",-1);


        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                showData(dataSnapshot);
                spinnerData();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        btJoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String keyString = String.valueOf(key);
                object.setEventKey(key);
                object.setUserId(uid);
                if(eventArray.get(key).child("sports").getValue().equals("Football")){
                    if(object.getPosition().equals("Attacker")){
                        int attacker = Integer.parseInt(eventArray.get(key).child("attacker").getValue().toString().trim());
                        attacker--;
                        int totalPlayers = Integer.parseInt(eventArray.get(key).child("totalPlayers").getValue().toString().trim());
                        totalPlayers--;
                        databaseReference.child(keyString).child("attacker").setValue(attacker);
                        databaseReference.child(keyString).child("totalPlayers").setValue(totalPlayers);
                    }
                    else if(object.getPosition().equals("Midfielder")){
                        int midfielder = Integer.parseInt(eventArray.get(key).child("midfielder").getValue().toString().trim());
                        midfielder--;
                        int totalPlayers = Integer.parseInt(eventArray.get(key).child("totalPlayers").getValue().toString().trim());
                        totalPlayers--;
                        databaseReference.child(keyString).child("midfielder").setValue(midfielder);
                        databaseReference.child(keyString).child("totalPlayers").setValue(totalPlayers);
                    }
                    else if(object.getPosition().equals("Goal Keeper")){
                        int keeper = Integer.parseInt(eventArray.get(key).child("keeper").getValue().toString().trim());
                        keeper--;
                        int totalPlayers = Integer.parseInt(eventArray.get(key).child("totalPlayers").getValue().toString().trim());
                        totalPlayers--;
                        databaseReference.child(keyString).child("keeper").setValue(keeper);
                        databaseReference.child(keyString).child("totalPlayers").setValue(totalPlayers);
                    }
                    else if(object.getPosition().equals("Defender")){
                        int defender = Integer.parseInt(eventArray.get(key).child("defender").getValue().toString().trim());
                        defender--;
                        int totalPlayers = Integer.parseInt(eventArray.get(key).child("totalPlayers").getValue().toString().trim());
                        totalPlayers--;
                        databaseReference.child(keyString).child("defender").setValue(defender);
                        databaseReference.child(keyString).child("totalPlayers").setValue(totalPlayers);
                    }
                }
                else if(eventArray.get(key).child("sports").getValue().equals("Cricket")){

                    if(object.getPosition().equals("Batsman")){
                        int batsman = Integer.parseInt(eventArray.get(key).child("batsman").getValue().toString().trim().trim());
                        batsman--;
                        int totalPlayers = Integer.parseInt(eventArray.get(key).child("totalPlayers").getValue().toString().trim());
                        totalPlayers--;
                        databaseReference.child(keyString).child("batsman").setValue(batsman);
                        databaseReference.child(keyString).child("totalPlayers").setValue(totalPlayers);
                    }
                    else if(object.getPosition().equals("Bowler")){
                        int bowlers = Integer.parseInt(eventArray.get(key).child("bowlers").getValue().toString().trim());
                        bowlers--;
                        int totalPlayers = Integer.parseInt(eventArray.get(key).child("totalPlayers").getValue().toString().trim());
                        totalPlayers--;
                        databaseReference.child(keyString).child("bowlers").setValue(bowlers);
                        databaseReference.child(keyString).child("totalPlayers").setValue(totalPlayers);
                    }
                    else if(object.getPosition().equals("Wicket Keeper")){
                        int wicketKeeper = Integer.parseInt(eventArray.get(key).child("wicketKeeper").getValue().toString().trim());
                        wicketKeeper--;
                        int totalPlayers = Integer.parseInt(eventArray.get(key).child("totalPlayers").getValue().toString().trim());
                        totalPlayers--;
                        databaseReference.child(keyString).child("wicketKeeper").setValue(wicketKeeper);
                        databaseReference.child(keyString).child("totalPlayers").setValue(totalPlayers);
                    }
                    else if(object.getPosition().equals("All Rounder")){
                        int allRounder = Integer.parseInt(eventArray.get(key).child("allRounder").getValue().toString().trim()) ;
                        allRounder--;
                        int totalPlayers = Integer.parseInt(eventArray.get(key).child("totalPlayers").getValue().toString().trim());
                        totalPlayers--;
                        databaseReference.child(keyString).child("allRounder").setValue(allRounder);
                        databaseReference.child(keyString).child("totalPlayers").setValue(totalPlayers);
                    }
                }
                else{
                    databaseReference.child(keyString).child("totalPlayers").setValue(0);
                }

                myReference.setValue(object);

            }
        });

    }

    public void showData(DataSnapshot dataSnapshot){
        for(DataSnapshot ds : dataSnapshot.getChildren()){
            eventArray.add(ds);
        }
        DataSnapshot eventKey = eventArray.get(key);

        if (eventKey != null){

            tvHost.setText(eventKey.getKey());
            tvDate.setText(eventKey.child("date").child("date").getValue().toString() + "/" + eventKey.child("date").child("month").getValue().toString() + "/" + eventKey.child("date").child("year").getValue().toString());
            tvLocation.setText(eventKey.child("resultLocation").getValue().toString());
            tvTime.setText(eventKey.child("timeHour").getValue().toString() +":" + eventKey.child("timeMinute").getValue().toString());
            tvDescription.setText(eventKey.child("description").getValue().toString());
//            tvTotalPlayersJoined.setText(eventKey.child("totalPlayers").getValue().toString());
            tvSports.setText(eventKey.child("sports").getValue().toString());
            sportsSet = eventKey.child("sports").getValue().toString().trim();
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