package com.regulasiudara.aircargoshippingguidelines;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class PackingInstructionAct extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private String urlJsonObj = Server.URL +"getlist_pi.php";
    private RecyclerView recyclerView;
    private ArticleAdapter adapter;
    private Context context = PackingInstructionAct.this;
    List<ArticleModel> articleModelList;
    private ProgressDialog pDialog;
    private static String TAG = PackingInstructionAct.class.getSimpleName();
    TextView judul, subJudul;
    ImageView header;

    String username;
    SharedPreferences sharedpreferences;
    public static final String TAG_USERNAME = "username";

    public boolean isOnline() {
        ConnectivityManager conMgr = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = conMgr.getActiveNetworkInfo();
        if(netInfo == null || !netInfo.isConnected() || !netInfo.isAvailable()){
            Toast.makeText(context, "Check Your Internet Connection!!!", Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_packing_instruction);

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
        navUsername.setText("Username : " + username);

        judul = (TextView) findViewById(R.id.judul);
        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Please wait...");
        pDialog.setCancelable(false);
        showpDialog();
        makeJsonObjectRequest();
    }
    private void makeJsonObjectRequest() {
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                urlJsonObj, null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                Log.d(TAG, response.toString());
                try {
                    JSONArray result = response.getJSONArray("result");
                    for (int i = 0; i < result.length(); i++) {
                        JSONObject c = result.getJSONObject(i);
                        ArticleModel articleData = new ArticleModel();
                        articleData.judul = c.getString("judul");
                        articleData.link = c.getString("link");
                        articleData.konten = c.getString("konten");
                        articleModelList.add(articleData);
                    }
                    recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
                    adapter = new ArticleAdapter(articleModelList, context);
                    recyclerView.setLayoutManager(new LinearLayoutManager(context));
                    recyclerView.setItemAnimator(new DefaultItemAnimator());
                    recyclerView.setAdapter(adapter);
                    recyclerView.addOnItemTouchListener(new CustomTouchListener(context, new onItemClickListener() {
                        @Override
                        public void onClick(View view, int index) {
                            Intent intent = new Intent(context, ArtikelPackingInstruction.class);
                            intent.putExtra("judul", articleModelList.get(index).judul);
                            intent.putExtra("konten", articleModelList.get(index).konten);
                            context.startActivity(intent);
                        }
                    }));
                }
                catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(),
                            "Error: " + e.getMessage(),
                            Toast.LENGTH_LONG).show();
                }
                hidepDialog();
                isOnline();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                hidepDialog();
                isOnline();
            }
        });
        AppController.getInstance().addToRequestQueue(jsonObjReq);
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
            SharedPreferences.Editor editor = sharedpreferences.edit();
            editor.putBoolean(Login.session_status, false);
            editor.putString(TAG_USERNAME, null);
            editor.commit();

            Intent intent = new Intent(PackingInstructionAct.this, Login.class);
            finish();
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_home:
                Intent intent1 = new Intent(PackingInstructionAct.this, MainActivity.class);
                startActivity(intent1);
                break;
            case R.id.nav_dg:
                Intent intent2 = new Intent(PackingInstructionAct.this, DangerousGoodsAct.class);
                startActivity(intent2);
                break;
            case R.id.nav_psn:
                Intent intent3 = new Intent(PackingInstructionAct.this, ProperShippingNameAct.class);
                startActivity(intent3);
                break;
            case R.id.nav_pi:
                break;
            case R.id.nav_limitation:
                Intent intent5 = new Intent(PackingInstructionAct.this, LimitationAct.class);
                startActivity(intent5);
                break;
//            case R.id.nav_about:
//                Intent intent6 = new Intent(PackingInstructionAct.this, AboutAct.class);
//                startActivity(intent6);
//                break;
//            case R.id.nav_contact:
//                Intent intent7 = new Intent(PackingInstructionAct.this, ContactAct.class);
//                startActivity(intent7);
//                break;
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    private void showpDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }
    private void hidepDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }
}
