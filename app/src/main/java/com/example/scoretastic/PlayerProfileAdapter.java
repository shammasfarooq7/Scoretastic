package com.example.scoretastic;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class PlayerProfileAdapter extends RecyclerView.Adapter<PlayerProfileAdapter.ViewHolder> {
    private ArrayList<ProfileRecycler> event;
    LinearLayoutManager mLinearLayoutManager;

    public PlayerProfileAdapter(Context context,ArrayList<ProfileRecycler> list){
        event = list;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView tvNameP,tvMatchP,tvFsportsP,tvPosition;
        ImageView ivProfilePic;

        public ViewHolder(@NonNull View itemView){
            super(itemView);
            tvFsportsP = itemView.findViewById(R.id.tvFsportsP);
            tvMatchP = itemView.findViewById(R.id.tvMatchP);
            tvPosition = itemView.findViewById(R.id.tvPosition);
            tvNameP = itemView.findViewById(R.id.tvNameP);
            ivProfilePic = itemView.findViewById(R.id.ivProfilePic);

        }

    }

    public  PlayerProfileAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.player_info_cardview, parent, false);
        mLinearLayoutManager = new LinearLayoutManager(parent.getContext(), LinearLayoutManager.VERTICAL, false);
        return new PlayerProfileAdapter.ViewHolder(v);
    }

    public void onBindViewHolder(@NonNull PlayerProfileAdapter.ViewHolder holder, int position) {
        holder.itemView.setTag(event.get(position));
        holder.tvFsportsP.setText(event.get(position).getFavSports());
        holder.tvMatchP.setText(event.get(position).getTotalMatch());
        holder.tvPosition.setText(event.get(position).getPos());
        holder.tvNameP.setText(event.get(position).getName());
        //holder.ivProfilePic.setImageResource(event.get(position).getImage());

    }

    @Override
    public int getItemCount() {
        return event.size();
    }

}
