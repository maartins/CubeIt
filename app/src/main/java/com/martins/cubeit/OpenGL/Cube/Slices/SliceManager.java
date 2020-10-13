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

    private ArrayList<SliceRotatationResult> currentMoves = new ArrayList<>();
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
                currentMoves.add(new SliceRotatationResult(result));
                allMoves.add(new SliceRotatationResult(result));
            }

            Log.d(TAG, "Rotation received");
            allMoves.forEach(o -> Log.d(TAG, o.toString()));

            angle = cube.getSliceByPosition(Position.Top).getRotationSpeed();
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
                    //Log.d(TAG, cube.getSliceByPosition(Position.Top).toString());
                    flipSubCubes(currentMove.slice, currentMove.rotationDirection);
                    //Log.d(TAG, cube.getSliceByPosition(Position.Top).toString());
                    currentMove = nextMove();
                }
            } else {
                cleanUp();
            }
        }
    }

    private SliceRotatationResult nextMove() {
        if (currentMoves == null || currentMoves.isEmpty())
            return null;

        cube.getSliceByPosition(currentMoves.get(0).slice).setOrigin();

        Log.d(TAG, "nextMove: " + currentMoves.get(0));

        return currentMoves.remove(0);
    }

    private void cleanUp() {
        Log.d(TAG, "Rotation sequence finished. Last rotations: ");
        allMoves.forEach(o -> Log.d(TAG, o.toString()));
        isRotating = false;
        isReseting = false;
        currentMoves.clear();
    }

    private boolean rotateSlice(Position position, RotationDirection direction) {
        cube.getSliceByPosition(position).rotate(direction);

        curAngle += angle / 3;

        if (curAngle >= maxAngle) {
            curAngle = 0;
            return true;
        }

        return false;
    }

    private void flipSubCubes(Position position, RotationDirection direction) {
        cube.getSliceByPosition(position).flipSubCubes(direction);
    }

    public void reverse() {
        if (allMoves != null) {
            if (!allMoves.isEmpty()) {
                Log.d(TAG, "Resetting whole cube.");

                currentMoves.clear();
                Collections.reverse(allMoves);
                allMoves.forEach(result -> currentMoves.add(new SliceRotatationResult(result)));
                Collections.reverse(allMoves);

                currentMove = nextMove();

                isRotating = true;
                isReseting = true;
            }
        }
    }
}
