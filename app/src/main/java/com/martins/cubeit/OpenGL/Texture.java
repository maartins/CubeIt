package com.martins.cubeit.OpenGL;

import android.graphics.Bitmap;

import java.nio.FloatBuffer;

public class Texture {
    private final TextureHandle textureHandle = new TextureHandle();
    private Bitmap bitmapTexture = null;

    private FloatBuffer textureBuffer = null;

    private int textureCoordHandle;
    private int textureBufferSize = 0;

    private boolean isNewTexture = true;

    public void setTextureBuffer (FloatBuffer textureBuffer, int size) {
        this.textureBuffer = textureBuffer;
        this.textureBufferSize = size;
    }

    public void setBitmapTexture (Bitmap bitmapTexture) {
        if (bitmapTexture != null) {
            this.bitmapTexture = bitmapTexture.copy(bitmapTexture.getConfig(), false);
            isNewTexture = true;
        }
    }

    public void setTextureCoordHandle(int textureCoord) {
        textureCoordHandle = textureCoord;
    }

    public int getTextureId() {
        return textureHandle.getTextureId();
    }

    public int getTextureCoordHandle() {
        return textureCoordHandle;
    }

    public int getTextureBufferSize() {
        return textureBufferSize;
    }

    public FloatBuffer getTextureBuffer() {
        return textureBuffer;
    }

    public boolean isValid() {
        return bitmapTexture != null;
    }

    public void setupTexture() {
        if (isNewTexture) {
            if (bitmapTexture != null) {
                textureHandle.init2D(bitmapTexture);
            }

            isNewTexture = false;
        }
    }
}
