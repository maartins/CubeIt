package com.martins.cubeit.OpenGL.Cube.Slices;

import com.martins.cubeit.CubeWare.CubeData.RotationDirection;
import com.martins.cubeit.OpenGL.Cube.SubCubeObject;

public class BackSlice extends CubeSlice {

    @Override
    public void rotate(int angle) {
        for (SubCubeObject subCube: subCubes)
            subCube.rotate(angle, new int[]{0, 0, 1});
    }

    @Override
    void flipSubCubes(RotationDirection direction) {
        flipSubCubes_Internal(direction);

        top.setTopSide(top_r, top_m, top_l);
        bottom.setBottomSide(bot_r, bot_m, bot_l);
        left.setRightSide(top_l, mid_l, bot_l);
        right.setLeftSide(top_r, mid_r, bot_r);
    }
}