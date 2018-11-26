package fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.os.shoppiapp.OrderActivity;
import com.example.os.shoppiapp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import adapters.Cart_Adapter;
import models.Model_Cart;


public class CartFragment extends Fragment {

    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mRef;
    private FirebaseAuth mAuth;

    private RecyclerView recyclerView;
    private Cart_Adapter cart_adapter;
    private ArrayList<Model_Cart> cart_model;

    TextView tv_total;
    int total = 0;
    LinearLayout lnOrder;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_cart, container, false);

        tv_total = view.findViewById(R.id.txttotal);
        lnOrder = view.findViewById(R.id.lnOrder);

        lnOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (total>0) {
                    Intent intentOrder = new Intent(getActivity(), OrderActivity.class);
                    startActivity(intentOrder);
                } else {
                    Toast.makeText(getActivity(), "Bạn không có đơn hàng nào", Toast.LENGTH_SHORT).show();
                }

            }
        });

        mAuth = FirebaseAuth.getInstance();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        String currentUserID = mAuth.getCurrentUser().getUid();
        mRef = mFirebaseDatabase.getReference("Carts").child(currentUserID);

        recyclerView = view.findViewById(R.id.lvCart);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        cart_model = new ArrayList<>();

        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                cart_model.clear();
                for (DataSnapshot artistSnapshot : dataSnapshot.getChildren()) {
                    Model_Cart model_cart = artistSnapshot.getValue(Model_Cart.class);
                    cart_model.add(model_cart);
                }
                cart_adapter = new Cart_Adapter(getActivity(), cart_model);
                recyclerView.setAdapter(cart_adapter);

                recyclerView.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        return false;
                    }
                });

                //Xét sự kiện xóa item
                cart_adapter.setOnItemClickedListener(new Cart_Adapter.OnItemClickedListener() {
                    @Override
                    public void onItemClick(String id) {
                        mRef.child(id).removeValue();

                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                total =0;
                for (DataSnapshot artistSnapshot : dataSnapshot.getChildren()) {
                    Model_Cart model_cart = artistSnapshot.getValue(Model_Cart.class);
                    total = total + Integer.parseInt(model_cart.getGia().substring(1));
                }
                tv_total.setText("TOTAL: $ "+total);
                Log.d("cvbnm", total + "");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
