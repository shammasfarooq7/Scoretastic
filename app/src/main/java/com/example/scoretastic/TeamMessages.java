package com.example.scoretastic;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class TeamMessages extends Fragment implements UserMessageAdapter.onItemClickListner{

    RecyclerView recyclerView;
    UserMessageAdapter userAdapter;
    private List<TeamUserMessage> mUser;

    FirebaseUser fuser;
    DatabaseReference reference;

    List<String> userList;

    public TeamMessages() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_team_messages, container, false);

        recyclerView = view.findViewById(R.id.recyclerTeamMessages);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        fuser = FirebaseAuth.getInstance().getCurrentUser();
        userList = new ArrayList<>();
        reference = FirebaseDatabase.getInstance().getReference("Chats");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                userList.clear();
                for(DataSnapshot ds : dataSnapshot.getChildren()){
                    Chat chat = ds.getValue(Chat.class);
                    if(chat.getSender().equals(fuser.getUid())){
                        userList.add(chat.getReceiver());
                    }
                    if(chat.getReceiver().equals(fuser.getUid())){
                        userList.add(chat.getSender());
                    }
                }
                readChats();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



        return  view;
    }
    private  void readChats(){
        mUser = new ArrayList<>();
        reference = FirebaseDatabase.getInstance().getReference("UserData");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mUser.clear();
                for(DataSnapshot ds : dataSnapshot.getChildren()){
                    TeamUserMessage user = new TeamUserMessage();
                    user.setUserId(ds.child("userId").getValue().toString().trim());
                    user.setName(ds.child("name").getValue().toString().trim());
                    if(ds.child("profilePic").exists()){
                        user.setProfilePic(ds.child("profilePic").getValue().toString().trim());
                    }
                    else{
                        user.setProfilePic("default");
                    }
                    for(int i = 0; i< userList.size();i++){
                        if(userList.get(i).equals(user.getUserId())){
                            if(mUser.size() != 0){
                                for(TeamUserMessage user1 : mUser){
                                    if(!user.getUserId().equals(user1.getUserId())){
                                        mUser.add(user);
                                    }
                                }
                            } else{
                                mUser.add(user);
                            }

                        }
                    }
                }

                userAdapter = new UserMessageAdapter(getContext(),mUser);
                recyclerView.setAdapter(userAdapter);
                userAdapter.setOnItemClickListner(new UserMessageAdapter.onItemClickListner() {
                    @Override
                    public void onItemClick(int position) {
                        String hostId = mUser.get(position).getUserId();
                        String hostName = mUser.get(position).getName();
                        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
                        Intent intent = new Intent(getContext(),Messenger.class);
                        intent.putExtra("Host Id", hostId);
                        intent.putExtra("Host Name", hostName);
                        intent.putExtra("User Id", uid);
                        startActivity(intent);
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onItemClick(int position) {

    }
}
