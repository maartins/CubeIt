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
import com.martins.cubeit.OpenGL.BaseObject;
import com.martins.cubeit.OpenGL.BoxObject;
import com.martins.cubeit.OpenGL.Cube.CubeObject;
import com.martins.cubeit.OpenGL.TransformationUtils;
import com.martins.cubeit.OpenGL.Vector3;
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


    BoxObject box = new BoxObject(1);
    BoxObject box2 = new BoxObject(2);

    public void init() {
        UiManager.addUiButtonListener(this);

        //TransformationUtils.translate(box2, 0, 1.5f, 0);
        //drawables.add(box);
        //drawables.add(box2);

        //Cube mainCube = new ScrableReader.generateCubeFromString(ScrableGenerator.generate(1));
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

        if (caller.equals(UiManager.methods.solveButton)) {
//            TransformationUtils.rotate(box2, 2, new Vector3(0, 1, 0));
            cubeObject.getSliceRotationManager().startRotation(moves);
        }

        if (caller.equals(UiManager.methods.resetButton)) {
//            TransformationUtils.rotate(box2, 2, new Vector3(1, 0, 0));
            cubeObject.getSubCubes().forEach(s -> s.setIsObjectHidden(true));
            newCube();
        }
    }

    private void newCube() {
        cubeObject = new CubeObject(new Cube(CubeType.Solved));
        drawables.addAll(cubeObject.getSubCubes());

        moves = ScrableReader.generateMoveSetFromString("F1U1L1R1B1D1");

//        cubeObject.hideCube();
//        cubeObject.showSlice(Position.Top);
//        cubeObject.showSlice(Position.Front);
    }
}
