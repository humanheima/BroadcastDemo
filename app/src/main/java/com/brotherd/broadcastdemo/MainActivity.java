package com.brotherd.broadcastdemo;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    private IntentFilter intentFilter;
    private MyReceiver receiver;
    private LocalBroadcastManager manager;

    private Handler handler;
    private Thread thread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        intentFilter = new IntentFilter("com.brotherd.broadcastdemo.BROADCAST");
        receiver = new MyReceiver();
        manager = LocalBroadcastManager.getInstance(this);
        manager.registerReceiver(receiver, intentFilter);
        thread = new Thread(new Runnable() {
            @Override
            public void run() {
                Looper.prepare();
                handler = new Handler(Looper.myLooper());
                Looper.loop();
            }
        });
        thread.start();
    }

    /**
     * 发送本地广播
     *
     * @param view
     */
    public void sendLocalBroadcast(View view) {
        Intent intent = new Intent();
        intent.setAction("com.brotherd.broadcastdemo.BROADCAST");
        manager.sendBroadcast(intent);
    }

    /**
     * 发送全局广播
     *
     * @param view
     */
    public void sendGlobalBroadcast(View view) {
        Intent intent = new Intent();
        intent.setAction("com.brotherd.broadcastdemo.BROADCAST");
        sendBroadcast(intent);
        //发送有序广播
        //sendOrderedBroadcast(intent, null);
    }

    /**
     * 发送有序广播
     *
     * @param view
     */
    public void sendOrderBroadcast(View view) {
        Intent intent = new Intent();
        intent.setAction("com.brotherd.broadcastdemo.BROADCAST");
        IntentFilter filter = new IntentFilter("com.brotherd.broadcastdemo.BROADCAST");
        //指定BroadcastReceiver的工作线程
        registerReceiver(receiver, filter, null, handler);
        sendOrderedBroadcast(intent, null);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //manager.unregisterReceiver(receiver);
        unregisterReceiver(receiver);
    }
}
