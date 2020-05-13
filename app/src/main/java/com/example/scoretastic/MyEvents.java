package com.example.scoretastic;


import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class MyEvents extends Fragment implements HostedRecyclerAdapter.ItemClicked, HostedRecyclerAdapter.onItemClickListner, SubscribedRecyclerAdapter.ItemClicked, SubscribedRecyclerAdapter.onItemClickListner{

    CardView cardView;
    RecyclerView recyclerViewSub;
    RecyclerView recyclerViewHost;
    Button btHosted;
    Button btSubscribed;
    HostedRecyclerAdapter hostAdapter;
    SubscribedRecyclerAdapter subAdapter;
    RecyclerView.LayoutManager layoutManagerHost;
    RecyclerView.LayoutManager layoutManagerSub;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference subDatabase;
    private DatabaseReference hostDatabase;
    private  DatabaseReference createDatabase;
    Recycler subObject = new Recycler();
    Recycler hostObject = new Recycler();
    ArrayList<Recycler> arrayListHost;
    ArrayList<Recycler> arrayListSub;
    ArrayList<DataSnapshot> subArray;
    ArrayList<DataSnapshot> hostArray;
    ArrayList<DataSnapshot> createArray;
    String uid;
    int keySub = 0;
    int keyHost = 0;
    String pos;

    public MyEvents() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            uid = user.getUid();
        }
        final View v = inflater.inflate(R.layout.fragment_my_events, container, false);
        btHosted = v.findViewById(R.id.btHosted);
        btSubscribed = v.findViewById(R.id.btSubscribed);
        subDatabase = firebaseDatabase.getInstance().getReference("JoinEvent");
        hostDatabase = firebaseDatabase.getInstance().getReference("UserEvent");
        createDatabase = firebaseDatabase.getInstance().getReference("CreateEvent");
        arrayListSub = new ArrayList<>();
        arrayListHost = new ArrayList<>();
        createArray = new ArrayList<>();
        subArray = new ArrayList();
        hostArray = new ArrayList();

        btHosted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                recyclerViewSub.setVisibility(View.GONE);
                recyclerViewHost.setVisibility(View.VISIBLE);
                layoutManagerHost = new LinearLayoutManager(getContext());
                recyclerViewHost.setLayoutManager(layoutManagerHost);

            }
        });

        btSubscribed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                recyclerViewHost.setVisibility(View.GONE);
                recyclerViewSub.setVisibility(View.VISIBLE);
                layoutManagerSub = new LinearLayoutManager(getContext());
                recyclerViewSub.setLayoutManager(layoutManagerSub);

            }
        });
        createDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                showData(dataSnapshot);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



        recyclerViewSub = v.findViewById(R.id.rvSubscribed);
        recyclerViewSub.setHasFixedSize(true);

        subAdapter = new SubscribedRecyclerAdapter(this, arrayListSub);
        recyclerViewSub.setAdapter(subAdapter);
        subAdapter.setOnItemClickListner(new SubscribedRecyclerAdapter.onItemClickListner() {
            @Override
            public void onItemClick(int position) {
                DataSnapshot ds = subArray.get(position);
                if(ds!=null){
                    String key1 = ds.child("eventKey").getValue().toString();
                    if(ds.hasChild("position")){
                        pos = ds.child("position").getValue().toString();
                    }
                    else{
                        pos = "Player";
                    }
                    keySub = Integer.parseInt(key1);
                    Intent intent = new Intent(getContext(), SubDetails.class);
                    intent.putExtra("Event key Sub", keySub);
                    intent.putExtra("Position", pos);
                    startActivity(intent);
                }
            }
        });
        subDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                subShowData(dataSnapshot);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        recyclerViewHost = v.findViewById(R.id.rvHosted);
        recyclerViewHost.setHasFixedSize(true);

        hostAdapter = new HostedRecyclerAdapter(this, arrayListHost);
        recyclerViewHost.setAdapter(hostAdapter);
        hostAdapter.setOnItemClickListner(new HostedRecyclerAdapter.onItemClickListner() {
            @Override
            public void onItemClick(int position) {
                DataSnapshot ds = hostArray.get(position);
                if(ds!=null){
                    String key1 = ds.child("eventId").getValue().toString();
                    keyHost = Integer.parseInt(key1);
                    Intent intent = new Intent(getContext(), HostDetails.class);
                    intent.putExtra("Event key Host", keyHost);
                    startActivity(intent);
                }
            }
        });

        hostDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                hostShowData(dataSnapshot);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        return v;
    }

    private void subShowData(DataSnapshot dataSnapshot){
        for(DataSnapshot ds : dataSnapshot.getChildren()){
            String currentUser = ds.child("userId").getValue().toString().trim();
            if(currentUser.trim().equals(uid.trim())){
                subArray.add(ds);
            }
        }
        for(int i = 0; i<subArray.size(); i++){
            for(int j = 0; j<createArray.size(); j++){
                if(subArray.get(i).child("eventKey").getValue().toString().trim().equals(createArray.get(j).child("id").getValue().toString().trim()) ){
                    String status = createArray.get(j).child("status").getValue().toString();
                    if(Integer.parseInt(status) == 1){
                        String day = createArray.get(j).child("day").getValue().toString();
                        String month = createArray.get(j).child("month").getValue().toString();
                        String year = createArray.get(j).child("year").getValue().toString();
                        String timeHour = createArray.get(j).child("timeHour").getValue().toString();
                        String timeMinute = createArray.get(j).child("timeMinute").getValue().toString();
                        subObject = new Recycler();
                        subObject.setLocation(createArray.get(j).child("resultLocation").getValue().toString());
                        subObject.setSports(createArray.get(j).child("sports").getValue().toString());
                        subObject.setDate(day + "/" + month + "/" + year);
                        subObject.setTime(timeHour + ":" + timeMinute);
                        arrayListSub.add(subObject);
                    }
                }
            }
        }
    }

    private void hostShowData(DataSnapshot dataSnapshot){
        for(DataSnapshot ds : dataSnapshot.getChildren()){
            String currentUser = ds.child("uid").getValue().toString().trim();
            if(currentUser.trim().equals(uid.trim())){
                hostArray.add(ds);
            }
        }
        for(int i = 0; i<hostArray.size(); i++){
            for(int j = 0; j<createArray.size(); j++){
                if(hostArray.get(i).child("eventId").getValue().toString().trim().equals(createArray.get(j).child("id").getValue().toString().trim()) ){
                    String day = createArray.get(j).child("day").getValue().toString();
                    String month = createArray.get(j).child("month").getValue().toString();
                    String year = createArray.get(j).child("year").getValue().toString();
                    String timeHour = createArray.get(j).child("timeHour").getValue().toString();
                    String timeMinute = createArray.get(j).child("timeMinute").getValue().toString();
                    hostObject = new Recycler();
                    hostObject.setLocation(createArray.get(j).child("resultLocation").getValue().toString());
                    hostObject.setSports(createArray.get(j).child("sports").getValue().toString());
                    hostObject.setDate(day + "/" + month + "/" + year);
                    hostObject.setTime(timeHour + ":" + timeMinute);
                    arrayListHost.add(hostObject);
                }
            }
        }
    }

    private void showData(DataSnapshot dataSnapshot){
        for(DataSnapshot ds : dataSnapshot.getChildren()){
            createArray.add(ds);
        }
    }

    @Override
    public void onItemClick(int position) {

    }

    @Override
    public void onItemClicked(int index) {

    }
}