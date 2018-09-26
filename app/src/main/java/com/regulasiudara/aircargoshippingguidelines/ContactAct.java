package com.regulasiudara.aircargoshippingguidelines;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ContactAct extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {

    private RecyclerView recyclerView;
    private ArticleAdapter adapter;
    private Context context = ContactAct.this;
    List<ArticleModel> articleModelList;
    private ProgressDialog p2Dialog;
    private static String TAG2 = ContactAct.class.getSimpleName();
    TextView judul, subJudul;
    ImageView header;
    public boolean isOnline() {
        ConnectivityManager conMgr = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = conMgr.getActiveNetworkInfo();
        if(netInfo == null || !netInfo.isConnected() || !netInfo.isAvailable()){
            Toast.makeText(context, "Check Your Internet Connection!!!", Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }

    String username, email, phone;
    SharedPreferences sharedpreferences;
    public static final String TAG_USERNAME = "username";
    public static final String TAG_EMAIL = "email";
    public static final String TAG_PHONE = "phone";

    //input database
    ProgressDialog pDialog;
    Button btn_contact;
    EditText txt_username, txt_email, txt_phone, txt_pesan;
    Intent intent;

    int success;
    ConnectivityManager conMgr;

    private String url = Server.URL + "contact.php";

    private static final String TAG = ContactAct.class.getSimpleName();

    private static final String TAG_SUCCESS = "success";
    private static final String TAG_MESSAGE = "message";

    String tag_json_obj = "json_obj_req";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View headerView = navigationView.getHeaderView(0);
        TextView navUsername = (TextView) headerView.findViewById(R.id.txt_account);
        TextView txt_usernamecontact = (TextView) findViewById(R.id.txt_usernamecontact);
        TextView txt_emailcontact = (TextView) findViewById(R.id.txt_emailcontact);
        TextView txt_phonecontact = (TextView) findViewById(R.id.txt_phonecontact);

        sharedpreferences = getSharedPreferences(Login.my_shared_preferences, Context.MODE_PRIVATE);
        username = getIntent().getStringExtra(TAG_USERNAME);
        email = getIntent().getStringExtra(TAG_EMAIL);
        phone = getIntent().getStringExtra(TAG_PHONE);

        navUsername.setText("Username : " + username);
        txt_usernamecontact.setText(username);
        txt_emailcontact.setText(email);
        txt_phonecontact.setText(phone);

        articleModelList = new ArrayList<>();
//        Button btn_contact = (Button) findViewById(R.id.btn_contact);
//        btn_contact.setOnClickListener(this);

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
//        txt_username = (TextView) findViewById(R.id.txt_usernamecontact);
//        txt_email = (TextView) findViewById(R.id.txt_emailcontact);
//        txt_phone = (TextView) findViewById(R.id.txt_phonecontact);
//        txt_username.setText(username);
//        txt_email.setText(email);
//        txt_phone.setText(phone);
        txt_pesan = (EditText) findViewById(R.id.txt_pesan);

        btn_contact.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                String nama = txt_username.getText().toString();
                String email = txt_email.getText().toString();
                String telephone = txt_phone.getText().toString();
                String pesan = txt_pesan.getText().toString();

                if (txt_username.getText().toString().length()==0) {
                    txt_username.setError("Username Null");
                }
                else if (txt_email.getText().toString().length()==0){
                    txt_email.setError("Email Null");
                }
                else if (txt_phone.getText().toString().length()==0){
                    txt_phone.setError("Telephone Null");
                }
                else if (txt_pesan.getText().toString().length()==0){
                    txt_pesan.setError("Please Input Message");
                }
                else if (conMgr.getActiveNetworkInfo() != null
                        && conMgr.getActiveNetworkInfo().isAvailable()
                        && conMgr.getActiveNetworkInfo().isConnected()) {
                    checkContact(nama, email, telephone, pesan);

                    Intent regis = new Intent(ContactAct.this, MainActivity.class);
                    startActivity(regis);

                } else {
                    Toast.makeText(getApplicationContext(), "No Internet Connection", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

    //proses database
    private void checkContact(final String nama, final String email, final String telephone, final String pesan) {
        p2Dialog = new ProgressDialog(this);
        p2Dialog.setCancelable(false);
        p2Dialog.setMessage("Send Message ...");
        showDialog();

        StringRequest strReq = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.e(TAG2, "Send Message Response: " + response.toString());
                hideDialog();

                try {
                    JSONObject jObj = new JSONObject(response);
                    success = jObj.getInt(TAG_SUCCESS);

                    // Check for error node in json
                    if (success == 1) {

                        Log.e("Success Send Message!", jObj.toString());

                        Toast.makeText(getApplicationContext(),
                                jObj.getString(TAG_MESSAGE), Toast.LENGTH_LONG).show();
//
//                        txt_username.setText("");
//                        txt_email.setText("");
//                        txt_phone.setText("");
                        txt_pesan.setText("");

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
                Log.e(TAG2, "Send Message Error: " + error.getMessage());
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
        if (!p2Dialog.isShowing())
            p2Dialog.show();
    }

    private void hideDialog() {
        if (p2Dialog.isShowing())
            p2Dialog.dismiss();
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
            SharedPreferences.Editor editor = sharedpreferences.edit();
            editor.putBoolean(Login.session_status, false);
            editor.putString(TAG_USERNAME, null);
            editor.commit();

            Intent intent = new Intent(ContactAct.this, Login.class);
            finish();
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_home:
                Intent intent1 = new Intent(ContactAct.this, MainActivity.class);
                startActivity(intent1);
                break;
            case R.id.nav_dg:
                Intent intent2 = new Intent(ContactAct.this, DangerousGoodsAct.class);
                startActivity(intent2);
                break;
            case R.id.nav_psn:
                Intent intent3 = new Intent(ContactAct.this, ProperShippingNameAct.class);
                startActivity(intent3);
                break;
            case R.id.nav_pi:
                Intent intent4 = new Intent(ContactAct.this, PackingInstructionAct.class);
                startActivity(intent4);
                break;
            case R.id.nav_limitation:
                Intent intent5 = new Intent(ContactAct.this, LimitationAct.class);
                startActivity(intent5);
                break;
//            case R.id.nav_about:
//                Intent intent6 = new Intent(ContactAct.this, AboutAct.class);
//                startActivity(intent6);
//                break;
//            case R.id.nav_contact:
//                break;
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onClick(View view) {

    }

}
