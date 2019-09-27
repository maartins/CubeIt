package com.martins.cubeit.OpenGL;

/*
 * Copyright (c) 2014-2019, Digi International Inc. <support@digi.com>
 *
 * Permission to use, copy, modify, and/or distribute this software for any
 * purpose with or without fee is hereby granted, provided that the above
 * copyright notice and this permission notice appear in all copies.
 *
 * THE SOFTWARE IS PROVIDED "AS IS" AND THE AUTHOR DISCLAIMS ALL WARRANTIES
 * WITH REGARD TO THIS SOFTWARE INCLUDING ALL IMPLIED WARRANTIES OF
 * MERCHANTABILITY AND FITNESS. IN NO EVENT SHALL THE AUTHOR BE LIABLE FOR
 * ANY SPECIAL, DIRECT, INDIRECT, OR CONSEQUENTIAL DAMAGES OR ANY DAMAGES
 * WHATSOEVER RESULTING FROM LOSS OF USE, DATA OR PROFITS, WHETHER IN AN
 * ACTION OF CONTRACT, NEGLIGENCE OR OTHER TORTIOUS ACTION, ARISING OUT OF
 * OR IN CONNECTION WITH THE USE OR PERFORMANCE OF THIS SOFTWARE.
 */

public final class Quaternion {
    private double x, y, z, w;

    void set(final Quaternion q) {
        this.x = q.x;
        this.y = q.y;
        this.z = q.z;
        this.w = q.w;
    }

    public Quaternion(Vector3 axis) {
        set(axis, (double) 0);
    }

    /**
     * @param axis rotation axis, unit vector.
     * @param angle the rotation angle.
     */
    public void set(Vector3 axis, double angle) {
        double s = Math.sin(angle / 2);
        w = Math.cos(angle / 2);
        x = axis.getX() * s;
        y = axis.getY() * s;
        z = axis.getZ() * s;
    }

    void multiply(Quaternion q) {
        double nw = w * q.w - x * q.x - y * q.y - z * q.z;
        double nx = w * q.x + x * q.w + y * q.z - z * q.y;
        double ny = w * q.y + y * q.w + z * q.x - x * q.z;
        z = w * q.z + z * q.w + x * q.y - y * q.x;
        w = nw;
        x = nx;
        y = ny;
    }

    /**
     * Converts this Quaternion into a matrix, returning it as a float array.
     */
    public float[] toMatrix() {
        float[] matrix = new float[16];
        toMatrix(matrix);
        return matrix;
    }

    /**
     * Converts this Quaternion into a matrix, placing the values into the given array.
     * @param matrix 16-length float array.
     */
    private void toMatrix(float[] matrix) {
        matrix[3] = 0.0f;
        matrix[7] = 0.0f;
        matrix[11] = 0.0f;
        matrix[12] = 0.0f;
        matrix[13] = 0.0f;
        matrix[14] = 0.0f;
        matrix[15] = 1.0f;

        matrix[0] = (float) (1.0f - (2.0f * ((y * y) + (z * z))));
        matrix[1] = (float) (2.0f * ((x * y) - (z * w)));
        matrix[2] = (float) (2.0f * ((x * z) + (y * w)));

        matrix[4] = (float) (2.0f * ((x * y) + (z * w)));
        matrix[5] = (float) (1.0f - (2.0f * ((x * x) + (z * z))));
        matrix[6] = (float) (2.0f * ((y * z) - (x * w)));

        matrix[8] = (float) (2.0f * ((x * z) - (y * w)));
        matrix[9] = (float) (2.0f * ((y * z) + (x * w)));
        matrix[10] = (float) (1.0f - (2.0f * ((x * x) + (y * y))));
    }

}
