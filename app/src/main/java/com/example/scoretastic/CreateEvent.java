package com.example.scoretastic;


import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import android.widget.AdapterView.OnItemSelectedListener;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;

public class CreateEvent extends Fragment implements OnItemSelectedListener,DatePickerDialog.OnDateSetListener {


    TextView tvCrtTitle,tvT1,tvT2,tvT3,tvT4,tvcategory,tvRb,tvRb1,tvRb2,tvRb3,tvRbC,tvRbC1,tvRbC2,tvRbC3,tvDate,tvTime,tvloc;
    private TextView mDisplayDate,mDisplayTime;
    EditText et4,etB,etB1,etB2,etB3,etBC,etBC1,etBC2,etBC3;
    Spinner spinner;
    private Button btce;
    ConstraintLayout cl1,cl2,cl3;
    Activity mActivity;
    long maxId = 0;
    int year;
    int month;
    int day;
    Date date;
    private static final String TAG = "Create Event";
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    private TimePickerDialog.OnTimeSetListener mTimeSetListener;
    CreateEventData createEventData = new CreateEventData();
    createEventCricket createEventCricket = new createEventCricket();
    createEventFootball createEventFootball = new createEventFootball();
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myReference = database.getReference("CreateEvent");


    public CreateEvent() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_create_event, container, false);
        initializeViews(view);
        tvloc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =  new Intent(getActivity(),CreateEventMap.class);
                startActivityForResult(intent,1);


                btce.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        createEventData.setDescription(et4.getText().toString());
                        createEventData.setDate(date);
                        if(createEventData.getSports().equals("Football")){
                            dataSenderFootball();
                        }
                        else if(createEventData.getSports().equals("Cricket")){
                            dataSenderCricket();
                        }
                        else{
                            dataSender();
                        }

                    }

                });

            }
        });
        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==1){
            if (resultCode == Activity.RESULT_OK){
                createEventData.setResultLocation(data.getStringExtra("resultLocation"));
                createEventData.setResultLat(data.getDoubleExtra("resultLat",0));
                createEventData.setResultLng(data.getDoubleExtra("resultLng",0));
                tvloc.setText(createEventData.getResultLocation());
            }
            else if(resultCode == Activity.RESULT_CANCELED){
                tvloc.setText("Get Location");
            }
        }
    }

    private void initializeViews(View view) {

        mActivity = getActivity();
        datePicker(view);
        timePicker(view);
        btce = view.findViewById(R.id.btce);
        tvCrtTitle = view.findViewById(R.id.tvCrtTitle);
        tvloc = view.findViewById(R.id.tvloc);
        tvT1 = view.findViewById(R.id.tvT1);
        tvT2 = view.findViewById(R.id.tvT2);
        tvT3 = view.findViewById(R.id.tvT3);
        tvT4 = view.findViewById(R.id.tvT4);
        tvcategory = view.findViewById(R.id.tvcategory);
        tvRb = view.findViewById(R.id.tvRb);
        tvRb1 = view.findViewById(R.id.tvRb1);
        tvRb2 = view.findViewById(R.id.tvRb2);
        tvRb3 = view.findViewById(R.id.tvRb3);
        tvRbC = view.findViewById(R.id.tvRbC);
        tvRbC1 = view.findViewById(R.id.tvRbC1);
        tvRbC2 = view.findViewById(R.id.tvRbC2);
        tvRbC3 = view.findViewById(R.id.tvRbC3);
        etB = view.findViewById(R.id.etB);
        etB1 = view.findViewById(R.id.etB1);
        etB2 = view.findViewById(R.id.etB2);
        etB3 = view.findViewById(R.id.etB3);
        etBC = view.findViewById(R.id.etBC);
        etBC1 = view.findViewById(R.id.etBC1);
        etBC2 = view.findViewById(R.id.etBC2);
        etBC3 = view.findViewById(R.id.etBC3);
        tvDate = view.findViewById(R.id.tvMarkerDate);
        tvTime = view.findViewById(R.id.tvMarkerTime);
        et4 = view.findViewById(R.id.tvSports);
        cl1 = view.findViewById(R.id.cl1);
        cl2 = view.findViewById(R.id.cl2);
        cl3 = view.findViewById(R.id.cl3);
        spinner = view.findViewById(R.id.spinner);
        spinner.setOnItemSelectedListener(this);
        List<String> categories = new ArrayList<String>();
        categories.add("Please Select");
        categories.add("Football");
        categories.add("Cricket");
        categories.add("tennis");
        categories.add("Badminton");
        categories.add("Squash");


        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, categories);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinner.setAdapter(dataAdapter);

        btce.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validationScene();


            }
        });

        myReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists())
                    maxId=(dataSnapshot.getChildrenCount());

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }


    private void datePicker(View view) {

        mDisplayDate = view.findViewById(R.id.tvMarkerDate);

        Calendar cal = Calendar.getInstance();
         int year = cal.get(Calendar.YEAR);
         int month = cal.get(Calendar.MONTH);
         int day = cal.get(Calendar.DAY_OF_MONTH);
         Calendar cal1 = Calendar.getInstance();
         cal1.set(Calendar.MONTH,month);
         cal1.set(Calendar.YEAR,year);
         cal1.set(Calendar.DATE,day);
         date =  cal1.getTime();

        final DatePickerDialog dialog = new DatePickerDialog(
                getContext(),
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
                Log.d(TAG, "onDateSet: mm/dd/yyy: " + month + "/" + day + "/" + year);
                String date = month + "/" + day + "/" + year;
                mDisplayDate.setText(date);
            }
        };
    }

    private void timePicker(View view) {
        mDisplayTime = view.findViewById(R.id.tvMarkerTime);

        mDisplayTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                createEventData.setTimeHour(cal.get(Calendar.HOUR));
                createEventData.setTimeMinute(cal.get(Calendar.MINUTE));
                TimePickerDialog dialog = new TimePickerDialog(getContext(), mTimeSetListener, createEventData.getTimeHour(), createEventData.getTimeMinute(), false);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));
                dialog.show();
            }
        });

        mTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int hour, int minute) {

                Log.d(TAG, "onTimeSet: hr/min: " + hour + "/" + minute);
                String time = hour + ":" + minute;
                mDisplayTime.setText(time);
            }
        };

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        String item = parent.getItemAtPosition(position).toString();

        if (item.equalsIgnoreCase("Please Select")) {
            cl1.setVisibility(View.GONE);
            cl2.setVisibility(View.GONE);
            cl3.setVisibility(View.GONE);

        } else if (item.equalsIgnoreCase("Football")) {
            createEventData.setSports("Football");

            cl1.setVisibility(View.VISIBLE);
            cl2.setVisibility(View.GONE);
            cl3.setVisibility(View.GONE);


        } else if (item.equalsIgnoreCase("Cricket")) {

            createEventData.setSports("Cricket");
            cl1.setVisibility(View.GONE);
            cl2.setVisibility(View.VISIBLE);
            cl3.setVisibility(View.GONE);


        } else if (item.equalsIgnoreCase("Tennis")) {

            cl1.setVisibility(View.GONE);
            cl2.setVisibility(View.GONE);
            cl3.setVisibility(View.VISIBLE);
            createEventData.setTotalPlayers(1);
            createEventData.setSports("Tennis");

        } else if (item.equalsIgnoreCase("Badminton")) {

            cl1.setVisibility(View.GONE);
            cl2.setVisibility(View.GONE);
            cl3.setVisibility(View.VISIBLE);
            createEventData.setTotalPlayers(1);
            createEventData.setSports("Badminton");

        } else if (item.equalsIgnoreCase("Squash")) {

            cl1.setVisibility(View.GONE);
            cl2.setVisibility(View.GONE);
            cl3.setVisibility(View.VISIBLE);
            createEventData.setTotalPlayers(1);
            createEventData.setSports("Squash");
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }

    public void validationScene() {

        if (tvloc.getText().toString().isEmpty()) {
            tvloc.setError("Please enter the location!");
        } else if (tvDate.getText().toString().isEmpty()) {
            tvDate.setError("Please enter the date");
        } else if (tvTime.getText().toString().isEmpty()) {
            tvTime.setError("Please enter the time!!!");
        } else if (et4.getText().toString().isEmpty()) {
            et4.setError("enter the description");
        } else if (etB.getText().toString().isEmpty()) {
            etB.setError("Please enter");
        } else if (etB1.getText().toString().isEmpty()) {
            etB1.setError("Please enter");
        } else if (etB2.getText().toString().isEmpty()) {
            etB2.setError("Please enter");
        } else if (etB3.getText().toString().isEmpty()) {
            etB3.setError("Please enter");
        }


    }
    @Override
    public void onDateSet(DatePicker view, int Year, int Month, int Day) {
        month = month + 1;
        Log.d(TAG, "onDateSet: mm/dd/yyy: " + month + "/" + day + "/" + year);
        String date = month + "/" + day + "/" + year;
        mDisplayDate.setText(date);
    }

    //THIS IS THE METHOD WHERE YOU HAVE TO SEND DATA TO FIREBASE. CALL THIS METHOD IN ONCREATEVIEW.
    private void dataSender(){


        myReference.child(String.valueOf(maxId+1)).setValue(createEventData);
        //myReference.setValue(createEventData);
        Toast.makeText(getContext(),"Data Saved",Toast.LENGTH_SHORT).show();
    }

    private void dataSenderFootball(){

        createEventFootball.setDate(createEventData.getDate());
        createEventFootball.setDescription(createEventData.getDescription());
        createEventFootball.setResultLat(createEventData.getResultLat());
        createEventFootball.setResultLng(createEventData.resultLng);
        createEventFootball.setResultLocation(createEventData.resultLocation);
        createEventFootball.setTimeHour(createEventData.getTimeHour());
        createEventFootball.setTimeMinute(createEventData.getTimeMinute());
        createEventFootball.setSports(createEventData.getSports());
        if(!etB.getText().toString().trim().equals("")){
            createEventFootball.setAttacker(Integer.valueOf(etB.getText().toString().trim()));
        }
        if(!etB1.getText().toString().trim().equals("")){
            createEventFootball.setDefender(Integer.valueOf(etB1.getText().toString().trim()));
        }
        if(!etB2.getText().toString().trim().equals("")){
            createEventFootball.setMidfielder(Integer.valueOf(etB2.getText().toString().trim()));
        }
        if(!etB3.getText().toString().trim().equals("")){
            createEventFootball.setKeeper(Integer.valueOf(etB3.getText().toString().trim()));
        }
        createEventFootball.setTotalPlayers(createEventFootball.getAttacker() + createEventFootball.getDefender() + createEventFootball.getMidfielder() + createEventFootball.getKeeper());

        //myReference.setValue(createEventFootball);

        myReference.child(String.valueOf(maxId+1)).setValue(createEventFootball);
        Toast.makeText(getContext(),"Data Saved",Toast.LENGTH_SHORT).show();
    }

    private void dataSenderCricket(){

        createEventCricket.setDate(createEventData.getDate());
        createEventCricket.setDescription(createEventData.getDescription());
        createEventCricket.setResultLat(createEventData.getResultLat());
        createEventCricket.setResultLng(createEventData.resultLng);
        createEventCricket.setResultLocation(createEventData.resultLocation);
        createEventCricket.setTimeHour(createEventData.getTimeHour());
        createEventCricket.setTimeMinute(createEventData.getTimeMinute());
        createEventCricket.setSports(createEventData.getSports());
        if(!etBC.getText().toString().trim().equals("")){
            createEventCricket.setBatsman(Integer.valueOf(etBC.getText().toString().trim()));
        }

        if(!etBC1.getText().toString().trim().equals("")){
            createEventCricket.setBowlers(Integer.valueOf(etBC1.getText().toString().trim()));
        }

        if(!etBC2.getText().toString().trim().equals("")){
            createEventCricket.setAllRounder(Integer.valueOf(etBC2.getText().toString().trim()));
        }

        if(!etBC3.getText().toString().trim().equals("")){
            createEventCricket.setWicketKeeper(Integer.valueOf(etBC3.getText().toString().trim()));
        }
        createEventCricket.setTotalPlayers(createEventCricket.getBatsman() + createEventCricket.getBowlers() + createEventCricket.getAllRounder() + createEventCricket.getWicketKeeper());

        //myReference.setValue(createEventCricket);

        myReference.child(String.valueOf(maxId+1)).setValue(createEventCricket);
        Toast.makeText(getContext(),"Data Saved",Toast.LENGTH_SHORT).show();

    }
}