package com.martins.cubeit.OpenGL.Cube.Slices;

import android.util.Log;

import com.martins.cubeit.CubeWare.CubeData.Position;
import com.martins.cubeit.CubeWare.CubeData.RotationDirection;
import com.martins.cubeit.CubeWare.CubeData.SliceRotatationResult;
import com.martins.cubeit.OpenGL.Cube.CubeObject;

import java.util.ArrayList;
import java.util.Collections;

public class SliceManager {
    private static final String TAG = "SliceManager";

    private CubeObject cube;

    private boolean isRotating = false;
    private boolean isReseting = false;
    private boolean shouldRepeat = false;

    private ArrayList<SliceRotatationResult> curMoves = new ArrayList<>();
    private ArrayList<SliceRotatationResult> allMoves = new ArrayList<>();
    private SliceRotatationResult currentMove = null;

    private int angle;
    private int curAngle;
    private int maxAngle;

    public SliceManager(CubeObject cube) {
        this.cube = cube;
    }

    public void startRotation(ArrayList<SliceRotatationResult> moves) {
        if ((!isRotating && moves != null) && !isReseting) {
            for (SliceRotatationResult result : moves) {
                curMoves.add(new SliceRotatationResult(result));
                allMoves.add(new SliceRotatationResult(result));
            }

            Log.d(TAG, "Rotation recieved");
            allMoves.forEach(o -> Log.d(TAG, o.toString()));

            angle = cube.getTopSlice().getRotationSpeed();
            curAngle = 0;
            maxAngle = 90;

            currentMove = nextMove();
            isRotating = true;
        }
    }

    public void rotate() {
        if (isRotating) {
            if (currentMove != null) {
                if (rotateSlice(currentMove.slice, currentMove.rotationDirection)) {
                    flipSubCubes(currentMove.slice, currentMove.rotationDirection);
                    currentMove = nextMove();
                }
            } else {
                cleanUp();
            }
        }
    }

    private SliceRotatationResult nextMove() {
        if (curMoves == null || curMoves.isEmpty())
            return null;

        Log.d(TAG, "nextMove: " + curMoves.get(0));

        return curMoves.remove(0);
    }

    private void cleanUp() {
        Log.d(TAG, "Rotation sequence finished. Last rotations: ");
        allMoves.forEach(o -> Log.d(TAG, o.toString()));
        isRotating = false;
        isReseting = false;
        curMoves.clear();
    }

    private boolean rotateSlice(Position position, RotationDirection direction) {
        cube.getSliceByPosition(position).rotate(direction);

        curAngle += angle;

        if (curAngle >= maxAngle) {
            curAngle = 0;
            return true;
        }

        return false;
    }

    private void flipSubCubes(Position position, RotationDirection direction) {
        cube.getSliceByPosition(position).flipSubCubes(direction);
    }

    public void reset() {
        if (allMoves != null) {
            if (!allMoves.isEmpty()) {
                Log.d(TAG, "Resetting whole cube.");

                curMoves.clear();
                Collections.reverse(allMoves);
                allMoves.forEach(result -> curMoves.add(new SliceRotatationResult(result)));
                Collections.reverse(allMoves);

                currentMove = nextMove();

                isRotating = true;
                isReseting = true;
            }
        }
    }
}
