package com.example.scoretastic;


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

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class Home extends Fragment implements OnMapReadyCallback, HomeRecyclerAdapter.ItemClicked{

    GoogleMap mMap;
    View j;
    RecyclerView recyclerView;
    HomeRecyclerAdapter myAdapter;
    RecyclerView.LayoutManager layoutManager;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    Recycler object = new Recycler();
    ArrayList<Recycler> arrayList = new ArrayList<>();


    public Home() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View v =  inflater.inflate(R.layout.fragment_home, container, false);
        databaseReference = firebaseDatabase.getInstance().getReference("CreateEvent");
        Button btMap = v.findViewById(R.id.btMap);
        Button btList = v.findViewById(R.id.btList);
        j = v.findViewById(R.id.map);
        recyclerView = v.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        myAdapter = new HomeRecyclerAdapter(this,arrayList);
        recyclerView.setAdapter(myAdapter);

        btMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                recyclerView.setVisibility(View.GONE);
                j.setVisibility(View.VISIBLE);
            }
        });

        btList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                recyclerView.setVisibility(View.VISIBLE);
                j.setVisibility(View.GONE);
                layoutManager = new LinearLayoutManager(getContext());
                recyclerView.setLayoutManager(layoutManager);
            }
        });
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                showData(dataSnapshot);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        return v;

    }

    private void showData(DataSnapshot dataSnapshot) {
        for(DataSnapshot ds : dataSnapshot.getChildren()){
            String day = ds.child("date").child("date").getValue().toString();
            String month = ds.child("date").child("month").getValue().toString();
            String year = ds.child("date").child("year").getValue().toString();
            String timeHour = ds.child("timeHour").getValue().toString();
            String timeMinute = ds.child("timeMinute").getValue().toString();
            object.setLocation(ds.child("resultLocation").getValue().toString());
            object.setSports(ds.child("sports").getValue().toString());
            object.setDate(day +"/"+ month+"/"+ year);
            object.setTime(timeHour +":"+ timeMinute);
            arrayList.add(object);

        }

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SupportMapFragment mapFragment = (SupportMapFragment)getChildFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap =googleMap;
        LatLng lahore = new LatLng(31.5204, 74.3587);
        mMap.addMarker(new MarkerOptions().position(lahore).title("Marker in Lahore"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(lahore));
    }

    @Override
    public void onItemClicked(int index) {

    }
}
