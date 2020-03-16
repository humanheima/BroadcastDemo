package com.brotherd.broadcastdemo;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class SecondActivity extends AppCompatActivity {


    private MyReceiver receiver;
    public static final String ACTION_FIRST = "com.brotherd.broadcastdemo.BROADCAST_FIRST";

    public static void launch(Context context) {
        Intent intent = new Intent(context, SecondActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        IntentFilter intentFilter = new IntentFilter(ACTION_FIRST);
        receiver = new MyReceiver();
        registerReceiver(receiver, intentFilter);
    }

    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.btnSendGlobalBroadcast:

                sendGlobalBroadcast();
                break;
            case R.id.btnSendLocalBroadcast:

                break;
            case R.id.btnSendOrderBroadcast:
                sendOrderBroadcast();
                break;
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
    }
}
