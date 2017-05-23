package com.brotherd.broadcastdemo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

public class MyReceiver extends BroadcastReceiver {

    private static final String TAG = "MyReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        //处理具体的逻辑
        Log.d(TAG, "onReceive: ");
        Toast.makeText(context.getApplicationContext(), "MyReceiver onReceive", Toast.LENGTH_SHORT).show();
       // abortBroadcast();
    }
}
