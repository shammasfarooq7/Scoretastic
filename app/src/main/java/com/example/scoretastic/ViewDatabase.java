package com.example.scoretastic;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class ViewDatabase extends AppCompatActivity {


    private static final String TAG = "ViewDatabase";

    //add Firebase Database stuff
    private FirebaseDatabase mFirebaseDatabase;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private DatabaseReference myRef;
    private String userID;

    private ListView mListView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_database);


        mListView = findViewById(R.id.listview);


        mAuth = FirebaseAuth.getInstance();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        myRef = mFirebaseDatabase.getReference();
        FirebaseUser user = mAuth.getCurrentUser();
        userID = user.getUid();


        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                    toastMessage("Successfully signed in with: " + user.getEmail());
                } else {
                    // User is signed out
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                    toastMessage("Successfully signed out.");
                }
                // ...
            }
        };


        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {


            }
        });

        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {


                String value = dataSnapshot.getValue(String.class);
                toastMessage("the stored data in firebase is: " + value);
                showData(dataSnapshot);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // ...
            }
        });




    }






    private void showData(DataSnapshot dataSnapshot) {
        for (DataSnapshot ds : dataSnapshot.getChildren()) {
            CreateEventData ced = new CreateEventData();

            ced.setResultLocation(ds.child(userID).getValue(CreateEventData.class).getResultLocation());
            ced.setResultLng(ds.child(userID).getValue(CreateEventData.class).getResultLng());
            ced.setResultLat(ds.child(userID).getValue(CreateEventData.class).getResultLat());

            ced.setDate(ds.child(userID).getValue(CreateEventData.class).getDate());
            ced.setTime(ds.child(userID).getValue(CreateEventData.class).getTime());

            ced.setSports(ds.child(userID).getValue(CreateEventData.class).getSports());
            ced.setTotalPlayers(ds.child(userID).getValue(CreateEventData.class).getTotalPlayers());

            ced.setTimeHour(ds.child(userID).getValue(CreateEventData.class).getTimeHour());
            ced.setTimeMinute(ds.child(userID).getValue(CreateEventData.class).getTimeMinute());


            //display all the information

            Log.d(TAG, "showData: ResultLocation: " + ced.getResultLocation());
            Log.d(TAG, "showData: ResultLng: " + ced.getResultLng());
            Log.d(TAG, "showData: ResultLat: " + ced.getResultLat());

            Log.d(TAG, "showData: Date: " + ced.getDate());
            Log.d(TAG, "showData: Time: " + ced.getTime());

            Log.d(TAG, "showData: Sports: " + ced.getSports());
            Log.d(TAG, "showData: TotalPlayers: " + ced.getTotalPlayers());

            Log.d(TAG, "showData: TimeHour: " + ced.getTimeHour());
            Log.d(TAG, "showData: TimeMinute: " + ced.getTimeMinute());


            ArrayList<String> array = new ArrayList<>();
            array.add(ced.getResultLocation());
            array.add(String.valueOf(ced.getResultLng()));
            array.add(String.valueOf(ced.getResultLat()));

            array.add(String.valueOf(ced.getDate()));
            array.add(String.valueOf(ced.getTime()));

            array.add(String.valueOf(ced.getSports()));
            array.add(String.valueOf(ced.getTotalPlayers()));

            array.add(String.valueOf(ced.getTimeHour()));
            array.add(String.valueOf(ced.getTimeMinute()));


            ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, array);
            mListView.setAdapter(adapter);


        }
    }


        @Override
        public void onStart () {
            super.onStart();
            mAuth.addAuthStateListener(mAuthListener);
        }

        @Override
        public void onStop () {
            super.onStop();
            if (mAuthListener != null) {
                mAuth.removeAuthStateListener(mAuthListener);
            }
        }


        /**
         * customizable toast
         * @param message
         */
        private void toastMessage (String message){
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        }






}

