package com.example.scoretastic;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;




public class Settings extends AppCompatActivity {

    private TextView tvradius;
    private SeekBar seekBar;
    private ImageButton ibBack;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        tvradius = findViewById(R.id.tvradius);
        seekBar = findViewById(R.id.seekBar);


        ibBack=findViewById(R.id.ibBack);

        ibBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                onBackPressed();
            }
        });





    }


}
