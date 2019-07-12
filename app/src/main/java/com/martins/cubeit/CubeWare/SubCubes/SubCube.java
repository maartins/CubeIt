package com.martins.cubeit.CubeWare.SubCubes;

import com.martins.cubeit.CubeWare.CubeData.Color;
import com.martins.cubeit.CubeWare.CubeData.ColorPosition;
import com.martins.cubeit.CubeWare.CubeData.Position;
import com.martins.cubeit.CubeWare.CubeData.RotationDirection;
import com.martins.cubeit.CubeWare.CubeData.SubCubeColorMap;
import com.martins.cubeit.CubeWare.CubeData.SubCubeType;

public class SubCube {
    private static int global_id = 0;
    private int id;
    private SubCubeType type;
    private SubCubeColorMap colors = new SubCubeColorMap();

    public SubCube(ColorPosition cp1){
        id = global_id++;
        type = SubCubeType.Center;

        colors.setColorWithPosition(cp1);
    }

    public SubCube(ColorPosition cp1, ColorPosition cp2){
        id = global_id++;
        type = SubCubeType.Edge;

        colors.setColorWithPosition(cp1);
        colors.setColorWithPosition(cp2);
    }

    public SubCube(ColorPosition cp1, ColorPosition cp2, ColorPosition cp3){
        id = global_id++;
        type = SubCubeType.Corner;

        colors.setColorWithPosition(cp1);
        colors.setColorWithPosition(cp2);
        colors.setColorWithPosition(cp3);
    }

    public void rotateTop(RotationDirection direction) {
        Color tmp0 = colors.getColor(Position.Back);
        Color tmp1 = colors.getColor(Position.Left);
        Color tmp2 = colors.getColor(Position.Front);
        Color tmp3 = colors.getColor(Position.Right);

        if(direction == RotationDirection.ACW){
            colors.setColorWithPosition(new ColorPosition(tmp3, Position.Back));
            colors.setColorWithPosition(new ColorPosition(tmp0, Position.Left));
            colors.setColorWithPosition(new ColorPosition(tmp1, Position.Front));
            colors.setColorWithPosition(new ColorPosition(tmp2, Position.Right));
        } else {
            colors.setColorWithPosition(new ColorPosition(tmp1, Position.Back));
            colors.setColorWithPosition(new ColorPosition(tmp2, Position.Left));
            colors.setColorWithPosition(new ColorPosition(tmp3, Position.Front));
            colors.setColorWithPosition(new ColorPosition(tmp0, Position.Right));
        }
    }

    public void rotateBottom(RotationDirection direction) {
        Color tmp0 = colors.getColor(Position.Back);
        Color tmp1 = colors.getColor(Position.Left);
        Color tmp2 = colors.getColor(Position.Front);
        Color tmp3 = colors.getColor(Position.Right);

        if (direction == RotationDirection.CCW) {
            colors.setColorWithPosition(new ColorPosition(tmp3, Position.Back));
            colors.setColorWithPosition(new ColorPosition(tmp0, Position.Left));
            colors.setColorWithPosition(new ColorPosition(tmp1, Position.Front));
            colors.setColorWithPosition(new ColorPosition(tmp2, Position.Right));
        } else {
            colors.setColorWithPosition(new ColorPosition(tmp1, Position.Back));
            colors.setColorWithPosition(new ColorPosition(tmp2, Position.Left));
            colors.setColorWithPosition(new ColorPosition(tmp3, Position.Front));
            colors.setColorWithPosition(new ColorPosition(tmp0, Position.Right));
        }
    }

    public void rotateRight(RotationDirection direction) {
        Color tmp0 = colors.getColor(Position.Back);
        Color tmp1 = colors.getColor(Position.Top);
        Color tmp2 = colors.getColor(Position.Front);
        Color tmp3 = colors.getColor(Position.Bottom);

        if (direction == RotationDirection.ACW) {
            colors.setColorWithPosition(new ColorPosition(tmp3, Position.Back));
            colors.setColorWithPosition(new ColorPosition(tmp0, Position.Top));
            colors.setColorWithPosition(new ColorPosition(tmp1, Position.Front));
            colors.setColorWithPosition(new ColorPosition(tmp2, Position.Bottom));
        } else {
            colors.setColorWithPosition(new ColorPosition(tmp1, Position.Back));
            colors.setColorWithPosition(new ColorPosition(tmp2, Position.Top));
            colors.setColorWithPosition(new ColorPosition(tmp3, Position.Front));
            colors.setColorWithPosition(new ColorPosition(tmp0, Position.Bottom));
        }
    }

    public void rotateLeft(RotationDirection direction) {
        Color tmp0 = colors.getColor(Position.Back);
        Color tmp1 = colors.getColor(Position.Top);
        Color tmp2 = colors.getColor(Position.Front);
        Color tmp3 = colors.getColor(Position.Bottom);

        if (direction == RotationDirection.CCW) {
            colors.setColorWithPosition(new ColorPosition(tmp3, Position.Back));
            colors.setColorWithPosition(new ColorPosition(tmp0, Position.Top));
            colors.setColorWithPosition(new ColorPosition(tmp1, Position.Front));
            colors.setColorWithPosition(new ColorPosition(tmp2, Position.Bottom));
        } else {
            colors.setColorWithPosition(new ColorPosition(tmp1, Position.Back));
            colors.setColorWithPosition(new ColorPosition(tmp2, Position.Top));
            colors.setColorWithPosition(new ColorPosition(tmp3, Position.Front));
            colors.setColorWithPosition(new ColorPosition(tmp0, Position.Bottom));
        }
    }

    public void rotateFront(RotationDirection direction) {
        Color tmp0 = colors.getColor(Position.Right);
        Color tmp1 = colors.getColor(Position.Top);
        Color tmp2 = colors.getColor(Position.Left);
        Color tmp3 = colors.getColor(Position.Bottom);

        if (direction == RotationDirection.ACW) {
            colors.setColorWithPosition(new ColorPosition(tmp3, Position.Right));
            colors.setColorWithPosition(new ColorPosition(tmp0, Position.Top));
            colors.setColorWithPosition(new ColorPosition(tmp1, Position.Left));
            colors.setColorWithPosition(new ColorPosition(tmp2, Position.Bottom));
        } else {
            colors.setColorWithPosition(new ColorPosition(tmp1, Position.Right));
            colors.setColorWithPosition(new ColorPosition(tmp2, Position.Top));
            colors.setColorWithPosition(new ColorPosition(tmp3, Position.Left));
            colors.setColorWithPosition(new ColorPosition(tmp0, Position.Bottom));
        }
    }

    public void rotateBack(RotationDirection direction) {
        Color tmp0 = colors.getColor(Position.Right);
        Color tmp1 = colors.getColor(Position.Top);
        Color tmp2 = colors.getColor(Position.Left);
        Color tmp3 = colors.getColor(Position.Bottom);

        if (direction == RotationDirection.CCW) {
            colors.setColorWithPosition(new ColorPosition(tmp3, Position.Right));
            colors.setColorWithPosition(new ColorPosition(tmp0, Position.Top));
            colors.setColorWithPosition(new ColorPosition(tmp1, Position.Left));
            colors.setColorWithPosition(new ColorPosition(tmp2, Position.Bottom));
        } else {
            colors.setColorWithPosition(new ColorPosition(tmp1, Position.Right));
            colors.setColorWithPosition(new ColorPosition(tmp2, Position.Top));
            colors.setColorWithPosition(new ColorPosition(tmp3, Position.Left));
            colors.setColorWithPosition(new ColorPosition(tmp0, Position.Bottom));
        }
    }

    public SubCubeType getType() {
        return type;
    }

    public Color getSide(Position position) {
        return colors.getColor(position);
    }

    public Color[] getAllColors() {
        return colors.getAllColors();
    }

    public SubCubeColorMap getAllColorsAndPos() {
        return colors;
    }

    public int getId(){
        return id;
    }

    public String coloursToString() {
        return colors.toString();
    }
}
