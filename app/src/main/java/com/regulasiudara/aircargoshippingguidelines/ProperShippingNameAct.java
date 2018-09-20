package com.regulasiudara.aircargoshippingguidelines;

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
import android.widget.Toast;

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

public class ProperShippingNameAct extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener{

    public Button tcari;
    public EditText unID;
    public EditText etPSN;
    public static String JSON_URL ;

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

        tcari.setOnClickListener(this);
    }

    private void sendRequest(){

        StringRequest stringRequest = new StringRequest(JSON_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        showJSON(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(ProperShippingNameAct.this,error.toString(),Toast.LENGTH_LONG).show();
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }

    public void showJSON(String json){
        String un_id="";
        String psn="";
        String classPsn="";
        String hazard= "";
        String pg="";
        String pa_pi="";
        String pa_net_qty="";
        String cao_pi="";
        String cao_net_qty="";
        String sp="";
        String erg="";

        try {
            JSONObject jsonObject = new JSONObject(json);

            JSONArray result = jsonObject.getJSONArray("result");

            if (result.length() < 1){
                Toast.makeText(ProperShippingNameAct.this, "Kode tidak ditemukan", Toast.LENGTH_SHORT).show();
            }

            JSONObject Data = result.getJSONObject(0);

            un_id = Data.getString("un_id");
            psn = Data.getString("psn");
            classPsn = Data.getString("class");
            hazard = Data.getString("hazard");
            pg = Data.getString("pg");
            pa_pi = Data.getString("pa_pi");
            pa_net_qty = Data.getString("pa_net_qty");
            cao_pi = Data.getString("cao_pi");
            cao_net_qty = Data.getString("cao_net_qty");
            sp = Data.getString("sp");
            erg = Data.getString("erg");

            Intent halamanpsn = new Intent(ProperShippingNameAct.this, TampilPSN.class);


            halamanpsn.putExtra("un_id",un_id);
            halamanpsn.putExtra("psn", psn);
            halamanpsn.putExtra("class", classPsn);
            halamanpsn.putExtra("hazard", hazard);
            halamanpsn.putExtra("pg", pg);
            halamanpsn.putExtra("pa_pi", pa_pi);
            halamanpsn.putExtra("pa_net_qty", pa_net_qty);
            halamanpsn.putExtra("cao_pi", cao_pi);
            halamanpsn.putExtra("cao_net_qty", cao_net_qty);
            halamanpsn.putExtra("sp", sp);
            halamanpsn.putExtra("erg", erg);

            startActivity(halamanpsn);

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onClick(View v){

        String getUNID = unID.getText().toString();
        String getPSN = etPSN.getText().toString();

        JSON_URL =  "http://192.168.43.225/test/getlist_pi.php/"+getUNID+"."+getPSN;

        Toast.makeText(ProperShippingNameAct.this, getUNID+getPSN, Toast.LENGTH_LONG );

        switch (v.getId()){
            case R.id.tcari:
                sendRequest();
                break;
        }
    }

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
            case R.id.nav_about:
                Intent intent6 = new Intent(ProperShippingNameAct.this, AboutAct.class);
                startActivity(intent6);
                break;
            case R.id.nav_contact:
                Intent intent7 = new Intent(ProperShippingNameAct.this, ContactAct.class);
                startActivity(intent7);
                break;
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
