package com.martins.cubeit.OpenGL;

import java.nio.Buffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

public class Mesh {

    private IntBuffer indexBuffer = null;
    private FloatBuffer vertexBuffer = null;

    public void setIndexBuffer (IntBuffer indexBuffer) {
        this.indexBuffer = indexBuffer;
    }

    public void setVertexBuffer (FloatBuffer vertexBuffer) {
        this.vertexBuffer = vertexBuffer;
    }

    public Buffer getIndexBuffer() {
        return indexBuffer;
    }

    public Buffer getVertexBuffer() {
        return vertexBuffer;
    }

    public boolean isValid() {
        return indexBuffer != null && vertexBuffer != null;
    }
}
