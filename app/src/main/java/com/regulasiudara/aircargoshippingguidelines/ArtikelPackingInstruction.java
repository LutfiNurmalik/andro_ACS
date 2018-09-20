package com.regulasiudara.aircargoshippingguidelines;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class ArtikelPackingInstruction extends AppCompatActivity {

    String judul,isi, konten;
    TextView judulTV, kontenTV, isiTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_artikel_packing_instruction);

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
        kontenTV = (TextView) findViewById(R.id.isi);
        judulTV.setText(judul);
        kontenTV.setText(Html.fromHtml(konten, new ImageGetter(this), null));
    }
}
