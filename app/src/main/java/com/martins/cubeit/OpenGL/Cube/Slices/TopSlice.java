package com.martins.cubeit.OpenGL.Cube.Slices;

import com.martins.cubeit.CubeWare.CubeData.RotationDirection;

public class TopSlice extends CubeSlice {

    public TopSlice() {
        super(1, new int[]{0, 1, 0});
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
        bottom.setTopSide(bot_l, bot_m, bot_r);
        left.setTopSide(top_l, mid_l, bot_l);
        right.setTopSide(bot_r, mid_r, top_r);
    }
}
