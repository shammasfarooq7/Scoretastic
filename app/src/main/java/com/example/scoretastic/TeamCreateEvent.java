package com.example.scoretastic;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
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
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    private TimePickerDialog.OnTimeSetListener mTimeSetListener;
    String uid;
    Date date;
    TeamCreateEventData teamCreateEventData = new TeamCreateEventData();
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myReference = database.getReference("TeamCreateEvent");
    long maxId = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team_create_event);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            uid = user.getUid();
        }

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

        btCreateMatch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);
        Calendar cal1 = Calendar.getInstance();
        cal1.set(Calendar.MONTH,month);
        cal1.set(Calendar.YEAR,year);
        cal1.set(Calendar.DATE,day);
        date =  cal1.getTime();
        final DatePickerDialog dialog = new DatePickerDialog(this,
                android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                mDateSetListener,
                year,month,day);
        dialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        mDisplayDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.show();
            }
        });
        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                String date = month + "/" + day + "/" + year;
                mDisplayDate.setText(date);
            }
        };


        /*mDisplayTime = findViewById(R.id.tvMarkerTimeT);
        mDisplayTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                teamCreateEventData.setTimeHour(cal.get(Calendar.HOUR));
                teamCreateEventData.setTimeMinute(cal.get(Calendar.MINUTE));
                TimePickerDialog dialog = new TimePickerDialog(getApplicationContext(), mTimeSetListener, teamCreateEventData.getTimeHour(), teamCreateEventData.getTimeMinute(), false);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));
                dialog.show();
            }
        });

        mTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int hour, int minute) {
                String time = hour + ":" + minute;
                mDisplayTime.setText(time);
            }
        };*/

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
        else{
            teamCreateEventData.setId(maxId);
            teamCreateEventData.setDescription(etDescription.getText().toString().trim());
            teamCreateEventData.setDate(date);
            teamCreateEventData.setVariation(etVariation.getText().toString().trim());
            teamCreateEventData.setVenue(etVenue.getText().toString().trim());
            myReference.child(uid).setValue(teamCreateEventData);
            Toast.makeText(this,"Event Created",Toast.LENGTH_SHORT).show();
            finish();
        }

    }
}
