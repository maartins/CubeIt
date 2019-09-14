package com.martins.cubeit.OpenGL.Cube.Slices;

import com.martins.cubeit.CubeWare.CubeData.RotationDirection;
import com.martins.cubeit.OpenGL.Cube.SubCubeObject;

public class BottomSlice extends CubeSlice {

    @Override
    public void rotate(int angle) {
        for (SubCubeObject subCube : subCubes)
            subCube.rotate(angle, new int[]{0, 1, 0});
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

        top.setBottomSide(top_l, top_m, top_r);
        bottom.setBottomSide(bot_r, bot_m, bot_l);
        left.setBottomSide(bot_l, mid_l, top_l);
        right.setBottomSide(top_r, mid_r, bot_r);
    }
}