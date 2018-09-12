package com.regulasiudara.aircargoshippingguidelines;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.regulasiudara.aircargoshippingguidelines.classification.Class1;
import com.regulasiudara.aircargoshippingguidelines.classification.Class2;
import com.regulasiudara.aircargoshippingguidelines.classification.Class3;
import com.regulasiudara.aircargoshippingguidelines.classification.Class4;
import com.regulasiudara.aircargoshippingguidelines.classification.Class5;
import com.regulasiudara.aircargoshippingguidelines.classification.Class6;
import com.regulasiudara.aircargoshippingguidelines.classification.Class7;
import com.regulasiudara.aircargoshippingguidelines.classification.Class8;
import com.regulasiudara.aircargoshippingguidelines.classification.Class9;
import com.regulasiudara.aircargoshippingguidelines.classification.Marking;

import org.apache.http.NameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Classification extends ListActivity {

    // Progress Dialog
    private ProgressDialog pDialog_class;

    // buat json object
    private JSONParser jParser = new JSONParser();

    private ArrayList<HashMap<String, String>> semuaList;

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
        setContentView(R.layout.activity_classification);

        url_semua = "http://192.168.2.103/dbpariwisata/getlist_classification.php";
        // Hashmap untuk ListView
        semuaList = new ArrayList<HashMap<String, String>>();

        // Loading products in Background Thread
        new LoadSemua().execute();

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


    public class LoadSemua extends AsyncTask<String, String, String> {

        /**
         * sebelum melakukan thread di background maka jalankan progres dialog
         */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog_class = new ProgressDialog(Classification.this);
            pDialog_class.setMessage("Mohon tunggu, Loading Data...");
            pDialog_class.setIndeterminate(false);
            pDialog_class.setCancelable(false);
            pDialog_class.show();
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
            pDialog_class.dismiss();
            // perbarui screen
            runOnUiThread(new Runnable() {
                public void run() {

                    ListAdapter adapter = new SimpleAdapter(Classification.this,
                            semuaList, R.layout.list, new String[]{TAG_MBL},
                            new int[]{R.id.mb});
                    setListAdapter(adapter);
                }
            });

        }
    }
}
