package com.regulasiudara.aircargoshippingguidelines;

import android.app.Fragment;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

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
import com.regulasiudara.aircargoshippingguidelines.packing.Pi101;
import com.regulasiudara.aircargoshippingguidelines.packing.Pi114;
import com.regulasiudara.aircargoshippingguidelines.packing.Pi219;
import com.regulasiudara.aircargoshippingguidelines.packing.Piy956;
import com.regulasiudara.aircargoshippingguidelines.packing.Piy964;

import org.apache.http.NameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Fragmentpi extends ListActivity {
//    public Fragmentpi(){}

    public static class Fragmentpacking extends Fragment {

        LinearLayout view;

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {

            view = (LinearLayout) inflater.inflate(R.layout.fragmentpi, container, false);

            getActivity().setTitle("Packing Instruction");

//            final Button move_contact = (Button) view.findViewById(R.id.pindah_contact);
//            move_contact.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    Intent move_contact = new Intent(getActivity() , Contact.class);
//                    getActivity().startActivity(move_contact);
//                }
//            });

            return view;
        }

    }

    private MenuItem activity;

    private LinearLayout parent1;

    ListView Listpacking;
    String[]Packing = new String[]{"Packing Instruction 219", "Packing Instructionn 101",
            "Packaging Instruction Y956", "Packing Instruction 114", "Packaging Instruction Y964"};

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
        setContentView(R.layout.activity_classification);

        url_semua = "http://192.168.2.103/dbpariwisata/getlist_airline.php";
        // Hashmap untuk ListView
        semuaList = new ArrayList<HashMap<String, String>>();

        // Loading products in Background Thread
        new Fragmentpi.LoadSemua().execute();

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
            pDialog = new ProgressDialog(Fragmentpi.this);
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

                    ListAdapter adapter = new SimpleAdapter(Fragmentpi.this,
                            semuaList, R.layout.list, new String[]{TAG_MBL},
                            new int[]{R.id.mb});
                    setListAdapter(adapter);
                }
            });

        }
    }


}
