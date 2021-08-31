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
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;

public class InterstitialActivity extends AppCompatActivity {
    private InterstitialAd mInterstitialAd;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interstitial);
        findViewById(R.id.ib_load).setOnClickListener(view -> loadAd());
        findViewById(R.id.ib_show).setOnClickListener(view -> showAd());
        loadAd();
    }

    private void loadAd() {
        ts("Loading Ad... Please wait...");
        if(mInterstitialAd == null){
            AdRequest adRequest = new AdRequest.Builder().build();
            InterstitialAd.load(this, "ca-app-pub-3940256099942544/1033173712", adRequest,
                    new InterstitialAdLoadCallback() {
                        @Override
                        public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
                            // The mInterstitialAd reference will be null until
                            // an ad is loaded.
                            ts("Ad Loaded");
                            mInterstitialAd = interstitialAd;
                            setCallBacks();
                        }

                        @Override
                        public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                            ts(loadAdError.getMessage());
                            mInterstitialAd = null;
                            ts("Ad Loading Failed");
                        }
                    });
        } else {
            ts("Ad Loaded");
        }
    }

    private void showAd() {
        if(mInterstitialAd == null){
            Log.d("TAG", "The interstitial ad wasn't ready yet.");
            ts("Ad Not Loaded Yet");
        } else {
            mInterstitialAd.show(InterstitialActivity.this);
        }
    }

    private void setCallBacks() {
        mInterstitialAd.setFullScreenContentCallback(new FullScreenContentCallback(){
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
                mInterstitialAd = null;
                Log.d("TAG", "The ad was shown.");
                ts("Ad Shown to User");
            }
        });
    }

    private void ts(String message){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

}
