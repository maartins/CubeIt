package com.martins.cubeit.AssetLoader;

public class StorageUnit <T> {
    private T asset = null;

    public StorageUnit(T asset) {
        this.asset = asset;
    }

    public Object getRepresentedClass() {
        return asset.getClass();
    }

    public T unpack(){
        return asset;
    }
}
