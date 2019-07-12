package com.martins.cubeit.CubeWare.Main;

import com.martins.cubeit.CubeWare.CubeData.CubeType;

import static com.martins.cubeit.CubeWare.CubeData.Position.*;
import static com.martins.cubeit.CubeWare.CubeData.RotationDirection.*;
import static com.martins.cubeit.CubeWare.CubeData.Color.*;

public class Testing {
    public static void main() {
        //testingHardcoded();
        testingScrable();
    }

    private static void testingScrable() {
        Cube scrabledCube = ScrableReader.generateCubeFromString("U1U1F1"); // U1U1R3L1F1F1U1F3B3R1L1U1U1R1U1D3R1L3D1R3L3D3
        scrabledCube.display();
    }

    private static void testingHardcoded() {
        Cube cube = new Cube(CubeType.Hardcoded);

        /*cube.rotate(Bottom, CCW);
        cube.rotate(Left, CCW);
        cube.rotate(Top, CCW);
        cube.rotate(Right, CCW);
        cube.rotate(Back, CCW);
        cube.rotate(Front, CCW);*/

        cube.rotateSlice(Bottom, ACW);
        cube.rotateSlice(Left, ACW);
        cube.rotateSlice(Top, ACW);
        cube.rotateSlice(Right, ACW);
        cube.rotateSlice(Back, ACW);
        cube.rotateSlice(Front, ACW);

        ComparableSlice newSlice = new ComparableSlice(Green, Green, Green, Green, Green, Green, Green, Green, Green);

        ComparableSliceResult frontCheck = cube.compareSlice(Front, newSlice);
        frontCheck.displayResult();
    }
}
