package com.example.os.shoppiapp;

import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import fragment.TagFragment;
import me.relex.circleindicator.CircleIndicator;
import models.Model_Home;
import models.Users;

public class DetailActivity extends AppCompatActivity {
    private ViewPager v1;
    private ProductpageAdapter a;
    private CircleIndicator indicator;
    private String ID;
    private String Name;
    private String Gia;

    private ImageView  addCart;
    private ViewPager viewpager1;
    private TabLayout tablayout1;
    private TabproductpageAdapter adapter;
    private TextView tv_nameShoeDetail, tv_giaDetail, tv_giakmDetail;

    private FirebaseAuth mAuth;
    private DatabaseReference rootRef;

    String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        init();
        mAuth = FirebaseAuth.getInstance();
        rootRef = FirebaseDatabase.getInstance().getReference();

        ID = getIntent().getStringExtra("ID");
        tv_nameShoeDetail.setText(getIntent().getStringExtra("ten"));
        Name = getIntent().getStringExtra("ten");
        Gia = getIntent().getStringExtra("giakm");
        tv_giaDetail.setText(getIntent().getStringExtra("gia"));
        tv_giakmDetail.setText(getIntent().getStringExtra("giakm"));



        v1 = (ViewPager) findViewById(R.id.v1);
        CircleIndicator indicator = (CircleIndicator) findViewById(R.id.indicator);
        a = new ProductpageAdapter(getSupportFragmentManager());
        v1.setAdapter(a);
        indicator.setViewPager(v1);
        a.registerDataSetObserver(indicator.getDataSetObserver());

        tablayout1 = findViewById(R.id.tablayout1);

        viewpager1 = findViewById(R.id.viewpager1);

        tablayout1.setTabGravity(TabLayout.GRAVITY_FILL);

        tablayout1.addTab(tablayout1.newTab().setText("INFO"));
        tablayout1.addTab(tablayout1.newTab().setText("VOTE"));

        adapter = new TabproductpageAdapter(getSupportFragmentManager(), tablayout1.getTabCount());
        viewpager1.setAdapter(adapter);
        viewpager1.setOffscreenPageLimit(2);
        viewpager1.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tablayout1));
        tablayout1.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {

            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewpager1.setCurrentItem(tab.getPosition());

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    private void init() {
        tv_nameShoeDetail = findViewById(R.id.tv_nameShoeDetail);
        tv_giaDetail = findViewById(R.id.tv_giaDetail);
        tv_giakmDetail = findViewById(R.id.tv_giakmDetail);
        addCart = findViewById(R.id.addCart);
    }

    public void backHome(View view) {
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
        finish();
    }

    public void addCart(View view) {
        addCart.setColorFilter(ContextCompat.getColor(view.getContext(), R.color.mau_cam));

        final String currentUserID = mAuth.getCurrentUser().getUid();

        rootRef.child("shoes").child(ID).child("image").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                url = dataSnapshot.getValue(String.class);
                Log.d("URLL", url);
                Model_Home cart = new Model_Home(url,getIntent().getStringExtra("ten"),getIntent().getStringExtra("giakm"),ID);
                rootRef.child("Carts").child(currentUserID).child(ID).setValue(cart);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

//        Model_Home cart = new Model_Home(url,getIntent().getStringExtra("ten"),getIntent().getStringExtra("giakm"),ID);
//        Log.d("Model_Home","URL: "+ url);
//        Log.d("Model_Home","Ten: "+ getIntent().getStringExtra("ten"));
//        Log.d("Model_Home","Gia: "+ getIntent().getStringExtra("giakm"));
//        Log.d("Model_Home","ID: "+ ID);
//        rootRef.child("Carts").child(currentUserID).child(ID).setValue(cart);

        Intent intent = new Intent(DetailActivity.this, HomeActivity.class);
        intent.putExtra("KEY", 1);
        intent.putExtra("IDDETAIL", ID);
        startActivity(intent);
    }

    public void buyNow(View view) {
        Intent intent = new Intent(DetailActivity.this, OrderActivity.class);
        intent.putExtra("IDDETAIL", ID);
        intent.putExtra("Name", Name);
        intent.putExtra("Gia", Gia);
        intent.putExtra("KEY", 1);
        Log.d("c","ID"+ ID);
        startActivity(intent);
    }
}
