package com.regulasiudara.aircargoshippingguidelines;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

/**
 * Created by Kuncoro on 22/03/2016.
 */
public class Send extends Fragment {

    public Send(){}
    LinearLayout view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = (LinearLayout) inflater.inflate(R.layout.send, container, false);

        getActivity().setTitle("Send");

        return view;
    }
}
