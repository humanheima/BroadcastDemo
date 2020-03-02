package com.brotherd.broadcastdemo;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    private LocalBroadcastManager manager;

    private Handler handler;
    private Thread thread;

    private MyReceiver receiver;
    private MyReceiver2 myReceiver2;
    private MyReceiver2Child myReceiver2Child;


    public static final String ACTION_FIRST = "com.brotherd.broadcastdemo.BROADCAST_FIRST";
    public static final String ACTION_TWO = "com.brotherd.broadcastdemo.BROADCAST_SECOND";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(TAG, "onCreate: context = " + this.toString());

        IntentFilter intentFilter = new IntentFilter(ACTION_FIRST);
        receiver = new MyReceiver();
        registerReceiver(receiver, intentFilter);

        /*
         * 注册本地广播
         */
        myReceiver2 = new MyReceiver2();
        manager = LocalBroadcastManager.getInstance(this);
        manager.registerReceiver(myReceiver2, new IntentFilter(ACTION_TWO));


        myReceiver2Child = new MyReceiver2Child();
        IntentFilter filter2Child = new IntentFilter(ACTION_FIRST);
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
            case R.id.btnWait:
                testWait();
                break;
            case R.id.btnCompute:
                compute();
                break;
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

    private static final String TAG = "MainActivity";


    /**
     * 模拟主线程等待获取锁造成anr
     */

    //用来充当锁资源
    private Object object = new Object();

    private void testWait() {

        new Thread("hello world") {

            @Override
            public void run() {
                synchronized (object) {
                    while (true) {
                        Log.d(TAG, "子线程 run: ");
                        try {
                            Thread.sleep(100);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        };

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Log.d(TAG, "testWait: ");

        synchronized (object) {
            compute();
        }


    }

    /**
     * 模拟耗时操作造成anr
     */
    private void compute() {
        long result = 0;
        for (int i = 0; i < Integer.MAX_VALUE; i++) {
            Log.d(TAG, "compute: ");
            if (i % 2 == 0) {
                result += i;
            } else {
                result -= i;
            }
        }
    }

    /**
     * 发送全局广播
     */
    public void sendGlobalBroadcast() {
        Intent intent = new Intent();
        intent.addFlags(Intent.FLAG_RECEIVER_FOREGROUND);
        intent.setAction(ACTION_FIRST);
        sendBroadcast(intent);
    }

    /**
     * 发送本地广播
     */
    public void sendLocalBroadcast() {
        Intent intent = new Intent();
        intent.setAction(ACTION_TWO);
        manager.sendBroadcast(intent);
    }

    /**
     * 发送有序广播
     */
    public void sendOrderBroadcast() {
        Intent intent = new Intent();
        intent.addFlags(Intent.FLAG_RECEIVER_FOREGROUND);
        intent.setAction(ACTION_FIRST);
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
