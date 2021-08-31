package com.allstudio.adstest;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.ads.MobileAds;

public class MainActivity extends AppCompatActivity {
    private SharedMemory shared;
    private RelativeLayout pBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        shared = new SharedMemory(this);
        pBar = findViewById(R.id.p_bar);
        MobileAds.initialize(this, initializationStatus -> {
            shared.setIsAdsInit();
            pBar.setVisibility(View.GONE);
        });
        // AdMob
        findViewById(R.id.b_mob_banner).setOnClickListener(view -> {
            Intent i = new Intent(MainActivity.this, BannerActivity.class);
            startActivity(i);
        });
        findViewById(R.id.b_mob_inter).setOnClickListener(view -> {
            Intent i = new Intent(MainActivity.this, InterstitialActivity.class);
            startActivity(i);
        });
        findViewById(R.id.b_mob_native).setOnClickListener(view -> {
            Intent i = new Intent(MainActivity.this, NativeActivity.class);
            startActivity(i);
        });
        findViewById(R.id.b_mob_rewarded).setOnClickListener(view -> {
            Intent i = new Intent(MainActivity.this, RewardingActivity.class);
            startActivity(i);
        });

        findViewById(R.id.b_manager_banner).setOnClickListener(view -> {
            Intent i = new Intent(MainActivity.this, BannerAMActivity.class);
            startActivity(i);
        });
        findViewById(R.id.b_manager_inter).setOnClickListener(view -> {
            Intent i = new Intent(MainActivity.this, InterstitialAMActivity.class);
            startActivity(i);
        });
        findViewById(R.id.b_manager_native).setOnClickListener(view -> {
            Intent i = new Intent(MainActivity.this, NativeAMActivity.class);
            startActivity(i);
        });
        findViewById(R.id.b_manager_rewarded).setOnClickListener(view -> {
            Intent i = new Intent(MainActivity.this, RewardingAMActivity.class);
            startActivity(i);
        });
    }

    private void ts(String message){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}