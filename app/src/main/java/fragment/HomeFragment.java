package fragment;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.os.shoppiapp.HomeActivity;
import com.example.os.shoppiapp.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.willowtreeapps.spruce.Spruce;
import com.willowtreeapps.spruce.animation.DefaultAnimations;
import com.willowtreeapps.spruce.sort.DefaultSort;

import java.util.ArrayList;

import adapters.Home_Adapter;
import models.Model_Home;

public class HomeFragment extends Fragment {

    private DatabaseReference mDatabase;

    private TextView tv_adidas, tv_nike,tv_lacoste, tv_puma, tv_parada;
    int totalAdidas = 0,totalNike = 0,totallacoste = 0,totalpuma = 0,totalparada = 0;

    private RecyclerView recyclerViewNew;
    private RecyclerView recyclerViewTop;
    private Home_Adapter home_adapter;
    private ArrayList<Model_Home> home_model;

    private ProgressDialog loadingBar;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home, container, false);

        init(view);

        mDatabase = FirebaseDatabase.getInstance().getReference().child("shoes");

        //recyclerviewTop code:
        RecyclerView.LayoutManager layoutManagerTop = new StaggeredGridLayoutManager(3,StaggeredGridLayoutManager.HORIZONTAL){
            @Override
            public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
                super.onLayoutChildren(recycler, state);
                Animator spruceAnimator = new Spruce
                        .SpruceBuilder(recyclerViewTop)
                        .sortWith(new DefaultSort(/*interObjectDelay=*/50L))
                        .animateWith(DefaultAnimations.shrinkAnimator(recyclerViewTop, /*duration=*/800),
                                ObjectAnimator.ofFloat(recyclerViewTop,"translationX",-recyclerViewTop.getWidth(),0f).setDuration(800))
                        .start();
            }
        };
        recyclerViewTop.setLayoutManager(layoutManagerTop);
        recyclerViewTop.setItemAnimator(new DefaultItemAnimator());

        //recyclerviewNew code:
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false){
            @Override
            public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
                super.onLayoutChildren(recycler, state);
                Animator spruceAnimator = new Spruce
                        .SpruceBuilder(recyclerViewNew)
                        .sortWith(new DefaultSort(/*interObjectDelay=*/50L))
                        .animateWith(DefaultAnimations.shrinkAnimator(recyclerViewNew, /*duration=*/800),
                                ObjectAnimator.ofFloat(recyclerViewNew,"translationX",-recyclerViewNew.getWidth(),0f).setDuration(800))
                        .start();
            }
        };
        recyclerViewNew.setLayoutManager(layoutManager);
        recyclerViewNew.setItemAnimator(new DefaultItemAnimator());

        home_model = new ArrayList<>();

        loadingBar.setMessage("Please waiting...");
        loadingBar.setCanceledOnTouchOutside(false);
        loadingBar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        loadingBar.show();

        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                home_model.clear();
                for(DataSnapshot artistSnapshot : dataSnapshot.getChildren()){
                    Model_Home model_home = artistSnapshot.getValue(Model_Home.class);

                    if(model_home.getTheloai().equals("Adidas")){
                        totalAdidas +=1;
                    }
                    if(model_home.getTheloai().equals("Nike")){
                        totalNike +=1;
                    }
                    if(model_home.getTheloai().equals("Lacoste")){
                        totallacoste +=1;
                    }
                    if(model_home.getTheloai().equals("Puma")){
                        totalpuma +=1;
                    }
                    if(model_home.getTheloai().equals("Parada")){
                        totalparada +=1;
                    }
                    home_model.add(model_home);
                }

                tv_adidas.setText(String.valueOf(totalAdidas)+"+ items");
                tv_nike.setText(String.valueOf(totalNike)+"+ items");
                tv_lacoste.setText(String.valueOf(totallacoste)+"+ items");
                tv_puma.setText(String.valueOf(totalpuma)+"+ items");
                tv_parada.setText(String.valueOf(totalparada)+"+ items");

                home_adapter = new Home_Adapter(getActivity(), home_model);
                home_adapter.notifyDataSetChanged();
                recyclerViewNew.setAdapter(home_adapter);
                recyclerViewTop.setAdapter(home_adapter);

                loadingBar.dismiss();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



        return view;
    }

    private void init(View view) {
        loadingBar = new ProgressDialog(getContext());
        recyclerViewNew = view.findViewById(R.id.lvNew);
        recyclerViewTop = view.findViewById(R.id.lvTop);
        tv_adidas = view.findViewById(R.id.tv_adidas);
        tv_nike = view.findViewById(R.id.tv_nike);
        tv_lacoste = view.findViewById(R.id.tv_Lacoste);
        tv_puma = view.findViewById(R.id.tv_puma);
        tv_parada = view.findViewById(R.id.tv_Parada);
    }

    @Override
    public void onStart() {
        super.onStart();

    }
}
