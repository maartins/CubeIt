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
    private float x, y, z, w;

    public Quaternion(float x, float y, float z, float w) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.w = w;
    }

    public Quaternion(Vector3 axis, float angle) {
        setFromAngle(axis, angle);
    }

    public Quaternion(float[] matrix) {
        setFromMatrix(matrix);
    }

    public void setFromAngle(Vector3 axis, float angle) {
        float angleRad = (float) Math.toRadians(angle * 0.5);
        float s = (float) Math.sin(angleRad);
        w = (float) Math.cos(angleRad);
        x = axis.getX() * s;
        y = axis.getY() * s;
        z = axis.getZ() * s;
    }

    public void setFromMatrix(float[] matrix) {
        float m00 = matrix[0];
        float m01 = matrix[1];
        float m02 = matrix[2];

        float m10 = matrix[4];
        float m11 = matrix[5];
        float m12 = matrix[6];

        float m20 = matrix[8];
        float m21 = matrix[9];
        float m22 = matrix[10];

        float tr = m00 + m11 + m22;

        if (tr > 0) {
            float s = (float) (Math.sqrt(tr + 1.0f) * 2.0f);
            w = 0.25f * s;
            x = (m21 - m12) / s;
            y = (m02 - m20) / s;
            z = (m10 - m01) / s;
        } else if ((m00 > m11) && (m00 > m22)) {
            float s = (float) (Math.sqrt(1.0f + m00 - m11 - m22) * 2.0f);
            w = (m21 - m12) / s;
            x = 0.25f * s;
            y = (m01 + m10) / s;
            z = (m02 + m20) / s;
        } else if (m11 > m22) {
            float s = (float) (Math.sqrt(1.0f + m11 - m00 - m22) * 2.0f);
            w = (m02 - m20) / s;
            x = (m01 + m10) / s;
            y = 0.25f * s;
            z = (m12 + m21) / s;
        } else {
            float s = (float) (Math.sqrt(1.0f + m22 - m00 - m11) * 2.0f);
            w = (m10 - m01) / s;
            x = (m02 + m20) / s;
            y = (m12 + m21) / s;
            z = 0.25f * s;
        }
    }

    public Quaternion normalize() {
        float x = this.x * this.x;
        float y = this.y * this.y;
        float z = this.z * this.z;
        float w = this.w * this.w;
        float magnitude = (float) Math.sqrt(x + y + z + w);

        if (magnitude != 0) {
            this.x = this.x / magnitude;
            this.y = this.y / magnitude;
            this.z = this.z / magnitude;
            this.w = this.w / magnitude;
        } else {
            this.x = 0;
            this.y = 0;
            this.z = 0;

            if (this.w > 0)
                this.w = 1;
            else
                this.w = -1;
        }
        return this;
    }

    Quaternion multiply(Quaternion q) {
        float resX = x * q.w + y * q.z - z * q.y + w * q.x;
        float resY = -x * q.z + y * q.w + z * q.x + w * q.y;
        float resZ = x * q.y - y * q.x + z * q.w + w * q.z;
        float resW = -x * q.x - y * q.y - z * q.z + w * q.w;
        return new Quaternion(resX, resY, resZ, resW);
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

    @Override
    public String toString() {
        return "Quaternion{" +
                "x=" + x +
                ", y=" + y +
                ", z=" + z +
                ", w=" + w +
                '}';
    }
}
