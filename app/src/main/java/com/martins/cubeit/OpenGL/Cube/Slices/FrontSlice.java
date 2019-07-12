package com.martins.cubeit.OpenGL.Cube.Slices;

import com.martins.cubeit.CubeWare.CubeData.RotationDirection;
import com.martins.cubeit.OpenGL.Cube.SubCubeObject;

public class FrontSlice extends CubeSlice {

    @Override
    public void rotate(int angle) {
        for (SubCubeObject subCube: subCubes)
            subCube.rotate(angle, new int[]{0, 0, 1});
    }

    @Override
    void flipSubCubes(RotationDirection direction) {
        flipSubCubes_Internal(direction);

        top.setBottomSide(top_l, top_m, top_r);
        bottom.setTopSide(bot_l, bot_m, bot_r);
        left.setRightSide(top_l, mid_l, bot_l);
        right.setLeftSide(top_r, mid_r, bot_r);
    }
}