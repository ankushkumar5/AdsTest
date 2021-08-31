package com.allstudio.adstest;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.admanager.AdManagerAdRequest;
import com.google.android.gms.ads.admanager.AdManagerInterstitialAd;
import com.google.android.gms.ads.admanager.AdManagerInterstitialAdLoadCallback;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;

public class InterstitialAMActivity extends AppCompatActivity {
    private AdManagerInterstitialAd mAdManagerInterstitialAd;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interstitial_am);
        findViewById(R.id.ibm_load).setOnClickListener(view -> loadAd());
        findViewById(R.id.ibm_show).setOnClickListener(view -> showAd());
        loadAd();
    }

    private void showAd() {
        if (mAdManagerInterstitialAd != null) {
            mAdManagerInterstitialAd.show(InterstitialAMActivity.this);
        } else {
            Log.d("TAG", "The interstitial ad wasn't ready yet.");
            ts("Ad Not Loaded Yet");
        }
    }

    private void loadAd() {
        if(mAdManagerInterstitialAd == null){
            ts("Loading Ad... Please wait...");
            AdManagerAdRequest adRequest = new AdManagerAdRequest.Builder().build();
            AdManagerInterstitialAd.load(this, getResources().getString(R.string.adim_unit_id), adRequest, new AdManagerInterstitialAdLoadCallback() {
                @Override
                public void onAdLoaded(@NonNull AdManagerInterstitialAd interstitialAd) {
                    // The mAdManagerInterstitialAd reference will be null until
                    // an ad is loaded.
                    mAdManagerInterstitialAd = interstitialAd;
                    Log.i("TAG", "onAdLoaded");
                    ts("Ad Loaded");
                    setCallBacks();
                }

                @Override
                public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                    // Handle the error
                    Log.i("TAG", loadAdError.getMessage());
                    mAdManagerInterstitialAd = null;
                    ts("Ad Loaded");
                }
            });
        } else {
            ts("Ad Loaded");
        }
    }

    private void setCallBacks() {
        mAdManagerInterstitialAd.setFullScreenContentCallback(new FullScreenContentCallback(){
            @Override
            public void onAdDismissedFullScreenContent() {
                // Called when fullscreen content is dismissed.
                Log.d("TAG", "The ad was dismissed.");
                ts("Ad Dismissed");
            }

            @Override
            public void onAdFailedToShowFullScreenContent(@NonNull AdError adError) {
                // Called when fullscreen content failed to show.
                Log.d("TAG", "The ad failed to show.");
                ts("Failed To Show Ad.");
            }

            @Override
            public void onAdShowedFullScreenContent() {
                // Called when fullscreen content is shown.
                // Make sure to set your reference to null so you don't
                // show it a second time.
                mAdManagerInterstitialAd = null;
                Log.d("TAG", "The ad was shown.");
                ts("Ad Shown to User");
            }
        });
    }

    private void ts(String message){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

}