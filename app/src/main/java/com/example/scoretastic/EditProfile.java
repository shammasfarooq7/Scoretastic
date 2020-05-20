package com.example.scoretastic;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class EditProfile extends AppCompatActivity {

    Button btSave;
    EditText etFavoriteSports,etPosition;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    String uid;
    ArrayList<DataSnapshot> userInfo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        btSave = findViewById(R.id.btSaveP);
        etFavoriteSports = findViewById(R.id.etFavoriteSports);
        etPosition = findViewById(R.id.etPosition);
        userInfo = new ArrayList<>();
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            uid = user.getUid();
        }
        databaseReference = firebaseDatabase.getInstance().getReference("UserData");

        btSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String id = userInfo.get(0).child("id").getValue().toString().trim();
                databaseReference.child(id).child("pos").setValue(etPosition.getText().toString().trim());
                databaseReference.child(id).child("fSports").setValue(etFavoriteSports.getText().toString().trim());
                finish();
            }
        });

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot ds : dataSnapshot.getChildren()){
                    String currentUser = ds.child("userId").getValue().toString().trim();
                    if(currentUser.equals(uid.trim())){
                        userInfo.add(ds);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }


}
