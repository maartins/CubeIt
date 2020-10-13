package com.martins.cubeit.OpenGL.Cube;

import com.martins.cubeit.OpenGL.BaseObject;
import com.martins.cubeit.OpenGL.Mesh;
import com.martins.cubeit.OpenGL.Texture;
import com.martins.cubeit.OpenGL.TransformationUtils;

public class SubCubeObject extends BaseObject {
    private static final String TAG = "SubCubeObject";

    public SubCubeObject(int id) {
        super(id);

        setMesh(new Mesh());
        setTexture(new Texture());
        TransformationUtils.scale(this, 0.1f);
    }
}
