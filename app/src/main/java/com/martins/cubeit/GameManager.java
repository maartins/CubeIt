package com.martins.cubeit;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.martins.cubeit.CubeWare.CubeData.CubeType;
import com.martins.cubeit.CubeWare.CubeData.Position;
import com.martins.cubeit.CubeWare.CubeData.SliceRotatationResult;
import com.martins.cubeit.CubeWare.Main.Cube;
import com.martins.cubeit.CubeWare.Main.ScrableReader;
import com.martins.cubeit.OpenGL.Cube.BaseObject;
import com.martins.cubeit.OpenGL.Cube.CubeObject;
import com.martins.cubeit.OpenGL.VirtualCamera;

import java.io.IOException;
import java.util.ArrayList;

import de.javagl.obj.Obj;
import de.javagl.obj.ObjReader;
import de.javagl.obj.ObjUtils;

public final class GameManager implements UiButtonListener{
    private static final String TAG = "GameManager";

    private CubeObject cubeObject = null;
    private ArrayList<SliceRotatationResult> moves;
    private ArrayList<BaseObject> drawables = new ArrayList<>();
    private Obj cubeMesh = null;

    public void init(Context context) {
        UiManager.addUiButtonListener(this);

        //Cube mainCube = new ScrableReader.generateCubeFromString(ScrableGenerator.generate(1));

        Bitmap bitmapTexture = null;
        try {
             bitmapTexture = BitmapFactory.decodeStream(context.getAssets().open("textures/cube.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            cubeMesh = ObjUtils.convertToRenderable(ObjReader.read(context.getAssets().open("cube.obj")));
        } catch (IOException e) {
            e.printStackTrace();
        }

        newCube();
    }

    public void draw(VirtualCamera camera) {
        for (BaseObject o : drawables)
            o.draw(camera);
    }

    public void update() {
        cubeObject.getSliceRotationManager().rotate();
    }

    @Override
    public void onButtonClick(UiManager.methods caller) {
        Log.d(TAG, "Button clicked.");

        if (caller.equals(UiManager.methods.solveButton))
            cubeObject.getSliceRotationManager().startRotation(moves);

        if (caller.equals(UiManager.methods.resetButton)) {
            cubeObject.getSubCubes().forEach(s -> s.setIsObjectHidden(true));
            newCube();
        }
    }

    private void newCube() {
        cubeObject = new CubeObject(new Cube(CubeType.Solved), cubeMesh);
        drawables.addAll(cubeObject.getSubCubes());

        moves = ScrableReader.generateMoveSetFromString("F3U3");

        cubeObject.hideCube();
        cubeObject.showSlice(Position.Top);
        cubeObject.showSlice(Position.Front);
    }
}
