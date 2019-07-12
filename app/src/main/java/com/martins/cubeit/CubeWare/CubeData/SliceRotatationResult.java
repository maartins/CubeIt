package com.martins.cubeit.CubeWare.CubeData;

public final class SliceRotatationResult {
    public final Position slice;
    public final RotationDirection rotationDirection;

    public SliceRotatationResult(Position slice, RotationDirection rotationDirection) {
        this.slice = slice;
        this.rotationDirection = rotationDirection;
    }
}
