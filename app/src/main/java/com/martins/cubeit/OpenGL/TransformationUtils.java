package com.martins.cubeit.OpenGL;

import android.opengl.Matrix;
import android.util.Log;

import com.martins.cubeit.PersonalUtils;

import java.lang.reflect.Array;

public final class TransformationUtils {
    private static final String TAG = "TransformationUtils";

    private TransformationUtils() {}

    public static void translate(BaseObject object, float x, float y, float z) {
        Vector3 newPos = new Vector3(object.getPosition());
        newPos.add(new Vector3(x, y, z));
        object.setPosition(newPos);
        Matrix.translateM(object.getModelMatrix(), 0, x, y, z);
    }

    public static void rotate(BaseObject object, int angle, Vector3 axis) {
        Vector3 moveToOrigin = new Vector3(object.getPosition());
        moveToOrigin.subtract(object.getOrigin());
        moveToOrigin.negate();

        translate(object, moveToOrigin.getX(), moveToOrigin.getY(), moveToOrigin.getZ());

        Quaternion q = new Quaternion(axis, angle).normalize();
        Quaternion q1 = new Quaternion(object.getRotationMatrix()).normalize();
        Quaternion q3 = q1.multiply(q).normalize();
        object.setRotationMatrix(q3.toMatrix());

        moveToOrigin.negate();
        translate(object, moveToOrigin.getX(), moveToOrigin.getY(), moveToOrigin.getZ());
    }

    public static void scale(BaseObject object, float scaleFactor) {
        Matrix.setIdentityM(object.getModelMatrix(), 0);
        Matrix.scaleM(object.getModelMatrix(), 0, object.getModelMatrix(), 0, scaleFactor, scaleFactor, scaleFactor);
    }
}
