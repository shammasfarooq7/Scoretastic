package com.example.scoretastic;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class TeamHomeRecyclerAdapter extends RecyclerView.Adapter<TeamHomeRecyclerAdapter.ViewHolder> {
    private ArrayList<Recycler> event;
    TeamHomeRecyclerAdapter.ItemClicked activity;
    LinearLayoutManager mLinearLayoutManager;
    private TeamHomeRecyclerAdapter.onItemClickListner mListner;

    public interface onItemClickListner{
        void onItemClick(int position);
    }
    public void setOnItemClickListner(onItemClickListner listner){
        mListner = listner;
    }
    public interface ItemClicked {
        void onItemClicked(int index);
    }

    public TeamHomeRecyclerAdapter(Context context, ArrayList<Recycler> list){
        event = list;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView tvSports;
        TextView tvLocation;
        TextView etTime;
        TextView etDate;

        public ViewHolder(View itemView, final onItemClickListner listner){
            super(itemView);
            tvSports = itemView.findViewById(R.id.tvMarkerSports);
            tvLocation = itemView.findViewById(R.id.tvMarkerLocation);
            etTime = itemView.findViewById(R.id.tvMarkerTime);
            etDate = itemView.findViewById(R.id.tvMarkerDate);

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

    public TeamHomeRecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view, parent, false);
        mLinearLayoutManager = new LinearLayoutManager(parent.getContext(), LinearLayoutManager.VERTICAL, false);
        return new TeamHomeRecyclerAdapter.ViewHolder(v,mListner);
    }

    public void onBindViewHolder(@NonNull TeamHomeRecyclerAdapter.ViewHolder holder, int position) {
        holder.itemView.setTag(event.get(position));
        holder.tvSports.setText(event.get(position).getSports());
        holder.tvLocation.setText(event.get(position).getLocation());
        holder.etDate.setText(event.get(position).date);
        holder.etTime.setText(event.get(position).time);

    }
    @Override
    public int getItemCount() {
        return event.size();
    }

}
