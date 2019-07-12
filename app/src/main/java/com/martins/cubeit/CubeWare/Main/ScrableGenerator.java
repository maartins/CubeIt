package com.martins.cubeit.CubeWare.Main;

import java.util.Random;

public class ScrableGenerator {

    public static String generate(int moveCount) {
        String[] moveName = {"U", "L", "F", "R", "B", "D"};
        String[] moveDirection = {"1", "2", "3"};
        StringBuilder result = new StringBuilder();
        Random r = new Random();

        for (int i = 0; i < moveCount; i++){
            int name = r.nextInt(moveName.length);
            int direction = r.nextInt(moveDirection.length);
            result.append(moveName[name]).append(moveDirection[direction]);
        }

        return result.toString();
    }
}
