package com.example.os.shoppiapp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.BottomBarTab;
import com.roughike.bottombar.OnTabSelectListener;

import adapters.Cart_Adapter;
import fragment.AboutFragment;
import fragment.CartFragment;
import fragment.HomeFragment;
import fragment.TagFragment;
import models.Model_Cart;
import models.Model_Home;

public class HomeActivity extends AppCompatActivity {
    private BottomBar bottomBar;
    private BottomBarTab alert;
    private TextView tv_thongBao;

    private HomeFragment homeFragment;
    private TagFragment tagFragment;
    private CartFragment cartFragment;
    private AboutFragment aboutFragment;

    private FirebaseAuth mAuth;
    private FirebaseUser mUser; //chỉnh sửa hôm nay để Log ra tên người dùng khi đã đăng nhập
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mRef, mRef2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);


        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN
        );

        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();//chỉnh sửa hôm nay để Log ra tên người dùng khi đã đăng nhập


        mFirebaseDatabase = FirebaseDatabase.getInstance();
        String currentUserID = mAuth.getCurrentUser().getUid();
        mRef    = mFirebaseDatabase.getReference("Carts").child(currentUserID);
        mRef2   = mFirebaseDatabase.getReference("Orders").child(currentUserID);

        init();
        int a = getIntent().getIntExtra("OpenFragmentCart", 0);
        if(a == 11){
            bottomBar.selectTabAtPosition(2);
        }

        int KEY = getIntent().getIntExtra("KEY", 0);
        if (KEY == 1) {
            bottomBar.selectTabAtPosition(2);
        }
        alert = bottomBar.getTabWithId(R.id.tab_cart);


        bottomBar.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelected(int tabId) {
                if (tabId == R.id.tab_home) {
                    showFragment(tagFragment, cartFragment, aboutFragment, homeFragment);
                }
                if (tabId == R.id.tab_tag) {
                    showFragment(homeFragment, cartFragment, aboutFragment, tagFragment);
                }
                if (tabId == R.id.tab_cart) {
                    showFragment(homeFragment, tagFragment, aboutFragment, cartFragment);
                }
                if (tabId == R.id.tab_about) {
                    showFragment(homeFragment, cartFragment, tagFragment, aboutFragment);
                }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                alert.removeBadge();
                for (DataSnapshot artistSnapshot : dataSnapshot.getChildren()) {
                    alert.setBadgeCount((int) dataSnapshot.getChildrenCount());
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        mRef2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot artistSnapshot : dataSnapshot.getChildren()) {
                    tv_thongBao.setText(dataSnapshot.getChildrenCount() + "");
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void init() {
        homeFragment = new HomeFragment();
        tagFragment = new TagFragment();
        cartFragment = new CartFragment();
        aboutFragment = new AboutFragment();
        bottomBar = findViewById(R.id.bottomBar);
        tv_thongBao = findViewById(R.id.notification_home);

    }
    public void sendFragmentCart(View view) {
        bottomBar.selectTabAtPosition(2);
    }

    public void showFragment(Fragment fr1, Fragment fr2, Fragment fr3, Fragment fr4) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        //Ẩn tất cả các fragment đang hiển thị
        if (fr1.isAdded()) {
            ft.hide(fr1);
        }
        if (fr2.isAdded()) {
            ft.hide(fr2);
        }
        if (fr3.isAdded()) {
            ft.hide(fr3);
        }
        //Hiển thị fragment mong muốn
        if (fr4.isAdded()) {
            ft.show(fr4);
        } else {
            ft.add(R.id.frame_container, fr4);
        }
        //yêu cầu thay đổi được châp nhận
        ft.commit();
    }

    private void sendActivity(Class activity) {
        Intent intent = new Intent(HomeActivity.this, activity);
        startActivity(intent);
    }

    public void adidasclick(View view) {
        Intent intent = new Intent(HomeActivity.this, BrandActivity.class);
        intent.putExtra("theloai", "Adidas");
        startActivity(intent);
    }

    public void nikeClick(View view) {
        Intent intent = new Intent(HomeActivity.this, BrandActivity.class);
        intent.putExtra("theloai", "Nike");
        startActivity(intent);

    }

    public void pumaClick(View view) {
        Intent intent = new Intent(HomeActivity.this, BrandActivity.class);
        intent.putExtra("theloai", "Puma");
        startActivity(intent);

    }

    public void paradaClick(View view) {
        Intent intent = new Intent(HomeActivity.this, BrandActivity.class);
        intent.putExtra("theloai", "Parada");
        startActivity(intent);

    }

    public void lacosteClick(View view) {
        Intent intent = new Intent(HomeActivity.this, BrandActivity.class);
        intent.putExtra("theloai", "Lacoste");
        startActivity(intent);

    }
    public void khacClick(View view) {
        Intent intent = new Intent(HomeActivity.this, BrandActivity.class);
        intent.putExtra("theloai", "Khac");
        startActivity(intent);
    }

    public void logout(View view) {
        mAuth.signOut();
        sendUserToLoginActivity();
    }

    private void sendUserToLoginActivity() {
        Intent intentLogin = new Intent(this, LoginActivity.class);
        intentLogin.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intentLogin);
        finish();
    }

    public void sendOrderHistory(View view) {
        Intent intentOrderHistory = new Intent(this, OrderHistoryActivity.class);
        startActivity(intentOrderHistory);
    }


    public void SendSetting(View view) {
        Intent intentUpdate = new Intent(HomeActivity.this, UpdateProfileActivity.class);
        startActivity(intentUpdate);
    }
}
