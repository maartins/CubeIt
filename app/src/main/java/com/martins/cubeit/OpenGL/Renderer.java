package com.martins.cubeit.OpenGL;

import android.opengl.GLES20;
import android.opengl.Matrix;

import com.martins.cubeit.R;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Renderer {
    private static final String TAG = "Renderer";

    private final List<BaseObject> drawables = new ArrayList<>();

    public void add(BaseObject object) {
        drawables.add(object);
    }

    public <E> void addAll(Collection<? extends E> list) {
        drawables.addAll((Collection<? extends BaseObject>) list);
    }

    public void update(VirtualCamera camera) {
        for (BaseObject drawable: drawables) {
            if (drawable.isValid()) {
                setupShaderProgram(drawable);

                final Texture texture = drawable.getTexture();
                final Mesh mesh = drawable.getMesh();

                texture.setupTexture();

                applyTransformation(drawable, camera);
                drawable.getObjectShader().useProgram();

                GLES20.glEnableVertexAttribArray(drawable.getPositionHandle());
                GLES20.glVertexAttribPointer(drawable.getPositionHandle(), 3, GLES20.GL_FLOAT, false, 3 * 4, mesh.getVertexBuffer());

                if (texture.isValid()) {
                    GLES20.glActiveTexture(GLES20.GL_TEXTURE0);
                    GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, texture.getTextureId());
                    GLES20.glUniform1i(drawable.getTexHandle(), 0);
                }

                GLES20.glEnableVertexAttribArray(texture.getTextureCoordHandle());
                GLES20.glVertexAttribPointer(texture.getTextureCoordHandle(), texture.getTextureBufferSize(), GLES20.GL_FLOAT, false, texture.getTextureBufferSize() * 4, texture.getTextureBuffer());

                GLES20.glUniformMatrix4fv(drawable.getMvpMatrixHandle(), 1, false, drawable.getMvpMatrix(), 0);

                GLES20.glDrawElements(GLES20.GL_TRIANGLES, mesh.getIndexBuffer().capacity(), GLES20.GL_UNSIGNED_INT, mesh.getIndexBuffer());
                GLES20.glDisableVertexAttribArray(drawable.getPositionHandle());
                GLES20.glDisableVertexAttribArray(texture.getTextureCoordHandle());
            }
        }
    }

    private void applyTransformation(BaseObject object, VirtualCamera camera) {
        Matrix.multiplyMM(object.getMvpMatrix(), 0, camera.getProjectionMatrix(), 0, camera.getViewMatrix(), 0);
        Matrix.multiplyMM(object.getTransfomationMatrix(), 0, object.getRotationMatrix(), 0, object.getModelMatrix(), 0);
        Matrix.multiplyMM(object.getMvpMatrix(), 0, object.getMvpMatrix(), 0, object.getTransfomationMatrix(), 0);
    }


    private void setupShaderProgram(BaseObject object) {
        if (!object.getObjectShader().isValid()) {
            if (object.getTexture().isValid()) {
                try {
                    object.getObjectShader().setProgram(R.raw.texture_vshader, R.raw.texture_fshader);
                    object.setPositionHandle(object.getObjectShader().getHandle("aPosition"));
                    object.getTexture().setTextureCoordHandle(object.getObjectShader().getHandle("atexCoord"));
                    object.setTexHandle(object.getObjectShader().getHandle("uTexture"));
                    object.setMvpMatrixHandle(object.getObjectShader().getHandle("uMVPMatrix"));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                try {
                    object.getObjectShader().setProgram(R.raw.color_vshader, R.raw.color_fshader);
                    object.setPositionHandle(object.getObjectShader().getHandle("aPosition"));
                    object.getTexture().setTextureCoordHandle(object.getObjectShader().getHandle("atexCoord"));
                    object.setMvpMatrixHandle(object.getObjectShader().getHandle("uMVPMatrix"));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
