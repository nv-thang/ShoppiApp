package com.example.os.shoppiapp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import adapters.Cart_Adapter;
import adapters.OrderHistory_Adapter;
import models.Model_Cart;
import models.Order;

public class OrderHistoryActivity extends AppCompatActivity {

    private DatabaseReference rootRef;
    private FirebaseAuth mAuth;

    private RecyclerView recyclerView;
    private OrderHistory_Adapter orderHistory_adapter;
    private ArrayList<Order> orderArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_history);

        mAuth   = FirebaseAuth.getInstance();
        final String currentUserID = mAuth.getCurrentUser().getUid();
        rootRef = FirebaseDatabase.getInstance().getReference("Orders").child(currentUserID);

        recyclerView = findViewById(R.id.lv_orderHistory);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(OrderHistoryActivity.this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        orderArrayList = new ArrayList<>();

        rootRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                orderArrayList.clear();
                for (DataSnapshot artistSnapshot : dataSnapshot.getChildren()) {
                    Order order = artistSnapshot.getValue(Order.class);
                    orderArrayList.add(order);
                }
                orderHistory_adapter = new OrderHistory_Adapter(OrderHistoryActivity.this, orderArrayList);
                recyclerView.setAdapter(orderHistory_adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    public void backToHomeActivity(View view) {
        Intent intentHome = new Intent(this, HomeActivity.class);
        startActivity(intentHome);
    }
}
