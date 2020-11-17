package com.martins.cubeit.OpenGL;

import android.opengl.Matrix;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

public final class VirtualCamera implements View.OnTouchListener{
    private static final String TAG = "VirtualCamera";

    private float previousX;
    private float previousY;

    private float[] projectionMatrix = new float[16];
    private float[] viewMatrix = new float[16];

    private float curAngleX = -2.35f;
    private float curAngleY = 0.8f;
    private float distance = 2.0f;
    private float cameraX = -1.4f, cameraY = 1.38f, cameraZ = -1.42f;
    private float speedModifier = 0.005f;

    private boolean isStatic = false;

    public void setup(int width, int height, boolean isStatic) {
        this.isStatic = isStatic;
        float ratio = (float)width / height;
        Matrix.setLookAtM(viewMatrix, 0, cameraX, cameraY, cameraZ, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f);
        Matrix.perspectiveM(projectionMatrix, 0, 60.0f, ratio, 1, 7);
    }

    @Override
    public boolean onTouch(View v, MotionEvent e) {
        Log.d(TAG, "Touch.");

        if (isStatic) {
            return true;
        }

        float x = e.getX();
        float y = e.getY();

        switch (e.getAction()) {
            case MotionEvent.ACTION_DOWN:
                previousX = x;
                previousY = y;
                return true;
            case MotionEvent.ACTION_UP:
                return true;
            case MotionEvent.ACTION_MOVE:
                float dx = x - previousX;
                float dy = y - previousY;

                curAngleX += (dx) * speedModifier;
                curAngleY -= (dy) * speedModifier;

                if (curAngleY > 3)
                    curAngleY = 3;
                if (curAngleY < 0)
                    curAngleY = 0;

                cameraX = (float) (distance * Math.cos(curAngleX));
                cameraY = (float) (distance * Math.cos(curAngleY));
                cameraZ = (float) (distance * Math.sin(curAngleX));

                Matrix.setLookAtM(viewMatrix, 0,
                        cameraX, cameraY, cameraZ,
                        0.0f, 0.0f, 0.0f,
                        0.0f, 1.0f, 0.0f);
        }

        previousX = x;
        previousY = y;
        return true;
    }

    public float[] getProjectionMatrix() {
        return projectionMatrix;
    }

    public float[] getViewMatrix() {
        return viewMatrix;
    }
}
