package com.regulasiudara.aircargoshippingguidelines;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

public class Fragmentabout extends Fragment {
    public Fragmentabout(){}
    LinearLayout view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = (LinearLayout) inflater.inflate(R.layout.fragmentabout, container, false);

        getActivity().setTitle("About");

        return view;
    }
}
