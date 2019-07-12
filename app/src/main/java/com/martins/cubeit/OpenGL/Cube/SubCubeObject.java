package com.martins.cubeit.OpenGL.Cube;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.Matrix;

import java.io.IOException;

import de.javagl.obj.Obj;
import de.javagl.obj.ObjData;
import de.javagl.obj.ObjReader;
import de.javagl.obj.ObjUtils;

public class SubCubeObject extends BaseObject {
    private static final String TAG = "SubCubeObject";

    public SubCubeObject(int id, Context context) {
        super(id);

        try {
            Bitmap bitmapTexture = BitmapFactory.decodeStream(context.getAssets().open("textures/cube.png"));

            if (bitmapTexture != null)
                setBitmapTexture(bitmapTexture);

            Obj board = ObjUtils.convertToRenderable(
                    ObjReader.read(context.getAssets().open("cube.obj")));

            if (board != null) {
                setIndexBuffer(ObjData.getFaceVertexIndices(board));
                setTextureBuffer(ObjData.getTexCoords(board, 2), 2);
                setVertexBuffer(ObjData.getVertices(board));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        scale(0.1f);
    }
}
