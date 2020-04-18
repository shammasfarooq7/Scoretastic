package com.example.scoretastic;


import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class Home extends Fragment implements OnMapReadyCallback, HomeRecyclerAdapter.ItemClicked, HomeRecyclerAdapter.onItemClickListner{

    GoogleMap mMap;
    View j,k;
    CardView cardView;
    TextView tvMarkerLocation,tvMarkerSports,tvMarkerTime,tvMarkerDate;
    Button btMarkerJoin;
    RecyclerView recyclerView;
    HomeRecyclerAdapter myAdapter;
    RecyclerView.LayoutManager layoutManager;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    Recycler object = new Recycler();
    ArrayList<Recycler> arrayList;
    String ACCESS_FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;
    String ACCESS_COARSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION;
    Boolean mLocationPermissionGranted = false;
    private final int LocationPermissionRequestCode = 1234;
    private FusedLocationProviderClient mFusedLocationProviderClient;
    private final float defaultZoom = 15f;
    Double lat, lng;
    ArrayList<LatLng> arrayListLoc = new ArrayList<LatLng>();
    ArrayList<DataSnapshot> eventArray = new ArrayList();
    int key = 0;

    public Home() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        arrayList = new ArrayList<>();
        final View v = inflater.inflate(R.layout.fragment_home, container, false);
        databaseReference = firebaseDatabase.getInstance().getReference("CreateEvent");
        Button btMap = v.findViewById(R.id.btMap);
        Button btList = v.findViewById(R.id.btList);
        j = v.findViewById(R.id.map);
        k = v.findViewById(R.id.mapMarker);
        tvMarkerDate = k.findViewById(R.id.tvMarkerDate);
        tvMarkerLocation = k.findViewById(R.id.tvMarkerLocation);
        tvMarkerSports = k.findViewById(R.id.tvMarkerSports);
        tvMarkerTime = k.findViewById(R.id.tvMarkerTime);
        btMarkerJoin = k.findViewById(R.id.btMarkerJoin);
        getLocationPermission();
        recyclerView = v.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        myAdapter = new HomeRecyclerAdapter(this, arrayList);
        recyclerView.setAdapter(myAdapter);
        myAdapter.setOnItemClickListner(new HomeRecyclerAdapter.onItemClickListner() {
            @Override
            public void onItemClick(int position) {
                DataSnapshot ds = eventArray.get(position);
                if(ds != null){
                    String key1 = ds.child("id").getValue().toString();
                    key = Integer.parseInt(key1);
                    Intent intent = new Intent(getContext(), JoinEvent.class);
                    intent.putExtra("Event key", key);
                    startActivity(intent);

                }
            }
        });
        k.setVisibility(View.GONE);

        btMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                recyclerView.setVisibility(View.GONE);
                k.setVisibility(View.GONE);
                j.setVisibility(View.VISIBLE);
            }
        });

        btList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                recyclerView.setVisibility(View.VISIBLE);
                j.setVisibility(View.INVISIBLE);
                k.setVisibility(View.GONE);
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

        btMarkerJoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), JoinEvent.class);
                intent.putExtra("Event key", key);
                startActivity(intent);
            }
        });

        return v;

    }

    private void showData(DataSnapshot dataSnapshot) {
        for (DataSnapshot ds : dataSnapshot.getChildren()) {
            String day = ds.child("date").child("date").getValue().toString();
            String month = ds.child("date").child("month").getValue().toString();
            String year = ds.child("date").child("year").getValue().toString();
            String timeHour = ds.child("timeHour").getValue().toString();
            String timeMinute = ds.child("timeMinute").getValue().toString();
            object = new Recycler();
            object.setLocation(ds.child("resultLocation").getValue().toString());
            object.setSports(ds.child("sports").getValue().toString());
            object.setDate(day + "/" + month + "/" + year);
            object.setTime(timeHour + ":" + timeMinute);
            arrayList.add(object);
            lat = (Double) ds.child("resultLat").getValue();
            lng = (Double) ds.child("resultLng").getValue();
            arrayListLoc.add(new LatLng(lat,lng));
            eventArray.add(ds);

        }

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        Toast.makeText(getContext(),"Map is ready",Toast.LENGTH_SHORT).show();
        mMap = googleMap;
        if(mLocationPermissionGranted){
            getDeviceLocation();
            mMap.setMyLocationEnabled(true);
            setMarkers();
            mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                @Override
                public boolean onMarkerClick(Marker marker) {

                    DataSnapshot ds =   eventArray.get(Integer.parseInt(marker.getTitle().trim()));
                    if(ds != null){
                        tvMarkerSports.setText(ds.child("sports").getValue().toString());
                        tvMarkerDate.setText(ds.child("date").child("date").getValue().toString() + "/" + ds.child("date").child("month").getValue().toString() + "/" + ds.child("date").child("year").getValue().toString() );
                        tvMarkerLocation.setText(ds.child("resultLocation").getValue().toString());
                        tvMarkerTime.setText(ds.child("timeHour").getValue().toString() +":" + ds.child("timeMinute").getValue().toString());
                        String key1 = ds.child("id").getValue().toString();
                        key = Integer.parseInt(key1);
                    }
                    k.setVisibility(View.VISIBLE);
                    return false;
                }
            });

        }
    }
    private void getDeviceLocation(){
        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(getContext());
        try{
            if(mLocationPermissionGranted){
                Task location = mFusedLocationProviderClient.getLastLocation();
                location.addOnCompleteListener(new OnCompleteListener() {
                    @Override
                    public void onComplete(@NonNull Task task) {
                        if(task.isSuccessful()){
                            Location currentLocation =(Location) task.getResult();
                            Toast.makeText(getContext(),"Location found", Toast.LENGTH_SHORT).show();
                            moveCamera(new LatLng(currentLocation.getLatitude(),currentLocation.getLongitude()),defaultZoom);
                        }
                        else{
                            Toast.makeText(getContext(),"Location not found", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        }
        catch(SecurityException e){

        }

    }
    private void moveCamera(LatLng latLng, Float zoom){
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoom));

    }

    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        mLocationPermissionGranted = false;

        switch (requestCode) {
            case LocationPermissionRequestCode: {
                if (grantResults.length > 0) {
                    for (int i = 0; i < grantResults.length; i++) {
                        if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                            mLocationPermissionGranted = false;
                            return;
                        }
                    }
                    mLocationPermissionGranted = true;
                }
            }
        }
    }
    private void getLocationPermission(){
        String[] permissions = {Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION};

        if(ContextCompat.checkSelfPermission(
                getContext(),ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
            if(ContextCompat.checkSelfPermission(
                    getContext(),ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED){
                mLocationPermissionGranted = true;
            }
            else {
                ActivityCompat.requestPermissions(getActivity(),permissions,LocationPermissionRequestCode);
            }
        }
        else {
            ActivityCompat.requestPermissions(getActivity(),permissions,LocationPermissionRequestCode);
        }

    }

    public void onItemClicked(int index) {

    }

    public void setMarkers(){
        for(int i = 0; i<arrayListLoc.size();i++){
            mMap.addMarker(new MarkerOptions().position(arrayListLoc.get(i)).title(""+i).draggable(false));
        }
    }

    @Override
    public void onItemClick(int position) {

    }
}