package com.example.os.shoppiapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import models.Users;

public class SignUpActivity extends AppCompatActivity {
    private EditText edt_Email, edt_Pass, edt_comfirmPass;
    private Button btn_SignUp;

    private FirebaseAuth mAuth;
    private DatabaseReference rootRef;

    private ProgressDialog loadingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        init();

        mAuth = FirebaseAuth.getInstance();
        rootRef = FirebaseDatabase.getInstance().getReference();

        btn_SignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createNewAccount();
            }
        });
    }

    private void createNewAccount() {
        final String email = edt_Email.getText().toString();
        final String pass = edt_Pass.getText().toString();
        String Confirmpass = edt_comfirmPass.getText().toString();

        if (TextUtils.isEmpty(email)) {
            Toast.makeText(this, "Bạn chưa nhập Email...", Toast.LENGTH_SHORT).show();
        }
        if (TextUtils.isEmpty(pass)) {
            Toast.makeText(this, "Bạn chưa nhập Mật khẩu...", Toast.LENGTH_SHORT).show();
        }
        if (TextUtils.isEmpty(Confirmpass)) {
            Toast.makeText(this, "Bạn chưa xác nhận Mật khẩu...", Toast.LENGTH_SHORT).show();

        } else if (!TextUtils.isEmpty(email) && !TextUtils.isEmpty(pass)  && !TextUtils.isEmpty(Confirmpass)) {
            if (pass.equals(Confirmpass)) {
                loadingBar.setTitle("Đang tạo tài khoản...");
                loadingBar.setMessage("Vui lòng chờ trong giây lát!");
                loadingBar.setCanceledOnTouchOutside(true);
                loadingBar.show();

                mAuth.createUserWithEmailAndPassword(email, pass)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {

                                    Toast.makeText(SignUpActivity.this, "Tạo tài khoản thành công! Hãy đăng nhập để tiếp tục.", Toast.LENGTH_SHORT).show();
                                    loadingBar.dismiss();
                                    Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
                                    startActivity(intent);
                                    finish();


                                } else {
                                    String message = task.getException().toString();
                                    Toast.makeText(SignUpActivity.this, "Error " + message, Toast.LENGTH_SHORT).show();
                                    loadingBar.dismiss();
                                }

                            }
                        });
            } else {
                Toast.makeText(this, "Mật khẩu chưa trùng khớp...", Toast.LENGTH_SHORT).show();
            }

        }
    }

    private void init() {
        loadingBar = new ProgressDialog(this);
        edt_Email = findViewById(R.id.edt_signupEmail);
        edt_Pass = findViewById(R.id.edt_signupPass);
        edt_comfirmPass = findViewById(R.id.edt_signupConfirmPass);
        btn_SignUp = findViewById(R.id.btn_Signup);
    }

    public void sendLoginActivity(View view) {
        Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
        startActivity(intent);
    }

    public void sendHomeActivity() {
        Intent intent = new Intent(SignUpActivity.this, HomeActivity.class);
        startActivity(intent);
        finish();
    }
}
