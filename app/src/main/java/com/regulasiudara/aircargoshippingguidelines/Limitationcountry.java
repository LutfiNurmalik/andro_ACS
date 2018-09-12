package com.regulasiudara.aircargoshippingguidelines;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.regulasiudara.aircargoshippingguidelines.lim_country.Nepal;
import com.regulasiudara.aircargoshippingguidelines.lim_country.Peru;
import com.regulasiudara.aircargoshippingguidelines.lim_country.Poland;
import com.regulasiudara.aircargoshippingguidelines.lim_country.Spain;
import com.regulasiudara.aircargoshippingguidelines.lim_country.Ukraine;

import org.apache.http.NameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Limitationcountry extends ListActivity {

    // Progress Dialog
    private ProgressDialog pDialog;

    // buat json object
    JSONParser jParser = new JSONParser();

    ArrayList<HashMap<String, String>> semuaList;

    String url_semua = "";

    // JSON Node
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_DATA = "data";
    private static final String TAG_MBL = "judul";

    // pendaftaran JSONArray
    JSONArray jmobil = null;
    String isi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_limitationcountry);

        url_semua = "http://192.168.2.103/dbpariwisata/getlist_country.php";
        // Hashmap untuk ListView
        semuaList = new ArrayList<HashMap<String, String>>();

        // Loading products in Background Thread
        new Limitationcountry.LoadSemua().execute();

        // Get listview
        ListView lv = getListView();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // jika kode hasil sama dengan 100
        if (resultCode == 100) {

            Intent intent = getIntent();
            finish();
            startActivity(intent);
        }

    }


    class LoadSemua extends AsyncTask<String, String, String> {

        /**
         * sebelum melakukan thread di background maka jalankan progres dialog
         */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(Limitationcountry.this);
            pDialog.setMessage("Mohon tunggu, Loading Data...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        /**
         * dapetkan semua produk dari get url di background
         */
        protected String doInBackground(String... args) {
            // Buat Parameter
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            // ambil json dari url
            JSONObject json = jParser.makeHttpRequest(url_semua, "GET", params);

            // cek logcat untuk response dari json
            Log.d("Semua  : ", json.toString());

            try {
                // cek jika tag success
                int success = json.getInt(TAG_SUCCESS);

                if (success == 1) {
                    // data ditemukan
                    jmobil = json.getJSONArray(TAG_DATA);

                    // tampilkan perulangan semua produk
                    for (int i = 0; i < jmobil.length(); i++) {
                        JSONObject c = jmobil.getJSONObject(i);

                        // simpan pada variabel
                        String mb = c.getString(TAG_MBL);

                        // buat new hashmap
                        HashMap<String, String> map = new HashMap<String, String>();

                        // key => value
                        map.put(TAG_MBL, mb);

                        // masukan HashList ke ArrayList
                        semuaList.add(map);
                    }
                } else {
                    // jika tidak ada data
                    // tutup semua proses sebelumnya

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }


        protected void onPostExecute(String file_url) {
            // hentikan progress ketika semua data didapat
            pDialog.dismiss();
            // perbarui screen
            runOnUiThread(new Runnable() {
                public void run() {

                    ListAdapter adapter = new SimpleAdapter(Limitationcountry.this,
                            semuaList, R.layout.list, new String[]{TAG_MBL},
                            new int[]{R.id.mb});
                    setListAdapter(adapter);
                }
            });

        }
    }
}
