package com.regulasiudara.aircargoshippingguidelines;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
    String unid, psn, username ;
    TextView txtunID,txtpsn,txtclass,txthazard,txtpg,txtpa_pi,txtpa_net_qty,txtcao_pi,txtcao_net_qty, txtsp,txterg;
    private ProgressDialog pd;

    private Context context = ProperShippingNameAct.this;

    List<ArticleModel> articleModelList;
    SharedPreferences sharedpreferences;
    public static final String TAG_USERNAME = "username";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_proper_shipping_name);

        sharedpreferences = getSharedPreferences(Login.my_shared_preferences, Context.MODE_PRIVATE);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        View headerView = navigationView.getHeaderView(0);
        navigationView.setNavigationItemSelectedListener(this);
        TextView navUsername = (TextView) headerView.findViewById(R.id.txt_account);
        articleModelList = new ArrayList<>();

        sharedpreferences = getSharedPreferences(Login.my_shared_preferences, Context.MODE_PRIVATE);
        username = getIntent().getStringExtra(TAG_USERNAME);
        navUsername.setText(username);

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

                psn = etPSN.getText().toString().trim();

                getSqlDetails();
            }
        });
    }


    private void getSqlDetails() {

        String url= Server.URL + "getlist_psn.php?psn="+psn;
        StringRequest stringRequest = new StringRequest(Request.Method.GET,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {

                            JSONArray jsonarray = new JSONArray(response);


                            for(int i=0; i < jsonarray.length(); i++) {

                                JSONObject jsonobject = jsonarray.getJSONObject(i);


                                String un_id = jsonobject.getString("un_id").trim();
                                String psn = jsonobject.getString("psn").trim();
                                String classPsn = jsonobject.getString("class").trim();
                                String hazard = jsonobject.getString("hazard").trim();
                                String pg = jsonobject.getString("pg").trim();
                                String pa_pi = jsonobject.getString("pa_pi").trim();
                                String pa_net_qty = jsonobject.getString("pa_net_qty").trim();
                                String cao_pi = jsonobject.getString("cao_pi").trim();
                                String cao_net_qty = jsonobject.getString("cao_net_qty").trim();
                                String sp = jsonobject.getString("sp").trim();
                                String erg = jsonobject.getString("erg").trim();

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

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
//            super.onBackPressed();
            Intent home = new Intent(ProperShippingNameAct.this, MainActivity.class);
            home.putExtra(TAG_USERNAME, username);
            finish();
            startActivity(home);
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
            SharedPreferences.Editor editor = sharedpreferences.edit();
            editor.putBoolean(Login.session_status, false);
            editor.putString(TAG_USERNAME, null);
            editor.commit();

            Intent intent = new Intent(ProperShippingNameAct.this, Login.class);
            finish();
            startActivity(intent);
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
                intent1.putExtra(TAG_USERNAME, username);
                finish();
                startActivity(intent1);
                break;
            case R.id.nav_dg:
                Intent intent2 = new Intent(ProperShippingNameAct.this, DangerousGoodsAct.class);
                intent2.putExtra(TAG_USERNAME, username);
                finish();
                startActivity(intent2);
                break;
            case R.id.nav_psn:
                break;
            case R.id.nav_pi:
                Intent intent4 = new Intent(ProperShippingNameAct.this, PackingInstructionAct.class);
                intent4.putExtra(TAG_USERNAME, username);
                finish();
                startActivity(intent4);
                break;
            case R.id.nav_limitation:
                Intent intent5 = new Intent(ProperShippingNameAct.this, LimitationAct.class);
                intent5.putExtra(TAG_USERNAME, username);
                finish();
                startActivity(intent5);
                break;
//            case R.id.nav_about:
//                Intent intent6 = new Intent(ProperShippingNameAct.this, AboutAct.class);
//                startActivity(intent6);
//                break;
//            case R.id.nav_contact:
//                Intent intent7 = new Intent(ProperShippingNameAct.this, ContactAct.class);
//                startActivity(intent7);
//                break;
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
