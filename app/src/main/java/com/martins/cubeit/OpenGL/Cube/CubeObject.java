package com.martins.cubeit.OpenGL.Cube;

import android.content.Context;

import com.martins.cubeit.CubeWare.CubeData.Position;
import com.martins.cubeit.CubeWare.CubeData.RotationDirection;
import com.martins.cubeit.CubeWare.CubeData.SliceRotatationResult;
import com.martins.cubeit.CubeWare.Main.Cube;
import com.martins.cubeit.CubeWare.SubCubes.SubCube;
import com.martins.cubeit.OpenGL.Cube.Slices.BackSlice;
import com.martins.cubeit.OpenGL.Cube.Slices.BottomSlice;
import com.martins.cubeit.OpenGL.Cube.Slices.CubeSlice;
import com.martins.cubeit.OpenGL.Cube.Slices.FrontSlice;
import com.martins.cubeit.OpenGL.Cube.Slices.LeftSlice;
import com.martins.cubeit.OpenGL.Cube.Slices.RightSlice;
import com.martins.cubeit.OpenGL.Cube.Slices.SliceManager;
import com.martins.cubeit.OpenGL.Cube.Slices.TopSlice;
import com.martins.cubeit.OpenGL.CubeTextureGenerator;
import com.martins.cubeit.OpenGL.TransformationUtils;

import java.util.ArrayList;

public class CubeObject {
    private static final String TAG = "CubeObject";

    private ArrayList<SubCubeObject> cubes = new ArrayList<>();

    private TopSlice topSlice = new TopSlice();
    private BottomSlice bottomSlice = new BottomSlice();
    private LeftSlice leftSlice = new LeftSlice();
    private RightSlice rightSlice = new RightSlice();
    private FrontSlice frontSlice = new FrontSlice();
    private BackSlice backSlice = new BackSlice();

    private SliceManager sliceRotationManager = new SliceManager(this);

    public CubeObject(int sliceCount, Context context) {
        float offset = 2.1f;
        int sliceStart = (int) Math.floor(sliceCount / 2.0f);
        int cubeId = 0;

        for (int curSlice1 = -sliceStart; curSlice1 <= sliceStart; curSlice1++) {
            for (int curSlice2 = -sliceStart; curSlice2 <= sliceStart; curSlice2++) {
                for (int curSlice3 = -sliceStart; curSlice3 <= sliceStart; curSlice3++) {
                    SubCubeObject subCube = new SubCubeObject(cubeId, context);
                    TransformationUtils.translate(subCube, offset * curSlice1, offset * curSlice2, offset * curSlice3);
                    cubes.add(subCube);
                    cubeId++;
                }
            }
        }

        initSlices();
    }

    public CubeObject(Cube cube, Context context) {
        ArrayList<SubCube> subCubes = cube.getSubCubes();

        float offset = 2.1f;
        int sliceStart = 1;
        int iter = 0;

        for (int curSlice1 = sliceStart; curSlice1 >= -sliceStart; curSlice1--) {
            for (int curSlice2 = -sliceStart; curSlice2 <= sliceStart; curSlice2++) {
                for (int curSlice3 = sliceStart; curSlice3 >= -sliceStart; curSlice3--) {
                    SubCubeObject subCube = new SubCubeObject(subCubes.get(iter).getId(), context);
                    subCube.getTexture().setBitmapTexture(
                            CubeTextureGenerator.generateFromSubCube(subCubes.get(iter), 256, 256));
                    TransformationUtils.translate(
                            subCube, offset * curSlice3, offset * curSlice1, offset * curSlice2);
                    cubes.add(subCube);
                    iter++;
                }
            }
        }

        initSlices();
    }

    private void initSlices() {
        topSlice.addRelations(backSlice, leftSlice, rightSlice, frontSlice);
        frontSlice.addRelations(topSlice, leftSlice, rightSlice, bottomSlice);
        bottomSlice.addRelations(frontSlice, leftSlice, rightSlice, backSlice);
        leftSlice.addRelations(topSlice, backSlice, frontSlice, bottomSlice);
        rightSlice.addRelations(topSlice, frontSlice, backSlice, bottomSlice);
        backSlice.addRelations(topSlice, rightSlice, leftSlice, bottomSlice);

        topSlice.addTopSubCubes(cubes.get(6), cubes.get(7), cubes.get(8));
        topSlice.addMidSubCubes(cubes.get(3), cubes.get(4), cubes.get(5));
        topSlice.addBottomSubCubes(cubes.get(0), cubes.get(1), cubes.get(2));

        bottomSlice.addTopSubCubes(cubes.get(18), cubes.get(19), cubes.get(20));
        bottomSlice.addMidSubCubes(cubes.get(21), cubes.get(22), cubes.get(23));
        bottomSlice.addBottomSubCubes(cubes.get(24), cubes.get(25), cubes.get(26));

        leftSlice.addTopSubCubes(cubes.get(6), cubes.get(3), cubes.get(0));
        leftSlice.addMidSubCubes(cubes.get(15), cubes.get(12), cubes.get(9));
        leftSlice.addBottomSubCubes(cubes.get(24), cubes.get(21), cubes.get(18));

        rightSlice.addTopSubCubes(cubes.get(2), cubes.get(5), cubes.get(8));
        rightSlice.addMidSubCubes(cubes.get(11), cubes.get(14), cubes.get(17));
        rightSlice.addBottomSubCubes(cubes.get(20), cubes.get(23), cubes.get(26));

        frontSlice.addTopSubCubes(cubes.get(0), cubes.get(1), cubes.get(2));
        frontSlice.addMidSubCubes(cubes.get(9), cubes.get(10), cubes.get(11));
        frontSlice.addBottomSubCubes(cubes.get(18), cubes.get(19), cubes.get(20));

        backSlice.addTopSubCubes(cubes.get(8), cubes.get(7), cubes.get(6));
        backSlice.addMidSubCubes(cubes.get(17), cubes.get(16), cubes.get(15));
        backSlice.addBottomSubCubes(cubes.get(26), cubes.get(25), cubes.get(24));
    }

    public void runRotationSequence(ArrayList<SliceRotatationResult> moves) {
        sliceRotationManager.startRotation(moves);
    }

    public void rotationUpdate() {
        sliceRotationManager.rotate();
    }

    public TopSlice getTopSlice() {
        return topSlice;
    }

    public BottomSlice getBottomSlice() {
        return bottomSlice;
    }

    public LeftSlice getLeftSlice() {
        return leftSlice;
    }

    public RightSlice getRightSlice() {
        return rightSlice;
    }

    public FrontSlice getFrontSlice() {
        return frontSlice;
    }

    public BackSlice getBackSlice() {
        return backSlice;
    }

    public CubeSlice getSliceByPosition(Position position) {
        switch (position) {
            case Top:
                return topSlice;
            case Bottom:
                return bottomSlice;
            case Front:
                return frontSlice;
            case Back:
                return backSlice;
            case Left:
                return leftSlice;
            case Right:
                return rightSlice;
            case Center:
                default:
                return null;
        }
    }

    public ArrayList<SubCubeObject> getSubCube() {
        return cubes;
    }

    public void reset() {
        sliceRotationManager.reset();
    }
}
