package com.regulasiudara.aircargoshippingguidelines;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import com.android.volley.Request;
//import com.android.volley.RequestQueue;
//import com.android.volley.Response;
//import com.android.volley.VolleyError;
//import com.android.volley.toolbox.StringRequest;
//import com.android.volley.toolbox.Volley;
//
//import org.json.JSONArray;
//import org.json.JSONException;
//import org.json.JSONObject;
//
//import java.util.ArrayList;
//import java.util.HashMap;


/**
 * A simple {@link Fragment} subclass.
 */
public class Frag_Class1 extends Fragment {


    public Frag_Class1() {
        // Required empty public constructor
    }


//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_frag_class1, container, false);
//    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View frag_class1 = inflater.inflate(R.layout.fragment_frag_class1, container, false);
        return frag_class1;

//        final class Frag1 extends AppCompatActivity {
//
//            private TextView txtjudul, txtisi;
//
//            private RequestQueue requestQueue;
//            private StringRequest stringRequest;
//
//            ArrayList<HashMap<String, String>> list_data;
//
//            @Override
//                    protected void onCreate(Bundle savedInstanceState) {
//                super.onCreate(savedInstanceState);
//                String url = "http://192.168.42.108/test/getdata.php";
//
//                txtjudul = (TextView) findViewById(R.id.judul);
//
//                txtisi = (TextView) findViewById(R.id.isi);
//
//                requestQueue = Volley.newRequestQueue(Frag1.this);
//
//                list_data = new ArrayList<HashMap<String, String>>();
//
//                stringRequest = new
//
//                        StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response) {
//                        try {
//                            JSONObject jsonObject = new JSONObject(response);
//                            JSONArray jsonArray = jsonObject.getJSONArray("classification");
//                            for (int a = 0; a < jsonArray.length(); a++) {
//                                JSONObject json = jsonArray.getJSONObject(a);
//                                HashMap<String, String> map = new HashMap<String, String>();
//                                map.put("judul", json.getString("judul"));
//                                map.put("konten", json.getString("konten"));
//                                list_data.add(map);
//                            }
//                            txtjudul.setText(list_data.get(0).get("judul"));
//                            txtisi.setText(list_data.get(0).get("konten"));
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                }, new Response.ErrorListener()
//
//                {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        Toast.makeText(Frag1.this, error.getMessage(), Toast.LENGTH_SHORT).show();
//                    }
//                });
//
//                requestQueue.add(stringRequest);
//
//            }
//        }
    }

}
