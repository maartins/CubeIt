package com.martins.cubeit.OpenGL.Cube.Slices;

import android.util.Log;

import com.martins.cubeit.CubeWare.CubeData.Position;
import com.martins.cubeit.CubeWare.CubeData.RotationDirection;
import com.martins.cubeit.CubeWare.CubeData.SliceRotatationResult;
import com.martins.cubeit.OpenGL.Cube.CubeObject;

import java.util.ArrayList;

public class SliceManager {
    private static final String TAG = "SliceManager";

    private CubeObject cube;

    private boolean isRotating = false;
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
        if (!isRotating && moves != null) {
            for (SliceRotatationResult result : moves) {
                curMoves.add(new SliceRotatationResult(result));
                allMoves.add(new SliceRotatationResult(result));
            }

            Log.d(TAG, "Rotation recieved");
            allMoves.forEach(o -> Log.d(TAG, o.toString()));

            angle = 1;
            curAngle = 0;
            maxAngle = 90;

            currentMove = nextMove();
            isRotating = true;
        }
    }

    public void rotate() {
        if (isRotating) {
            if (rotateSlice(currentMove.slice, currentMove.rotationDirection)) {
                flipSubCubes(currentMove.slice, currentMove.rotationDirection);
                currentMove = nextMove();
                if (currentMove == null) {
                    Log.d(TAG, "Rotation sequence finished. Last rotations: ");
                    allMoves.forEach(o -> Log.d(TAG, o.toString()));
                    isRotating = false;
                }
            }
        }
    }

    private SliceRotatationResult nextMove() {
        if (curMoves == null || curMoves.isEmpty())
            return null;

        Log.d(TAG, "nextMove: " + curMoves.get(0));

        return curMoves.remove(0);
    }

    private boolean rotateSlice(Position position, RotationDirection direction) {
        boolean isDone = false;

        switch (position) {
            case Top:
                if (direction == RotationDirection.CCW)
                    cube.getTopSlice().rotate(-angle);
                else
                    cube.getTopSlice().rotate(angle);
                break;
            case Bottom:
                if (direction == RotationDirection.CCW)
                    cube.getBottomSlice().rotate(angle);
                else
                    cube.getBottomSlice().rotate(-angle);
                break;
            case Front:
                if (direction == RotationDirection.CCW)
                    cube.getFrontSlice().rotate(angle);
                else
                    cube.getFrontSlice().rotate(-angle);
                break;
            case Back:
                if (direction == RotationDirection.CCW)
                    cube.getBackSlice().rotate(-angle);
                else
                    cube.getBackSlice().rotate(angle);
                break;
            case Left:
                if (direction == RotationDirection.CCW)
                    cube.getLeftSlice().rotate(-angle);
                else
                    cube.getLeftSlice().rotate(angle);
                break;
            case Right:
                if (direction == RotationDirection.CCW)
                    cube.getRightSlice().rotate(angle);
                else
                    cube.getRightSlice().rotate(-angle);
                break;
        }

        curAngle += angle;

        if (curAngle >= maxAngle) {
            isDone = true;
            curAngle = 0;
        }

        return isDone;
    }

    private void flipSubCubes(Position position, RotationDirection direction) {
        switch (position) {
            case Top:
                    cube.getTopSlice().flipSubCubes(direction);
                break;
            case Bottom:
                    cube.getBottomSlice().flipSubCubes(direction);
                break;
            case Front:
                    cube.getFrontSlice().flipSubCubes(direction);
                break;
            case Back:
                    cube.getBackSlice().flipSubCubes(direction);
                break;
            case Left:
                    cube.getLeftSlice().flipSubCubes(direction);
                break;
            case Right:
                    cube.getRightSlice().flipSubCubes(direction);
                break;
        }
    }
}
