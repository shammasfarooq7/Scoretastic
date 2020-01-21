package com.example.scoretastic;


import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class Profile extends Fragment implements View.OnClickListener {


    Button btSettings ,btchgPassword,btFaqs,btAboutUs;

    ImageButton ivprofpic, ivchgpic;
     ImageView       ivDemoPic;
    //TextView tvSettings, tvChgPass;
    LinearLayout llbt;


    public Profile() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        initializeView(view);

        // Inflate the layout for this fragment


        return view;





    }

    private void initializeView(View view) {


        btSettings = view.findViewById(R.id.btSettings);
        btchgPassword = view.findViewById(R.id.btchgPassword);
        btFaqs = view.findViewById(R.id.btFaqs);
        btAboutUs = view.findViewById(R.id.btAboutUs);


        btSettings = (Button) view.findViewById(R.id.btSettings);
        btSettings.setOnClickListener(this);



        btchgPassword = (Button) view.findViewById(R.id.btchgPassword);
        btSettings.setOnClickListener(this);


        btSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openSettings();

            }
        });

        btchgPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openchangePassword();

            }
        });


        btFaqs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFaqs();

            }
        });


        btAboutUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openAboutUs();

            }
        });







        //LLset = view.findViewById(R.id.LLset);
        //LLchgPass = view.findViewById(R.id.LLchgPass);

        ivprofpic = view.findViewById(R.id.ivprofpic);
        ivchgpic = view.findViewById(R.id.ivchgpic);

        ivDemoPic = view.findViewById(R.id.ivDemoPic);
        //tvChgPass = view.findViewById(R.id.tvChgPass);
        //tvSettings = view.findViewById(R.id.tvSettings);
    }



     private void openSettings(){

        Intent intent = new Intent(getActivity(),Settings.class);
        startActivity(intent);
    }

    private void openchangePassword(){

        Intent intent = new Intent(getActivity(),ChangePassword.class);
        startActivity(intent);
    }

    private void openFaqs(){

        Intent intent = new Intent(getActivity(),Faqs.class);
        startActivity(intent);
    }

    private void openAboutUs(){

        Intent intent = new Intent(getActivity(),AboutUs.class);
        startActivity(intent);
    }



    @Override
    public void onClick(View v) {

    }
}