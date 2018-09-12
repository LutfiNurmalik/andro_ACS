package com.regulasiudara.aircargoshippingguidelines;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Contact extends AppCompatActivity {

    //input database
    ProgressDialog pDialog;
    Button btn_register, btn_login;
    Button btn_contact;
    EditText contactname, contactemail, contactphone, contactmessage;
    EditText txt_username,txt_password, txt_confirm_password, txt_firstname, txt_lastname, txt_email, txt_phone, Txt_tanggal, txt_address, txt_institution;
    Intent intent;

    int success;
    ConnectivityManager conMgr;

    private String url = Server.URL_REGIS + "contact.php";

    private static final String TAG = Contact.class.getSimpleName();

    private static final String TAG_SUCCESS = "success";
    private static final String TAG_MESSAGE = "message";

    String tag_json_obj = "json_obj_req";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);

        conMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        {
            if (conMgr.getActiveNetworkInfo() != null
                    && conMgr.getActiveNetworkInfo().isAvailable()
                    && conMgr.getActiveNetworkInfo().isConnected()) {
            } else {
                Toast.makeText(getApplicationContext(), "No Internet Connection",
                        Toast.LENGTH_LONG).show();
            }
        }

        btn_contact = (Button) findViewById(R.id.btn_contact);
        contactname = (EditText) findViewById(R.id.contactname);
        contactemail = (EditText) findViewById(R.id.contactemail);
        contactphone = (EditText) findViewById(R.id.contactphone);
        contactmessage = (EditText) findViewById(R.id.contactmessage);

        btn_contact.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                String nama = contactname.getText().toString();
                String email = contactemail.getText().toString();
                String telephone = contactphone.getText().toString();
                String pesan = contactmessage.getText().toString();

                if (contactname.getText().toString().length()==0){
                    contactname.setError("Please Input Name");
                }
                else if (contactemail.getText().toString().length()==0){
                    contactemail.setError("Please Input Email");
                }
                else if (contactphone.getText().toString().length()==0){
                    contactphone.setError("Please Input Phone");
                }
                else if (contactmessage.getText().toString().length()==0){
                    contactmessage.setError("Please Input Message");
                }
                else if (conMgr.getActiveNetworkInfo() != null
                        && conMgr.getActiveNetworkInfo().isAvailable()
                        && conMgr.getActiveNetworkInfo().isConnected()) {
                    checkContact(nama, email, telephone, pesan);

                    Intent kontak = new Intent(Contact.this, MainActivity.class);
                    startActivity(kontak);

                } else {
                    Toast.makeText(getApplicationContext(), "No Internet Connection", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

    //proses database
    private void checkContact(final String nama, final String email, final String telephone, final String pesan) {
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);
        pDialog.setMessage("Send Message ...");
        showDialog();

        StringRequest strReq = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.e(TAG, "Send Message Response: " + response.toString());
                hideDialog();

                try {
                    JSONObject jObj = new JSONObject(response);
                    success = jObj.getInt(TAG_SUCCESS);

                    // Check for error node in json
                    if (success == 1) {

                        Log.e("Send Message Success!", jObj.toString());

                        Toast.makeText(getApplicationContext(),
                                jObj.getString(TAG_MESSAGE), Toast.LENGTH_LONG).show();

                        contactname.setText("");
                        contactemail.setText("");
                        contactphone.setText("");
                        contactmessage.setText("");

                    } else {
                        Toast.makeText(getApplicationContext(),
                                jObj.getString(TAG_MESSAGE), Toast.LENGTH_LONG).show();

                    }
                } catch (JSONException e) {
                    // JSON error
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Send Message Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_LONG).show();

                hideDialog();

            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url
                Map<String, String> params = new HashMap<String, String>();
                params.put("nama", nama);
                params.put("email", email);
                params.put("telephone", telephone);
                params.put("pesan", pesan);

                return params;
            }

        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_json_obj);
    }

    private void showDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hideDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }

    @Override
    public void onBackPressed() {
        intent = new Intent(Contact.this, Fragmentcontact.class);
        finish();
        startActivity(intent);
    }
}
