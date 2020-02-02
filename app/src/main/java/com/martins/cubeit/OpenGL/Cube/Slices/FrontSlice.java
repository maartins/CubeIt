package com.martins.cubeit.OpenGL.Cube.Slices;

import android.util.Log;

import com.martins.cubeit.CubeWare.CubeData.RotationDirection;

public class FrontSlice extends CubeSlice {

    public FrontSlice() {
        super(1, new int[]{0, 0, 1});
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
        Log.d("asa 1", toString());
        flipSubCubes_Internal(direction);
        Log.d("asa 2", toString());
        top.setBottomSide(top_l, top_m, top_r);
        bottom.setTopSide(bot_l, bot_m, bot_r);
        left.setRightSide(top_l, mid_l, bot_l);
        right.setLeftSide(top_r, mid_r, bot_r);
        Log.d("asa 3", toString());
    }
}