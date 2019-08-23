package com.martins.cubeit.CubeWare.CubeData;

public final class SliceRotatationResult {
    public final Position slice;
    public final RotationDirection rotationDirection;

    public SliceRotatationResult(SliceRotatationResult result) {
        this.slice = result.slice;
        this.rotationDirection = result.rotationDirection;
    }

    public SliceRotatationResult(Position slice, RotationDirection rotationDirection) {
        this.slice = slice;
        this.rotationDirection = rotationDirection;
    }

    @Override
    public String toString() {
        return "SliceRotatationResult{" +
                "slice=" + slice +
                ", rotationDirection=" + rotationDirection +
                '}';
    }
}
