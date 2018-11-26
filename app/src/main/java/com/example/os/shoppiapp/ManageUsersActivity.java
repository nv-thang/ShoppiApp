package com.example.os.shoppiapp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import adapters.Cart_Adapter;
import adapters.Home_Adapter;
import adapters.User_Adapter;
import models.Model_Cart;
import models.Model_Home;
import models.Users;

public class ManageUsersActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private User_Adapter user_adapter;
    private ArrayList<Users> users_model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_users);

        recyclerView = findViewById(R.id.lv_manage);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        users_model = new ArrayList<>();

        FirebaseDatabase.getInstance().getReference().child("Users").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                users_model.clear();
                for (DataSnapshot artistSnapshot : dataSnapshot.getChildren()) {
                    Users user = artistSnapshot.getValue(Users.class);
                    users_model.add(user);
                }
                user_adapter = new User_Adapter(ManageUsersActivity.this, users_model);
                recyclerView.setAdapter(user_adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    public void backManageActivity(View view) {
        Intent intent = new Intent(ManageUsersActivity.this, ManageActivity.class);
        startActivity(intent);
    }

}
