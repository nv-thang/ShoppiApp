package com.example.os.shoppiapp;

import android.app.Dialog;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Date;

import models.Model_Cart;
import models.Order;

public class OrderActivity extends AppCompatActivity {
    TextView tv_total, tv_hi, tv_ds;
    EditText ed_ten, ed_sdt, ed_diachi;
    Button btnDatHang, btnOk;

    private NotificationManager manager; //quan ly cac thong bao;
    private NotificationCompat.Builder builder;

    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mRef;
    private DatabaseReference rootRef;
    private FirebaseAuth mAuth;

    int total = 0, KEY = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        mAuth = FirebaseAuth.getInstance();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        final String currentUserID = mAuth.getCurrentUser().getUid();
        mRef = mFirebaseDatabase.getReference("Carts").child(currentUserID);
        rootRef = FirebaseDatabase.getInstance().getReference();

        init();

        String ID = getIntent().getStringExtra("IDDETAIL");
        String Name = getIntent().getStringExtra("Name");
        String Gia = getIntent().getStringExtra("Gia");
        KEY = getIntent().getIntExtra("KEY", 0);
        Log.d("KEYY", KEY + "");
        Log.d("KEYY", Name + "");
        Log.d("KEYY", Gia + "");
        if (KEY == 1) {
            Log.d("KEYY", "Vào đây");
            tv_total.setText("TOTAL: "+ Gia);
            tv_ds.setText("Name: " + Name);
        }

        btnDatHang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ed_ten.getText().toString().trim().isEmpty()) {
                    Toast.makeText(OrderActivity.this, "Bạn chưa nhập tên!", Toast.LENGTH_SHORT).show();
                } else if (ed_diachi.getText().toString().trim().isEmpty()) {
                    Toast.makeText(OrderActivity.this, "Bạn chưa nhập địa chỉ!", Toast.LENGTH_SHORT).show();
                } else if (ed_sdt.getText().toString().trim().isEmpty()) {
                    Toast.makeText(OrderActivity.this, "Bạn chưa nhập số điện thoại!", Toast.LENGTH_SHORT).show();
                } else {
                    Date date = new Date();
                    Order order = new Order(
                            ed_ten.getText().toString(),
                            ed_sdt.getText().toString(),
                            ed_diachi.getText().toString(),
                            tv_total.getText().toString(),
                            tv_ds.getText().toString(),
                            currentUserID.toString(),
                            date.toString());
                    rootRef.child("Orders").child(currentUserID).push().setValue(order);
                    rootRef.child("Carts").child(currentUserID).removeValue();
                    ed_ten.setText("");
                    ed_sdt.setText("");
                    ed_diachi.setText("");
                    //Tao thong bao
                    //khoi tao notificationManager
                    manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                    builder = new NotificationCompat.Builder(OrderActivity.this, "default");
                    //sau có thể tạo ra các channelld khác nhau để sử dụng cho các mục đích quảng cáo

                    //thiết lập các thông tin cho thông báo
                    //thiết kế icon: lấy trên mạng
                    builder.setSmallIcon(R.drawable.logo);
                    builder.setContentTitle("Shoppi");
                    builder.setContentText("Bạn vừa đặt sản phẩm trên Shoppi");
                    builder.setDefaults(NotificationCompat.DEFAULT_ALL); //thiết lập chuông, rung như tin nhắn(mặc định)
                    builder.setPriority(1);// thiết lập độ ưu tiên

                    //muốn khi người dùng click vào sẽ làm gì đó
                    Intent intent = new Intent(OrderActivity.this, OrderHistoryActivity.class);
                    PendingIntent pendingIntent = PendingIntent.getActivity(OrderActivity.this, 0, intent, 0);
                    builder.setContentIntent(pendingIntent);

                    manager.notify(1409, builder.build());

                    //End tao thong bao


                    Dialog dialog = new Dialog(OrderActivity.this);
                    dialog.setContentView(R.layout.dialog_alert);
                    // dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
                    dialog.show();
                    btnOk = (Button) dialog.findViewById(R.id.btn_ok);
                    btnOk.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(OrderActivity.this, HomeActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                            finish();
                        }
                    });


                }

            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        if (KEY == 0) {

            mRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    String tv = "";
                    total = 0;
                    for (DataSnapshot artistSnapshot : dataSnapshot.getChildren()) {
                        Log.d("count", dataSnapshot.getChildrenCount() + "");
                        Model_Cart model_cart = artistSnapshot.getValue(Model_Cart.class);
                        total = total + Integer.parseInt(model_cart.getGia().substring(1));
                        tv = tv + model_cart.getTen().toString() + "; ";
                    }
                    Log.d("KEYY", "Vào đây tiếp");
                    tv_total.setText("TOTAL: $ " + total);
                    tv_ds.setText("Name: " + tv);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
    }

    private void init() {
        tv_total = findViewById(R.id.tvthanhtoan);
        tv_ds = findViewById(R.id.tvDs);
        tv_hi = findViewById(R.id.tv_hi);
        tv_hi.setText(FirebaseAuth.getInstance().getCurrentUser().getDisplayName());
        ed_ten = findViewById(R.id.edt_hoten);
        ed_sdt = findViewById(R.id.edt_sdt);
        ed_diachi = findViewById(R.id.edt_diachi);
        btnDatHang = findViewById(R.id.btnDatHang);

    }
}
