package com.example.os.shoppiapp;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.willowtreeapps.spruce.Spruce;
import com.willowtreeapps.spruce.animation.DefaultAnimations;
import com.willowtreeapps.spruce.sort.DefaultSort;

import java.util.ArrayList;

import adapters.Home_Adapter;
import models.Model_Home;

public class BrandActivity extends AppCompatActivity {
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mRef;

    private RecyclerView recyclerView;
    private Home_Adapter home_adapter;
    private ArrayList<Model_Home> home_model;

    private TextView txtBrand;
    private String theloai ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_brand);

        recyclerView = findViewById(R.id.lvWhatNew);
        txtBrand = findViewById(R.id.txtBrand);

        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mRef = mFirebaseDatabase.getReference("shoes");
        //recyclerview code:
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this, 2){
            @Override
            public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
                super.onLayoutChildren(recycler, state);
                Animator spruceAnimator = new Spruce
                        .SpruceBuilder(recyclerView)
                        .sortWith(new DefaultSort(/*interObjectDelay=*/50L))
                        .animateWith(DefaultAnimations.shrinkAnimator(recyclerView, /*duration=*/800),
                                ObjectAnimator.ofFloat(recyclerView,"translationX",-recyclerView.getWidth(),0f).setDuration(800))
                        .start();
            }
        };
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        home_model = new ArrayList<>();

        theloai=  getIntent().getStringExtra("theloai");

    }
    @Override
    public void onStart() {
        super.onStart();
        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                home_model.clear();
                for(DataSnapshot artistSnapshot : dataSnapshot.getChildren()){
                    Model_Home model_home = artistSnapshot.getValue(Model_Home.class);
                    txtBrand.setText(theloai);
                    if(model_home.getTheloai().equals(theloai)){
                        home_model.add(model_home);
                    }
                }
                if (home_model.size()<=0){
                    Toast.makeText(BrandActivity.this, "Không thấy mặt hàng nào!", Toast.LENGTH_SHORT).show();
                }
                home_adapter = new Home_Adapter(BrandActivity.this, home_model);
                recyclerView.setAdapter(home_adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void backHome(View view) {
        Intent intent = new Intent(BrandActivity.this, HomeActivity.class);
        startActivity(intent);
        finish();
    }
}
