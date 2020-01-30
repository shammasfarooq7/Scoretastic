package com.example.scoretastic;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Login extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListner;
    Button btLogin;
    EditText etEmail;
    EditText etPassword;
    TextView signUp;
    Context mContext;

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        mContext = this;

        btLogin = findViewById(R.id.btlogin);
        etEmail     = findViewById(R.id.etEmail);
        etPassword   = findViewById(R.id.etPassword);
        signUp = findViewById(R.id.SignUp);
        mAuth = FirebaseAuth.getInstance();
        mAuthListner = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if(user!=null){

                }else{

                }
            }
        };


        btLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = etEmail.getText().toString().trim();
                String password = etPassword.getText().toString().trim();
                if(!email.equals("") && !password.equals("")){
                    mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(getApplicationContext(),"Signed IN",Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(mContext, Main.class));
                            }
                            else{
                                Toast.makeText(getApplicationContext(),"Error Signing In" +task.getException().getMessage() ,Toast.LENGTH_SHORT).show();
                            }                        }
                    });
                }
                else{
                    Toast.makeText(getApplicationContext(),"Please enter email and password",Toast.LENGTH_SHORT).show();
                }
            }
        });

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =  new Intent(mContext, SignUp.class);
                startActivity(intent);
            }
        });

    }
    public void onStart(){
        super.onStart();
        mAuth.addAuthStateListener(mAuthListner);
    }
    public void onStop(){
        super.onStop();
        if(mAuthListner!=null){
            mAuth.removeAuthStateListener(mAuthListner);
        }
    }


}

