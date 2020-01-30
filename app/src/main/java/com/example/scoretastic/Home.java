package com.example.scoretastic;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
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

/**
 * A simple {@link Fragment} subclass.
 */
public class Home extends Fragment implements OnMapReadyCallback, homeRecyclerAdapter.ItemClicked{

    GoogleMap mMap;
    View j;
    RecyclerView recyclerView;
    RecyclerView.Adapter myAdapter;
    RecyclerView.LayoutManager layoutManager;


    public Home() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View v =  inflater.inflate(R.layout.fragment_home, container, false);
        Button btMap = v.findViewById(R.id.btMap);
        Button btList = v.findViewById(R.id.btList);
        j = v.findViewById(R.id.map);
        final RecyclerView recyclerView = v.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);


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
            }
        });

        //ArrayList<recycler> events = new ArrayList<>();
        //events.add(new recycler("FT","JT","",""));
        //myAdapter = new homeRecyclerAdapter(this,events);
        //recyclerView.setAdapter(myAdapter);

        return v;

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
