package com.allstudio.adstest;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdLoader;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.nativead.NativeAd;
import com.google.android.gms.ads.nativead.NativeAdOptions;
import com.google.android.gms.ads.nativead.NativeAdView;

import java.util.Objects;

public class NativeActivity extends AppCompatActivity {
    private NativeAd mNativeAd;
    private FrameLayout frameLayout;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_native);
        findViewById(R.id.ibn_load).setOnClickListener(view -> loadAd());
        findViewById(R.id.ibn_show).setOnClickListener(view -> showAd());
        frameLayout = findViewById(R.id.ibn_ad_placeholder);
        loadAd();
    }

    private void showAd(){
        if(mNativeAd != null){
            NativeAdView adView = (NativeAdView) getLayoutInflater().inflate(R.layout.native_ad_layout, null);

            // Locate the view that will hold the headline, set its text, and call the
            // NativeAdView's setHeadlineView method to register it.
            TextView headlineView = adView.findViewById(R.id.ad_headline);
            headlineView.setText(mNativeAd.getHeadline());
            adView.setHeadlineView(headlineView);

            ImageView icon = adView.findViewById(R.id.ad_app_icon);
            icon.setImageDrawable(Objects.requireNonNull(mNativeAd.getIcon()).getDrawable());
            adView.setIconView(icon);

            frameLayout.removeAllViews();
            frameLayout.addView(adView);
            mNativeAd.destroy();
            mNativeAd = null;
        } else {
            ts("Ad Not Loaded Yet");
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(mNativeAd != null) mNativeAd.destroy();
    }

    private void loadAd() {
        if(mNativeAd == null){
            ts("Loading Ad... Please wait...");
            AdLoader adLoader = new AdLoader.Builder(NativeActivity.this, getResources().getString(R.string.adn_unit_id))
                    .forNativeAd(nativeAd -> {
                        // Show the ad.
                        ts("Ad Loaded");
                        mNativeAd = nativeAd;
                        if (isDestroyed()) {
                            mNativeAd.destroy();
                            mNativeAd = null;
                        }
                    })
                    .withAdListener(new AdListener() {
                        @Override
                        public void onAdFailedToLoad(@NonNull LoadAdError adError) {
                            // Handle the failure by logging, altering the UI, and so on.
                            ts("Failed to load ad.");
                            mNativeAd = null;
                        }
                    })
                    .withNativeAdOptions(new NativeAdOptions.Builder()
                            // Methods in the NativeAdOptions.Builder class can be
                            // used here to specify individual options settings.
                            .build())
                    .build();
            adLoader.loadAd(new AdRequest.Builder().build());
        } else {
            ts("Ad loaded");
        }
    }

    private void ts(String message){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
