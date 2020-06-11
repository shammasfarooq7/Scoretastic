package com.example.scoretastic;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.auth.data.model.User;

import java.util.List;

public class UserMessageAdapter extends RecyclerView.Adapter<UserMessageAdapter.ViewHolder> {
    private Context mContext;
    private List<TeamUserMessage> mUsers;
    private onItemClickListner mListner;

    public interface onItemClickListner{
        void onItemClick(int position);
    }
    public void setOnItemClickListner(UserMessageAdapter.onItemClickListner listner){
        mListner = listner;
    }

    public UserMessageAdapter(Context mContext, List<TeamUserMessage> mUsers){
        this.mUsers = mUsers;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.user_item,parent,false);
        return new UserMessageAdapter.ViewHolder(view,mListner);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        TeamUserMessage user = mUsers.get(position);
        holder.userName.setText(user.getName());
        if(!user.getProfilePic().equals("default")){
            Glide.with(mContext).load(user.getProfilePic()).into(holder.profile_image);
        }
    }

    @Override
    public int getItemCount() {
        return mUsers.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView userName;
        public ImageView profile_image;


        public ViewHolder(@NonNull View itemView, final onItemClickListner listner) {
            super(itemView);
            userName = itemView.findViewById(R.id.username);
            profile_image = itemView.findViewById(R.id.profileImage);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(listner != null){
                        int position = getAdapterPosition();
                        if(position != RecyclerView.NO_POSITION){
                            listner.onItemClick(position);
                        }
                    }
                }
            });
        }

    }

}
