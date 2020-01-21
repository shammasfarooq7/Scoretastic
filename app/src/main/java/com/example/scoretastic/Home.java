package com.example.scoretastic;


import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


/**
 * A simple {@link Fragment} subclass.
 */
public class Home extends Fragment {


    Button btToje;

    public Home() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view =inflater.inflate(R.layout.fragment_home, container, false);


        initializeViews(view);
        setListner();

        // Inflate the layout for this fragment
        return view;




    }

    private void setListner() {

        btToje.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openJoinEvent();
            }
        });

    }

    private void openJoinEvent() {


        Intent intent = new Intent(getActivity(),JoinEvent.class);
        startActivity(intent);


    }


    public void initializeViews(View view)
        {
            btToje=  view.findViewById(R.id.btToje);








        }

}
