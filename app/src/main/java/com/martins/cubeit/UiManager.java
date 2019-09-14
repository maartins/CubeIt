package com.martins.cubeit;

import android.widget.Button;

import java.util.ArrayList;

public final class UiManager {

    private static final String TAG = "UiManager";

    public enum methods {
        solveButton
    }

    private ArrayList<UiButtonListener> listeners = new ArrayList<>();

    private UiManager() {}

    public static void addElement(Button button, methods id) {
        button.setOnClickListener(v -> {
            for (UiButtonListener l : SingletonHelper.INSTANCE.listeners) {
                l.onButtonClick(id);
            }
        });
    }

    public static void addUiButtonListener(UiButtonListener listener) {
        SingletonHelper.INSTANCE.listeners.add(listener);
    }

    private static class SingletonHelper {
        private static final UiManager INSTANCE = new UiManager();
    }
}
