package com.example.os.shoppiapp;

import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

import de.hdodenhof.circleimageview.CircleImageView;
import models.Users;

import static com.google.android.gms.common.util.IOUtils.copyStream;

public class UpdateProfileActivity extends AppCompatActivity {
    private EditText edt_username;
    private Button btnSave;
    private ImageView imgAvt;

    private int REQUEST_CODE_IMG = 1;
    private File mFileTemp;
    public static final String TEMP_PHOTO_FILE_NAME = "temp_photo.jpg";

    private ProgressDialog loadingBar;

    private DatabaseReference rootRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_profile);

        rootRef = FirebaseDatabase.getInstance().getReference();
        init();
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            mFileTemp = new File(Environment.getExternalStorageDirectory(), TEMP_PHOTO_FILE_NAME);
        } else {
            mFileTemp = new File(getFilesDir(), TEMP_PHOTO_FILE_NAME);
        }

        imgAvt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                photoPickerIntent.setType("image/*");
                startActivityForResult(photoPickerIntent, REQUEST_CODE_IMG);

            }
        });
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edt_username.getText().toString().isEmpty()) {
                    Toast.makeText(UpdateProfileActivity.this, "Bạn chưa nhập tên", Toast.LENGTH_SHORT).show();
                } else {
                    loadingBar.setTitle("Đang hoàn tất...");
                    loadingBar.setMessage("Vui lòng chờ trong giây lát!");
                    loadingBar.setCanceledOnTouchOutside(true);
                    loadingBar.show();
                    UserProfileChangeRequest.Builder builder = new UserProfileChangeRequest.Builder();
                    builder.setDisplayName(edt_username.getText().toString());

                    if (mFileTemp != null) {
                        builder.setPhotoUri(getImageUri(UpdateProfileActivity.this,BitmapFactory.decodeFile(mFileTemp.getPath())));
                    }
                    FirebaseAuth.getInstance().getCurrentUser().updateProfile(builder.build()).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                            String currentUserID = FirebaseAuth.getInstance().getCurrentUser().getUid();
                            String img = FirebaseAuth.getInstance().getCurrentUser().getPhotoUrl().toString();
                            String name = FirebaseAuth.getInstance().getCurrentUser().getDisplayName();
                            String email = FirebaseAuth.getInstance().getCurrentUser().getEmail();
                            String pass = FirebaseAuth.getInstance().getCurrentUser().getUid();

                            Users user = new Users(name,email,pass,img);
                            rootRef.child("Users").child(currentUserID).setValue(user);
                            loadingBar.dismiss();
                            Intent intent = new Intent(UpdateProfileActivity.this, HomeActivity.class);
                            startActivity(intent);
                            Toast.makeText(UpdateProfileActivity.this, "Xin chào: "+FirebaseAuth.getInstance().getCurrentUser().getDisplayName(), Toast.LENGTH_SHORT).show();

                        }
                    });

                }
            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK) {

            return;
        }

        Bitmap bitmap;

        switch (requestCode) {

            case 1:

                try {

                    InputStream inputStream = getContentResolver().openInputStream(data.getData());
                    FileOutputStream fileOutputStream = new FileOutputStream(mFileTemp);
                    copyStream(inputStream, fileOutputStream);
                    fileOutputStream.close();
                    inputStream.close();


                } catch (Exception e) {


                }
                bitmap = BitmapFactory.decodeFile(mFileTemp.getPath());
                imgAvt.setImageBitmap(bitmap);

                break;
        }

    }

    private void init() {
        loadingBar = new ProgressDialog(this);
        edt_username = findViewById(R.id.edt_usernameProfile);
        btnSave = findViewById(R.id.btn_SaveProfile);
        imgAvt = findViewById(R.id.imgProfile);
    }
    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }


}
