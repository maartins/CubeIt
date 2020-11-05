package com.martins.cubeit;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;

import com.martins.cubeit.AssetLoader.AssetLoader;
import com.martins.cubeit.OpenGL.BaseGLSurface;

import java.util.ArrayList;

import de.javagl.obj.Obj;

import org.opencv.core.Mat;

public class MainActivity extends AppCompatActivity implements UiButtonListener {
    private static final String TAG = "MainActivity";

    static {
        System.loadLibrary("native-lib");
        System.loadLibrary("opencv_java4");
    }

    public native String stringFromJNI();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        removeTitleFromActionbar();
        PersonalUtils.resources = getResources();

        if (checkDevicePermissions()) {
            Log.d(TAG, "Permissions are good.");

            Mat test = new Mat();
            Log.d(TAG, "OPENCV: " + stringFromJNI() + " asd " + test.depth());

            AssetLoader.changeAssetManager(this.getAssets());
            AssetLoader.addAsset(Bitmap.class, "textures/cube.png");
            AssetLoader.addAsset(Obj.class, "cube.obj");

            ConstraintLayout layout = (ConstraintLayout) findViewById(R.id.constraindedLayout);
            layout.addView(new BaseGLSurface(this));

            UiManager.addUiButtonListener(this);
            UiManager.addElement(findViewById(R.id.solve_button), UiManager.methods.solveButton);
            UiManager.addElement(findViewById(R.id.reset_button), UiManager.methods.resetButton);
            UiManager.addElement(findViewById(R.id.capture_button), UiManager.methods.captureButton);
        } else {
            this.recreate();
        }
    }

    @Override
    public void onButtonClick(UiManager.methods caller) {
        if (caller.equals(UiManager.methods.captureButton)) {
            Log.d(TAG, "Start Capture activity.");
            Intent intent = new Intent(this, CameraActivity.class);
            this.startActivity(intent);
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

        if (permissions.size() > 0) {
            String[] permissionArray = permissions.toArray(new String[0]);
            if (permissionArray != null)
                ActivityCompat.requestPermissions(this, permissionArray, 1);

            return false;
        } else {
            return true;
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
