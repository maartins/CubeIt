package com.martins.cubeit.CubeWare.Main;

import com.martins.cubeit.CubeWare.CubeData.ColorPosition;
import com.martins.cubeit.CubeWare.CubeData.CubeType;
import com.martins.cubeit.CubeWare.CubeData.Position;
import com.martins.cubeit.CubeWare.CubeData.RotationDirection;
import com.martins.cubeit.CubeWare.Slices.CubeSliceManager;
import com.martins.cubeit.CubeWare.SubCubes.Center;
import com.martins.cubeit.CubeWare.SubCubes.Corner;
import com.martins.cubeit.CubeWare.SubCubes.Edge;
import com.martins.cubeit.CubeWare.SubCubes.SubCube;

import java.util.ArrayList;
import java.util.Arrays;

import static com.martins.cubeit.CubeWare.CubeData.Color.*;
import static com.martins.cubeit.CubeWare.CubeData.Position.*;

public class Cube {
    private ArrayList<SubCube> subCubes = new ArrayList<>();
    private CubeSliceManager csm;

    public Cube(CubeType type){
        switch (type) {
            case Solved:
                createSolvedCube();
                break;
            case Hardcoded:
                createHardcodedCube();
                break;
        }

        if (verifyColorCount()) {
            csm = new CubeSliceManager(subCubes);
        } else {
            throw new InternalError("Too many colors of the same color.");
        }
    }

    public ArrayList<SubCube> getSubCubes() {
        return csm.getSubCubes();
    }

    private void createSolvedCube() {
        // top
        subCubes.add(new Corner(new ColorPosition(Red, Left), new ColorPosition(White, Front), new ColorPosition(Green, Top)));
        subCubes.add(new Edge(new ColorPosition(White, Front), new ColorPosition(Green, Top)));
        subCubes.add(new Corner(new ColorPosition(White, Front), new ColorPosition(Green, Top), new ColorPosition(Orange, Right)));
        subCubes.add(new Edge(new ColorPosition(Red, Left), new ColorPosition(Green, Top)));
        subCubes.add(new Center(new ColorPosition(Green, Top)));
        subCubes.add(new Edge(new ColorPosition(Green, Top), new ColorPosition(Orange, Right)));
        subCubes.add(new Corner(new ColorPosition(Red, Left), new ColorPosition(Green, Top), new ColorPosition(Yellow, Back)));
        subCubes.add(new Edge(new ColorPosition(Green, Top), new ColorPosition(Yellow, Back)));
        subCubes.add(new Corner(new ColorPosition(Green, Top), new ColorPosition(Orange, Right), new ColorPosition(Yellow, Back)));
        // middle
        subCubes.add(new Edge(new ColorPosition(Red, Left), new ColorPosition(White, Front)));
        subCubes.add(new Center(new ColorPosition(White, Front)));
        subCubes.add(new Edge(new ColorPosition(White, Front), new ColorPosition(Orange, Right)));
        subCubes.add(new Center(new ColorPosition(Red, Left)));
        subCubes.add(new Center(new ColorPosition(None, Center))); // empty core sub cube
        subCubes.add(new Center(new ColorPosition(Orange, Right)));
        subCubes.add(new Edge(new ColorPosition(Red, Left), new ColorPosition(Yellow, Back)));
        subCubes.add(new Center(new ColorPosition(Yellow, Back)));
        subCubes.add(new Edge(new ColorPosition(Yellow, Back), new ColorPosition(Orange, Right)));
        // bottom
        subCubes.add(new Corner(new ColorPosition(Red, Left), new ColorPosition(White, Front), new ColorPosition(Blue, Bottom)));
        subCubes.add(new Edge(new ColorPosition(White, Front), new ColorPosition(Blue, Bottom)));
        subCubes.add(new Corner(new ColorPosition(White, Front), new ColorPosition(Blue, Bottom), new ColorPosition(Orange, Right)));
        subCubes.add(new Edge(new ColorPosition(Red, Left), new ColorPosition(Blue, Bottom)));
        subCubes.add(new Center(new ColorPosition(Blue, Bottom)));
        subCubes.add(new Edge(new ColorPosition(Blue, Bottom), new ColorPosition(Orange, Right)));
        subCubes.add(new Corner(new ColorPosition(Red, Left), new ColorPosition(Blue, Bottom), new ColorPosition(Yellow, Back)));
        subCubes.add(new Edge(new ColorPosition(Blue, Bottom), new ColorPosition(Yellow, Back)));
        subCubes.add(new Corner(new ColorPosition(Blue, Bottom), new ColorPosition(Orange, Right), new ColorPosition(Yellow, Back)));
    }

    private void createHardcodedCube() {
        // top
        subCubes.add(new Corner(new ColorPosition(Red, Left), new ColorPosition(White, Front), new ColorPosition(Green, Top)));
        subCubes.add(new Edge(new ColorPosition(Yellow, Front), new ColorPosition(Green, Top)));
        subCubes.add(new Corner(new ColorPosition(Yellow, Front), new ColorPosition(Blue, Top), new ColorPosition(Orange, Right)));
        subCubes.add(new Edge(new ColorPosition(Yellow, Left), new ColorPosition(Red, Top)));
        subCubes.add(new Center(new ColorPosition(Blue, Top)));
        subCubes.add(new Edge(new ColorPosition(Green, Top), new ColorPosition(Red, Right)));
        subCubes.add(new Corner(new ColorPosition(White, Left), new ColorPosition(Blue, Top), new ColorPosition(Orange, Back)));
        subCubes.add(new Edge(new ColorPosition(Blue, Top), new ColorPosition(White, Back)));
        subCubes.add(new Corner(new ColorPosition(Red, Top), new ColorPosition(Green, Right), new ColorPosition(Yellow, Back)));
        // middle
        subCubes.add(new Edge(new ColorPosition(White, Left), new ColorPosition(Red, Front)));
        subCubes.add(new Center(new ColorPosition(Yellow, Front)));
        subCubes.add(new Edge(new ColorPosition(White, Front), new ColorPosition(Orange, Right)));
        subCubes.add(new Center(new ColorPosition(Red, Left)));
        subCubes.add(new Center(new ColorPosition(None, Top))); // empty core sub cube
        subCubes.add(new Center(new ColorPosition(Orange, Right)));
        subCubes.add(new Edge(new ColorPosition(Green, Left), new ColorPosition(Orange, Back)));
        subCubes.add(new Center(new ColorPosition(White, Back)));
        subCubes.add(new Edge(new ColorPosition(Yellow, Back), new ColorPosition(Orange, Right)));
        // bottom
        subCubes.add(new Corner(new ColorPosition(Blue, Left), new ColorPosition(Red, Front), new ColorPosition(White, Bottom)));
        subCubes.add(new Edge(new ColorPosition(Orange, Front), new ColorPosition(Blue, Bottom)));
        subCubes.add(new Corner(new ColorPosition(Red, Front), new ColorPosition(Yellow, Bottom), new ColorPosition(Blue, Right)));
        subCubes.add(new Edge(new ColorPosition(Blue, Left), new ColorPosition(Yellow, Bottom)));
        subCubes.add(new Center(new ColorPosition(Green, Bottom)));
        subCubes.add(new Edge(new ColorPosition(White, Bottom), new ColorPosition(Green, Right)));
        subCubes.add(new Corner(new ColorPosition(Orange, Left), new ColorPosition(Green, Bottom), new ColorPosition(Yellow, Back)));
        subCubes.add(new Edge(new ColorPosition(Red, Bottom), new ColorPosition(Blue, Back)));
        subCubes.add(new Corner(new ColorPosition(White, Bottom), new ColorPosition(Green, Right), new ColorPosition(Orange, Back)));
    }

    private boolean verifyColorCount(){
        int greenCount = 0, yellowCount = 0, redCount = 0, blueCount = 0, orangeCount = 0, whiteCount = 0;

        for (SubCube sc: subCubes) {
            redCount += Arrays.stream(sc.getAllColors()).filter(c -> c == Red).count();
            greenCount += Arrays.stream(sc.getAllColors()).filter(c -> c == Green).count();
            yellowCount += Arrays.stream(sc.getAllColors()).filter(c -> c == Yellow).count();
            blueCount += Arrays.stream(sc.getAllColors()).filter(c -> c == Blue).count();
            orangeCount += Arrays.stream(sc.getAllColors()).filter(c -> c == Orange).count();
            whiteCount += Arrays.stream(sc.getAllColors()).filter(c -> c == White).count();
        }

        int[] colorCount = new int[]{greenCount, yellowCount, redCount, blueCount, orangeCount, whiteCount};
        return Arrays.stream(colorCount).sum() == 54;
    }

    public void rotateSlice(Position position, RotationDirection direction) {
        csm.rotateSlice(position, direction);
    }

    public ComparableSliceResult compareSlice(Position position, ComparableSlice slice){
        return csm.compareSlice(position, slice);
    }

    public void display() {
        csm.displayAllSlices();
    }
}
