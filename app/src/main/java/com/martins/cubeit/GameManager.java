package com.martins.cubeit;

import android.content.Context;
import android.util.Log;
import android.view.View;

import com.martins.cubeit.CubeWare.CubeData.CubeType;
import com.martins.cubeit.CubeWare.CubeData.SliceRotatationResult;
import com.martins.cubeit.CubeWare.Main.Cube;
import com.martins.cubeit.CubeWare.Main.ScrableReader;
import com.martins.cubeit.OpenGL.Cube.BaseObject;
import com.martins.cubeit.OpenGL.Cube.CubeObject;
import com.martins.cubeit.OpenGL.VirtualCamera;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;

public final class GameManager implements UiButtonListener{
    private static final String TAG = "GameManager";

    private CubeObject cubeObject = null;
    private ArrayList<SliceRotatationResult> moves;
    private ArrayList<BaseObject> drawables = new ArrayList<>();

    public void init(Context context) {
        UiManager.addUiButtonListener(this);

        //Cube mainCube = new ScrableReader.generateCubeFromString(ScrableGenerator.generate(1));
        Cube solverCube = new Cube(CubeType.Solved);
        cubeObject = new CubeObject(solverCube, context);
        drawables.addAll(cubeObject.getSubCube());
        moves = ScrableReader.generateMoveSetFromString("F1U1");
    }

    public void draw(VirtualCamera camera) {
        for (BaseObject o : drawables)
            o.draw(camera);
    }

    public void update() {
        cubeObject.rotationUpdate();
    }

    @Override
    public void onButtonClick(UiManager.methods caller) {
        Log.d(TAG, "Button clicked.");

        if (caller.equals(UiManager.methods.solveButton))
            cubeObject.runRotationSequence(moves);

        if (caller.equals(UiManager.methods.resetButton))
            cubeObject.reset();
    }
}
