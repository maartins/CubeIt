package com.martins.cubeit.OpenGL.Cube.Slices;

import android.util.Log;

import com.martins.cubeit.CubeWare.CubeData.RotationDirection;
import com.martins.cubeit.OpenGL.Vector3;

public class FrontSlice extends CubeSlice {

    public FrontSlice() {
        super(1, new Vector3(0, 0, 1), new Vector3(0, 0, 0));
    }

    @Override
    void rotate(RotationDirection direction) {
        if (direction == RotationDirection.CCW)
            rotate(getRotationSpeed());
        else
            rotate(-getRotationSpeed());
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