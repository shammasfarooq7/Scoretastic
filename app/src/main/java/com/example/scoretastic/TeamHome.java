package com.example.scoretastic;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class TeamHome extends Fragment {
    ImageView btCreate, btBack;
    RecyclerView recyclerViewTeamHome;
    TeamHomeRecyclerAdapter adapter;
    RecyclerView.LayoutManager layoutManager;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference myReference;
    Recycler recyclerObject;
    String uid;
    ArrayList<Recycler> list;
   public TeamHome() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            uid = user.getUid();
        }
        View view = inflater.inflate(R.layout.fragment_team_home, container, false);
        // Inflate the layout for this fragment
        btBack = view.findViewById(R.id.btBackTeam);
        btCreate = view.findViewById(R.id.btCreate);
        recyclerViewTeamHome = (RecyclerView)view.findViewById(R.id.recyclerTeamHome);
        myReference = firebaseDatabase.getInstance().getReference("TeamCreateEvent");
        list = new ArrayList<>();
        recyclerViewTeamHome.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getActivity().getBaseContext());
        recyclerViewTeamHome.setLayoutManager(layoutManager);
        recyclerViewTeamHome.setVisibility(View.VISIBLE);

        btCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(),TeamCreateEvent.class);
                startActivity(intent);
            }
        });

        btBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(),Main.class);
                startActivity(intent);
                getActivity().finish();
            }
        });


        myReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                showData(dataSnapshot);
                adapter = new TeamHomeRecyclerAdapter(getContext(),list);
                recyclerViewTeamHome.setAdapter(adapter);
                adapter.notifyDataSetChanged();
                adapter.setOnItemClickListner(new TeamHomeRecyclerAdapter.onItemClickListner() {
                    @Override
                    public void onItemClick(int position) {

                    }
                });

            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        return view;
    }

    public void showData(DataSnapshot dataSnapshot){
       for(DataSnapshot ds : dataSnapshot.getChildren()){
           String userId = ds.child("uid").getValue().toString().trim();
           String status = ds.child("status").getValue().toString().trim();
           if(userId.equals(uid) || Integer.parseInt(status) == 0){

           }
           else{
               String day = ds.child("day").getValue().toString();
               String month = ds.child("month").getValue().toString();
               String year = ds.child("year").getValue().toString();
               String timeHour = ds.child("timeHour").getValue().toString();
               String timeMinute = ds.child("timeMinute").getValue().toString();
               recyclerObject = new Recycler();
               recyclerObject.setLocation(ds.child("resultLocation").getValue().toString());
               recyclerObject.setSports(ds.child("sports").getValue().toString());
               recyclerObject.setDate(day + "/" + month + "/" + year);
               recyclerObject.setTime(timeHour + ":" + timeMinute);
               list.add(recyclerObject);
           }
       }
    }

}
