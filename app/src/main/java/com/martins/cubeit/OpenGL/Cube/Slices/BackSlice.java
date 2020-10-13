package com.martins.cubeit.OpenGL.Cube.Slices;

import com.martins.cubeit.CubeWare.CubeData.RotationDirection;
import com.martins.cubeit.OpenGL.Vector3;

public class BackSlice extends CubeSlice {

    public BackSlice() {
        super(1, new Vector3(0, 0, 1), new Vector3(0, 0, 0));
    }

    @Override
    void rotate(RotationDirection direction) {
        if (direction == RotationDirection.CCW)
            rotate(-getRotationSpeed());
        else
            rotate(getRotationSpeed());
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