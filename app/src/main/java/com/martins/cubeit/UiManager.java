package com.martins.cubeit;

import android.util.Log;
import android.view.View;

import java.util.ArrayList;

public final class UiManager {
    public enum methods {
        solveButton
    }

    private static final String TAG = "UiManager";

    private static ArrayList<UiButtonListener> listeners = new ArrayList<>();

    public static void addUiButtonListener(UiButtonListener listener) {
        listeners.add(listener);
    }

    public static final View.OnClickListener solveButton = v -> {
        for (UiButtonListener l : listeners)
            l.onButtonClick(methods.solveButton);
    };
}
