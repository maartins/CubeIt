package com.martins.cubeit.OpenGL;

import android.graphics.Bitmap;
import android.util.Log;

import com.martins.cubeit.AssetLoader.AssetStorage;

import de.javagl.obj.Obj;
import de.javagl.obj.ObjData;

public class BoxObject extends BaseObject {
    public BoxObject(int id) {
        super(id);

        Obj object = AssetStorage.getAsset("cube.obj");
        Bitmap texture = AssetStorage.getAsset("textures/cube.png");

        assert object != null;
        assert texture != null;

        this.setMesh(new Mesh());
        this.setTexture(new Texture());

        this.getTexture().setBitmapTexture(texture);
        this.getTexture().setTextureBuffer(ObjData.getTexCoords(object, 2), 2);
        this.getMesh().setIndexBuffer(ObjData.getFaceVertexIndices(object));
        this.getMesh().setVertexBuffer(ObjData.getVertices(object));

        TransformationUtils.scale(this, 0.1f);

    }
}
