package com.martins.cubeit.OpenGL;

import android.content.Context;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.util.AttributeSet;
import android.util.Log;

import com.martins.cubeit.GameManager;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

public class BaseGLSurface extends GLSurfaceView implements GLSurfaceView.Renderer {
    private static final String TAG = "CubeGLSurface";

    private int viewWidth = 0;
    private int viewHeight = 0;

    private long startTime = System.currentTimeMillis();
    private int frames = 0;

    private GameManager logic = new GameManager();
    private VirtualCamera camera = new VirtualCamera();

    public BaseGLSurface(Context context) {
        super(context);
        init(context);
    }

    public BaseGLSurface(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        setOnTouchListener(camera);
        setPreserveEGLContextOnPause(true);
        setEGLContextClientVersion(2);
        setRenderer(this);
        setRenderMode(GLSurfaceView.RENDERMODE_CONTINUOUSLY);

        logic.init(context);
    }

    @Override
    public synchronized void onDrawFrame(GL10 gl) {
        GLES20.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT | GLES20.GL_DEPTH_BUFFER_BIT);

        logic.draw(camera);
        logic.update();

        frames++;
        if(System.currentTimeMillis() - startTime >= 1000) {
            Log.d(TAG, "onDrawFrame: " + frames + " @" + viewHeight + "x" + viewWidth);
            frames = 0;
            startTime = System.currentTimeMillis();
        }
    }

    @Override
    public synchronized void onSurfaceCreated(GL10 gl, EGLConfig config) {}

    @Override
    public synchronized void onSurfaceChanged(GL10 gl, int width, int height) {
        Log.d(TAG, "SurfaceChanged...");
        GLES20.glViewport(0, 0, width, height);
        GLES20.glEnable(GLES20.GL_DEPTH_TEST);
        GLES20.glDepthFunc(GLES20.GL_LEQUAL);
        viewWidth = width;
        viewHeight = height;

        camera.setup(viewWidth, viewHeight);
    }
}

