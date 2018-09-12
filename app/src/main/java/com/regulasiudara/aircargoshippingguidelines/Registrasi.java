package com.regulasiudara.aircargoshippingguidelines;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class Registrasi extends AppCompatActivity implements View.OnClickListener{
    CheckBox cbPersetujuan;
    private ImageView btn_tanggal;
    private LinearLayout parent1;
    private EditText txt_tanggal;
    private int mYear, mMonth, mDay;

    //input spinner
    Spinner spgender;
//    String gender;

    //input database
    ProgressDialog pDialog;
    Button btn_register, btn_login;
    EditText txt_username,txt_password, txt_confirm_password, txt_firstname, txt_lastname, txt_email, txt_phone, Txt_tanggal, txt_address, txt_institution;
    Intent intent;

    int success;
    ConnectivityManager conMgr;

    private String url = Server.URL_REGIS + "register.php";

    private static final String TAG = Registrasi.class.getSimpleName();

    private static final String TAG_SUCCESS = "success";
    private static final String TAG_MESSAGE = "message";

    String tag_json_obj = "json_obj_req";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrasi);

        btn_tanggal = (ImageView) findViewById(R.id.btn_tanggal);
        txt_tanggal = (EditText) findViewById(R.id.txt_tanggal);

        btn_tanggal.setOnClickListener(this);
        cbPersetujuan = findViewById(R.id.persetujuan);

        spgender = (Spinner) findViewById(R.id.sp_gender);

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

        btn_register = (Button) findViewById(R.id.btn_registrasi);
        txt_username = (EditText) findViewById(R.id.txt_usernameregis);
        txt_password = (EditText) findViewById(R.id.txt_passwordregis);
        txt_confirm_password = (EditText) findViewById(R.id.txt_confirmpasswordregis);
        txt_firstname = (EditText) findViewById(R.id.txt_firstname);
        txt_lastname = (EditText) findViewById(R.id.txt_lastname);
        txt_email = (EditText) findViewById(R.id.txt_email);
        txt_phone = (EditText) findViewById(R.id.txt_phone);
        txt_tanggal = (EditText) findViewById(R.id.txt_tanggal);
        txt_address = (EditText) findViewById(R.id.txt_address);
        txt_institution = (EditText) findViewById(R.id.txt_institution);

        btn_register.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                String username = txt_username.getText().toString();
                String password = txt_password.getText().toString();
                String confirm_password = txt_confirm_password.getText().toString();
                String first_name = txt_firstname.getText().toString();
                String last_name = txt_lastname.getText().toString();
                String email = txt_email.getText().toString();
                String phone = txt_phone.getText().toString();
                String birth = txt_tanggal.getText().toString();
                String gender = spgender.getSelectedItem().toString();
                String address = txt_address.getText().toString();
                String institution = txt_institution.getText().toString();

                if (txt_username.getText().toString().length()==0){
                    txt_username.setError("Please Input Username");
                }
                else if (txt_password.getText().toString().length()==0){
                    txt_password.setError("Please Input Password");
                }
                else if (txt_confirm_password.getText().toString().length()==0){
                    txt_confirm_password.setError("Please Input Password");
                }
                else if (txt_firstname.getText().toString().length()==0){
                    txt_firstname.setError("Please Input First Name");
                }
                else if (txt_lastname.getText().toString().length()==0){
                    txt_lastname.setError("Please Input Last Name");
                }
                else if (txt_email.getText().toString().length()==0){
                    txt_email.setError("Please Input Email");
                }
                else if (txt_phone.getText().toString().length()==0){
                    txt_phone.setError("Please Input Phone");
                }
                else if (txt_tanggal.getText().toString().length()==0){
                    txt_tanggal.setError("Please Input Birth Date");
                }
                else if (spgender.getSelectedItem().toString().length()==0){
                    txt_password.setError("Please Input Gender");
                }
                else if (txt_address.getText().toString().length()==0){
                    txt_address.setError("Please Input Address");
                }
                else if (txt_institution.getText().toString().length()==0){
                    txt_institution.setError("Please Input Institution");
                }
                else if (cbPersetujuan.isChecked()==false){
                    cbPersetujuan.setError("Please Check The Agreement");
                }
                else if (conMgr.getActiveNetworkInfo() != null
                        && conMgr.getActiveNetworkInfo().isAvailable()
                        && conMgr.getActiveNetworkInfo().isConnected()) {
                    checkRegister(username, password, confirm_password, first_name, last_name, email, phone, birth, gender, address, institution);

                    Intent regis = new Intent(Registrasi.this, Login.class);
                    startActivity(regis);

                } else {
                    Toast.makeText(getApplicationContext(), "No Internet Connection", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

    @Override
    public void onClick(View v){
        switch (v.getId()){
            case R.id.btn_tanggal:

                Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                txt_tanggal.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
                break;
        }
    }

    //proses database
    private void checkRegister(final String username, final String password, final String confirm_password, final String firstname, final String lastname, final String email, final String phone, final String birth, final String gender, final String address, final String institution) {
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);
        pDialog.setMessage("Register ...");
        showDialog();

        StringRequest strReq = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.e(TAG, "Register Response: " + response.toString());
                hideDialog();

                try {
                    JSONObject jObj = new JSONObject(response);
                    success = jObj.getInt(TAG_SUCCESS);

                    // Check for error node in json
                    if (success == 1) {

                        Log.e("Successfully Register!", jObj.toString());

                        Toast.makeText(getApplicationContext(),
                                jObj.getString(TAG_MESSAGE), Toast.LENGTH_LONG).show();

                        txt_username.setText("");
                        txt_password.setText("");
                        txt_confirm_password.setText("");
                        txt_firstname.setText("");
                        txt_lastname.setText("");
                        txt_email.setText("");
                        txt_phone.setText("");
                        txt_tanggal.setText("");
//                        txt_gender.setText("");
                        txt_address.setText("");
                        txt_institution.setText("");

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
                Log.e(TAG, "Registration Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_LONG).show();

                hideDialog();

            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url
                Map<String, String> params = new HashMap<String, String>();
                params.put("username", username);
                params.put("password", password);
                params.put("confirm_password", confirm_password);
                params.put("first_name", firstname);
                params.put("last_name", lastname);
                params.put("email", email);
                params.put("phone", phone);
                params.put("birth", birth);
                params.put("gender", gender);
                params.put("address", address);
                params.put("institution", institution);

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
        intent = new Intent(Registrasi.this, Login.class);
        finish();
        startActivity(intent);
    }
}


//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_registrasi);
//
//        btn_tanggal = (ImageView) findViewById(R.id.btn_tanggal);
//        txt_tanggal = (EditText) findViewById(R.id.txt_tanggal);
//
//        btn_tanggal.setOnClickListener(this);
//        cbPersetujuan = findViewById(R.id.persetujuan);
//
//        spgender = (Spinner) findViewById(R.id.sp_gender);
//
//        conMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
//        {
//            if (conMgr.getActiveNetworkInfo() != null
//                    && conMgr.getActiveNetworkInfo().isAvailable()
//                    && conMgr.getActiveNetworkInfo().isConnected()) {
//            } else {
//                Toast.makeText(getApplicationContext(), "No Internet Connection",
//                        Toast.LENGTH_LONG).show();
//            }
//        }
//
//        btn_register = (Button) findViewById(R.id.btn_registrasi);
//        txt_username = (EditText) findViewById(R.id.txt_usernameregis);
//        txt_password = (EditText) findViewById(R.id.txt_passwordregis);
//        txt_confirm_password = (EditText) findViewById(R.id.txt_confirmpasswordregis);
//        txt_firstname = (EditText) findViewById(R.id.txt_firstname);
//        txt_lastname = (EditText) findViewById(R.id.txt_lastname);
//        txt_email = (EditText) findViewById(R.id.txt_email);
//        txt_phone = (EditText) findViewById(R.id.txt_phone);
//        txt_tanggal = (EditText) findViewById(R.id.txt_tanggal);
//        txt_address = (EditText) findViewById(R.id.txt_address);
//        txt_institution = (EditText) findViewById(R.id.txt_institution);
//
//        btn_register.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//                // TODO Auto-generated method stub
//                String username = txt_username.getText().toString();
//                String password = txt_password.getText().toString();
//                String confirm_password = txt_confirm_password.getText().toString();
//                String first_name = txt_firstname.getText().toString();
//                String last_name = txt_lastname.getText().toString();
//                String email = txt_email.getText().toString();
//                String phone = txt_phone.getText().toString();
//                String birth = txt_tanggal.getText().toString();
//                String gender = spgender.getSelectedItem().toString();
//                String address = txt_address.getText().toString();
//                String institution = txt_institution.getText().toString();
//
//                if (txt_username.getText().toString().length()==0){
//                    txt_username.setError("Please Input Username");
//                }
//                else if (txt_password.getText().toString().length()==0){
//                    txt_password.setError("Please Input Password");
//                }
//                else if (txt_confirm_password.getText().toString().length()==0){
//                    txt_confirm_password.setError("Please Input Password");
//                }
//                else if (txt_firstname.getText().toString().length()==0){
//                    txt_firstname.setError("Please Input First Name");
//                }
//                else if (txt_lastname.getText().toString().length()==0){
//                    txt_lastname.setError("Please Input Last Name");
//                }
//                else if (txt_email.getText().toString().length()==0){
//                    txt_email.setError("Please Input Email");
//                }
//                else if (txt_phone.getText().toString().length()==0){
//                    txt_phone.setError("Please Input Phone");
//                }
//                else if (txt_tanggal.getText().toString().length()==0){
//                    txt_tanggal.setError("Please Input Birth Date");
//                }
//                else if (spgender.getSelectedItem().toString().length()==0){
//                    txt_password.setError("Please Input Gender");
//                }
//                else if (txt_address.getText().toString().length()==0){
//                    txt_address.setError("Please Input Address");
//                }
//                else if (txt_institution.getText().toString().length()==0){
//                    txt_institution.setError("Please Input Institution");
//                }
//                else if (cbPersetujuan.isChecked()==false){
//                    Toast.makeText(Registrasi.this, "Tolong Ceklis Persetujuan", Toast.LENGTH_LONG);
//                }
//                else if (conMgr.getActiveNetworkInfo() != null
//                        && conMgr.getActiveNetworkInfo().isAvailable()
//                        && conMgr.getActiveNetworkInfo().isConnected()) {
//                    checkRegister(username, password, confirm_password, first_name, last_name, email, phone, birth, gender, address, institution);
//
//                    Intent regis = new Intent(Registrasi.this, Login.class);
//                    startActivity(regis);
//
//                } else {
//                    Toast.makeText(getApplicationContext(), "No Internet Connection", Toast.LENGTH_SHORT).show();
//                }
//
//            }
//        });
//
//    }
