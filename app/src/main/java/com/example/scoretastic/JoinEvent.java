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



public class JoinEvent extends AppCompatActivity {  //implements OnItemSelectedListener

     TextView tveventdet,tvT1,tvT2,tvT3,tvT4,tvRb,tvRb1,tvRb2,tvRb3,tvcategory,tvC,tvC1,tvC2,tvC3;
     EditText et1,et2,et3,et4;
     Button btje;
     RadioButton rb1,rb2,rb3,rb4,rbC1,rbC2,rbC3,rbC4;

     RadioGroup RGroup,RGroupC;
    // Spinner spinner;


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




        tvC = findViewById(R.id.tvC);
        tvC1 = findViewById(R.id.tvC1);
        tvC2 = findViewById(R.id.tvC2);
        tvC3 =findViewById(R.id.tvC3);





        tvcategory= findViewById(R.id.tvcategory);

        tvT1 = findViewById(R.id.tvT1);
        tvT2 = findViewById(R.id.tvT2);
        tvT3 = findViewById(R.id.tvT3);
        tvT4 = findViewById(R.id.tvT4);



        et1 = findViewById(R.id.et1);
        et2 = findViewById(R.id.et2);
        et3 = findViewById(R.id.et3);
        et4 = findViewById(R.id.et4);


        btje=findViewById(R.id.btje);

        rb1 =findViewById(R.id.rb1);
        rb2 =findViewById(R.id.rb2);
        rb3 =findViewById(R.id.rb3);
        rb4 =findViewById(R.id.rb4);

        rbC1 =findViewById(R.id.rbC1);
        rbC2 =findViewById(R.id.rbC2);
        rbC3 =findViewById(R.id.rbC3);
        rbC4 =findViewById(R.id.rbC4);




        // spinner = findViewById(R.id.spinner);

        RGroup =  findViewById(R.id.RGroup);
        RGroupC =  findViewById(R.id.RGroupC);
        // Spinner click listener
        //spinner.setOnItemSelectedListener(this);



//         Spinner Drop down elements
//        List<String> categories = new ArrayList<String>();
//        categories.add("Football");
//        categories.add("Cricket");
//        categories.add("Badminton");
//        categories.add("Squash");
//        categories.add("tennis");
//        categories.add("table tennis");
//
//
//        // Creating adapter for spinner
//        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories);
//
//        // Drop down layout style - list view with radio button
//        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//
//        // attaching data adapter to spinner
//        spinner.setAdapter(dataAdapter);
//
//

    }

//    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//        // On selecting a spinner item
//        String item = parent.getItemAtPosition(position).toString();
//
//        // Showing selected spinner item
//        Toast.makeText(parent.getContext(), "Selected: " + item, Toast.LENGTH_LONG).show();
//    }
//    public void onNothingSelected(AdapterView<?> arg0) {
//        // TODO Auto-generated method stub
//    }



}
