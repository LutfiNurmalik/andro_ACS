package com.regulasiudara.aircargoshippingguidelines;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

public class Fragmentlimitation extends Fragment {
    public Fragmentlimitation(){}
    LinearLayout view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = (LinearLayout) inflater.inflate(R.layout.fragmentlimitation, container, false);

        getActivity().setTitle("Limitation");

        final Button move_airline = (Button) view.findViewById(R.id.pindah_airline);
        move_airline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent move_airline = new Intent(getActivity() , Limitationairline.class);
                getActivity().startActivity(move_airline);
            }
        });

        Button move_country = (Button) view.findViewById(R.id.pindah_country);
        move_country.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent move_country = new Intent(getActivity() , Limitationcountry.class);
                getActivity().startActivity(move_country);
            }
        });

        return view;
    }
}
