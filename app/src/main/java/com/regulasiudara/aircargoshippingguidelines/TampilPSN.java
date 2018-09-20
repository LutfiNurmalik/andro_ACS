package com.regulasiudara.aircargoshippingguidelines;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

public class TampilPSN extends AppCompatActivity {

    TextView txtunID,txtpsn,txtclass,txthazard,txtpg,txtpa_pi,txtpa_net_qty,txtcao_pi,txtcao_net_qty, txtsp,txterg;



    public int un_id;
    public String psn;
    public String classPsn;
    public String hazard;
    public String pg;
    public int pa_pi;
    public String pa_net_qty;
    public int cao_pi;
    public String cao_net_qty;
    public String sp;
    public String erg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tampil_psn);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar2);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        un_id = this.getIntent().getExtras().getInt("un_id");
        psn = this.getIntent().getExtras().getString("psn");
        classPsn = this.getIntent().getExtras().getString("class");
        hazard= this.getIntent().getExtras().getString("hazard");
        pg = this.getIntent().getExtras().getString("pg");
        pa_pi = this.getIntent().getExtras().getInt("pa_pi");
        pa_net_qty = this.getIntent().getExtras().getString("pa_net_qty");
        cao_pi = this.getIntent().getExtras().getInt("cao_pi");
        cao_net_qty = this.getIntent().getExtras().getString("cao_net_qty");
        sp = this.getIntent().getExtras().getString("sp");
        erg = this.getIntent().getExtras().getString("erg");

        txtunID = findViewById(R.id.txt_UNID);
        txtpsn = findViewById(R.id.txt_psn);
        txtclass = findViewById(R.id.txt_class);
        txthazard = findViewById(R.id.txt_hazard);
        txtpg = findViewById(R.id.txt_PackingGrup);
        txtpa_pi = findViewById(R.id.txt_pa_pi);
        txtpa_net_qty = findViewById(R.id.txt_pa_max_qty);
        txtcao_pi = findViewById(R.id.txt_cao_pi);
        txtcao_net_qty = findViewById(R.id.txt_cao_max_qty);
        txtsp = findViewById(R.id.txt_SP);
        txterg = findViewById(R.id.txt_erg);

        txtunID.setText(un_id);
        txtpsn.setText(psn);
        txthazard.setText(hazard);
        txtpg.setText(pg);
        txtpa_pi.setText(pa_pi);
        txtpa_net_qty.setText(pa_net_qty);
        txtcao_pi.setText(cao_pi);
        txtcao_net_qty.setText(cao_net_qty);
        txtsp.setText(sp);
        txterg.setText(erg);

    }
}
