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
        //这个方法运行在主线程中的，处理具体的逻辑
        Log.d(TAG, "onReceive: this is " + "in thread " + Thread.currentThread().getName());
        /*new Thread(){
            @Override
            public void run() {
                super.run();
                Log.d(TAG, "run: 在onReceive中开启线程");
            }
        }.start();*/
        //超过10秒会ANR
      /*  try {
            Thread.sleep(12000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/
        Toast.makeText(context.getApplicationContext(), "MyReceiver onReceive", Toast.LENGTH_SHORT).show();
        // abortBroadcast();
    }
}
