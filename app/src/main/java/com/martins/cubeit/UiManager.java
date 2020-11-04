package com.martins.cubeit;

import android.util.Log;
import android.widget.Button;

import java.util.ArrayList;

public final class UiManager {

    private static final String TAG = "UiManager";

    public enum methods {
        resetButton, captureButton, solveButton
    }

    private ArrayList<UiButtonListener> listeners = new ArrayList<>();

    private UiManager() {}

    public static void addElement(Button button, methods id) {
        Log.d(TAG, "UI element added.");

        button.setOnClickListener(v -> {
            for (UiButtonListener l : SingletonHelper.INSTANCE.listeners) {
                l.onButtonClick(id);
            }
        });
    }

    public static void addUiButtonListener(UiButtonListener listener) {
        Log.d(TAG, "UI listener added.");
        SingletonHelper.INSTANCE.listeners.add(listener);
    }

    private static class SingletonHelper {
        private static final UiManager INSTANCE = new UiManager();
    }
}
