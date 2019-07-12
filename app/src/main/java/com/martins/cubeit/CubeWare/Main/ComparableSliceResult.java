package com.martins.cubeit.CubeWare.Main;

import android.util.Log;

public class ComparableSliceResult {
    private static final String TAG = "ComparableSliceResult";
    private boolean[][] comparisons;

    public ComparableSliceResult(boolean testTop_l, boolean testTop_m, boolean testTop_r,
                                 boolean testMid_l, boolean testMid_m, boolean testMid_r,
                                 boolean testBot_l, boolean testBot_m, boolean testBot_r) {
        comparisons = new boolean[][] {{testTop_l, testTop_m, testTop_r},
                                       {testMid_l, testMid_m, testMid_r},
                                       {testBot_l, testBot_m, testBot_r}};
    }

    public void displayResult(){
        Log.d(TAG, comparisons[0][0] + " \t" + comparisons[0][1] + " \t" + comparisons[0][2]);
        Log.d(TAG, comparisons[1][0] + " \t" + comparisons[1][1] + " \t" + comparisons[1][2]);
        Log.d(TAG, comparisons[2][0] + " \t" + comparisons[2][1] + " \t" + comparisons[2][2]);
    }
}
