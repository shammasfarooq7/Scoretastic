package com.example.scoretastic;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class HomeRecyclerAdapter extends RecyclerView.Adapter<HomeRecyclerAdapter.ViewHolder> {

    private ArrayList<Recycler> event;
    ItemClicked activity;
    LinearLayoutManager mLinearLayoutManager;

    public interface ItemClicked {
        void onItemClicked(int index);
    }

    public HomeRecyclerAdapter(Home context, ArrayList<Recycler> list) {
        event = list;
        activity = (ItemClicked) context;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvSports;
        TextView tvLocation;
        TextView etTime;
        TextView etDate;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvSports = itemView.findViewById(R.id.tvSports);
            tvLocation = itemView.findViewById(R.id.tvLocation);
            etTime = itemView.findViewById(R.id.etTime);
            etDate = itemView.findViewById(R.id.etDate);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });
        }

    }

    @NonNull
    @Override
    public HomeRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view, parent, false);
        mLinearLayoutManager = new LinearLayoutManager(parent.getContext(), LinearLayoutManager.VERTICAL, false);
        return new HomeRecyclerAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull HomeRecyclerAdapter.ViewHolder holder, int position) {
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