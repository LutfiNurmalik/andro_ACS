package com.regulasiudara.aircargoshippingguidelines;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ProperShippingNameAct extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    public Button tcari;
    public EditText unID;
    public EditText etPSN;
    String unid,psn ;
    TextView txtunID,txtpsn,txtclass,txthazard,txtpg,txtpa_pi,txtpa_net_qty,txtcao_pi,txtcao_net_qty, txtsp,txterg;
    private ProgressDialog pd;

    private Context context = ProperShippingNameAct.this;

    List<ArticleModel> articleModelList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_proper_shipping_name);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        articleModelList = new ArrayList<>();

        unID = (EditText) findViewById(R.id.un_id);
        etPSN = (EditText) findViewById(R.id.et_psn);
        tcari = (Button) findViewById(R.id.tcari);

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


        tcari.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                unid = unID.getText().toString().trim();
                psn = etPSN.getText().toString().trim();

                getSqlDetails();
            }
        });
    }
    private void getSqlDetails() {

        String url= "http://192.168.2.103/dbpariwisata/getlist_psn.php";
        StringRequest stringRequest = new StringRequest(Request.Method.GET,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {

                            JSONArray jsonarray = new JSONArray(response);

                            for(int i=0; i < jsonarray.length(); i++) {

                                JSONObject jsonobject = jsonarray.getJSONObject(i);


                                String un_id = jsonobject.getString("un_id");
                                String psn = jsonobject.getString("psn");
                                String classPsn = jsonobject.getString("class");
                                String hazard = jsonobject.getString("hazard");
                                String pg = jsonobject.getString("pg");
                                String pa_pi = jsonobject.getString("pa_pi");
                                String pa_net_qty = jsonobject.getString("pa_net_qty");
                                String cao_pi = jsonobject.getString("cao_pi");
                                String cao_net_qty = jsonobject.getString("cao_net_qty");
                                String sp = jsonobject.getString("sp");
                                String erg = jsonobject.getString("erg");

                                txtunID.setText(un_id);
                                txtpsn.setText(psn);
                                txtclass.setText(classPsn);
                                txthazard.setText(hazard);
                                txtpg.setText(pg);
                                txtpa_pi.setText(pa_pi);
                                txtpa_net_qty.setText(pa_net_qty);
                                txtcao_pi.setText(cao_pi);
                                txtcao_net_qty.setText(cao_net_qty);
                                txtsp.setText(sp);
                                txterg.setText(erg);

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        if(error != null){
                            Toast.makeText(getApplicationContext(), "Something went wrong.", Toast.LENGTH_LONG).show();
                        }
                    }
                }
        );

        MySingleton.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);

    }

//    private void sendRequest(){
//
//        StringRequest stringRequest = new StringRequest(JSON_URL,
//                new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response) {
//                        showJSON(response);
//                    }
//                },
//                new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        Toast.makeText(ProperShippingNameAct.this,error.toString(),Toast.LENGTH_LONG).show();
//                    }
//                });
//
//        RequestQueue requestQueue = Volley.newRequestQueue(this);
//        requestQueue.add(stringRequest);
//
//    }
//
//    public void showJSON(String json){
//        String un_id="";
//        String psn="";
//        String classPsn="";
//        String hazard= "";
//        String pg="";
//        String pa_pi="";
//        String pa_net_qty="";
//        String cao_pi="";
//        String cao_net_qty="";
//        String sp="";
//        String erg="";
//
//        try {
//            JSONObject jsonObject = new JSONObject(json);
//
//            JSONArray result = jsonObject.getJSONArray("result");
//
//            if (result.length() < 1){
//                Toast.makeText(ProperShippingNameAct.this, "Kode tidak ditemukan", Toast.LENGTH_SHORT).show();
//            }
//
//            JSONObject Data = result.getJSONObject(0);
//
//            un_id = Data.getString("un_id");
//            psn = Data.getString("psn");
//            classPsn = Data.getString("class");
//            hazard = Data.getString("hazard");
//            pg = Data.getString("pg");
//            pa_pi = Data.getString("pa_pi");
//            pa_net_qty = Data.getString("pa_net_qty");
//            cao_pi = Data.getString("cao_pi");
//            cao_net_qty = Data.getString("cao_net_qty");
//            sp = Data.getString("sp");
//            erg = Data.getString("erg");
//
//            Intent halamanpsn = new Intent(ProperShippingNameAct.this, TampilPSN.class);
//
//
//            halamanpsn.putExtra("un_id",un_id);
//            halamanpsn.putExtra("psn", psn);
//            halamanpsn.putExtra("class", classPsn);
//            halamanpsn.putExtra("hazard", hazard);
//            halamanpsn.putExtra("pg", pg);
//            halamanpsn.putExtra("pa_pi", pa_pi);
//            halamanpsn.putExtra("pa_net_qty", pa_net_qty);
//            halamanpsn.putExtra("cao_pi", cao_pi);
//            halamanpsn.putExtra("cao_net_qty", cao_net_qty);
//            halamanpsn.putExtra("sp", sp);
//            halamanpsn.putExtra("erg", erg);
//
//            startActivity(halamanpsn);
//
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//
//    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
//            Intent intent6 = new Intent(DangerousGoodsAct.this, AboutUsActivity.class);
//            startActivity(intent6);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_home:
                Intent intent1 = new Intent(ProperShippingNameAct.this, MainActivity.class);
                startActivity(intent1);
                break;
            case R.id.nav_dg:
                Intent intent2 = new Intent(ProperShippingNameAct.this, MainActivity.class);
                startActivity(intent2);
                break;
            case R.id.nav_psn:
                break;
            case R.id.nav_pi:
                Intent intent4 = new Intent(ProperShippingNameAct.this, PackingInstructionAct.class);
                startActivity(intent4);
                break;
            case R.id.nav_limitation:
                Intent intent5 = new Intent(ProperShippingNameAct.this, LimitationAct.class);
                startActivity(intent5);
                break;
//            case R.id.nav_about:
//                Intent intent6 = new Intent(DangerousGoodsAct.this, AboutUsActivity.class);
//                startActivity(intent6);
//                break;
//            case R.id.nav_contact:
//                Intent intent7 = new Intent(DangerousGoodsAct.this, BmiCalcActivity.class);
//                startActivity(intent7);
//                break;
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
