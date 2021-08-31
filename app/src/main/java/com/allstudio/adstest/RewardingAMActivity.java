package com.allstudio.adstest;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.admanager.AdManagerAdRequest;
import com.google.android.gms.ads.rewarded.RewardedAd;
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback;

public class RewardingAMActivity extends AppCompatActivity {
    private RewardedAd mRewardedAd;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rewarding_am);
        findViewById(R.id.ib_load).setOnClickListener(view -> loadAd());
        findViewById(R.id.ib_show).setOnClickListener(view -> showAd());
        loadAd();
    }

    private void showAd() {
        if (mRewardedAd != null) {
            mRewardedAd.show(RewardingAMActivity.this, rewardItem -> {
                // Handle the reward.
                Log.d("TAG", "The user earned the reward.");
                int rewardAmount = rewardItem.getAmount();
                String rewardType = rewardItem.getType();
                ts("The user earned reward.\nReward Amount: " + rewardAmount + "\nReward Type: " + rewardType);
            });
        } else {
            Log.d("TAG", "The rewarded ad wasn't ready yet.");
            ts("Ad Not Loaded Yet");
        }
    }

    private void loadAd() {
        if(mRewardedAd == null) {
            ts("Loading Ad... Please wait...");
            AdManagerAdRequest adRequest = new AdManagerAdRequest.Builder().build();
            RewardedAd.load(this, getResources().getString(R.string.adrm_unit_id),
                    adRequest, new RewardedAdLoadCallback(){
                        @Override
                        public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                            // Handle the error.
                            Log.d("TAG", loadAdError.getMessage());
                            mRewardedAd = null;
                        }

                        @Override
                        public void onAdLoaded(@NonNull RewardedAd rewardedAd) {
                            mRewardedAd = rewardedAd;
                            Log.d("TAG", "Ad Loaded");
                            ts("Ad Loaded");
                            setCallBacks();
                        }
                    });
        } else {
            ts("Ad Loaded");
        }
    }

    private void setCallBacks() {
        mRewardedAd.setFullScreenContentCallback(new FullScreenContentCallback(){
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
                mRewardedAd = null;
                Log.d("TAG", "The ad was shown.");
                ts("Ad Shown to User");
            }
        });
    }

    private void ts(String message){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
