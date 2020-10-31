package com.martins.cubeit.OpenGL;

import android.opengl.GLES20;
import android.opengl.Matrix;
import android.util.Log;

import com.martins.cubeit.R;

public class BaseObject {
    private static final String TAG = "BaseObject";
    private final int id;

    private Texture texture;
    private Mesh mesh;

    private int positionHandle;
    private int texHandle;
    private int mvpMatrixHandle;

    private final Shader objectShader = new Shader();

    private float[] mvpMatrix = new float[16];
    private float[] modelMatrix = new float[16];
    private float[] rotationMatrix = new float[16];
    private float[] transfomationMatrix = new float[16];

    private Vector3 position = new Vector3(0.0f, 0.0f, 0.0f);
    private Vector3 origin = new Vector3(0.0f, 0.0f, 0.0f);

    private boolean isHidden = false;

    protected BaseObject(int id) {
        this.id = id;
        Matrix.setIdentityM(rotationMatrix, 0);
    }

    protected void setTexture(Texture texture) {
        this.texture = texture;
    }

    protected void setMesh(Mesh mesh) {
        this.mesh = mesh;
    }

    public void setOrigin(Vector3 origin) {
        this.origin = origin;
    }

    public Vector3 getOrigin() {
        return origin;
    }

    public void setRotationMatrix(float[] rotationMatrix) {
        this.rotationMatrix = rotationMatrix;
    }

    public float[] getRotationMatrix() {
        return rotationMatrix;
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

    public Texture getTexture() {
        return texture;
    }

    public Mesh getMesh() {
        return mesh;
    }

    public float[] getMvpMatrix() {
        return mvpMatrix;
    }

    public float[] getTransfomationMatrix() {
        return transfomationMatrix;
    }

    public int getPositionHandle() {
        return positionHandle;
    }

    public int getTexHandle() {
        return texHandle;
    }

    public int getMvpMatrixHandle() {
        return mvpMatrixHandle;
    }

    public Shader getObjectShader() {
        return objectShader;
    }

    public void setPositionHandle(int positionHandle) {
        this.positionHandle = positionHandle;
    }

    public void setTexHandle(int texHandle) {
        this.texHandle = texHandle;
    }

    public void setMvpMatrixHandle(int mvpMatrixHandle) {
        this.mvpMatrixHandle = mvpMatrixHandle;
    }

    boolean isValid() {
        return mesh.isValid() && texture.isValid() && !isHidden;
    }

    @Override
    public String toString() {
        return "BaseObject{" +
                "id=" + id +
                "position=" + position +
                ", origin=" + origin +
                ", isHidden=" + isHidden +
                '}';
    }
}