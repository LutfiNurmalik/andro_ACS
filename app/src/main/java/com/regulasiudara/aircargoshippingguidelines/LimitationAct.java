package com.regulasiudara.aircargoshippingguidelines;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class LimitationAct extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener
    {

    private Context context = LimitationAct.this;
    List<ArticleModel> articleModelList;

        public static final String TAG_USERNAME = "username";
        String username;
        SharedPreferences sharedpreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_limitation);

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

        sharedpreferences = getSharedPreferences(Login.my_shared_preferences, Context.MODE_PRIVATE);
        username = getIntent().getStringExtra(TAG_USERNAME);
        navUsername.setText(username);

        Button bCountry = (Button)findViewById(R.id.pindah_limCountry);
        bCountry.setOnClickListener(this);
        Button bAirline = (Button)findViewById(R.id.pindah_limAirlines);
        bAirline.setOnClickListener(this);
    }

        @Override
        public void onBackPressed() {
            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            if (drawer.isDrawerOpen(GravityCompat.START)) {
                drawer.closeDrawer(GravityCompat.START);
            } else {
//                super.onBackPressed();
                Intent home = new Intent(LimitationAct.this, MainActivity.class);
                home.putExtra(TAG_USERNAME, username);
                finish();
                startActivity(home);
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

                Intent intent = new Intent(LimitationAct.this, Login.class);
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
                    Intent intent1 = new Intent(LimitationAct.this, MainActivity.class);
                    intent1.putExtra(TAG_USERNAME, username);
                    finish();
                    startActivity(intent1);
                    break;
                case R.id.nav_dg:
                    Intent intent2 = new Intent(LimitationAct.this, DangerousGoodsAct.class);
                    intent2.putExtra(TAG_USERNAME, username);
                    finish();
                    startActivity(intent2);
                    break;
            case R.id.nav_psn:
                Intent intent3 = new Intent(LimitationAct.this, ProperShippingNameAct.class);
                intent3.putExtra(TAG_USERNAME, username);
                finish();
                startActivity(intent3);
                break;
                case R.id.nav_pi:
                    Intent intent4 = new Intent(LimitationAct.this, PackingInstructionAct.class);
                    intent4.putExtra(TAG_USERNAME, username);
                    finish();
                    startActivity(intent4);
                    break;
            case R.id.nav_limitation:
                break;
//            case R.id.nav_about:
//                Intent intent6 = new Intent(LimitationAct.this, AboutAct.class);
//                startActivity(intent6);
//                break;
//            case R.id.nav_contact:
//                Intent intent7 = new Intent(LimitationAct.this, ContactAct.class);
//                startActivity(intent7);
//                break;
            }
            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            drawer.closeDrawer(GravityCompat.START);
            return true;
        }


        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.pindah_limCountry:
                    Intent moveCountry = new Intent(LimitationAct.this, LimCountry.class);
                    startActivity(moveCountry);
                    break;

                case R.id.pindah_limAirlines:
                    Intent moveAirline = new Intent(LimitationAct.this, LimAirline.class);
                    startActivity(moveAirline);
                    break;
            }
        }

    }
