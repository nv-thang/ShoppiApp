package com.example.os.shoppiapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.hsalf.smilerating.BaseRating;
import com.hsalf.smilerating.SmileRating;

import java.util.ArrayList;

import adapters.Review_Adapter;
import models.Review;

public class Rateproductpage extends Fragment {
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mRef;

    private SmileRating rating;

    private RecyclerView recyclerView;
    private Review_Adapter review_adapter;
    private ArrayList<Review> review_model;
    String id;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.item_rate, container, false);
        id = getActivity().getIntent().getStringExtra("ID");

        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mRef = mFirebaseDatabase.getReference("shoes").child(id).child("Reviews");


        init(view);
        recyclerView = view.findViewById(R.id.lv_rate);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        review_model = new ArrayList<>();

        rating.setOnSmileySelectionListener(new SmileRating.OnSmileySelectionListener() {
            @Override
            public void onSmileySelected(@BaseRating.Smiley int smiley, boolean reselected) {
                switch (smiley) {
                    case SmileRating.BAD:
                        sendRateActivity("BAD");
                        Log.d("Rateing", "Bad");
                        break;
                    case SmileRating.GOOD:
                        sendRateActivity("GOOD");
                        Log.d("Rateing", "Good");
                        break;
                    case SmileRating.GREAT:
                        sendRateActivity("GREAT");
                        Log.d("Rateing", "Great");
                        break;
                    case SmileRating.OKAY:
                        sendRateActivity("OKAY");
                        Log.d("Rateing", "Okay");
                        break;
                    case SmileRating.TERRIBLE:
                        sendRateActivity("TERRIBLE");
                        Log.d("Rateing", "Terrible");
                        break;
                }
            }
        });
        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                review_model.clear();
                for (DataSnapshot artistSnapshot : dataSnapshot.getChildren()) {
                    Review review = artistSnapshot.getValue(Review.class);
                    review_model.add(review);
                }
                review_adapter = new Review_Adapter(getActivity(), review_model);
                recyclerView.setAdapter(review_adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        return view;
    }

    private void init(View view) {
        rating = view.findViewById(R.id.smile_ratingbar);
        recyclerView = view.findViewById(R.id.lv_rate);
    }
    public void sendRateActivity(String smile){
        Intent intent = new Intent(getActivity(), RateActivity.class);
        intent.putExtra("RATE", smile);
        intent.putExtra("ID", id);
        startActivity(intent);

    }
}
