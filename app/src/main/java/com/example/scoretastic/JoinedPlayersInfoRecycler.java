package com.example.scoretastic;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class JoinedPlayersInfoRecycler extends AppCompatActivity {

    int id;
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    PlayerProfileAdapter adapter;
    ArrayList<ProfileRecycler> list;
    ProfileRecycler object;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference joinEvent;
    private DatabaseReference userInfo;
    ArrayList<DataSnapshot> userList;
    ArrayList<DataSnapshot> joinList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_joined_players_info_recycler);
        id = getIntent().getIntExtra("Event key",-1);
        joinList = new ArrayList<>();
        userList = new ArrayList<>();
        list = new ArrayList<>();

        recyclerView = findViewById(R.id.rvJoinedPlayers);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setVisibility(View.VISIBLE);

        joinEvent = firebaseDatabase.getInstance().getReference("JoinEvent");
        joinEvent.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                joinShowData(dataSnapshot);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        userInfo = firebaseDatabase.getInstance().getReference("UserData");
        userInfo.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                showData(dataSnapshot);
                adapter = new PlayerProfileAdapter(getApplicationContext(),list);
                recyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void joinShowData(DataSnapshot dataSnapshot){
        for(DataSnapshot ds : dataSnapshot.getChildren()){
            int eventKey = Integer.parseInt(ds.child("eventKey").getValue().toString().trim());
            if(eventKey == id){
                joinList.add(ds);
            }
        }
    }

    public void showData(DataSnapshot dataSnapshot){
        for(DataSnapshot ds : dataSnapshot.getChildren()){
            userList.add(ds);
        }

        for(int i = 0; i<joinList.size(); i++){
            for (int j = 0; j<userList.size(); j++){
                if(joinList.get(i).child("userId").getValue().toString().trim().equals(userList.get(j).child("userId").getValue().toString().trim())){
                    object = new ProfileRecycler();
                    object.setName(userList.get(j).child("name").getValue().toString().trim());
                    object.setPos(userList.get(j).child("pos").getValue().toString().trim());
                    object.setFavSports(userList.get(j).child("fSports").getValue().toString().trim());
                    int host = Integer.parseInt(userList.get(j).child("hosted").getValue().toString().trim());
                    int sub = Integer.parseInt(userList.get(j).child("subscribed").getValue().toString().trim());
                    int total = host+sub;
                    object.setTotalMatch(String.valueOf(total));
                    list.add(object);
                }
            }
        }
    }
}
