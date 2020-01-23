package com.example.scoretastic;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import android.app.Activity;

import android.view.View;
import android.widget.AdapterView;


import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;



public class JoinEvent extends AppCompatActivity implements OnItemSelectedListener{

     TextView tveventdet,tvRb,tvRb1,tvRb2,tvRb3,tvcategory,tvT1,tvT2,tvT3,tvRbC,tvRbC1,tvRbC2,tvRbC3;
     EditText et1,et2,et3;//et4;
     Button btje;
     RadioButton rb1,rb2,rb3,rb4;

     RadioGroup RGroup;
     Spinner spinner;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join_event);


        intializeView();

    }

    public void intializeView(){


        tveventdet = findViewById(R.id.tveventdet);
        tvRb = findViewById(R.id.tvRb);
        tvRb1 = findViewById(R.id.tvRb1);
        tvRb2 = findViewById(R.id.tvRb2);
        tvRb3 =findViewById(R.id.tvRb3);


        /**

        tvRbC = findViewById(R.id.tvRbC);
        tvRbC1 = findViewById(R.id.tvRbC1);
        tvRbC2 = findViewById(R.id.tvRbC2);
        tvRbC3 =findViewById(R.id.tvRbC3);

         */



        tvcategory= findViewById(R.id.tvcategory);
        tvT1 = findViewById(R.id.tvT1);
        tvT2 = findViewById(R.id.tvT2);
        tvT3 = findViewById(R.id.tvT3);


        et1 = findViewById(R.id.et1);
        et2 = findViewById(R.id.et2);
        et3 = findViewById(R.id.et3);
        //et4 = findViewById(R.id.et4);


        btje=findViewById(R.id.btje);

        rb1 =findViewById(R.id.rb1);
        rb2 =findViewById(R.id.rb2);
        rb3 =findViewById(R.id.rb3);
        rb4 =findViewById(R.id.rb4);



        spinner = findViewById(R.id.spinner);

        RGroup =  findViewById(R.id.RGroup);
        // Spinner click listener
        spinner.setOnItemSelectedListener(this);



        // Spinner Drop down elements
        List<String> categories = new ArrayList<String>();
        categories.add("Football");
        categories.add("Cricket");
        categories.add("Badminton");
        categories.add("Squash");
        categories.add("tennis");
        categories.add("table tennis");


        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinner.setAdapter(dataAdapter);






    }

    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        // On selecting a spinner item
        String item = parent.getItemAtPosition(position).toString();

        // Showing selected spinner item
        Toast.makeText(parent.getContext(), "Selected: " + item, Toast.LENGTH_LONG).show();
    }
    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub
    }



}
