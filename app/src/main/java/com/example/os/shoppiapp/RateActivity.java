package com.example.os.shoppiapp;

import android.app.Dialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.hsalf.smilerating.BaseRating;
import com.hsalf.smilerating.SmileRating;

import models.Review;

public class RateActivity extends AppCompatActivity {
    private ImageView imgReview;
    private EditText writeReview;
    private SmileRating rating;
    private String smile;
    private Button btnSendReview, btnOk;
    private TextView tvStatusAlert;

    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mRef;
    private FirebaseAuth mAuth;

    String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rate);

        String ID = getIntent().getStringExtra("ID");

        mAuth = FirebaseAuth.getInstance();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        final String currentUserID = mAuth.getCurrentUser().getUid();
        mRef = mFirebaseDatabase.getReference("shoes").child(ID).child("Reviews").push();

        init();

        if (smile.equals("BAD")) {
            imgReview.setImageResource(R.drawable.bad);
            rating.setSelectedSmile(BaseRating.BAD);
        }
        if (smile.equals("GOOD")) {
            imgReview.setImageResource(R.drawable.good);
            rating.setSelectedSmile(BaseRating.GOOD);
        }
        if (smile.equals("GREAT")) {
            imgReview.setImageResource(R.drawable.great);
            rating.setSelectedSmile(BaseRating.GREAT);
        }
        if (smile.equals("OKAY")) {
            imgReview.setImageResource(R.drawable.okay);
            rating.setSelectedSmile(BaseRating.OKAY);
        }
        if (smile.equals("TERRIBLE")) {
            imgReview.setImageResource(R.drawable.terrible);
            rating.setSelectedSmile(BaseRating.TERRIBLE);
        }

        rating.setOnSmileySelectionListener(new SmileRating.OnSmileySelectionListener() {
            @Override
            public void onSmileySelected(@BaseRating.Smiley int smiley, boolean reselected) {
                switch (smiley) {
                    case SmileRating.BAD:
                        imgReview.setImageResource(R.drawable.bad);
                        break;
                    case SmileRating.GOOD:
                        imgReview.setImageResource(R.drawable.good);
                        break;
                    case SmileRating.GREAT:
                        imgReview.setImageResource(R.drawable.great);
                        break;
                    case SmileRating.OKAY:
                        imgReview.setImageResource(R.drawable.okay);
                        break;
                    case SmileRating.TERRIBLE:
                        imgReview.setImageResource(R.drawable.terrible);
                        break;
                }
            }
        });
        btnSendReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (writeReview.getText().toString().trim().isEmpty()) {
                    Toast.makeText(RateActivity.this, "Bạn chưa nhập đánh giá", Toast.LENGTH_SHORT).show();
                } else {

                    mRef.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            mFirebaseDatabase.getReference("Users").child(currentUserID).child("name").addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    Log.d("aaaaaaa ", dataSnapshot.toString());
                                    name = dataSnapshot.getValue().toString();
                                    String status = writeReview.getText().toString();
                                    int smile = rating.getSelectedSmile();
                                    Review review = new Review(status, name, smile,mAuth.getCurrentUser().getPhotoUrl().toString());
                                    mRef.setValue(review);
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            });
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });

                    Dialog dialog = new Dialog(RateActivity.this);
                    dialog.setContentView(R.layout.dialog_alert);
                    // dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
                    dialog.show();
                    btnOk = (Button) dialog.findViewById(R.id.btn_ok);
                    tvStatusAlert = (TextView) dialog.findViewById(R.id.tvStatusAlert);
                    tvStatusAlert.setText("Thank you for your review!");
                    btnOk.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(RateActivity.this, HomeActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                            finish();
                        }
                    });
                }
            }
        });
    }

    private void init() {
        smile = getIntent().getStringExtra("RATE");
        imgReview = findViewById(R.id.imgReview);
        writeReview = findViewById(R.id.Writereview);
        rating = findViewById(R.id.smile_ratingReview);
        btnSendReview = findViewById(R.id.btn_SendReview);


    }
}
