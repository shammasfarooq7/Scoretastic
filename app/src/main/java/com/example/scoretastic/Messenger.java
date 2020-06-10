package com.example.scoretastic;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Messenger extends AppCompatActivity {
    String uid,hostId,hostName;
    TextView tvName;
    EditText etMessage;
    ImageView btBack,btSend;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private DatabaseReference chatReference;
    MessageAdapter messageAdapter;
    List<Chat> mChat;
    RecyclerView recyclerView;

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

        recyclerView = findViewById(R.id.messageRecyclerView);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(linearLayoutManager);


        tvName.setText(hostName);
        databaseReference = firebaseDatabase.getInstance().getReference();
        chatReference = firebaseDatabase.getInstance().getReference();

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
        readMessage(uid,hostId);


    }

    private void readMessage(final String myId, final String userId){
        mChat = new ArrayList<>();
        chatReference = FirebaseDatabase.getInstance().getReference("Chats");
        chatReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mChat.clear();
                for(DataSnapshot ds : dataSnapshot.getChildren()){

                    Chat chat = ds.getValue(Chat.class);
                    if(chat.getReceiver().equals(myId) && chat.getSender().equals(userId) ||
                        chat.getReceiver().equals(userId) && chat.getSender().equals(myId)){
                        mChat.add(chat);
                    }
                    messageAdapter = new MessageAdapter(getApplicationContext(),mChat);
                    recyclerView.setAdapter(messageAdapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}