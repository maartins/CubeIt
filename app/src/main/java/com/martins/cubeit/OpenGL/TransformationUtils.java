package com.martins.cubeit.OpenGL;

import android.opengl.Matrix;

import com.martins.cubeit.OpenGL.Cube.BaseObject;

public final class TransformationUtils {

    private TransformationUtils() {}

    public static void translate(BaseObject object, float x, float y, float z) {
        object.setPosition(new Vector3(x, y, z));
        Matrix.translateM(object.getModelMatrix(), 0, x, y, z);
    }

    public static void rotate(BaseObject object, int angle, int[] axis) {
        float[] rotationMatrix = new float[16];
        Vector3 pos = new Vector3(object.getPosition());
        //Log.d(TAG, "x: " + x + " y: " + y + " z: " + z);
        //PersonalUtils.displaySquareMatrix(modelMatrix, "model");

        //x = modelMatrix[12];
        //y = modelMatrix[13];
        //z = modelMatrix[14];

        //Log.d(TAG, "x: " + x + " y: " + y + " z: " + z);

        translate(object, -pos.getX(), -pos.getY(), -pos.getZ());

        //modelMatrix[12] = 0;
        //modelMatrix[13] = 0;
        //modelMatrix[14] = 0;

        //Matrix.rotateM(rotationMatrix, 0, angle, axis[0], axis[1], axis[2]);
        Quaternion q = new Quaternion(new Vector3(pos));
        q.set(new Vector3(-axis[0], -axis[1], -axis[2]), -angle);
        q.normalize();
        Matrix.multiplyMM(object.getModelMatrix(), 0, object.getModelMatrix(), 0, q.toMatrix(), 0);
        //Matrix.setIdentityM(rotationMatrix, 0);
        //Matrix.rotateM(rotationMatrix, 0, angle, axis[0], axis[1], axis[2]);
        //object.setRotationMatrix(rotationMatrix);
        //Matrix.setIdentityM(modelMatrix, 0);
        //Matrix.rotateM(rotationMatrixTemp, 0, angle, axis[0], axis[1], axis[2]);
        //Matrix.setRotateEulerM(rotationMatrixTemp, 0, angle * axis[0], angle * axis[1], angle * axis[2]);
        //Matrix.setRotateM(rotationMatrix, 0, angle, axis[0], axis[1], axis[2]);
        //Matrix.multiplyMM(rotationMatrix, 0, rotationMatrixTemp, 0, rotationMatrix, 0);
        //Matrix.multiplyMM(modelMatrix, 0, modelMatrix, 0, rotationMatrix, 0);
        translate(object, pos.getX(), pos.getY(), pos.getZ());

        //modelMatrix[12] = x;
        //modelMatrix[13] = y;
        //modelMatrix[14] = z;
    }

    public static void scale(BaseObject object, float scaleFactor) {
        Matrix.setIdentityM(object.getModelMatrix(), 0);
        Matrix.scaleM(object.getModelMatrix(), 0, object.getModelMatrix(), 0, scaleFactor, scaleFactor, scaleFactor);
    }
}
