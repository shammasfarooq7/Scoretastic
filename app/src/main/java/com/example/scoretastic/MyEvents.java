package com.example.scoretastic;


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
    ArrayList<DataSnapshot> subArray = new ArrayList();
    ArrayList<DataSnapshot> hostArray = new ArrayList();
    ArrayList<DataSnapshot> createArray = new ArrayList();
    String uid;

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
        btHosted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                recyclerViewSub.setVisibility(View.GONE);
                recyclerViewHost.setVisibility(View.VISIBLE);

            }
        });

        btSubscribed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                recyclerViewHost.setVisibility(View.GONE);
                recyclerViewSub.setVisibility(View.VISIBLE);

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
        layoutManagerSub = new LinearLayoutManager(getContext());
        recyclerViewSub.setLayoutManager(layoutManagerSub);
        subAdapter = new SubscribedRecyclerAdapter(this, arrayListSub);
        subAdapter.setOnItemClickListner(new SubscribedRecyclerAdapter.onItemClickListner() {
            @Override
            public void onItemClick(int position) {

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
        layoutManagerHost = new LinearLayoutManager(getContext());
        recyclerViewHost.setLayoutManager(layoutManagerHost);
        hostAdapter = new HostedRecyclerAdapter(this, arrayListHost);
        hostAdapter.setOnItemClickListner(new HostedRecyclerAdapter.onItemClickListner() {
            @Override
            public void onItemClick(int position) {

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
                    String day = createArray.get(j).child("date").child("date").getValue().toString();
                    String month = createArray.get(j).child("date").child("month").getValue().toString();
                    String year = createArray.get(j).child("date").child("year").getValue().toString();
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
                    String day = createArray.get(j).child("date").child("date").getValue().toString();
                    String month = createArray.get(j).child("date").child("month").getValue().toString();
                    String year = createArray.get(j).child("date").child("year").getValue().toString();
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