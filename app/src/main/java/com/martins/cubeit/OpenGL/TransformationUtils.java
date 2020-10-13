package com.martins.cubeit.OpenGL;

import android.opengl.Matrix;
import android.util.Log;

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

        object.angle += angle;

        translate(object, moveToOrigin.getX(), moveToOrigin.getY(), moveToOrigin.getZ());

//        Log.d(TAG, "ORIGIN " + object.getOrigin());
//        Log.d(TAG, "ORIGIN_MOVE " + moveToOrigin);
//        Log.d(TAG, "ORIGINAL " + originalPos);
//
        Quaternion q = new Quaternion(moveToOrigin);
        q.set(new Vector3(axis.getX(), axis.getY(), axis.getZ()), object.angle);
        q.normalize();
        object.setRotationMatrix(q.toMatrix());
//        Matrix.multiplyMM(object.getModelMatrix(), 0, object.getModelMatrix(), 0, object.getRotationMatrix(), 0);
//
//        moveToOrigin.negate();
        translate(object, moveToOrigin.getX(), moveToOrigin.getY(), moveToOrigin.getZ());

        Log.d(TAG, "angle: " + object.angle);
    }

    public static void scale(BaseObject object, float scaleFactor) {
        Matrix.setIdentityM(object.getModelMatrix(), 0);
        Matrix.scaleM(object.getModelMatrix(), 0, object.getModelMatrix(), 0, scaleFactor, scaleFactor, scaleFactor);
    }
}
