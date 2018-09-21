package com.regulasiudara.aircargoshippingguidelines;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.View;
import android.webkit.WebView;
import android.widget.TextView;

public class ArtikelLimAirline extends AppCompatActivity {

    String judul,isi, konten;
    TextView judulTV, kontenTV, isiTV;
    WebView isi_web;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_artikel_lim_airline);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar2);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        //deklarasi variabel
        isi_web = findViewById(R.id.isi_web);

        //pengaturan webview
        final String mimeType = "text/html";
        final String encoding = "UTF-8";

        Intent intent = getIntent();
        judul = intent.getStringExtra("link");
        konten = intent.getStringExtra("konten");
        judulTV = (TextView) findViewById(R.id.judul);
        kontenTV = (TextView) findViewById(R.id.isi);
        judulTV.setText(judul);
//        kontenTV.setText(Html.fromHtml(konten, new ImageGetter(this), null));
        // webview
        isi_web.loadDataWithBaseURL("", konten, mimeType, encoding, "");
        //supaya bisa di zoom
        isi_web.getSettings().setBuiltInZoomControls(true);
//        isi_web.getSettings().setUseWideViewPort(true);
    }
}
