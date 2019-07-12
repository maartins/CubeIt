package com.martins.cubeit;

import android.view.View;

import java.lang.reflect.Field;
import java.util.function.Predicate;

public interface UiButtonListener {
    void onButtonClick(UiManager.methods caller);
}
