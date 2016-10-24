package com.jiang.android.push.myservice;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.jiang.android.push.utils.RomUtil;
import com.jiang.android.push.utils.Target;

/**
 * DO NOT do anything in this Receiver!<br/>
 * <p/>
 * Created by Mars on 12/24/15.
 */
public class Receiver1 extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if (Intent.ACTION_BOOT_COMPLETED.equals(intent.getAction())) {
            if(RomUtil.rom() == Target.MyRoom){
                Intent ootStartIntent = new Intent(context, Service1.class);
//            ootStartIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startService(ootStartIntent);
            }
            return;
        }

        if (Intent.ACTION_MEDIA_MOUNTED.equals(intent.getAction())) {
            if(RomUtil.rom() == Target.MyRoom){
                Intent ootStartIntent = new Intent(context, Service1.class);
//            ootStartIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startService(ootStartIntent);
            }
            return;
        }


    }
}
