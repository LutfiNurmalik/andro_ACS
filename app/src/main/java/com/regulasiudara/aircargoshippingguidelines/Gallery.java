package com.regulasiudara.aircargoshippingguidelines;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

/**
 * Created by Kuncoro on 22/03/2016.
 */
public class Gallery extends Fragment {

    public Gallery() {
    }

    LinearLayout view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        view = (LinearLayout) inflater.inflate(R.layout.gallery, container, false);

        getActivity().setTitle("Gallery");

        Button move = (Button) view.findViewById(R.id.pindah);
        move.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent move = new Intent(getActivity() , Coba.class);
                getActivity().startActivity(move);
            }
        });

        return view;

    }

//    @Override
//    public void onClick(View view) {
////        switch (view.getId()){
////            case R.id.pindah:
//                Intent move = new Intent(Gallery.this.getActivity(),Send.class);
//                startActivity(move);
//        }
//    }

    //    @Override
//    public void onCreate() {
//        Button move = (Button)

}