package fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.os.shoppiapp.R;

import java.util.ArrayList;

import adapters.Tag_Adapter;
import models.Model_Tag;

public class TagFragment extends Fragment {
    String[] text = {"WHAT’S NEW","TOP SHOES","DẠO PHỐ","HỌC ĐƯỜNG","CÔNG SỞ"};
    private Tag_Adapter homepageAdapter;

    private RecyclerView recyclerview;
    private ArrayList<Model_Tag> todotodaytaskModelArrayList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_tag, container, false);

        recyclerview = view.findViewById(R.id.lvTag);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerview.setLayoutManager(layoutManager);
        recyclerview.setItemAnimator(new DefaultItemAnimator());

        todotodaytaskModelArrayList = new ArrayList<>();

        for (int i = 0; i < text.length; i++) {
            Model_Tag view1 = new Model_Tag(text[i]);
            todotodaytaskModelArrayList.add(view1);
        }
        homepageAdapter = new Tag_Adapter(getContext(), todotodaytaskModelArrayList);
        recyclerview.setAdapter(homepageAdapter);



        return view;
    }
}
