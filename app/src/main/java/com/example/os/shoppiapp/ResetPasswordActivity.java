package com.example.os.shoppiapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.util.AsyncListUtil;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ResetPasswordActivity extends AppCompatActivity {
    private EditText edt_EmailReset;
    private Button btn_Reset;

    private FirebaseAuth mAuth;
    private ProgressDialog loadingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        init();
        mAuth = FirebaseAuth.getInstance();

        btn_Reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadingBar.setMessage("Vui lòng chờ trong giây lát!");
                loadingBar.setCanceledOnTouchOutside(true);
                loadingBar.show();
                if (edt_EmailReset.getText().toString().trim().isEmpty()) {
                    Toast.makeText(ResetPasswordActivity.this, "Bạn chưa nhập Email", Toast.LENGTH_SHORT).show();
                } else {
                    mAuth.sendPasswordResetEmail(edt_EmailReset.getText().toString().trim()).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                loadingBar.dismiss();
                                Toast.makeText(ResetPasswordActivity.this, "Vui lòng kiểm tra Email của bạn để đổi lại mật khẩu của mình", Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(ResetPasswordActivity.this, LoginActivity.class);
                                startActivity(intent);
                            } else {
                                loadingBar.dismiss();
                                Toast.makeText(ResetPasswordActivity.this, "Không thành công. Vui lòng thử lại!", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });
    }

    private void init() {
        edt_EmailReset = findViewById(R.id.edt_ResetEmail);
        btn_Reset = findViewById(R.id.btn_Reset);
        loadingBar = new ProgressDialog(this);
    }

    public void backToLoginActivity(View view) {
        Intent sendLoginActivity = new Intent(ResetPasswordActivity.this, LoginActivity.class);
        startActivity(sendLoginActivity);
    }

}
