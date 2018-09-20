package com.regulasiudara.aircargoshippingguidelines;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SegregationAct extends AppCompatActivity {

    private TextView judulTV, kontenTV;

    private RequestQueue requestQueue;
    private StringRequest stringRequest;

    private Context context = SegregationAct.this;

    ArrayList<HashMap<String, String>> list_data;


    public boolean isOnline() {
        ConnectivityManager conMgr = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = conMgr.getActiveNetworkInfo();
        if(netInfo == null || !netInfo.isConnected() || !netInfo.isAvailable()){
            Toast.makeText(context, "PERIKSA KONEKSI INTERNET ANDA!!!", Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }

    String judul,isi, konten;
    ImageView gambarIV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_segregation);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar2);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        String url = "http://192.168.43.225/test/getlist_segregation.php";

//        Intent intent = getIntent();
//        judul = intent.getStringExtra("judul");
//        konten = intent.getStringExtra("konten");
        judulTV = (TextView) findViewById(R.id.judul);
        kontenTV = (TextView) findViewById(R.id.isi);
//        judulTV.setText(judul);
//        kontenTV.setText(Html.fromHtml(konten, new ImageGetter(this), null));

        requestQueue = Volley.newRequestQueue(SegregationAct.this);

        list_data = new ArrayList<HashMap<String, String>>();

        stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("segregation");
                    for (int a = 0; a < jsonArray.length(); a ++){
                        JSONObject json = jsonArray.getJSONObject(a);
                        HashMap<String, String> map  = new HashMap<String, String>();
                        map.put("judul", json.getString("judul"));
                        map.put("konten", json.getString("konten"));
                        list_data.add(map);
                    }
                    judulTV.setText(list_data.get(0).get("judul"));
                    kontenTV.setText(Html.fromHtml(list_data.get(0).get("konten")));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(SegregationAct.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        requestQueue.add(stringRequest);
    }
}