package com.example.scoretastic;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class Messenger extends AppCompatActivity {
    String uid,hostId,hostName;
    TextView tvName;
    EditText etMessage;
    ImageView btBack,btSend;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messenger);
        uid = getIntent().getStringExtra("User Id");
        hostId = getIntent().getStringExtra("Host Id");
        hostName = getIntent().getStringExtra("Host Name");
        tvName = findViewById(R.id.tvName);
        btBack = findViewById(R.id.btBack);
        btSend = findViewById(R.id.btSend);
        etMessage = findViewById(R.id.etMessage);
        tvName.setText(hostName);
        databaseReference = firebaseDatabase.getInstance().getReference();

        btBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        btSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String message = etMessage.getText().toString().trim();
                if(!message.equals("")){
                    HashMap<String, Object> hashMap = new HashMap<>();
                    hashMap.put("sender",uid);
                    hashMap.put("receiver",hostId);
                    hashMap.put("message",message);

                    databaseReference.child("Chats").push().setValue(hashMap);
                    etMessage.getText().clear();
                }
            }
        });


    }
}