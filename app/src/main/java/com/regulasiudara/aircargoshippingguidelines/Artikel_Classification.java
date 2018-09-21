package com.regulasiudara.aircargoshippingguidelines;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.Spannable;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class Artikel_Classification extends AppCompatActivity {
    String judul,isi, konten;
    TextView judulTV, kontenTV, isiTV;
    WebView isi_web;
    ImageView gambarIV;
    private Context context = Artikel_Classification.this;
    private ProgressDialog pDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_artikel__classification);

        isi_web = findViewById(R.id.isi_web);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar2);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        Intent intent = getIntent();
        judul = intent.getStringExtra("judul");
        konten = intent.getStringExtra("konten");
        judulTV = (TextView) findViewById(R.id.judul);
//        kontenTV = (TextView) findViewById(R.id.isi);
        final String mimeType = "text/html";
        final String encoding = "UTF-8";
        judulTV.setText(judul);
//        kontenTV.setText(Html.fromHtml(konten, new ImageGetter(this), null));
        isi_web.loadDataWithBaseURL("", konten, mimeType, encoding, "");
        //supaya bisa di zoom
        isi_web.getSettings().setBuiltInZoomControls(true);
//        isi_web.getSettings().setUseWideViewPort(true);

    }
}