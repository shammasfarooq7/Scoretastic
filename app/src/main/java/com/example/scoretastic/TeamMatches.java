package com.example.scoretastic;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import javax.sql.DataSource;

public class TeamMatches extends Fragment {

    RecyclerView recyclerViewTeamMatches;
    TeamMatchesRecyclerAdapter adapter;
    RecyclerView.LayoutManager layoutManager;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference eventReference;
    private DatabaseReference userReference;
    Recycler recyclerObject;
    String uid;
    ArrayList<Recycler> list;
    ArrayList<DataSnapshot> eventList;
    ArrayList<DataSnapshot> userList;

    public TeamMatches() {
        // Required empty public constructor
    }

    @Override
    public void onStart() {
        super.onStart();

    }

    @Override
    public void onResume() {
        super.onResume();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            uid = user.getUid();
        }
        eventReference = firebaseDatabase.getInstance().getReference("TeamCreateEvent");
        userReference = firebaseDatabase.getInstance().getReference("TeamUserEvent");
        list = new ArrayList<>();
        userList = new ArrayList<>();
        eventList = new ArrayList<>();

        recyclerViewTeamMatches.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getActivity().getBaseContext());
        recyclerViewTeamMatches.setLayoutManager(layoutManager);
        recyclerViewTeamMatches.setVisibility(View.VISIBLE);

        eventReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                showEventData(dataSnapshot);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        userReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                showUserData(dataSnapshot);
                adapter = new TeamMatchesRecyclerAdapter(getContext(),list);
                recyclerViewTeamMatches.setAdapter(adapter);
                adapter.notifyDataSetChanged();
                adapter.setOnItemClickListner(new TeamMatchesRecyclerAdapter.onItemClickListner() {
                    @Override
                    public void onItemClick(int position) {
                        DataSnapshot ds = eventList.get(position);
                        if(ds!=null){
                            int key =Integer.parseInt(ds.child("id").getValue().toString().trim());
                            Intent intent = new Intent(getContext(),TeamHostMatchDetails.class);
                            intent.putExtra("Key",key);
                            startActivity(intent);
                        }
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_team_matches, container, false);
        recyclerViewTeamMatches = view.findViewById(R.id.recyclerTeamMatches);


        return view;
    }

    public void showUserData(DataSnapshot dataSnapshot){
        for (DataSnapshot ds : dataSnapshot.getChildren()){
            String userId = ds.child("uid").getValue().toString().trim();
            if(userId.equals(uid)){
                userList.add(ds);
            }
        }
        for(int i = 0; i<userList.size();i++){
            for(int j = 0; j<eventList.size();j++){
                if(userList.get(i).child("eventId").getValue().toString().trim().equals(eventList.get(j).child("id").getValue().toString().trim())){
                    String day = eventList.get(j).child("day").getValue().toString();
                    String month = eventList.get(j).child("month").getValue().toString();
                    String year = eventList.get(j).child("year").getValue().toString();
                    String timeHour = eventList.get(j).child("timeHour").getValue().toString();
                    String timeMinute = eventList.get(j).child("timeMinute").getValue().toString();
                    recyclerObject = new Recycler();
                    recyclerObject.setLocation(eventList.get(j).child("resultLocation").getValue().toString());
                    recyclerObject.setSports(eventList.get(j).child("sports").getValue().toString());
                    recyclerObject.setDate(day + "/" + month + "/" + year);
                    recyclerObject.setTime(timeHour + ":" + timeMinute);
                    list.add(recyclerObject);
                }
            }
        }
    }

    public void showEventData(DataSnapshot dataSnapshot){
        for(DataSnapshot ds : dataSnapshot.getChildren()){
            eventList.add(ds);
        }
    }

}
