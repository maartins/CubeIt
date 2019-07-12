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

    private ArrayList<SliceRotatationResult> moves = null;
    private SliceRotatationResult currentMove = null;

    private int angle = 1;
    private int curAngle = 0;
    private int maxAngle = 90;

    private int moveSelector = 0;

    public SliceManager(CubeObject cube) {
        this.cube = cube;
    }

    public void startRotation(ArrayList<SliceRotatationResult> moves) {
        if (shouldRepeat && !isRotating) {
            this.moves = moves;

            angle = 1;
            curAngle = 0;
            maxAngle = 90;
            moveSelector = 0;

            currentMove = nextMove();
            isRotating = true;
        }
    }

    public void rotate() {
        if (isRotating) {
            if (rotateSlice(currentMove.slice, currentMove.rotationDirection)) {
                flipSubCubes(currentMove.slice, currentMove.rotationDirection);
                currentMove = nextMove();
                if (currentMove == null)
                    isRotating = false;
            }
        }
    }

    private SliceRotatationResult nextMove() {
        if (moves == null)
            return null;
        if (moves.isEmpty())
            return null;

        if (moveSelector < moves.size())
            return moves.get(moveSelector++);
        else
            return null;
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
