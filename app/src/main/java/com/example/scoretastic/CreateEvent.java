package com.example.scoretastic;


import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

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

import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import android.widget.TimePicker;
import java.util.Calendar;

public class CreateEvent extends Fragment implements OnItemSelectedListener,DatePickerDialog.OnDateSetListener {


    TextView tvCrtTitle,tvT1,tvT2,tvT3,tvT4,tvcategory,tvRb,tvRb1,tvRb2,tvRb3,tvRbC,tvRbC1,tvRbC2,tvRbC3,tvDate,tvTime,tvloc;
    private TextView mDisplayDate,mDisplayTime;
    EditText et4,etB,etB1,etB2,etB3,etBC,etBC1,etBC2,etBC3;
    Spinner spinner;
    Button btce;
    ConstraintLayout cl1,cl2,cl3;
    Activity mActivity;
    int year;
    int month;
    int day;
    Date date;
    Time time;
    private static final String TAG = "Create Event";
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    private TimePickerDialog.OnTimeSetListener mTimeSetListener;
    CreateEventData object = new CreateEventData();


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
                object.setDescription(et4.getText().toString());
                date.setDate(day);
                date.setMonth(month);
                date.setYear(year);
                object.setDate(date);
                time.setHours(object.getTimeHour());
                time.setMinutes(object.getTimeMinute());
                object.setTime(time);
                btce.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dataSender();
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
                object.setResultLocation(data.getStringExtra("resultLocation"));
                object.setResultLat(data.getDoubleExtra("resultLat",0));
                object.setResultLng(data.getDoubleExtra("resultLng",0));
                tvloc.setText(object.getResultLocation());
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
        btce.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validationScene();
            }
        });
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
        etBC = view.findViewById(R.id.etB);
        etBC1 = view.findViewById(R.id.etBC1);
        etBC2 = view.findViewById(R.id.etBC2);
        etBC3 = view.findViewById(R.id.etBC3);
        tvDate = view.findViewById(R.id.tvDate);
        tvTime = view.findViewById(R.id.tvTime);
        et4 = view.findViewById(R.id.et4);
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

    }

    private void datePicker(View view) {

        mDisplayDate = view.findViewById(R.id.tvDate);

        Calendar cal = Calendar.getInstance();
         int year = cal.get(Calendar.YEAR);
         int month = cal.get(Calendar.MONTH);
         int day = cal.get(Calendar.DAY_OF_MONTH);

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
        mDisplayTime = view.findViewById(R.id.tvTime);

        mDisplayTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                object.setTimeHour(cal.get(Calendar.HOUR));
                object.setTimeMinute(cal.get(Calendar.MINUTE));

                TimePickerDialog dialog = new TimePickerDialog(getContext(), mTimeSetListener, object.getTimeHour(), object.getTimeMinute(), false);
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

            cl1.setVisibility(View.VISIBLE);
            cl2.setVisibility(View.GONE);
            cl3.setVisibility(View.GONE);
            object.setAttacker(Integer.valueOf(etB.getText().toString().trim()));
            object.setDefender(Integer.valueOf(etB1.getText().toString().trim()));
            object.setMidfielder(Integer.valueOf(etB2.getText().toString().trim()));
            object.setKeeper(Integer.valueOf(etB3.getText().toString().trim()));
            object.setTotalPlayers(object.getAttacker() + object.getDefender() + object.getMidfielder() + object.getKeeper());
            object.setSports("Football");

        } else if (item.equalsIgnoreCase("Cricket")) {

            cl1.setVisibility(View.GONE);
            cl2.setVisibility(View.VISIBLE);
            cl3.setVisibility(View.GONE);
            object.setBatsman(Integer.valueOf(etBC.getText().toString().trim()));
            object.setBowlers(Integer.valueOf(etBC1.getText().toString().trim()));
            object.setAllRounder(Integer.valueOf(etBC2.getText().toString().trim()));
            object.setWicketKeeper(Integer.valueOf(etBC3.getText().toString().trim()));
            object.setTotalPlayers(object.getBatsman() + object.getBowlers() + object.getAllRounder() + object.getWicketKeeper());
            object.setSports("Cricket");

        } else if (item.equalsIgnoreCase("Tennis")) {

            cl1.setVisibility(View.GONE);
            cl2.setVisibility(View.GONE);
            cl3.setVisibility(View.VISIBLE);
            object.setSports("Tennis");

        } else if (item.equalsIgnoreCase("Badminton")) {

            cl1.setVisibility(View.GONE);
            cl2.setVisibility(View.GONE);
            cl3.setVisibility(View.VISIBLE);
            object.setTotalPlayers(1);
            object.setSports("Badminton");

        } else if (item.equalsIgnoreCase("Squash")) {

            cl1.setVisibility(View.GONE);
            cl2.setVisibility(View.GONE);
            cl3.setVisibility(View.VISIBLE);
            object.setTotalPlayers(1);
            object.setSports("Squash");
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
        } else if (et4.getText().toString().isEmpty()) {            // to strict legth of char(.length() < 6)
            et4.setError("enter the description");
        } else if (etB.getText().toString().isEmpty()) {
            etB.setError("plz enter");
        } else if (etB1.getText().toString().isEmpty()) {
            etB1.setError("plz enter");
        } else if (etB2.getText().toString().isEmpty()) {
            etB2.setError("plz enter");
        } else if (etB3.getText().toString().isEmpty()) {
            etB3.setError("plz enter");
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
        if(object.getSports().equals("Football")){

        }
        else if(object.getSports().equals("Cricket")){

        }
        else if(object.getSports().equals("Tennis")){

        }
        else if(object.getSports().equals("Badminton")){

        }
        else if(object.getSports().equals("Squash")){

        }
    }

}

