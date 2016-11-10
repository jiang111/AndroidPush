package com.jiang.android.pushdemo;

import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

public class LauncherActivity extends AppCompatActivity {

    private static final String TAG = "LauncherActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launcher);

        String scheml = getIntent().getScheme();
        if (scheml.equals("custom")) {
            Uri uri = getIntent().getData();
            Log.i(TAG, "onCreate: " + uri.getQueryParameter("aaa"));
        } else {
            String aa = getIntent().getExtras().getString("aaa");
            Log.i(TAG, "onCreate: " + aa);
        }
    }
}
