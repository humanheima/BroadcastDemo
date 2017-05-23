package com.brotherd.broadcastdemo;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    private IntentFilter intentFilter;
    private MyReceiver receiver;
    private LocalBroadcastManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        intentFilter = new IntentFilter("com.brotherd.broadcastdemo.BROADCAST");
        receiver = new MyReceiver();
        manager = LocalBroadcastManager.getInstance(this);
        manager.registerReceiver(receiver, intentFilter);
    }

    /**
     * 发送广播
     *
     * @param view
     */
    public void sendBroadcast(View view) {
        Intent intent = new Intent();
        intent.setAction("com.brotherd.broadcastdemo.BROADCAST");
        manager.sendBroadcast(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        manager.unregisterReceiver(receiver);
    }
}
