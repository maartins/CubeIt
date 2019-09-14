package com.martins.cubeit.OpenGL.Cube.Slices;

import com.martins.cubeit.CubeWare.CubeData.RotationDirection;
import com.martins.cubeit.OpenGL.Cube.SubCubeObject;

public class RightSlice extends CubeSlice {

    @Override
    public void rotate(int angle) {
        for (SubCubeObject subCube : subCubes)
            subCube.rotate(angle, new int[]{1, 0, 0});
    }

    @Override
    void rotate(RotationDirection direction) {
        if (direction == RotationDirection.CCW)
            rotate(getDefaultRotationSpeed());
        else
            rotate(-getDefaultRotationSpeed());
    }

    @Override
    void flipSubCubes(RotationDirection direction) {
        flipSubCubes_Internal(direction);

        top.setRightSide(top_r, top_m, top_l);
        bottom.setRightSide(bot_l, bot_m, bot_r);
        left.setRightSide(top_l, mid_l, bot_l);
        right.setLeftSide(top_r, mid_r, bot_r);
    }
}