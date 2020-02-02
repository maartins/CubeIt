package com.martins.cubeit.OpenGL.Cube.Slices;

import android.util.Log;

import com.martins.cubeit.CubeWare.CubeData.RotationDirection;
import com.martins.cubeit.OpenGL.Cube.SubCubeObject;
import com.martins.cubeit.OpenGL.TransformationUtils;

import java.util.ArrayList;

public abstract class CubeSlice {
    private static final String TAG = "CubeSlice";

    protected SubCubeObject top_l, top_m, top_r;
    protected SubCubeObject mid_l, mid_m, mid_r;
    protected SubCubeObject bot_l, bot_m, bot_r;

    protected CubeSlice top, left, right, bottom;

    private ArrayList<SubCubeObject> subCubes = new ArrayList<>();

    private int[] rotationAxis;

    private int rotationSpeed;

    protected CubeSlice(int rotationSpeed, int[] rotationAxis) {
        this.rotationAxis = rotationAxis;
        this.rotationSpeed = rotationSpeed;
    }

    public void addRelations(CubeSlice top, CubeSlice left, CubeSlice right, CubeSlice bottom) {
        this.top = top;
        this.left = left;
        this.right = right;
        this.bottom = bottom;
    }

    void setLeftSide(SubCubeObject top_l, SubCubeObject mid_l, SubCubeObject bot_l) {
        this.top_l = top_l;
        this.mid_l = mid_l;
        this.bot_l = bot_l;
        updateSubCubeList();
    }

    void setRightSide(SubCubeObject top_r, SubCubeObject mid_r, SubCubeObject bot_r) {
        this.top_r = top_r;
        this.mid_r = mid_r;
        this.bot_r = bot_r;
        updateSubCubeList();
    }

    void setTopSide(SubCubeObject top_l, SubCubeObject top_m, SubCubeObject top_r) {
        this.top_l = top_l;
        this.top_m = top_m;
        this.top_r = top_r;
        updateSubCubeList();
    }

    void setBottomSide(SubCubeObject bot_l, SubCubeObject bot_m, SubCubeObject bot_r) {
        this.bot_l = bot_l;
        this.bot_m = bot_m;
        this.bot_r = bot_r;
        updateSubCubeList();
    }

    int getRotationSpeed() {
        return rotationSpeed;
    }

    void flipSubCubes_Internal(RotationDirection direction) {
        SubCubeObject ttop_l = top_l, ttop_m = top_m, ttop_r = top_r;
        SubCubeObject tmid_l = mid_l, tmid_m = mid_m, tmid_r = mid_r;
        SubCubeObject tbot_l = bot_l, tbot_m = bot_m, tbot_r = bot_r;

        if(direction == RotationDirection.ACW) {
            top_l = tbot_l; top_m = tmid_l; top_r = ttop_l;
            mid_l = tbot_m; mid_m = tmid_m; mid_r = ttop_m;
            bot_l = tbot_r; bot_m = tmid_r; bot_r = ttop_r;
        } else {
            top_l = ttop_r; top_m = tmid_r; top_r = tbot_r;
            mid_l = ttop_m; mid_m = tmid_m; mid_r = tbot_m;
            bot_l = ttop_l; bot_m = tmid_l; bot_r = tbot_l;
        }
    }

    private void updateSubCubeList(){
        subCubes.clear();
        subCubes.add(top_l);
        subCubes.add(top_m);
        subCubes.add(top_r);
        subCubes.add(mid_l);
        subCubes.add(mid_m);
        subCubes.add(mid_r);
        subCubes.add(bot_l);
        subCubes.add(bot_m);
        subCubes.add(bot_r);
    }

    public void addTopSubCubes(SubCubeObject top_l, SubCubeObject top_m, SubCubeObject top_r){
        this.top_l = top_l;
        this.top_m = top_m;
        this.top_r = top_r;

        subCubes.add(this.top_l);
        subCubes.add(this.top_m);
        subCubes.add(this.top_r);
    }

    public void addMidSubCubes(SubCubeObject mid_l, SubCubeObject mid_m, SubCubeObject mid_r){
        this.mid_l = mid_l;
        this.mid_m = mid_m;
        this.mid_r = mid_r;

        subCubes.add(this.mid_l);
        subCubes.add(this.mid_m);
        subCubes.add(this.mid_r);
    }

    public void addBottomSubCubes(SubCubeObject bot_l, SubCubeObject bot_m, SubCubeObject bot_r){
        this.bot_l = bot_l;
        this.bot_m = bot_m;
        this.bot_r = bot_r;

        subCubes.add(this.bot_l);
        subCubes.add(this.bot_m);
        subCubes.add(this.bot_r);
    }

    protected void rotate(int angle) {
        for (SubCubeObject subCube : subCubes)
            TransformationUtils.rotate(subCube, angle, rotationAxis);
    }

    public ArrayList<SubCubeObject> getSubCubes() {
        return subCubes;
    }

    abstract void rotate(RotationDirection direction);
    abstract void flipSubCubes(RotationDirection direction);

    public String toString() {
        return "tl: " + top_l.getId() + "\t tm: " + top_m.getId() + "\t tr: " + top_r.getId() + "\n" +
               "ml: " + mid_l.getId() + "\t mm: " + mid_m.getId() + "\t mr: " + mid_r.getId() + "\n" +
               "bl: " + bot_l.getId() + "\t bm: " + bot_m.getId() + "\t br: " + bot_r.getId();
    }
}
