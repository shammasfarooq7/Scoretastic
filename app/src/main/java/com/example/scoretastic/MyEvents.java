package com.example.scoretastic;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


/**
 * A simple {@link Fragment} subclass.
 */
public class MyEvents extends Fragment implements EventRecyclerAdapter.ItemClicked{

    public MyEvents() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View v = inflater.inflate(R.layout.fragment_my_events, container, false);
        Button btHosted = v.findViewById(R.id.btHosted);
        Button btSubscribed = v.findViewById(R.id.btSubscribed);
        final RecyclerView recyclerViewSub = v.findViewById(R.id.rvSubscribed);
        final RecyclerView recyclerViewHost = v.findViewById(R.id.rvHosted);

        btHosted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                recyclerViewSub.setVisibility(View.GONE);
                recyclerViewHost.setVisibility(View.VISIBLE);
            }
        });

        btSubscribed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                recyclerViewHost.setVisibility(View.GONE);
                recyclerViewSub.setVisibility(View.VISIBLE);
            }
        });


        return v;
    }

    @Override
    public void onItemClicked(int index) {

    }
}