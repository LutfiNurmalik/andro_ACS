package com.regulasiudara.aircargoshippingguidelines;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
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
import java.util.HashMap;

public class Coba extends AppCompatActivity {

    private TextView txtjudul, txtisi;

    private RequestQueue requestQueue;
    private StringRequest stringRequest;

    ArrayList<HashMap<String, String>> list_data;

    private WebView webLink = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coba);

//        String url = "http://192.168.42.108/test/getdata.php";
//
//        txtjudul = (TextView) findViewById(R.id.judul);
//
//        txtisi = (TextView) findViewById(R.id.isi);
//
//        requestQueue = Volley.newRequestQueue(Coba.this);
//
//        list_data = new ArrayList<HashMap<String, String>>();
//
//        stringRequest = new
//
//                StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
//            @Override
//            public void onResponse(String response) {
//                try {
//                    JSONObject jsonObject = new JSONObject(response);
//                    JSONArray jsonArray = jsonObject.getJSONArray("classification");
//                    for (int a = 0; a < jsonArray.length(); a++) {
//                        JSONObject json = jsonArray.getJSONObject(a);
//                        HashMap<String, String> map = new HashMap<String, String>();
//                        map.put("judul", json.getString("judul"));
//                        map.put("konten", json.getString("konten"));
//                        list_data.add(map);
//                    }
//                    txtjudul.setText(list_data.get(0).get("judul"));
//                    txtisi.setText(list_data.get(0).get("konten"));
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//            }
//        }, new Response.ErrorListener()
//
//        {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                Toast.makeText(Coba.this, error.getMessage(), Toast.LENGTH_SHORT).show();
//            }
//        });
//
//        requestQueue.add(stringRequest);


        WebView weblink = (WebView)findViewById(R.id.webviewCoba);
        WebSettings webSettings = weblink.getSettings();
        webSettings.setJavaScriptEnabled(true);

        WebViewClientImpl webViewClient = new WebViewClientImpl(this);
        weblink.setWebViewClient(webViewClient);

        weblink.loadUrl("http://127.0.0.1/test/getdata.php");
    }

    private class WebViewClientImpl extends WebViewClient {
        private Activity activity = null;

        public WebViewClientImpl(Activity activity) {
            this.activity = activity;
        }
    }}

//        @Override
//        public boolean shouldOverrideUrlLoading(WebView webView, String url) {
//            if(url.indexOf("www.dgregulations.com") > -1 ) return false;
//
//            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
//            activity.startActivity(intent);
//            return true;
//        }
//    }
//
//    @Override
//    public boolean onKeyDown(int keyCode, KeyEvent event) {
//        if ((keyCode == KeyEvent.KEYCODE_BACK) && this.webLink.canGoBack()) {
//            this.webLink.goBack();
//            return true;
//        }
//
//        return super.onKeyDown(keyCode, event);
//    }
