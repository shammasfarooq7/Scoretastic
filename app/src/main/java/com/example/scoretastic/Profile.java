package com.example.scoretastic;


import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.net.URL;
import java.util.ArrayList;

import static android.app.Activity.RESULT_OK;
import static com.firebase.ui.auth.AuthUI.getApplicationContext;


/**
 * A simple {@link Fragment} subclass.
 */
public class Profile extends Fragment{

    TextView tvName,tvEmail,btChangePassword;
    EditText oldPass,newPass,newPassConfirm;
    Button btSave,btSignOut;
    ImageView profilePic,btPicChange;
    View v;
    String uid;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    ArrayList<DataSnapshot> userInfo = new ArrayList();
    private Uri image;
    private final int PICK_IMAGE_REQUEST = 1;
    StorageReference storageReferencege;

    public Profile() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        tvName = view.findViewById(R.id.tvName);
        tvEmail = view.findViewById(R.id.tvEmail);
        btChangePassword = view.findViewById(R.id.btChangePassword);
        btSignOut = view.findViewById(R.id.btSignOut);
        profilePic = view.findViewById(R.id.profilePic);
        btPicChange = view.findViewById(R.id.btPic);
        v = view.findViewById(R.id.passLayout);
        oldPass = v.findViewById(R.id.oldPass);
        newPass = v.findViewById(R.id.newPass);
        newPassConfirm = v.findViewById(R.id.newPassConfirm);
        btSave = v.findViewById(R.id.btSave);
        v.setVisibility(View.GONE);
        databaseReference = firebaseDatabase.getInstance().getReference("UserData");
        storageReferencege = FirebaseStorage.getInstance().getReference("uploads");
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            uid = user.getUid();
        }

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot ds : dataSnapshot.getChildren()){
                    String currentUser = ds.child("userId").getValue().toString().trim();
                    if(currentUser.equals(uid.trim())){
                        userInfo.add(ds);
                    }
                }
                tvEmail.setText(userInfo.get(0).child("email").getValue().toString().trim());
                tvName.setText(userInfo.get(0).child("name").getValue().toString().trim());
                if(userInfo.get(0).hasChild("profilePic")){
                    String i = dataSnapshot.child(userInfo.get(0).child("id").getValue().toString().trim()).child("profilePic").getValue().toString().trim();
                    Picasso.get().load(i).into(profilePic);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        btSignOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(getContext(),Login.class);
                startActivity(intent);
                getActivity().finish();
                Toast.makeText(getContext(),"Signed out Successfully",Toast.LENGTH_LONG).show();
            }
        });

        btChangePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                v.setVisibility(View.VISIBLE);
            }
        });
        btSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                v.setVisibility(View.GONE);
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
                                            Toast.makeText(getContext(),"Password Successfully Changed",Toast.LENGTH_LONG).show();
                                        }
                                    }
                                });
                    }
                    else{
                        Toast.makeText(getContext(),"Your Password doesn't match or length is too short",Toast.LENGTH_LONG).show();
                    }
                }
                else{
                    Toast.makeText(getContext(),"Your Old Password doesn't Match, Try Again",Toast.LENGTH_LONG).show();
                }
            }
        });
        btPicChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent,PICK_IMAGE_REQUEST);
            }
        });

        return view;

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
        && data!= null && data.getData() != null){
            image = data.getData();
            if(image!=null){
                final StorageReference storageReference = storageReferencege.child(System.currentTimeMillis()
                        +"."+ getFileExtension(image));
                storageReference.putFile(image).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        profilePic.setImageURI(image);
                        storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                databaseReference.child(userInfo.get(0).child("id").getValue().toString().trim()).child("profilePic").setValue(uri.toString());
                            }
                        });

                    }
                });
            }

        }

    }

    private String getFileExtension(Uri uri){
        ContentResolver cR = getContext().getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }

}