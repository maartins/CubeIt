package com.martins.cubeit.AssetLoader;

import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import de.javagl.obj.Obj;
import de.javagl.obj.ObjReader;
import de.javagl.obj.ObjUtils;

public class AssetLoader {
    private static final String TAG = "AssetLoader";

    private AssetManager manager = null;

    private Map<Class, Method> loaderMap = new HashMap<>();

    private ExecutorService assetService = null;

    private AssetLoader() {
        assetService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

        try {
            loaderMap.put(Bitmap.class, LoaderHelper.class.getMethod("bitmapLoader", AssetManager.class, String.class));
            loaderMap.put(Obj.class, LoaderHelper.class.getMethod("objLoader", AssetManager.class, String.class));
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

    public static void changeAssetManager(AssetManager manager) {
        if (SingletonHelper.INSTANCE.manager == null)
            SingletonHelper.INSTANCE.manager = manager;
    }

    public static void addAsset(Class<?> cls, String path) {
        if (SingletonHelper.INSTANCE.manager != null && path != null) {
            Log.d(TAG, "Try to add: " + path + " of " + cls.toString());

            if (SingletonHelper.INSTANCE.loaderMap.containsKey(cls)) {
                try {
                    Objects.requireNonNull(SingletonHelper.INSTANCE.loaderMap.get(cls)).invoke(null, SingletonHelper.INSTANCE.manager, path);
                } catch (IllegalAccessException | InvocationTargetException e) {
                    e.printStackTrace();
                }
            } else {
                Log.d(TAG, "Unsupported asset class: " + cls.toString());
            }
        }
    }

    private static class SingletonHelper {
        private static final AssetLoader INSTANCE = new AssetLoader();
    }

    private static class LoaderHelper {
        private static final String TAG = "AssetLoader";

        public static void bitmapLoader(AssetManager manager, String path) {
            SingletonHelper.INSTANCE.assetService.execute(() -> {
                Bitmap bitmapTexture = null;

                try {
                    bitmapTexture = BitmapFactory.decodeStream(manager.open(path));
                    Log.d(TAG, "Successfully loaded: " + path + " as " + Bitmap.class.toString());
                } catch (IOException e) {
                    e.printStackTrace();
                }

                if (bitmapTexture != null) {
                    AssetStorage.putAsset(path, new StorageUnit(bitmapTexture));
                }
            });
        }

        public static void objLoader(AssetManager manager, String path) {
            SingletonHelper.INSTANCE.assetService.execute(() -> {
                Obj cubeMesh = null;

                try {
                    cubeMesh = ObjUtils.convertToRenderable(ObjReader.read(manager.open("cube.obj")));
                    Log.d(TAG, "Successfully loaded: " + path + " as " + Obj.class.toString());
                } catch (IOException e) {
                    e.printStackTrace();
                }

                if (cubeMesh != null) {
                    AssetStorage.putAsset(path, new StorageUnit(cubeMesh));
                }
            });
        }
    }
}
