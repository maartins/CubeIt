package com.martins.cubeit.CubeWare.Slices;

import com.martins.cubeit.CubeWare.CubeData.RotationDirection;
import com.martins.cubeit.CubeWare.Main.ComparableSliceResult;
import com.martins.cubeit.CubeWare.Main.ComparableSlice;
import com.martins.cubeit.CubeWare.SubCubes.SubCube;

import java.util.ArrayList;

/**
 * Represents a side of a cube in a 2D space
 */

public abstract class CubeSlice {
    SubCube top_l, top_m, top_r;
    SubCube mid_l, mid_m, mid_r;
    SubCube bot_l, bot_m, bot_r;

    protected ArrayList<SubCube> subCubes = new ArrayList<>();

    protected CubeSlice top, bottom, left, right;

    CubeSlice(SubCube top_l, SubCube top_m, SubCube top_r, SubCube mid_l, SubCube mid_m, SubCube mid_r, SubCube bot_l, SubCube bot_m, SubCube bot_r) {
        this.top_l = top_l;
        this.top_m = top_m;
        this.top_r = top_r;
        this.mid_l = mid_l;
        this.mid_m = mid_m;
        this.mid_r = mid_r;
        this.bot_l = bot_l;
        this.bot_m = bot_m;
        this.bot_r = bot_r;

        subCubes.add(this.top_l);
        subCubes.add(this.top_m);
        subCubes.add(this.top_r);
        subCubes.add(this.mid_l);
        subCubes.add(this.mid_m);
        subCubes.add(this.mid_r);
        subCubes.add(this.bot_l);
        subCubes.add(this.bot_m);
        subCubes.add(this.bot_r);
    }

    void setRelations(CubeSlice top, CubeSlice bottom, CubeSlice left, CubeSlice right){
        this.top = top;
        this.bottom = bottom;
        this.left = left;
        this.right = right;
    }

    void setLeftSide(SubCube top_l, SubCube mid_l, SubCube bot_l) {
        this.top_l = top_l;
        this.mid_l = mid_l;
        this.bot_l = bot_l;
        updateSubCubeList();
    }

    void setRightSide(SubCube top_r, SubCube mid_r, SubCube bot_r) {
        this.top_r = top_r;
        this.mid_r = mid_r;
        this.bot_r = bot_r;
        updateSubCubeList();
    }

    void setTopSide(SubCube top_l, SubCube top_m, SubCube top_r) {
        this.top_l = top_l;
        this.top_m = top_m;
        this.top_r = top_r;
        updateSubCubeList();
    }

    void setBottomSide(SubCube bot_l, SubCube bot_m, SubCube bot_r) {
        this.bot_l = bot_l;
        this.bot_m = bot_m;
        this.bot_r = bot_r;
        updateSubCubeList();
    }

    protected SubCube[] getSubCubes() {
        //                    0      1      2      3      4      5      6      7      8
        return new SubCube[] {top_l, top_m, top_r, mid_l, mid_m, mid_r, bot_l, bot_m, bot_r};
    }

    private void updateSubCubeList(){
        subCubes.clear();
        subCubes.add(this.top_l);
        subCubes.add(this.top_m);
        subCubes.add(this.top_r);
        subCubes.add(this.mid_l);
        subCubes.add(this.mid_m);
        subCubes.add(this.mid_r);
        subCubes.add(this.bot_l);
        subCubes.add(this.bot_m);
        subCubes.add(this.bot_r);
    }

    public abstract void rotate(RotationDirection direction);
    public abstract ComparableSliceResult compareSlice(ComparableSlice slice);
    public abstract void display();
}
