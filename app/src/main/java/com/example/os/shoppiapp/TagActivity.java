package com.example.os.shoppiapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
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

import java.util.ArrayList;

import adapters.Home_Adapter;
import models.Model_Home;

public class TagActivity extends AppCompatActivity {
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mRef;

    private RecyclerView recyclerView;
    private Home_Adapter home_adapter;
    private ArrayList<Model_Home> home_model;

    private int position;
    private TextView txtTag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tag);

        txtTag = findViewById(R.id.txtTag);

        recyclerView = findViewById(R.id.lvListShoesTag);
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mRef = mFirebaseDatabase.getReference("shoes");
        //recyclerview code:
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        home_model = new ArrayList<>();
        position = getIntent().getIntExtra("position", 0);

    }

    @Override
    public void onStart() {
        super.onStart();
        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot artistSnapshot : dataSnapshot.getChildren()) {
                    Model_Home model_home = artistSnapshot.getValue(Model_Home.class);
                    switch (position) {
                        case 0:
                            txtTag.setText("What 's new");
                            home_model.add(model_home);
                            break;
                        case 1:
                            txtTag.setText("Top Shoes");
                            home_model.add(model_home);
                            break;
                        case 2:
                            txtTag.setText("Dạo Phố");
                            if (model_home.getTag().equals("daopho")) {
                                home_model.add(model_home);
                            }
                            break;
                        case 3:
                            txtTag.setText("Học Đường");
                            if (model_home.getTag().equals("hocduong")) {
                                home_model.add(model_home);
                            }
                            break;
                        case 4:
                            txtTag.setText("Công Sở");
                            if (model_home.getTag().equals("congso")) {
                                home_model.add(model_home);
                            }
                            break;
                    }

                }
                if (home_model.size() <= 0) {
                    Toast.makeText(TagActivity.this, "Không thấy mặt hàng nào!", Toast.LENGTH_SHORT).show();
                }
                home_adapter = new Home_Adapter(TagActivity.this, home_model);
                recyclerView.setAdapter(home_adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void backHome(View view) {
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
        finish();
    }

    public void sendCart(View view) {
        Intent intent = new Intent(this, HomeActivity.class);
        intent.putExtra("OpenFragmentCart", 11);
        startActivity(intent);
    }
}
