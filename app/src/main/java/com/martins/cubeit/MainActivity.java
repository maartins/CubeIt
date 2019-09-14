package com.martins.cubeit;

import android.Manifest;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    static {
        System.loadLibrary("native-lib");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        removeTitleFromActionbar();
        PersonalUtils.resources = getResources();

        if (!checkDevicePermissions()) {
            Log.d(TAG, "Permissions are good.");
            UiManager.addElement(findViewById(R.id.solve_button), UiManager.methods.solveButton);
        }
    }

    private boolean checkDevicePermissions() {
        Log.d(TAG, "Checking Permissions");

        ArrayList<String> permissions = new ArrayList<>();
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            permissions.add(Manifest.permission.CAMERA);
            Log.e(TAG, "Asking for " + Manifest.permission.CAMERA);
        }

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            permissions.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
            Log.e(TAG, "Asking for " + Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            permissions.add(Manifest.permission.READ_EXTERNAL_STORAGE);
            Log.e(TAG, "Asking for " + Manifest.permission.READ_EXTERNAL_STORAGE);
        }

        /*if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_NETWORK_STATE)
                != PackageManager.PERMISSION_GRANTED) {
            permissions.add(Manifest.permission.ACCESS_NETWORK_STATE);
            Log.e(TAG, "Asking for " + Manifest.permission.ACCESS_NETWORK_STATE);
        }

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_WIFI_STATE)
                != PackageManager.PERMISSION_GRANTED) {
            permissions.add(Manifest.permission.ACCESS_WIFI_STATE);
            Log.e(TAG, "Asking for " + Manifest.permission.ACCESS_WIFI_STATE);
        }

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.INTERNET)
                != PackageManager.PERMISSION_GRANTED) {
            permissions.add(Manifest.permission.INTERNET);
            Log.e(TAG, "Asking for " + Manifest.permission.INTERNET);
        }*/

        if (permissions.size() > 0) {
            String[] permissionArray = permissions.toArray(new String[0]);
            if (permissionArray != null)
                ActivityCompat.requestPermissions(this, permissionArray, 1);

            return true;
        } else {
            return false;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == 1) {
            int check = 0;
            for (int grantResult : grantResults)
                if (grantResult == PackageManager.PERMISSION_GRANTED)
                    check++;

            Log.e(TAG, check + " / " + permissions.length + " / " + grantResults.length);

            if (permissions.length != check) {
                Log.e(TAG, "Failed to get permissions.");
                finish();
                System.exit(0);
            } else {
                Log.e(TAG, "Permissions granted.");
            }
        }
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
