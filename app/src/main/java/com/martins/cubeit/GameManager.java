package com.martins.cubeit;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.martins.cubeit.CubeWare.CubeData.CubeType;
import com.martins.cubeit.CubeWare.CubeData.Position;
import com.martins.cubeit.CubeWare.CubeData.SliceRotatationResult;
import com.martins.cubeit.CubeWare.Main.Cube;
import com.martins.cubeit.CubeWare.Main.ScrableReader;
import com.martins.cubeit.OpenGL.BaseObject;
import com.martins.cubeit.OpenGL.BoxObject;
import com.martins.cubeit.OpenGL.Cube.CubeObject;
import com.martins.cubeit.OpenGL.Renderer;
import com.martins.cubeit.OpenGL.TransformationUtils;
import com.martins.cubeit.OpenGL.Vector3;
import com.martins.cubeit.OpenGL.VirtualCamera;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import de.javagl.obj.Obj;
import de.javagl.obj.ObjReader;
import de.javagl.obj.ObjUtils;

public final class GameManager implements UiButtonListener{
    private static final String TAG = "GameManager";

    private CubeObject cubeObject = null;
    private ArrayList<SliceRotatationResult> moves;
    private final Renderer renderer = new Renderer();

    public void init() {
        UiManager.addUiButtonListener(this);

        newCube();
    }

    public void draw(VirtualCamera camera) {
        renderer.update(camera);
    }

    public void update() {
        cubeObject.getSliceRotationManager().rotate();
    }

    @Override
    public void onButtonClick(UiManager.methods caller) {
        Log.d(TAG, "Button clicked.");

        if (caller.equals(UiManager.methods.solveButton)) {
            cubeObject.getSliceRotationManager().startRotation(moves);
        }

        if (caller.equals(UiManager.methods.resetButton)) {
            cubeObject.getSubCubes().forEach(s -> s.setIsObjectHidden(true));
            newCube();
        }
    }

    private void newCube() {
        cubeObject = new CubeObject(new Cube(CubeType.Solved));

        renderer.addAll(cubeObject.getSubCubes());

        moves = ScrableReader.generateMoveSetFromString("F1U1L1R1B1D1");
    }
}
