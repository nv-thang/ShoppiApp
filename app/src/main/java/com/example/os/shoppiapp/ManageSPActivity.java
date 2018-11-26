package com.example.os.shoppiapp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import adapters.Cart_Adapter;
import models.Model_Cart;

public class ManageSPActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private Cart_Adapter cart_adapter;
    private ArrayList<Model_Cart> cart_model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_sp);

        recyclerView = findViewById(R.id.lv_manage_sp);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        cart_model = new ArrayList<>();

        FirebaseDatabase.getInstance().getReference().child("shoes").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                cart_model.clear();
                for (DataSnapshot artistSnapshot : dataSnapshot.getChildren()) {
                    Model_Cart model_cart = artistSnapshot.getValue(Model_Cart.class);
                    cart_model.add(model_cart);
                }
                cart_adapter = new Cart_Adapter(ManageSPActivity.this, cart_model);
                recyclerView.setAdapter(cart_adapter);
                cart_adapter.setOnItemClickedListener(new Cart_Adapter.OnItemClickedListener() {
                    @Override
                    public void onItemClick(String id) {
                        FirebaseDatabase.getInstance().getReference().child("shoes").child(id).removeValue();
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    public void backManageActivity(View view) {
        Intent intent = new Intent(ManageSPActivity.this, ManageActivity.class);
        startActivity(intent);
    }
}
