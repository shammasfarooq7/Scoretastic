package com.example.scoretastic;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

public class AboutUs extends AppCompatActivity {

    private TextView tvaboutus,tvparagraph,tvparagraph1;
    private ImageButton ibBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);

        tvaboutus = findViewById(R.id.tvaboutus);
        tvparagraph= findViewById(R.id.tvparagraph);

        tvparagraph1= findViewById(R.id.tvparagraph1);

        ibBack=findViewById(R.id.ibBack);

        ibBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                onBackPressed();
            }
        });


    }
}
