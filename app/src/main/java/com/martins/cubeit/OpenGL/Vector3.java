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

import android.support.annotation.NonNull;

public final class Vector3 {
    private float x,y,z;

    public Vector3(float x, float y) {
        this.x = x;
        this.y = y;
        this.z = (float) 0;
    }

    public Vector3(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Vector3(Vector3 vector) {
        this.x = vector.x;
        this.y = vector.y;
        this.z = vector.z;
    }

    float getX() {
        return x;
    }

    float getY() {
        return y;
    }

    float getZ() {
        return z;
    }

    void set(float ix, float iy) {
        x = ix;
        y = iy;
        z = (float) 0;
    }

    public void add(Vector3 vec) {
        x += vec.getX();
        y += vec.getY();
        z += vec.getZ();
    }

    public void subtract(Vector3 vec) {
        x -= vec.getX();
        y -= vec.getY();
        z -= vec.getZ();
    }

    public void negate() {
        x = -x;
        y = -y;
        z = -z;
    }

    float magnitude() {
        return (float) Math.sqrt(x * x + y * y + z * z);
    }

    void normalise() {
        float mag = magnitude();
        x /= mag;
        y /= mag;
        z /= mag;
    }

    @NonNull
    @Override
    public String toString() {
        return "vec3-> x:" + x + " y:" + y + " z:" + z;
    }
}
