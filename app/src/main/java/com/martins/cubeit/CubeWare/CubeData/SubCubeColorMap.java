package com.martins.cubeit.CubeWare.CubeData;

import com.martins.cubeit.CubeWare.CubeData.Color;
import com.martins.cubeit.CubeWare.CubeData.ColorPosition;
import com.martins.cubeit.CubeWare.CubeData.Position;

import java.util.EnumMap;
import java.util.Map;
import static com.martins.cubeit.CubeWare.CubeData.Position.*;

public class SubCubeColorMap {
    private Map<Position, Color> colors = new EnumMap<>(Position.class);

    public SubCubeColorMap(){
        colors.put(Top, Color.None);
        colors.put(Bottom, Color.None);
        colors.put(Front, Color.None);
        colors.put(Back, Color.None);
        colors.put(Left, Color.None);
        colors.put(Right, Color.None);
    }

    public void setColorWithPosition(ColorPosition cp) {
        colors.replace(cp.getPosition(), cp.getColor());
    }

    public Color getColor(Position pos) {
        return colors.get(pos);
    }

    public Color[] getAllColors() {
        return new Color[]{colors.get(Top), colors.get(Bottom), colors.get(Front), colors.get(Back), colors.get(Left), colors.get(Right)};
    }

    @Override
    public String toString() {
        return "[top: " + colors.get(Top).name()
                + ", bottom: " + colors.get(Bottom)
                + ", front: " + colors.get(Front)
                + ", back: " + colors.get(Back)
                + ", left: " + colors.get(Left)
                + ", right: " + colors.get(Right) + "]";
    }
}
