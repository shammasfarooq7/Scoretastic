package com.example.scoretastic;


import android.content.Intent;
import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Toast;

import com.bumptech.glide.load.model.stream.MediaStoreVideoThumbLoader;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class CreateEvent extends Fragment  implements OnItemSelectedListener {



    TextView tvCrtTitle ,tvT1,tvT2,tvT3,tvT4,tvcategory,tvRb,tvRb1,tvRb2,tvRb3,tvRbC,tvRbC1,tvRbC2,tvRbC3;
    EditText et1,et2,et3,et4,etB,etB1,etB2,etB3,etBC,etBC1,etBC2,etBC3;
    Spinner spinner;
    Button btce,btmap;
    ConstraintLayout cl1,cl2,cl3;





    public CreateEvent() {
        // Required empty public constructor
    }





    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.fragment_create_event, container, false);
        // Inflate the layout for this fragment



        initializeViews(view);

        return view;

    }

    private void initializeViews(View view) {

        final View f = view.findViewById(R.id.cl2);






        btce = view.findViewById(R.id.btce);
        btmap = view.findViewById(R.id.btmap);

        tvCrtTitle = view.findViewById(R.id.tvCrtTitle);

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


        etB=view.findViewById(R.id.etB);
        etB1=view.findViewById(R.id.etB1);
        etB2=view.findViewById(R.id.etB2);
        etB3=view.findViewById(R.id.etB3);


        etBC=view.findViewById(R.id.etB);
        etBC1=view.findViewById(R.id.etBC1);
        etBC2=view.findViewById(R.id.etBC2);
        etBC3=view.findViewById(R.id.etBC3);




        et1 = view.findViewById(R.id.et1);
        et2 = view.findViewById(R.id.et2);
        et3 = view.findViewById(R.id.et3);
        et4 = view.findViewById(R.id.et4);




        cl1=view.findViewById(R.id.cl1);
        cl2=view.findViewById(R.id.cl2);
        cl3=view.findViewById(R.id.cl3);


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




        btmap = view.findViewById(R.id.btmap);
        btmap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openCreateEventMap();

            }
        });

    }

    private void openCreateEventMap(){

        Intent intent = new Intent(getActivity(),CreateEventMap.class);
        startActivity(intent);
    }







    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


        String item = parent.getItemAtPosition(position).toString();

        if(item.equalsIgnoreCase("Football") ){

           cl1.setVisibility(View.VISIBLE);
           cl2.setVisibility(View.GONE);
           cl3.setVisibility(View.GONE);






        }


        else if(item.equalsIgnoreCase("Cricket")){

            cl1.setVisibility(View.GONE);
            cl2.setVisibility(View.VISIBLE);
            cl3.setVisibility(View.GONE);

        }

        else if(item.equalsIgnoreCase("Tennis")){

            cl1.setVisibility(View.GONE);
            cl2.setVisibility(View.GONE);
            cl3.setVisibility(View.VISIBLE);

        }


        else if(item.equalsIgnoreCase("Badminton") ){

            cl1.setVisibility(View.GONE);
            cl2.setVisibility(View.GONE);
            cl3.setVisibility(View.VISIBLE);

        }

        else if(item.equalsIgnoreCase("Squash")){

            cl1.setVisibility(View.GONE);
            cl2.setVisibility(View.GONE);
            cl3.setVisibility(View.VISIBLE);

        }





    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {



    }
}
