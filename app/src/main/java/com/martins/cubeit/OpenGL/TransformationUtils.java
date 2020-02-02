package com.martins.cubeit.OpenGL;

import android.opengl.Matrix;
import android.util.Log;

import com.martins.cubeit.OpenGL.Cube.BaseObject;

public final class TransformationUtils {
    private static final String TAG = "TransformationUtils";

    private TransformationUtils() {}

    public static void translate(BaseObject object, float x, float y, float z) {
        object.setPosition(new Vector3(x, y, z));
        Matrix.translateM(object.getModelMatrix(), 0, x, y, z);
    }

    public static void rotate(BaseObject object, int angle, int[] axis) {
        Vector3 pos = new Vector3(object.getPosition());

        translate(object, -pos.getX(), -pos.getY(), -pos.getZ());

        Log.d(TAG, "x: " + axis[0] + " y: " + axis[1] + " z: " + axis[2]);

        Quaternion q = new Quaternion(new Vector3(pos));
        q.set(new Vector3(-axis[0], -axis[1], -axis[2]), -angle);
        q.normalize();
        object.setRotationMatrix(q.toMatrix());
        Matrix.multiplyMM(object.getModelMatrix(), 0, object.getModelMatrix(), 0, object.getRotationMatrix(), 0);
        translate(object, pos.getX(), pos.getY(), pos.getZ());
    }

    public static void scale(BaseObject object, float scaleFactor) {
        Matrix.setIdentityM(object.getModelMatrix(), 0);
        Matrix.scaleM(object.getModelMatrix(), 0, object.getModelMatrix(), 0, scaleFactor, scaleFactor, scaleFactor);
    }
}
