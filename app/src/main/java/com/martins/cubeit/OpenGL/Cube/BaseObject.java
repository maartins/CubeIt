package com.martins.cubeit.OpenGL.Cube;

import android.graphics.Bitmap;
import android.opengl.GLES20;
import android.opengl.Matrix;
import android.util.Log;

import com.martins.cubeit.OpenGL.Shader;
import com.martins.cubeit.OpenGL.TextureHandle;
import com.martins.cubeit.OpenGL.Vector3;
import com.martins.cubeit.OpenGL.VirtualCamera;
import com.martins.cubeit.R;


import java.nio.FloatBuffer;
import java.nio.IntBuffer;

public class BaseObject {
    private static final String TAG = "BaseObject";
    private final int id;

    private final TextureHandle textureHandle = new TextureHandle();
    private Bitmap bitmapTexture = null;

    private IntBuffer indexBuffer = null;
    private FloatBuffer vertexBuffer = null;
    private FloatBuffer textureBuffer = null;

    private int texBufferSize = 0;

    private int positionHandle;
    private int texCoordHandle;
    private int texHandle;
    private int mvpMatrixHandle;

    private final Shader objectShader = new Shader();

    private float[] mvpMatrix = new float[16];
    private float[] modelMatrix = new float[16];
    private float[] rotationMatrix = new float[16];

    private Vector3 position = new Vector3(0.0f, 0.0f, 0.0f);

    private boolean isHidden = false;
    private boolean isFirstSetup = true;

    BaseObject(int id) {
        this.id = id;
    }

    void setIndexBuffer (IntBuffer indexBuffer) {
        this.indexBuffer = indexBuffer;
    }

    void setVertexBuffer (FloatBuffer vertexBuffer) {
        this.vertexBuffer = vertexBuffer;
    }

    void setTextureBuffer (FloatBuffer textureBuffer, int size) {
        this.textureBuffer = textureBuffer;
        this.texBufferSize = size;
    }

    void setBitmapTexture (Bitmap bitmapTexture) {
        this.bitmapTexture = bitmapTexture.copy(bitmapTexture.getConfig(), false);
    }

    public void setIsObjectHidden(boolean isHidden) {
        this.isHidden = isHidden;
    }

    public void setPosition(Vector3 position) {
        this.position = position;
    }

    public int getId() {
        return id;
    }

    public float[] getModelMatrix() {
        return modelMatrix;
    }

    public Vector3 getPosition() {
        return position;
    }

    public void draw(VirtualCamera camera) {
        if (isFirstSetup) {
            setupShaderProgram();
            setupTexture();
            isFirstSetup = false;
        }

        if (!isHidden && isValid()) {
            Matrix.multiplyMM(mvpMatrix, 0, camera.getProjectionMatrix(), 0, camera.getViewMatrix(), 0);
            //Matrix.multiplyMM(modelMatrix, 0, modelMatrix, 0, rotationMatrix, 0);
            Matrix.multiplyMM(mvpMatrix, 0, mvpMatrix, 0, modelMatrix, 0);
            objectShader.useProgram();

            GLES20.glEnableVertexAttribArray(positionHandle);
            GLES20.glVertexAttribPointer(positionHandle, 3, GLES20.GL_FLOAT, false, 3 * 4, vertexBuffer);

            if (bitmapTexture != null) {
                GLES20.glActiveTexture(GLES20.GL_TEXTURE0);
                GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textureHandle.getTextureId());
                GLES20.glUniform1i(texHandle, 0);
            }

            GLES20.glEnableVertexAttribArray(texCoordHandle);
            GLES20.glVertexAttribPointer(
                    texCoordHandle, texBufferSize, GLES20.GL_FLOAT, false, texBufferSize * 4, textureBuffer);

            GLES20.glUniformMatrix4fv(mvpMatrixHandle, 1, false, mvpMatrix, 0);

            GLES20.glDrawElements(
                    GLES20.GL_TRIANGLES, indexBuffer.capacity(), GLES20.GL_UNSIGNED_INT, indexBuffer);
            GLES20.glDisableVertexAttribArray(positionHandle);
            GLES20.glDisableVertexAttribArray(texCoordHandle);
        }
    }

    private void setupShaderProgram() {
        if (bitmapTexture != null) {
            try {
                objectShader.setProgram(R.raw.texture_vshader, R.raw.texture_fshader);
                positionHandle = objectShader.getHandle("aPosition");
                texCoordHandle = objectShader.getHandle("atexCoord");
                texHandle = objectShader.getHandle("uTexture");
                mvpMatrixHandle = objectShader.getHandle("uMVPMatrix");
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            try {
                objectShader.setProgram(R.raw.color_vshader, R.raw.color_fshader);
                positionHandle = objectShader.getHandle("aPosition");
                texCoordHandle = objectShader.getHandle("aColor");
                mvpMatrixHandle = objectShader.getHandle("uMVPMatrix");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void setupTexture() {
        if (bitmapTexture != null)
            textureHandle.init2D(bitmapTexture);
    }

    private boolean isValid() {
        return ((indexBuffer != null && vertexBuffer != null) && textureBuffer != null);
    }
}