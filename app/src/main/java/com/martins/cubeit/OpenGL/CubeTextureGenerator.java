package com.martins.cubeit.OpenGL;

import android.graphics.Bitmap;

import com.martins.cubeit.CubeWare.CubeData.Color;
import com.martins.cubeit.CubeWare.CubeData.Position;
import com.martins.cubeit.CubeWare.SubCubes.SubCube;
import com.martins.cubeit.CubeWare.CubeData.SubCubeColorMap;


public class CubeTextureGenerator {

    public static Bitmap generateFromSubCube(SubCube subCube, int width, int height) {
        Bitmap texture = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);

        SubCubeColorMap colorMap = subCube.getAllColorsAndPos();

        Color topCubeColor = colorMap.getColor(Position.Top);
        Color backCubeColor = colorMap.getColor(Position.Back);
        Color bottomCubeColor = colorMap.getColor(Position.Bottom);
        Color rightCubeColor = colorMap.getColor(Position.Right);
        Color leftCubeColor = colorMap.getColor(Position.Left);
        Color frontCubeColor = colorMap.getColor(Position.Front);

        int topAndroidColor = matchColor(topCubeColor);
        int backAndroidColor = matchColor(backCubeColor);
        int bottomAndroidColor = matchColor(bottomCubeColor);
        int rightAndroidColor = matchColor(rightCubeColor);
        int leftAndroidColor = matchColor(leftCubeColor);
        int frontAndroidColor = matchColor(frontCubeColor);

        int blockWidth = width / 4;

        int[] topCoords = new int[] {blockWidth, blockWidth * 2, 0, blockWidth};
        int[] backCoords = new int[] {blockWidth, blockWidth * 2, blockWidth, blockWidth * 2};
        int[] bottomCoords = new int[] {blockWidth, blockWidth * 2, blockWidth * 2, blockWidth * 3};
        int[] rightCoords = new int[] {0, blockWidth, blockWidth, blockWidth * 2};
        int[] leftCoords = new int[] {blockWidth * 2, blockWidth * 3, blockWidth, blockWidth * 2};
        int[] frontCoords = new int[] {blockWidth, blockWidth * 2, blockWidth * 3, blockWidth * 4};

        setBitmapColors(texture, topCoords, topAndroidColor);
        setBitmapColors(texture, backCoords, backAndroidColor);
        setBitmapColors(texture, bottomCoords, bottomAndroidColor);
        setBitmapColors(texture, rightCoords, rightAndroidColor);
        setBitmapColors(texture, leftCoords, leftAndroidColor);
        setBitmapColors(texture, frontCoords, frontAndroidColor);

        return texture;
    }

    private static int matchColor(Color color) {
        switch (color) {
            case Red:
                return android.graphics.Color.RED;
            case Blue:
                return android.graphics.Color.BLUE;
            case Green:
                return android.graphics.Color.GREEN;
            case White:
                return android.graphics.Color.WHITE;
            case Orange:
                return android.graphics.Color.HSVToColor(new float[] {37.0f, 0.79f, 0.94f});
            case Yellow:
                return android.graphics.Color.YELLOW;
            case None:
                return android.graphics.Color.BLACK;
            default:
                return android.graphics.Color.BLACK;
        }
    }

    private static void setBitmapColors(Bitmap texture, int[] coords, int color) {
        for (int x = coords[0]; x < coords[1]; x++) {
            for (int y = coords[2]; y < coords[3]; y++) {
                    texture.setPixel(x, y, color);
            }
        }
    }
}
