package com.example.os.shoppiapp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ManageActivity extends AppCompatActivity {
    private TextView tv_SP, tv_TV, tv_Review, tv_DH;

    private DatabaseReference mDatabase;
    private int coutReview = 0;
    private int coutOrder = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage);

        init();

        mDatabase = FirebaseDatabase.getInstance().getReference().child("shoes");


    }

    private void init() {
        tv_SP = findViewById(R.id.tv_quanlySP);
        tv_TV = findViewById(R.id.tv_quanlyTV);
        tv_Review = findViewById(R.id.tv_quanlyReview);
        tv_DH = findViewById(R.id.tv_quanlyDonHang);
    }

    @Override
    protected void onStart() {
        super.onStart();
        //lấy số lượng sản phẩm và review
        FirebaseDatabase.getInstance().getReference().child("shoes").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot artistSnapshot : dataSnapshot.getChildren()) {
                    tv_SP.setText(dataSnapshot.getChildrenCount()+" items");

                    FirebaseDatabase.getInstance().getReference().child("shoes").child(artistSnapshot.getKey()).child("Reviews").addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                            for (DataSnapshot reviewSnapshot : dataSnapshot.getChildren()) {
                                Log.d("123456789", reviewSnapshot.getValue().toString());
                                coutReview ++;
                            }
                            tv_Review.setText(coutReview+" items");
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
        //lấy số lượng thành viên
        FirebaseDatabase.getInstance().getReference().child("Users").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot artistSnapshot : dataSnapshot.getChildren()) {
                    tv_TV.setText(dataSnapshot.getChildrenCount()+" items");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        //lấy số lượng đơn hàng
        FirebaseDatabase.getInstance().getReference().child("Orders").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot artistSnapshot : dataSnapshot.getChildren()) {
                    FirebaseDatabase.getInstance().getReference().child("Orders").child(artistSnapshot.getKey()).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                            for (DataSnapshot reviewSnapshot : dataSnapshot.getChildren()) {
                                Log.d("123456789", reviewSnapshot.getValue().toString());
                                coutOrder ++;
                            }
                            tv_DH.setText(coutOrder+" items");
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

    public void manageUserClick(View view) {
        Intent intent = new Intent(ManageActivity.this, ManageUsersActivity.class);
        startActivity(intent);

    }

    public void thoat(View view) {
        Intent intentThoat = new Intent(ManageActivity.this, HomeActivity.class);
        startActivity(intentThoat);
        finish();
    }

    public void themSP(View view) {
        Intent intent = new Intent(ManageActivity.this, ThemSanPhamActivity.class);
        startActivity(intent);
    }

    public void manageSpClick(View view) {
        Intent intent = new Intent(ManageActivity.this, ManageSPActivity.class);
        startActivity(intent);
    }

    public void manageReviewClick(View view) {
        Intent intent = new Intent(ManageActivity.this, ManageReviewActivity.class);
        startActivity(intent);
    }

    public void manageOrderClick(View view) {
        Intent intent = new Intent(ManageActivity.this, ManageOrderActivity.class);
        startActivity(intent);
    }
}
