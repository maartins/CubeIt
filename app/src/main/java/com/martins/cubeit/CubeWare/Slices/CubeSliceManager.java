package com.martins.cubeit.CubeWare.Slices;

import android.util.Log;

import com.martins.cubeit.CubeWare.CubeData.ColorPosition;
import com.martins.cubeit.CubeWare.CubeData.Position;
import com.martins.cubeit.CubeWare.CubeData.RotationDirection;
import com.martins.cubeit.CubeWare.Main.ComparableSliceResult;
import com.martins.cubeit.CubeWare.Main.ComparableSlice;
import com.martins.cubeit.CubeWare.SubCubes.Center;
import com.martins.cubeit.CubeWare.SubCubes.SubCube;

import java.util.ArrayList;
import java.util.HashMap;

import static com.martins.cubeit.CubeWare.CubeData.Color.None;
import static com.martins.cubeit.CubeWare.CubeData.Position.*;

public class CubeSliceManager {
    private static final String TAG = "CubeSliceManager";

    private HashMap<Position, CubeSlice> slices = new HashMap<>();

    public CubeSliceManager(ArrayList<SubCube> subCubes){
        slices.put(Top, new TopCubeSlice(subCubes.get(6), subCubes.get(7), subCubes.get(8),
                                         subCubes.get(3), subCubes.get(4), subCubes.get(5),
                                         subCubes.get(0), subCubes.get(1), subCubes.get(2)));
        slices.put(Bottom, new BottomCubeSlice(subCubes.get(18), subCubes.get(19), subCubes.get(20),
                                               subCubes.get(21), subCubes.get(22), subCubes.get(23),
                                               subCubes.get(24), subCubes.get(25), subCubes.get(26)));
        slices.put(Left, new LeftCubeSlice(subCubes.get(6), subCubes.get(3), subCubes.get(0),
                                           subCubes.get(15), subCubes.get(12), subCubes.get(9),
                                           subCubes.get(24), subCubes.get(21), subCubes.get(18)));
        slices.put(Right, new RightCubeSlice(subCubes.get(2), subCubes.get(5), subCubes.get(8),
                                             subCubes.get(11), subCubes.get(14), subCubes.get(17),
                                             subCubes.get(20), subCubes.get(23), subCubes.get(26)));
        slices.put(Front, new FrontCubeSlice(subCubes.get(0), subCubes.get(1), subCubes.get(2),
                                             subCubes.get(9), subCubes.get(10), subCubes.get(11),
                                             subCubes.get(18), subCubes.get(19), subCubes.get(20)));
        slices.put(Back, new BackCubeSlice(subCubes.get(8), subCubes.get(7), subCubes.get(6),
                                           subCubes.get(17), subCubes.get(16), subCubes.get(15),
                                           subCubes.get(26), subCubes.get(25), subCubes.get(24)));

        slices.get(Top).setRelations(slices.get(Back), slices.get(Front), slices.get(Left), slices.get(Right));
        slices.get(Bottom).setRelations(slices.get(Front), slices.get(Back), slices.get(Left), slices.get(Right));
        slices.get(Front).setRelations(slices.get(Top), slices.get(Bottom), slices.get(Left), slices.get(Right));
        slices.get(Back).setRelations(slices.get(Top), slices.get(Bottom), slices.get(Right), slices.get(Left));
        slices.get(Left).setRelations(slices.get(Top), slices.get(Bottom), slices.get(Back), slices.get(Front));
        slices.get(Right).setRelations(slices.get(Top), slices.get(Bottom), slices.get(Front), slices.get(Back));
    }

    public void rotateSlice(Position position, RotationDirection direction){
        Log.d(TAG, "----- Rotating " + position.name() + ": " + direction.name());
        slices.get(position).rotate(direction);
        displayAllSlices();
    }

    public ComparableSliceResult compareSlice(Position position, ComparableSlice slice) {
        Log.d(TAG, "----- Comparing " + position.name() + " slices");
        return slices.get(position).compareSlice(slice);
    }

    public void displayAllSlices() {
        Log.d(TAG, "----- Displaying all slices");
        slices.forEach((p, s) -> s.display());
    }

    public ArrayList<SubCube> getSubCubes() {
        ArrayList<SubCube> subcubes = new ArrayList<>();

        SubCube[] topLayer = slices.get(Top).getSubCubes();
        subcubes.add(topLayer[6]);
        subcubes.add(topLayer[7]);
        subcubes.add(topLayer[8]);
        subcubes.add(topLayer[3]);
        subcubes.add(topLayer[4]);
        subcubes.add(topLayer[5]);
        subcubes.add(topLayer[0]);
        subcubes.add(topLayer[1]);
        subcubes.add(topLayer[2]);

        SubCube[] midLayerBack = slices.get(Back).getSubCubes();
        SubCube[] midLayerLeft = slices.get(Left).getSubCubes();
        SubCube[] midLayerRight = slices.get(Right).getSubCubes();
        SubCube[] midLayerFront = slices.get(Front).getSubCubes();
        subcubes.add(midLayerLeft[5]);
        subcubes.add(midLayerFront[4]);
        subcubes.add(midLayerRight[3]);
        subcubes.add(midLayerLeft[4]);
        subcubes.add(new Center(new ColorPosition(None, Center)));
        subcubes.add(midLayerRight[4]);
        subcubes.add(midLayerLeft[3]);
        subcubes.add(midLayerBack[4]);
        subcubes.add(midLayerRight[5]);

        SubCube[] bottomLayer = slices.get(Bottom).getSubCubes();
        for (int i = 0; i < bottomLayer.length; i++)
            subcubes.add(bottomLayer[i]);

        return subcubes;
    }
}
