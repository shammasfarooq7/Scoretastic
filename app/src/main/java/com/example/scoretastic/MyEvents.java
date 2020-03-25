package com.example.scoretastic;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class MyEvents extends Fragment implements EventRecyclerAdapter.ItemClicked{


    RecyclerView recyclerView;
    EventRecyclerAdapter myAdapter;
    RecyclerView.LayoutManager layoutManager;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    Recycler object = new Recycler();
    ArrayList<Recycler> arrayList;
    ArrayList<DataSnapshot> userEventArray = new ArrayList();

    public MyEvents() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View v = inflater.inflate(R.layout.fragment_my_events, container, false);

        arrayList = new ArrayList<>();

        databaseReference = firebaseDatabase.getInstance().getReference("UserEvent");

        Button btHosted = v.findViewById(R.id.btHosted);
        Button btSubscribed = v.findViewById(R.id.btSubscribed);
        final RecyclerView recyclerViewSub = v.findViewById(R.id.rvSubscribed);
        final RecyclerView recyclerViewHost = v.findViewById(R.id.rvHosted);

        recyclerView = v.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        myAdapter = new EventRecyclerAdapter(this, arrayList);
        recyclerView.setAdapter(myAdapter);

        btHosted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                recyclerViewHost.setVisibility(View.VISIBLE);
                recyclerViewSub.setVisibility(View.GONE);
                layoutManager = new LinearLayoutManager(getContext());
                recyclerViewHost.setLayoutManager(layoutManager);
            }
        });

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                // userEvent sy data get krny wala code

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        btSubscribed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                recyclerViewHost.setVisibility(View.GONE);
                recyclerViewSub.setVisibility(View.VISIBLE);
                layoutManager = new LinearLayoutManager(getContext());
                recyclerViewSub.setLayoutManager(layoutManager);
            }
        });


        return v;
    }

    @Override
    public void onItemClicked(int index) {

    }
}
