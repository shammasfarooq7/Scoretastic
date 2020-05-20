package com.example.scoretastic;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ChangePassword extends AppCompatActivity {

    EditText oldPass,newPass,newPassConfirm;
    Button btSave;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    String uid;
    ArrayList<DataSnapshot> userInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        oldPass = findViewById(R.id.oldPass);
        newPass = findViewById(R.id.newPass);
        newPassConfirm = findViewById(R.id.newPassConfirm);
        btSave = findViewById(R.id.btSave);

        userInfo = new ArrayList<>();
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            uid = user.getUid();
        }
        databaseReference = firebaseDatabase.getInstance().getReference("UserData");


        btSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

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
        btSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String old = oldPass.getText().toString().trim();
                final String newP = newPass.getText().toString().trim();
                String newPA = newPassConfirm.getText().toString().trim();

                if(old.equals(userInfo.get(0).child("password").getValue().toString().trim())){
                    if(newP.equals(newPA) && (newP.length()>7)){
                        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                        user.updatePassword(newP)
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            databaseReference.child(userInfo.get(0).child("id").getValue().toString().trim()).child("password").setValue(newP);
                                            Toast.makeText(getApplicationContext(),"Password Successfully Changed",Toast.LENGTH_LONG).show();
                                            finish();
                                        }
                                    }
                                });
                    }
                    else{
                        Toast.makeText(getApplicationContext(),"Your Password doesn't match or length is too short",Toast.LENGTH_LONG).show();
                        finish();
                    }
                }
                else{
                    Toast.makeText(getApplicationContext(),"Your Old Password doesn't Match, Try Again",Toast.LENGTH_LONG).show();
                    finish();
                }
            }
        });
    }

}
