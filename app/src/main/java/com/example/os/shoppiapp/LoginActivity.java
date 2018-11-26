package com.example.os.shoppiapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import models.Users;

public class LoginActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener, View.OnClickListener, FirebaseAuth.AuthStateListener {
    private EditText edt_email, edt_pass;
    private Button btn_Login;
    private ImageView img_LoginGoogle;

    private FirebaseAuth mAuth;
    private FirebaseUser user;
    private DatabaseReference rootRef;

    private GoogleApiClient apiClient;
    public static int CODE_DANG_NHAP_GOOGLE = 3;
    public static int KIEMTRA_PROVIDER_DANGNHAP = 1; //0 là đăng nhập bằng google

    private ProgressDialog loadingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        rootRef = FirebaseDatabase.getInstance().getReference();

        init();

        btn_Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AllowUserLogin();
            }
        });

        img_LoginGoogle.setOnClickListener(this);

        TaoClientDangNhapGoogle();
    }

    private void TaoClientDangNhapGoogle() {
        // Configure Google Sign In
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder()
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        apiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

    }

    private void DangNhapGoogle(GoogleApiClient apiClient) {
        KIEMTRA_PROVIDER_DANGNHAP = 1;
        Intent itGoogle = Auth.GoogleSignInApi.getSignInIntent(apiClient);
        startActivityForResult(itGoogle, CODE_DANG_NHAP_GOOGLE);
    }

    private void ChungThucDangNhapFirebase(String tokenID) {
        if (KIEMTRA_PROVIDER_DANGNHAP == 1) {//người dùng đăng nhập bằng google
            AuthCredential authCredential = GoogleAuthProvider.getCredential(tokenID, null);
            mAuth.signInWithCredential(authCredential);
            sendHomeActivity();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CODE_DANG_NHAP_GOOGLE) {
            GoogleSignInResult signInResult = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            if (signInResult.isSuccess()) {
                GoogleSignInAccount signInAccount = signInResult.getSignInAccount();

                String tokenID = signInAccount.getIdToken();
                String img = signInAccount.getPhotoUrl().toString();
                String name = signInAccount.getDisplayName();
                String email = signInAccount.getEmail();

                Users user = new Users(name,email,tokenID,img);
                rootRef.child("Users").child(tokenID).setValue(user);

                ChungThucDangNhapFirebase(tokenID);
            } else {
                //thát bại
            }
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(this);
        if (user != null && user.getDisplayName() != null) {
            sendHomeActivity();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        mAuth.removeAuthStateListener(this);
    }

    private void AllowUserLogin() {
        String email = edt_email.getText().toString();
        String pass = edt_pass.getText().toString();

        if (TextUtils.isEmpty(email)) {
            Toast.makeText(this, "Bạn chưa nhập Email...", Toast.LENGTH_SHORT).show();
        }
        if (TextUtils.isEmpty(pass)) {
            Toast.makeText(this, "Bạn chưa nhập Mật khẩu...", Toast.LENGTH_SHORT).show();
        } else if (!TextUtils.isEmpty(email) && !TextUtils.isEmpty(pass)) {
            loadingBar.setTitle("Đăng nhập");
            loadingBar.setMessage("Vui lòng chờ trong giây lát!");
            loadingBar.setCanceledOnTouchOutside(true);
            loadingBar.show();

            mAuth.signInWithEmailAndPassword(email, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        if (FirebaseAuth.getInstance().getCurrentUser().getDisplayName() == null) {
                            sendUpdateProfileActivity();
                            loadingBar.dismiss();
                        } else {
                            sendHomeActivity();
                            Toast.makeText(LoginActivity.this, "Đăng nhập thành công!", Toast.LENGTH_SHORT).show();
                            loadingBar.dismiss();
                        }

                    } else {
                        String message = task.getException().toString();
                        Toast.makeText(LoginActivity.this, "Error " + message, Toast.LENGTH_SHORT).show();
                        loadingBar.dismiss();
                    }
                }
            });

        }
    }

    private void init() {
        edt_email = findViewById(R.id.edt_LoginEmail);
        img_LoginGoogle = findViewById(R.id.img_loginGoogle);
        edt_pass = findViewById(R.id.edt_LoginPass);
        btn_Login = findViewById(R.id.btn_Login);
        loadingBar = new ProgressDialog(this);
    }

    public void sendSignUpActivity(View view) {
        Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
        startActivity(intent);
    }

    public void sendHomeActivity() {
        Intent sendMainActivity = new Intent(LoginActivity.this, HomeActivity.class);
        startActivity(sendMainActivity);
        finish();
    }

    public void resetPassword(View view) {
        Intent sendResetPassActivity = new Intent(LoginActivity.this, ResetPasswordActivity.class);
        startActivity(sendResetPassActivity);
    }

    public void sendUpdateProfileActivity() {
        Intent intenUpdateProfileActivity = new Intent(LoginActivity.this, UpdateProfileActivity.class);
        startActivity(intenUpdateProfileActivity);
        finish();
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.img_loginGoogle:
                DangNhapGoogle(apiClient);
                break;
        }

    }

    @Override
    public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null) {
            Toast.makeText(this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
        } else {
        }
    }
}
