package com.regulasiudara.aircargoshippingguidelines;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
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
import android.widget.Button;
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

public class DangerousGoodsAct extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {

    //private String urlJsonObj = "http://192.168.2.103/dbpariwisata/getlist_segregation.php";
    private RecyclerView recyclerView;
    private ArticleAdapter adapter;
    private Context context = DangerousGoodsAct.this;
    List<ArticleModel> articleModelList;
    private ProgressDialog pDialog;
    private static String TAG = DangerousGoodsAct.class.getSimpleName();
    TextView judul, subJudul;
    ImageView header;
    public boolean isOnline() {
        ConnectivityManager conMgr = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = conMgr.getActiveNetworkInfo();
        if(netInfo == null || !netInfo.isConnected() || !netInfo.isAvailable()){
            Toast.makeText(context, "PERIKSA KONEKSI INTERNET ANDA!!!", Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dangerous_goods);

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
        Button bclass = (Button) findViewById(R.id.pindah_classification);
        bclass.setOnClickListener(this);
        Button bsegre = (Button) findViewById(R.id.pindah_segregation);
        bsegre.setOnClickListener(this);

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
                Intent intent1 = new Intent(DangerousGoodsAct.this, MainActivity.class);
                startActivity(intent1);
                break;
            case R.id.nav_dg:
                break;
            case R.id.nav_psn:
                Intent intent3 = new Intent(DangerousGoodsAct.this, ProperShippingNameAct.class);
                startActivity(intent3);
                break;
            case R.id.nav_pi:
                Intent intent4 = new Intent(DangerousGoodsAct.this, PackingInstructionAct.class);
                startActivity(intent4);
                break;
            case R.id.nav_limitation:
                Intent intent5 = new Intent(DangerousGoodsAct.this, LimitationAct.class);
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


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.pindah_classification:
                Intent moveClass = new Intent(DangerousGoodsAct.this, ClassificationAct.class);
                startActivity(moveClass);
                break;

            case R.id.pindah_segregation:
                Intent moveSegre = new Intent(DangerousGoodsAct.this, SegregationAct.class);
                startActivity(moveSegre);
                break;
        }
    }

//    private void showpDialog() {
//        if (!pDialog.isShowing())
//            pDialog.show();
//    }
//    private void hidepDialog() {
//        if (pDialog.isShowing())
//            pDialog.dismiss();
//    }
}