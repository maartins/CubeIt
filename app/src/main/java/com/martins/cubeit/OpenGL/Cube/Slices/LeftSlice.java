package com.martins.cubeit.OpenGL.Cube.Slices;

import com.martins.cubeit.CubeWare.CubeData.RotationDirection;
import com.martins.cubeit.OpenGL.Cube.SubCubeObject;

public class LeftSlice extends CubeSlice {

    @Override
    public void rotate(int angle) {
        for (SubCubeObject subCube: subCubes)
            subCube.rotate(angle, new int[]{1, 0, 0});
    }

    @Override
    void flipSubCubes(RotationDirection direction) {
        flipSubCubes_Internal(direction);

        top.setLeftSide(top_l, top_m, top_r);
        bottom.setLeftSide(bot_r, bot_m, bot_l);
        left.setRightSide(top_l, mid_l, bot_l);
        right.setLeftSide(top_r, mid_r, bot_r);
    }
}