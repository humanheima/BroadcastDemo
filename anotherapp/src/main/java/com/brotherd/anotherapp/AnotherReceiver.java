package com.brotherd.anotherapp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class AnotherReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context, "AnotherReceiver onReceive", Toast.LENGTH_SHORT).show();
    }
}
