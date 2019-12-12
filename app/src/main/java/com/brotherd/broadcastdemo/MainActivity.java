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
    private LocalBroadcastManager manager;

    private Handler handler;
    private Thread thread;

    private MyReceiver receiver;
    private MyReceiver2 myReceiver2;
    private MyReceiver2Child myReceiver2Child;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        /*
          注册广播
         */
        intentFilter = new IntentFilter("com.brotherd.broadcastdemo.BROADCAST");
        intentFilter.setPriority(8);
        receiver = new MyReceiver();
        registerReceiver(receiver, intentFilter);

        /*
         * 注册本地广播
         */
        myReceiver2 = new MyReceiver2();
        manager = LocalBroadcastManager.getInstance(this);
        manager.registerReceiver(myReceiver2, new IntentFilter("com.brotherd.broadcastdemo.BROADCAST_TWO"));

        /*
         *
         */
        myReceiver2Child = new MyReceiver2Child();
        IntentFilter filter2Child = new IntentFilter("com.brotherd.broadcastdemo.BROADCAST");
        /**
         * 让这个优先级比intentFilter高
         */
        filter2Child.setPriority(10);
        registerReceiver(myReceiver2Child, filter2Child,
                null, handler);

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

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnSendGlobalBroadcast:
                sendGlobalBroadcast();
                break;
            case R.id.btnSendLocalBroadcast:
                sendLocalBroadcast();
                break;
            case R.id.btnSendOrderBroadcast:
                sendOrderBroadcast();
                break;
            default:
                break;
        }
    }

    /**
     * 发送全局广播
     */
    public void sendGlobalBroadcast() {
        Intent intent = new Intent();
        intent.setAction("com.brotherd.broadcastdemo.BROADCAST");
        sendBroadcast(intent);
        //发送有序广播
        //sendOrderedBroadcast(intent, null);
    }

    /**
     * 发送本地广播
     */
    public void sendLocalBroadcast() {
        Intent intent = new Intent();
        intent.setAction("com.brotherd.broadcastdemo.BROADCAST_TWO");
        manager.sendBroadcast(intent);
    }

    /**
     * 发送有序广播
     */
    public void sendOrderBroadcast() {
        Intent intent = new Intent();
        intent.setAction("com.brotherd.broadcastdemo.BROADCAST");
        sendOrderedBroadcast(intent, null);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(receiver);
        unregisterReceiver(myReceiver2Child);
        manager.unregisterReceiver(myReceiver2);
    }

}
