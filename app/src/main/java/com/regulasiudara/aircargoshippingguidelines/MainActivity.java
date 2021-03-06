package com.regulasiudara.aircargoshippingguidelines;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.Image;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageClickListener;
import com.synnapps.carouselview.ImageListener;

import org.json.JSONArray;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    private RecyclerView recyclerView;
    private ArticleAdapter adapter;
    private Context context = MainActivity.this;
    List<ArticleModel> articleModelList;

    //    private ProgressDialog pDialog;
    private static String TAG = MainActivity.class.getSimpleName();
    TextView judul, txt_username;
    ImageView header;
    String username;
    public static final String TAG_USERNAME = "username";
    SharedPreferences sharedpreferences;

    public boolean isOnline() {
        ConnectivityManager conMgr = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = conMgr.getActiveNetworkInfo();
        if(netInfo == null || !netInfo.isConnected() || !netInfo.isAvailable()){
            Toast.makeText(context, "Check Your Internet Connection!!!", Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }

    //fungsi carousel
    CarouselView carouselView;
    int[] sampleImages = {R.drawable.carslide1, R.drawable.carslide2, R.drawable.carslide3, R.drawable.carslide4, R.drawable.carslide5};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
        judul = (TextView) findViewById(R.id.judul);
//
//        TextView linkdg = findViewById(R.id.txt_linkdg);
//        linkdg.setText( Html.fromHtml("<font color=#0066ff><a href=\"http://www.dgregulations.com\">www.dgregulations.com</a></font>"));
//        linkdg.setMovementMethod(LinkMovementMethod.getInstance());

        sharedpreferences = getSharedPreferences(Login.my_shared_preferences, Context.MODE_PRIVATE);
        username = getIntent().getStringExtra(TAG_USERNAME);
        navUsername.setText(username);

//        txt_username = (TextView) findViewById(R.id.txt_account);
//
//        sharedpreferences = getSharedPreferences(Login.my_shared_preferences, Context.MODE_PRIVATE);
//        username = getIntent().getStringExtra(TAG_USERNAME);
//        txt_username.setText("USERNAME : " + username);

        //fungsi carousel
        carouselView = findViewById(R.id.carouselView);
        carouselView.setPageCount(sampleImages.length);
        carouselView.setImageListener(imageListener);

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
    }

    //proses carousel
    ImageListener imageListener = new ImageListener() {
        @Override
        public void setImageForPosition(int position, ImageView imageView) {
            imageView.setImageResource(sampleImages[position]);
        }
    };

    @Override
    public void onBackPressed() {
        AlertDialog.Builder keluar = new AlertDialog.Builder(MainActivity.this);
        keluar.setMessage("Are you want to exit?").setCancelable(false)
                .setPositiveButton("Yes", new AlertDialog.OnClickListener(){
                    public void onClick(DialogInterface arg0, int arg1){
                        Intent intent = new Intent(Intent.ACTION_MAIN);
                        intent.addCategory(Intent.CATEGORY_HOME);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                    }
                }).setNegativeButton("No", new AlertDialog.OnClickListener(){
            public void onClick(DialogInterface dialog, int arg1){
                dialog.cancel();
            }
        });
        AlertDialog dialog1 = keluar.create();
        dialog1.setTitle("Exit");
        dialog1.show();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
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

            Intent intent = new Intent(MainActivity.this, Login.class);
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
                break;
            case R.id.nav_dg:
                Intent intent2 = new Intent(MainActivity.this, DangerousGoodsAct.class);
                intent2.putExtra(TAG_USERNAME, username);
                finish();
                startActivity(intent2);
                break;
            case R.id.nav_psn:
                Intent intent3 = new Intent(MainActivity.this, ProperShippingNameAct.class);
                intent3.putExtra(TAG_USERNAME, username);
                finish();
                startActivity(intent3);
                break;
            case R.id.nav_pi:
                Intent intent4 = new Intent(MainActivity.this, PackingInstructionAct.class);
                intent4.putExtra(TAG_USERNAME, username);
                finish();
                startActivity(intent4);
                break;
            case R.id.nav_limitation:
                Intent intent5 = new Intent(MainActivity.this, LimitationAct.class);
                intent5.putExtra(TAG_USERNAME, username);
                finish();
                startActivity(intent5);
                break;
//            case R.id.nav_about:
//                Intent intent6 = new Intent(MainActivity.this, AboutAct.class);
//                startActivity(intent6);
//                break;
//            case R.id.nav_contact:
//                Intent intent7 = new Intent(MainActivity.this, ContactAct.class);
//                startActivity(intent7);
//                break;

        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

//    @Override
//    public void onClick(int position) {
//        switch (position.get()){
//            case R.drawable.carslide2:
//                Intent intentslide2 = new Intent(MainActivity.this, DangerousGoodsAct.class);
//                intentslide2.putExtra(TAG_USERNAME, username);
//                finish();
//                startActivity(intentslide2);
//            case R.drawable.carslide3:
//                Intent intentslide3 = new Intent(MainActivity.this, ProperShippingNameAct.class);
//                intentslide3.putExtra(TAG_USERNAME, username);
//                finish();
//                startActivity(intentslide3);
//            case R.drawable.carslide4:
//                Intent intentslide4 = new Intent(MainActivity.this, PackingInstructionAct.class);
//                intentslide4.putExtra(TAG_USERNAME, username);
//                finish();
//                startActivity(intentslide4);
//            case R.drawable.carslide5:
//                Intent intentslide5 = new Intent(MainActivity.this, LimitationAct.class);
//                intentslide5.putExtra(TAG_USERNAME, username);
//                finish();
//                startActivity(intentslide5);
//        }
//    }
//    private void showpDialog() {
//        if (!pDialog.isShowing())
//            pDialog.show();
//    }
//    private void hidepDialog() {
//        if (pDialog.isShowing())
//            pDialog.dismiss();
//    }
}
