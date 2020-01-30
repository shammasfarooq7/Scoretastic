package com.example.scoretastic;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUp extends AppCompatActivity {


    EditText etName;
    EditText etEmail;
    EditText etPassword;
    Button btSignUp;
    FirebaseAuth fAuth;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myReference = database.getReference("UserData");
    userData user = new userData();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);

        etName = findViewById(R.id.etName);
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        btSignUp = findViewById(R.id.signup);
        fAuth = FirebaseAuth.getInstance();

        btSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = etEmail.getText().toString().trim();
                String password = etPassword.getText().toString().trim();
                String name = etName.getText().toString().trim();
                user.setName(name);
                user.setEmail(email);
                user.setPassword(password);

                if(!email.equals("")&&!password.equals("")&&!name.equals("")){
                    if(password.length() < 7){
                        Toast.makeText(getApplicationContext(),"Password must be equal or greater than 8 letters",Toast.LENGTH_SHORT).show();
                    }
                    else{
                        fAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(task.isSuccessful()){
                                    myReference.child("UserData").setValue(user);
                                    Toast.makeText(getApplicationContext(),"User Created",Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(getApplicationContext(),Main.class));

                                }else{
                                    Toast.makeText(getApplicationContext(),"Error Signing Up" +task.getException().getMessage() ,Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }

                }
                else{
                    Toast.makeText(getApplicationContext(),"Enter Credentials",Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}
