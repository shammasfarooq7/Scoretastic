package com.example.scoretastic;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class TeamCreateEvent extends AppCompatActivity {

    Spinner sportsSpinner;
    EditText etVariation, etVenue, etDescription;
    TextView mDisplayDate,mDisplayTime,btLocation;
    Button btCreateMatch;
    private static final String TAG = "Create Team Event";
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    private TimePickerDialog.OnTimeSetListener mTimeSetListener;
    String uid;
    TeamCreateEventData teamCreateEventData = new TeamCreateEventData();
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myReference = database.getReference("TeamCreateEvent");
    DatabaseReference teamUserEvent = database.getReference("TeamUserEvent");
    long maxId = 0;
    long maxIdU = 0;
    TimePickerDialog dialogTime;
    DatePickerDialog dialogDate;
    TeamUserEventData teamUserEventData = new TeamUserEventData();
    ArrayList<DataSnapshot> userInfo;
    DatabaseReference userData = database.getReference("UserData");

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team_create_event);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            uid = user.getUid();
        }
        userInfo = new ArrayList<>();

        initializeView();

        btLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =  new Intent(getApplicationContext(),CreateEventMap.class);
                startActivityForResult(intent,1);
            }
        });
        myReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    maxId=(dataSnapshot.getChildrenCount());
                }
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
        teamUserEvent.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    maxIdU=(dataSnapshot.getChildrenCount());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        btCreateMatch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int host = Integer.parseInt(userInfo.get(0).child("hosted").getValue().toString().trim());
                host++;
                userData.child(userInfo.get(0).child("id").getValue().toString().trim()).child("hosted").setValue(host);
                dataSender();
            }
        });
    }

    public void initializeView(){
        etDescription = findViewById(R.id.etDescriptionT);
        etVenue = findViewById(R.id.etVenue);
        etVariation = findViewById(R.id.etVariation);
        btCreateMatch = findViewById(R.id.btCreateMatch);
        btLocation = findViewById(R.id.btLocationT);

        sportsSpinner = findViewById(R.id.spinnerTeam);
        List<String> categories = new ArrayList<String>();
        categories.add("Please Select");
        categories.add("Football");
        categories.add("Cricket");
        categories.add("Futsal");
        categories.add("Basketball");
        categories.add("Hockey");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sportsSpinner.setAdapter(dataAdapter);
        sportsSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String item = adapterView.getItemAtPosition(i).toString();

                if (item.equalsIgnoreCase("Please Select")) {

                } else if (item.equalsIgnoreCase("Football")) {
                    teamCreateEventData.setSports("Football");

                } else if (item.equalsIgnoreCase("Cricket")) {
                    teamCreateEventData.setSports("Cricket");

                } else if (item.equalsIgnoreCase("Futsal")) {
                    teamCreateEventData.setSports("Futsal");

                } else if (item.equalsIgnoreCase("Basketball")) {
                    teamCreateEventData.setSports("Basketball");

                } else if (item.equalsIgnoreCase("Hockey")) {
                    teamCreateEventData.setSports("Hockey");

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        mDisplayDate = findViewById(R.id.tvMarkerDateT);
        mDisplayDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                dialogDate = new DatePickerDialog(TeamCreateEvent.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        String date = month+1 + "/" + day + "/" + year;
                        mDisplayDate.setText(date);
                        teamCreateEventData.setDay(day);
                        teamCreateEventData.setYear(year);
                        teamCreateEventData.setMonth(month+1);

                    }
                },year,month,day);
                dialogDate.show();
            }
        });

        mDisplayTime = findViewById(R.id.tvMarkerTimeT);
        mDisplayTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar calendar = Calendar.getInstance();
                teamCreateEventData.setTimeHour(calendar.get(Calendar.HOUR));
                teamCreateEventData.setTimeMinute(calendar.get(Calendar.MINUTE));
                dialogTime = new TimePickerDialog(TeamCreateEvent.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int i, int i1) {
                        teamCreateEventData.setTimeHour(i);
                        teamCreateEventData.setTimeMinute(i1);
                        mDisplayTime.setText(i +" : "+i1);
                    }
                },teamCreateEventData.getTimeHour(),teamCreateEventData.getTimeMinute(),false);
                dialogTime.show();
            }
        });

    }

    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==1){
            if (resultCode == Activity.RESULT_OK){
                teamCreateEventData.setResultLocation(data.getStringExtra("resultLocation"));
                teamCreateEventData.setResultLat(data.getDoubleExtra("resultLat",0));
                teamCreateEventData.setResultLng(data.getDoubleExtra("resultLng",0));
                btLocation.setText(teamCreateEventData.getResultLocation());
            }
            else if(resultCode == Activity.RESULT_CANCELED){
                btLocation.setText("Get Location");
            }
        }
    }
    public void dataSender(){
        if(etDescription.getText().toString().isEmpty()){
            etDescription.setError("Please enter Description");
        }
        else if(etVariation.getText().toString().isEmpty()){
            etVariation.setError("Please enter Variation");
        }
        else if(etVenue.getText().toString().isEmpty()){
            etVenue.setError("Please enter Venue");
        }
        else if(mDisplayDate.getText().toString().isEmpty()){
            mDisplayDate.setError("Please Enter date");
        }
        else if(mDisplayTime.getText().toString().isEmpty()){
            mDisplayTime.setError("Please Enter Time");
        }
        else if(btLocation.getText().toString().isEmpty()){
            btLocation.setError("Please Select Location");
        }
        else{
            teamCreateEventData.setId(maxId+1);
            teamCreateEventData.setDescription(etDescription.getText().toString().trim());
            teamCreateEventData.setVariation(etVariation.getText().toString().trim());
            teamCreateEventData.setVenue(etVenue.getText().toString().trim());
            teamCreateEventData.setUid(uid);
            teamCreateEventData.setStatus(1);
            teamUserEventData.setUid(uid);
            teamUserEventData.setEventId(maxId+1);
            myReference.child(String.valueOf(maxId+1)).setValue(teamCreateEventData);
            teamUserEvent.child(String.valueOf(maxIdU+1)).setValue(teamUserEventData);
            Toast.makeText(this,"Event Created",Toast.LENGTH_SHORT).show();
            finish();
        }

    }
}
