package com.allstudio.adstest;

import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.admanager.AdManagerAdRequest;
import com.google.android.gms.ads.admanager.AdManagerAdView;

public class BannerAMActivity extends AppCompatActivity {
    AdManagerAdView mAdManagerAdView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_banner_am);
        mAdManagerAdView = findViewById(R.id.adManagerAdView);
        findViewById(R.id.amb_load_next).setOnClickListener(view -> loadNextAd());
        setUpBannerAdListeners();
        loadNextAd();
    }

    private void loadNextAd() {
        ts("Loading Ad... Please Wait");
        AdManagerAdRequest adRequest = new AdManagerAdRequest.Builder().build();
        mAdManagerAdView.loadAd(adRequest);
    }

    private void setUpBannerAdListeners() {
        mAdManagerAdView.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                // Code to be executed when an ad finishes loading.
                ts("Ad Loading Finished");
            }

            @Override
            public void onAdFailedToLoad(LoadAdError adError) {
                // Code to be executed when an ad request fails.
                ts("Ad Failed to Load. Error: " + adError.getMessage());
            }

            @Override
            public void onAdOpened() {
                // Code to be executed when an ad opens an overlay that
                // covers the screen.
                ts("Ad Opened");
            }

            @Override
            public void onAdClicked() {
                // Code to be executed when the user clicks on an ad.
                ts("User Just Clicked on Ad");
            }

            @Override
            public void onAdClosed() {
                // Code to be executed when the user is about to return
                // to the app after tapping on an ad.
                ts("Ad Closed By User");
            }
        });
    }

    private void ts(String message){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

}
