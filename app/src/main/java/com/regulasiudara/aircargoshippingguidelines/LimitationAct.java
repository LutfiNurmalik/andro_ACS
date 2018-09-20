package com.regulasiudara.aircargoshippingguidelines;

import android.content.Context;
import android.content.Intent;
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

import java.util.ArrayList;
import java.util.List;

public class LimitationAct extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener
    {

    private Context context = LimitationAct.this;
    List<ArticleModel> articleModelList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_limitation);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        articleModelList = new ArrayList<>();
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
//            Intent intent6 = new Intent(DangerousGoodsAct.this, AboutUsActivity.class);
//            startActivity(intent6);
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
                    startActivity(intent1);
                    break;
                case R.id.nav_dg:
                    Intent intent2 = new Intent(LimitationAct.this, DangerousGoodsAct.class);
                    startActivity(intent2);
                    break;
            case R.id.nav_psn:
                Intent intent3 = new Intent(LimitationAct.this, ProperShippingNameAct.class);
                startActivity(intent3);
                break;
                case R.id.nav_pi:
                    Intent intent4 = new Intent(LimitationAct.this, PackingInstructionAct.class);
                    startActivity(intent4);
                    break;
            case R.id.nav_limitation:
                break;
//            case R.id.nav_about:
//                Intent intent6 = new Intent(DangerousGoodsAct.this, AboutUsActivity.class);
//                startActivity(intent6);
//                break;
//            case R.id.nav_contact:
//                Intent intent7 = new Intent(DangerousGoodsAct.this, BmiCalcActivity.class);
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
