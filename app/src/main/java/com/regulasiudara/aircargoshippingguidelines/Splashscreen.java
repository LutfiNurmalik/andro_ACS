package com.regulasiudara.aircargoshippingguidelines;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class Splashscreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashscreen);

        final ImageView AnimasiIntro = (ImageView) findViewById(R.id.img_splashscreen);

        AnimasiIntro.setAlpha(0f);

        ValueAnimator fadeAnim = ObjectAnimator.ofFloat(AnimasiIntro, "alpha", 3f, 0f);

        fadeAnim.setDuration(3000);

        fadeAnim.start();

        Thread timer = new Thread() {
            public void run() {
                try {
                    sleep(3100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    PrefManager prefManager = new PrefManager(getApplicationContext());

                    // make first time launch TRUE
                    prefManager.setFirstTimeLaunch(true);

                    startActivity(new Intent(Splashscreen.this, MainActivity.class));
                    finish();
                }
            }
        };
        timer.start();
    }

    private int getDisplayHeight() {
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        return metrics.widthPixels;
    }

    @Override
    protected void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
        finish();
    }
}
