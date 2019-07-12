package com.martins.cubeit.OpenGL.Cube.Slices;

import com.martins.cubeit.CubeWare.CubeData.RotationDirection;
import com.martins.cubeit.OpenGL.Cube.SubCubeObject;

public class TopSlice extends CubeSlice {

    @Override
    public void rotate(int angle) {
        for (SubCubeObject subCube: subCubes)
            subCube.rotate(angle, new int[]{0, 1, 0});
    }

    @Override
    void flipSubCubes(RotationDirection direction) {
        flipSubCubes_Internal(direction);

        top.setTopSide(top_r, top_m, top_l);
        bottom.setTopSide(bot_l, bot_m, bot_r);
        left.setTopSide(top_l, mid_l, bot_l);
        right.setTopSide(bot_r, mid_r, top_r);
    }
}
