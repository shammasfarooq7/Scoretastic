package com.example.scoretastic;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class HostDetails extends AppCompatActivity {
    TextView tvHost, tvSports, tvLocation, tvDate, tvTime, tvDescription, tvTotalPlayersJoined;
    Button btDelete;
    int key;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    String uid;
    ArrayList<DataSnapshot> eventArray = new ArrayList();
    ArrayList<DataSnapshot> hostArray = new ArrayList();
    private DatabaseReference hostReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_host_details);
        tvDate = findViewById(R.id.tvDate);
        tvHost = findViewById(R.id.tvHost);
        tvSports = findViewById(R.id.tvSports);
        tvLocation = findViewById(R.id.tvLocation);
        tvTime = findViewById(R.id.tvTime);
        tvDescription = findViewById(R.id.tvDescription);
        tvTotalPlayersJoined = findViewById(R.id.tvPlayersJoined);
        btDelete = findViewById(R.id.btDelete);
        key = getIntent().getIntExtra("Event key Host",-1);
        databaseReference = firebaseDatabase.getInstance().getReference("CreateEvent");
        hostReference = firebaseDatabase.getInstance().getReference("UserEvent");
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            uid = user.getUid();
        }
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                showData(dataSnapshot);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        hostReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                hostShowData(dataSnapshot);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        btDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hostReference.child(hostArray.get(0).getKey()).child("uid").setValue(0);
                String keyEvent = hostArray.get(0).child("eventId").getValue().toString().trim();
                databaseReference.child(keyEvent).child("totalPlayers").setValue(0);
                Toast.makeText(getApplicationContext(),"Event Deleted",Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }

    public void showData(DataSnapshot dataSnapshot){
        for(DataSnapshot ds : dataSnapshot.getChildren()){
            eventArray.add(ds);
        }
        DataSnapshot eventKey = eventArray.get(key-1);
        if(eventKey!=null){
            tvHost.setText(eventKey.getKey());
            tvDate.setText(eventKey.child("date").child("date").getValue().toString() + "/" + eventKey.child("date").child("month").getValue().toString() + "/" + eventKey.child("date").child("year").getValue().toString());
            tvLocation.setText(eventKey.child("resultLocation").getValue().toString());
            tvTime.setText(eventKey.child("timeHour").getValue().toString() +":" + eventKey.child("timeMinute").getValue().toString());
            tvDescription.setText(eventKey.child("description").getValue().toString());
            //           tvTotalPlayersJoined.setText(eventKey.child("totalPlayers").getValue().toString());
            tvSports.setText(eventKey.child("sports").getValue().toString());
        }
    }

    public void hostShowData(DataSnapshot dataSnapshot){
        for(DataSnapshot ds : dataSnapshot.getChildren()){
            String currentUser = ds.child("uid").getValue().toString().trim();
            int key1 = Integer.parseInt(ds.child("eventId").getValue().toString().trim());
            if(currentUser.trim().equals(uid.trim()) && (key1 == key)) {
                hostArray.add(ds);
            }
        }
    }
}
