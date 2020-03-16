package com.brotherd.broadcastdemo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.WindowManager;
import android.widget.Toast;

/**
 * Crete by dumingwei on 2020-02-26
 * Desc:
 */
public class MyReceiver extends BroadcastReceiver {

    private static final String TAG = "MyReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(TAG, "onReceive: context = " + context.toString());
        //这个方法运行在主线程中的，处理具体的逻辑
        Log.d(TAG, "onReceive: this is " + "in thread " + Thread.currentThread().getName());
        try {
            Thread.sleep(12000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //showDialog(context);
        //showDialog(context);

        Toast.makeText(context.getApplicationContext(), "MyReceiver onReceive", Toast.LENGTH_SHORT).show();
        // abortBroadcast();
    }

    /**
     *
     */
    private void showDialog(Context context) {
        AlertDialog dialog = new AlertDialog.Builder(context)
                .setView(LayoutInflater.from(context).inflate(R.layout.dialog_signin, null))
                .setPositiveButton("confirm", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).create();
        dialog.show();

    }
}
