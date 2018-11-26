package com.example.os.shoppiapp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import adapters.Review_Adapter;
import models.Review;

public class ManageReviewActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private Review_Adapter review_adapter;
    private ArrayList<Review> review_model;

    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_review);

        recyclerView = findViewById(R.id.lv_manage_review);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(ManageReviewActivity.this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        review_model = new ArrayList<>();
    }

    @Override
    protected void onStart() {
        super.onStart();
        //lấy số lượng sản phẩm và review
        FirebaseDatabase.getInstance().getReference().child("shoes").addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                review_model.clear();
                for (DataSnapshot artistSnapshot : dataSnapshot.getChildren()) {

                    FirebaseDatabase.getInstance().getReference().child("shoes").child(artistSnapshot.getKey()).child("Reviews").addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                            for (DataSnapshot reviewSnapshot : dataSnapshot.getChildren()) {
                                Review review = reviewSnapshot.getValue(Review.class);
                                review_model.add(review);
                            }
                            review_adapter = new Review_Adapter(ManageReviewActivity.this, review_model);
                            recyclerView.setAdapter(review_adapter);
                        }


                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }

                    });
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void sendManageActivity(View view) {
        Intent intent = new Intent(ManageReviewActivity.this, ManageActivity.class);
        startActivity(intent);
    }
}
