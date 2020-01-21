package com.example.scoretastic;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

public class Faqs extends AppCompatActivity {

    private ImageButton ibBack;
    private TextView tvfaqs;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faqs);


        tvfaqs=(TextView)findViewById(R.id.tvfaqs);



        ibBack=findViewById(R.id.ibBack);

        ibBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                onBackPressed();
            }
        });


    }


}
