package com.example.os.shoppiapp;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class Infoproductpage extends Fragment {
    private TextView tvName, tvDetail;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.item_info, container, false);
        tvName = view.findViewById(R.id.nameDetail);
        tvDetail = view.findViewById(R.id.infoDetail);

        tvName.setText(getActivity().getIntent().getStringExtra("ten"));
        tvDetail.setText(getActivity().getIntent().getStringExtra("thongtin"));

        return view;
    }
}
