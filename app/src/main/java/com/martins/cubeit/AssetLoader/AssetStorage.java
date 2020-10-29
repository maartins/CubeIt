package com.martins.cubeit.AssetLoader;

import android.graphics.Bitmap;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

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

            Log.d(TAG, "Retrieved asset: " + path + " type of " + asset.getRepresentedClass().toString());
            return (T) asset.unpack();
        } else {
            ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
            Future<T> result = executorService.schedule(() -> {
                while (!storage.containsKey(path)) {
                    Log.d(TAG, "Looking for asset: " + path + " on thread: " + Thread.currentThread().getName());
                    Thread.sleep(100);
                }
                return (T) storage.get(path);
            }, 1, TimeUnit.MILLISECONDS);

            StorageUnit asset = null;
            try {
                asset = (StorageUnit) result.get(10, TimeUnit.SECONDS);
            } catch (ExecutionException | InterruptedException | TimeoutException e) {
                e.printStackTrace();
            }

            if (asset != null) {
                Log.d(TAG, "Retrieved asset: " + path + " type of " + asset.getRepresentedClass().toString());
                return (T) asset.unpack();
            } else {
                return null;
            }
        }
    }
}
