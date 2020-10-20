package com.martins.cubeit.AssetLoader;

import android.graphics.Bitmap;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class AssetStorage {
    private static final String TAG = "AssetStorage";

    private static Map<String, StorageUnit> storage = new ConcurrentHashMap<>();

    public static void putAsset(String path, StorageUnit asset) {
        storage.put(path, asset);
        Log.d(TAG, "Successfully put asset: " + path + " as " + asset.getRepresentedClass().toString());
    }

    public static <T> T getAsset(String path) {
        if (storage.containsKey(path)) {
            StorageUnit asset = storage.get(path);

            Log.d(TAG, "Accessing asset: " + path + " type of " + asset.getRepresentedClass().toString());
            return (T) asset.unpack();
        } else {
            Log.d(TAG, "Asset: " + path + " not found");
        }

        return null;
    }
}
