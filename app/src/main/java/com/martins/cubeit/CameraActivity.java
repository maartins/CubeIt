package com.martins.cubeit;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class CameraActivity extends AppCompatActivity {
    private static final String TAG = "CameraActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);
        removeTitleFromActionbar();
        Log.d(TAG, "Camera activity started.");
    }

    private void removeTitleFromActionbar() {
        android.app.ActionBar ab1 = getActionBar();
        if (ab1 != null) {
            ab1.setDisplayShowTitleEnabled(false);
        } else {
            android.support.v7.app.ActionBar ab2 = getSupportActionBar();
            if (ab2 != null)
                ab2.setDisplayShowTitleEnabled(false);
            else
                Log.d(TAG, "Could not remove Title from Actionbar.");
        }
    }
}