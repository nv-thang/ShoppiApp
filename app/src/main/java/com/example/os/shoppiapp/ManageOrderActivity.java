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
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import adapters.OrderHistory_Adapter;
import models.Order;

public class ManageOrderActivity extends AppCompatActivity {
    private DatabaseReference rootRef;
    private FirebaseAuth mAuth;

    private TextView tv_useName, tv_userDiadiem;

    private RecyclerView recyclerView;
    private OrderHistory_Adapter orderHistory_adapter;
    private ArrayList<Order> orderArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_order);

        recyclerView = findViewById(R.id.lv_order);

//        tv_useName = findViewById(R.id.tv_userName);
//        tv_userDiadiem = findViewById(R.id.tv_diachi);
//        tv_useName.setVisibility(View.VISIBLE);
//        tv_userDiadiem.setVisibility(View.VISIBLE);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(ManageOrderActivity.this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        orderArrayList = new ArrayList<>();


    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseDatabase.getInstance().getReference().child("Orders").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot artistSnapshot : dataSnapshot.getChildren()) {
                    FirebaseDatabase.getInstance().getReference().child("Orders").child(artistSnapshot.getKey()).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                            for (DataSnapshot reviewSnapshot : dataSnapshot.getChildren()) {
                                Order order = reviewSnapshot.getValue(Order.class);
                                orderArrayList.add(order);
                            }
                            orderHistory_adapter = new OrderHistory_Adapter(ManageOrderActivity.this, orderArrayList);
                            recyclerView.setAdapter(orderHistory_adapter);
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


    public void sendHomeActivity(View view) {
        Intent intentHome = new Intent(this, ManageActivity.class);
        startActivity(intentHome);
    }
}
