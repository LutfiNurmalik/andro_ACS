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
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class PackingInstructionAct extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener,
        SearchView.OnQueryTextListener, SwipeRefreshLayout.OnRefreshListener {

    private String urlJsonObj = Server.URL +"getlist_pi.php";
    private String cari_pi = Server.URL +"cari_data.php";
    private RecyclerView recyclerView;
    private ArticleAdapter adapter;


    List<DataModel> listData;
    CariAdapter cariAdapter;

    private Context context = PackingInstructionAct.this;
    List<ArticleModel> articleModelList;
    SwipeRefreshLayout swipe;
    private ProgressDialog pDialog;
    private String TAG = PackingInstructionAct.class.getSimpleName();
    TextView judul, subJudul;
    ImageView header;

    String username;
    SharedPreferences sharedpreferences;
    public static final String TAG_USERNAME = "username";

    String tag_json_obj = "json_obj_req";


    public static final String TAG_NAMA = "judul";
    public static final String TAG_RESULTS = "results";
    public static final String TAG_MESSAGE = "message";
    public static final String TAG_VALUE = "value";

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

        swipe = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        adapter = new ArticleAdapter(articleModelList, context);
        recyclerView.setAdapter(adapter);

//        cariAdapter = new CariAdapter()

        swipe.setOnRefreshListener(this);
//        swipe.setRefreshing(false);

        swipe.post(new Runnable() {
                       @Override
                       public void run() {
                           swipe.setRefreshing(true);
                           makeJsonObjectRequest();
                       }
                   }
        );

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

        RecyclerView recyclerCari = findViewById(R.id.recycler_view);

//        listData = new ArrayList<>();
//        adapter_cari = new ArticleAdapter(PackingInstructionAct.this, listData);
//        recyclerCari.setAdapter(adapter_cari);

        sharedpreferences = getSharedPreferences(Login.my_shared_preferences, Context.MODE_PRIVATE);
        username = getIntent().getStringExtra(TAG_USERNAME);
        navUsername.setText(username);

        final SwipeRefreshLayout swipe = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh);

        swipe.setOnRefreshListener(this);

        swipe.post(new Runnable() {
                       @Override
                       public void run() {
                           swipe.setRefreshing(true);
                           makeJsonObjectRequest();
                       }
                   }
        );

        judul = (TextView) findViewById(R.id.judul);
        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Please wait...");
        pDialog.setCancelable(false);
        showpDialog();
        makeJsonObjectRequest();
    }

    private void makeJsonObjectRequest() {

        articleModelList.clear();
        adapter.notifyDataSetChanged();
        swipe.setRefreshing(false);

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

//                VolleyLog.e(TAG, "Error: " + error.getMessage());
//                Toast.makeText(PackingInstructionAct.this, error.getMessage(), Toast.LENGTH_LONG).show();
//                swipe.setRefreshing(false);
            }
        });
        AppController.getInstance().addToRequestQueue(jsonObjReq);
    }

    @Override
    public void onRefresh() {
        makeJsonObjectRequest();
    }

//    @Override
//    public boolean onQueryTextSubmit(String query) {
//        cariData(query);
//        return false;
//    }
//
//    @Override
//    public boolean onQueryTextChange(String newText) {
//        return false;
//    }

    @Override
    public boolean onCreateOptionsMenu(android.view.Menu menu) {
        getMenuInflater().inflate(R.menu.main_cari, menu);
        final MenuItem item = menu.findItem(R.id.action_search);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(item);
        searchView.setQueryHint(getString(R.string.type_name));
        searchView.setIconified(true);
        searchView.setOnQueryTextListener(this);
        return true;
    }

    private void cariData(final String keyword) {
        pDialog = new ProgressDialog(PackingInstructionAct.this);
        pDialog.setCancelable(false);
        pDialog.setMessage("Loading...");
        pDialog.show();

        StringRequest strReq = new StringRequest(Request.Method.POST, cari_pi, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.e("Response: ", response.toString());

                try {
                    JSONObject jObj = new JSONObject(response);

                    int value = jObj.getInt(TAG_VALUE);

                    if (value == 1) {
                        articleModelList.clear();
                        cariAdapter.notifyDataSetChanged();


                        String getObject = jObj.getString("result");
                        JSONArray jsonArray = new JSONArray(getObject);

                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject c = jsonArray.getJSONObject(i);

                            DataModel articleData = new DataModel();

                            articleData.setJudul(c.getString(TAG_NAMA));

                            listData.add(articleData);
                        }

                    } else {
                        Toast.makeText(getApplicationContext(), jObj.getString(TAG_MESSAGE), Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    // JSON error
                    e.printStackTrace();
                }

                cariAdapter.notifyDataSetChanged();
                pDialog.dismiss();
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.e(TAG, "Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                pDialog.dismiss();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url
                Map<String, String> params = new HashMap<String, String>();
                params.put("keyword", keyword);

                return params;
            }

        };

        AppController.getInstance().addToRequestQueue(strReq, tag_json_obj);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
//            super.onBackPressed();
            Intent home = new Intent(PackingInstructionAct.this, MainActivity.class);
            home.putExtra(TAG_USERNAME, username);
            finish();
            startActivity(home);
        }
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.main, menu);
//        return true;
//    }

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
                intent1.putExtra(TAG_USERNAME, username);
                finish();
                startActivity(intent1);
                break;
            case R.id.nav_dg:
                Intent intent2 = new Intent(PackingInstructionAct.this, DangerousGoodsAct.class);
                intent2.putExtra(TAG_USERNAME, username);
                finish();
                startActivity(intent2);
                break;
            case R.id.nav_psn:
                Intent intent3 = new Intent(PackingInstructionAct.this, ProperShippingNameAct.class);
                intent3.putExtra(TAG_USERNAME, username);
                finish();
                startActivity(intent3);
                break;
            case R.id.nav_pi:
                break;
            case R.id.nav_limitation:
                Intent intent5 = new Intent(PackingInstructionAct.this, LimitationAct.class);
                intent5.putExtra(TAG_USERNAME, username);
                finish();
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

    @Override
    public boolean onQueryTextSubmit(String query) {
        cariData(query);
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }
}
