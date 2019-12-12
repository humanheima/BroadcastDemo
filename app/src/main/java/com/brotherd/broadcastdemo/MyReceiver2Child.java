package com.brotherd.broadcastdemo;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class MyReceiver2Child extends MyReceiver2 {

    private static final String TAG = "MyReceiver2Child";

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(TAG, "onReceive: " + intent.getAction());
        //abortBroadcast();
    }
}
