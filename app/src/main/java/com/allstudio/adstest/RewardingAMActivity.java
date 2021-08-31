package com.allstudio.adstest;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.ads.rewarded.RewardedAd;

public class RewardingAMActivity extends AppCompatActivity {
    private RewardedAd mRewardedAd;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rewarding_am);
    }
}
