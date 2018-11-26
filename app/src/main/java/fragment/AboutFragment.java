package fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.os.shoppiapp.ManageActivity;
import com.example.os.shoppiapp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;


public class AboutFragment extends Fragment {
    private TextView tv_Name, tv_Email;
    private Button btnManage;
    CircleImageView imageView;

    private FirebaseAuth mAuth;
    private FirebaseUser mUser;
    private String currentUserID;
    private DatabaseReference rootRef;
    private StorageReference userProfileImageRef;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_about, container, false);

        init(view);
        mAuth = FirebaseAuth.getInstance();
        mUser = FirebaseAuth.getInstance().getCurrentUser();
        rootRef = FirebaseDatabase.getInstance().getReference();
        currentUserID = mAuth.getCurrentUser().getUid();

        if (currentUserID.equals("2IvTdbYiLCaozw2H6VW81ncGItJ2")) {
            btnManage.setVisibility(View.VISIBLE);
        }

        getProfileDatabase();

        btnManage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ManageActivity.class);
                startActivity(intent);
            }
        });

        return view;
    }

    private void getProfileDatabase() {
        tv_Name.setText(mUser.getDisplayName());
        tv_Email.setText(mUser.getEmail());
        Picasso.get().load(mUser.getPhotoUrl()).error(R.drawable.img_loading).into(imageView);
    }

    private void init(View view) {
        imageView = view.findViewById(R.id.profile_image_about);
        tv_Email = view.findViewById(R.id.tv_aboutEmail);
        tv_Name = view.findViewById(R.id.tv_aboutName);
        btnManage = view.findViewById(R.id.btn_Admin);
    }
}
