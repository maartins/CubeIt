package com.martins.cubeit.OpenGL.Cube;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.Matrix;

import com.martins.cubeit.OpenGL.Mesh;
import com.martins.cubeit.OpenGL.Texture;
import com.martins.cubeit.OpenGL.TransformationUtils;

import java.io.IOException;

import de.javagl.obj.Obj;
import de.javagl.obj.ObjData;
import de.javagl.obj.ObjReader;
import de.javagl.obj.ObjUtils;

public class SubCubeObject extends BaseObject {
    private static final String TAG = "SubCubeObject";

    public SubCubeObject(int id) {
        super(id);

        setMesh(new Mesh());
        setTexture(new Texture());
        TransformationUtils.scale(this, 0.1f);
    }
}
