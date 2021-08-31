package com.allstudio.adstest;

import android.content.Context;
import android.content.SharedPreferences;

class SharedMemory {
    private final SharedPreferences prefs;
    SharedMemory(Context ctx) {prefs = ctx.getSharedPreferences("SCREEN_SETTINGS", Context.MODE_PRIVATE);}

    // Local Settings
    void setIsAdsInit() {prefs.edit().putBoolean("IsAdsInit", true).apply();}
    boolean getIsAdsInit() { return  prefs.getBoolean("IsAdsInit", false); }


}