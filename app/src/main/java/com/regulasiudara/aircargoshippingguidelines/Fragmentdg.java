package com.regulasiudara.aircargoshippingguidelines;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

public class Fragmentdg extends Fragment {
    public Fragmentdg(){}
    LinearLayout view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = (LinearLayout) inflater.inflate(R.layout.fragmentdg, container, false);

        getActivity().setTitle("Dangerous Goods");

        final Button move_classification = (Button) view.findViewById(R.id.pindah_classification);
        move_classification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent move_classification = new Intent(getActivity() , Classification.class);
                getActivity().startActivity(move_classification);
            }
        });

        Button move_segregation = (Button) view.findViewById(R.id.pindah_segregation);
        move_segregation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent move_segregation = new Intent(getActivity() , Informationsegregation.class);
                getActivity().startActivity(move_segregation);
            }
        });

        return view;
    }
}
