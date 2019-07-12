package com.martins.cubeit.CubeWare.Slices;

import android.util.Log;

import com.martins.cubeit.CubeWare.CubeData.Position;
import com.martins.cubeit.CubeWare.CubeData.RotationDirection;
import com.martins.cubeit.CubeWare.Main.ComparableSliceResult;
import com.martins.cubeit.CubeWare.Main.ComparableSlice;
import com.martins.cubeit.CubeWare.SubCubes.SubCube;

public class TopCubeSlice extends CubeSlice {
    private static final String TAG = "TopCubeSlice";
    private static final Position POSITION = Position.Top;

    public TopCubeSlice(SubCube top_l, SubCube top_m, SubCube top_r, SubCube mid_l, SubCube mid_m, SubCube mid_r, SubCube bot_l, SubCube bot_m, SubCube bot_r) {
        super(top_l, top_m, top_r, mid_l, mid_m, mid_r, bot_l, bot_m, bot_r);
    }

    @Override
    public void rotate(RotationDirection direction) {
        subCubes.forEach(sc -> sc.rotateTop(direction));

        SubCube ttop_l = top_l, ttop_m = top_m, ttop_r = top_r;
        SubCube tmid_l = mid_l, tmid_m = mid_m, tmid_r = mid_r;
        SubCube tbot_l = bot_l, tbot_m = bot_m, tbot_r = bot_r;

        if(direction == RotationDirection.CCW) {
            top_l = tbot_l; top_m = tmid_l; top_r = ttop_l;
            mid_l = tbot_m; mid_m = tmid_m; mid_r = ttop_m;
            bot_l = tbot_r; bot_m = tmid_r; bot_r = ttop_r;
        } else {
            top_l = ttop_r; top_m = tmid_r; top_r = tbot_r;
            mid_l = ttop_m; mid_m = tmid_m; mid_r = tbot_m;
            bot_l = ttop_l; bot_m = tmid_l; bot_r = tbot_l;
        }

        top.setTopSide(top_r, top_m, top_l);
        bottom.setTopSide(bot_l, bot_m, bot_r);
        left.setTopSide(top_l, mid_l, bot_l);
        right.setTopSide(bot_r, mid_r, top_r);
    }

    @Override
    public ComparableSliceResult compareSlice(ComparableSlice slice) {
        boolean testTop_l = top_l.getSide(POSITION) == slice.getTop_l();
        boolean testTop_m = top_m.getSide(POSITION) == slice.getTop_m();
        boolean testTop_r = top_r.getSide(POSITION) == slice.getTop_r();

        boolean testMid_l = mid_l.getSide(POSITION) == slice.getMid_l();
        boolean testMid_m = mid_m.getSide(POSITION) == slice.getMid_m();
        boolean testMid_r = mid_r.getSide(POSITION) == slice.getMid_r();

        boolean testBot_l = bot_l.getSide(POSITION) == slice.getBot_l();
        boolean testBot_m = bot_m.getSide(POSITION) == slice.getBot_m();
        boolean testBot_r = bot_r.getSide(POSITION) == slice.getBot_r();

        return new ComparableSliceResult(testTop_l, testTop_m, testTop_r,
                                         testMid_l, testMid_m, testMid_r,
                                         testBot_l, testBot_m, testBot_r);
    }

    @Override
    public void display() {
        Log.d(TAG, "-- " + POSITION.name());
        Log.d(TAG, top_l.getSide(POSITION).name() + " \t" + top_m.getSide(POSITION).name() + " \t" + top_r.getSide(POSITION).name());
        Log.d(TAG, mid_l.getSide(POSITION).name() + " \t" + mid_m.getSide(POSITION).name() + " \t" + mid_r.getSide(POSITION).name());
        Log.d(TAG, bot_l.getSide(POSITION).name() + " \t" + bot_m.getSide(POSITION).name() + " \t" + bot_r.getSide(POSITION).name());
        Log.d(TAG, "••••••••••••••••••••••••••");
    }
}
