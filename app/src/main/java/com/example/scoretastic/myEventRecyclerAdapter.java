package com.example.scoretastic;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class myEventRecyclerAdapter extends RecyclerView.Adapter<myEventRecyclerAdapter.ViewHolder> {

    private ArrayList<recycler> event;
    homeRecyclerAdapter.ItemClicked activity;

    public interface  ItemClicked{
        void onItemClicked(int index);
    }

    public myEventRecyclerAdapter(Context context, ArrayList<recycler> list){
        event = list;
        activity = (homeRecyclerAdapter.ItemClicked) context;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvSports;
        TextView tvLocation;
        EditText etTime;
        EditText etDate;
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
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view, parent,false);
        return new myEventRecyclerAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.itemView.setTag(event.get(position));
        holder.tvSports.setText(event.get(position).sports);
        holder.tvLocation.setText(event.get(position).location);
        holder.etDate.setText((CharSequence) event.get(position).date);
        holder.etTime.setText((CharSequence) event.get(position).time);
    }

    @Override
    public int getItemCount() {
        return event.size();
    }

}
