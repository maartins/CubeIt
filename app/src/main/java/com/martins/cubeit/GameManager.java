package com.martins.cubeit;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.View;

import com.martins.cubeit.CubeWare.CubeData.CubeType;
import com.martins.cubeit.CubeWare.CubeData.SliceRotatationResult;
import com.martins.cubeit.CubeWare.Main.Cube;
import com.martins.cubeit.CubeWare.Main.ScrableReader;
import com.martins.cubeit.OpenGL.Cube.BaseObject;
import com.martins.cubeit.OpenGL.Cube.CubeObject;
import com.martins.cubeit.OpenGL.VirtualCamera;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;

import de.javagl.obj.Obj;
import de.javagl.obj.ObjReader;
import de.javagl.obj.ObjUtils;

public final class GameManager implements UiButtonListener{
    private static final String TAG = "GameManager";

    private CubeObject cubeObject = null;
    private ArrayList<SliceRotatationResult> moves;
    private ArrayList<BaseObject> drawables = new ArrayList<>();

    public void init(Context context) {
        UiManager.addUiButtonListener(this);

        //Cube mainCube = new ScrableReader.generateCubeFromString(ScrableGenerator.generate(1));

        Bitmap bitmapTexture = null;
        try {
             bitmapTexture = BitmapFactory.decodeStream(context.getAssets().open("textures/cube.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        Obj cubeMesh = null;
        try {
            cubeMesh = ObjUtils.convertToRenderable(ObjReader.read(context.getAssets().open("cube.obj")));
        } catch (IOException e) {
            e.printStackTrace();
        }

        cubeObject = new CubeObject(new Cube(CubeType.Solved), cubeMesh);
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
