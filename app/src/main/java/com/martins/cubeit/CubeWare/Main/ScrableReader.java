package com.martins.cubeit.CubeWare.Main;

import com.martins.cubeit.CubeWare.CubeData.CubeType;
import com.martins.cubeit.CubeWare.CubeData.Position;
import com.martins.cubeit.CubeWare.CubeData.RotationDirection;
import com.martins.cubeit.CubeWare.CubeData.SliceRotatationResult;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ScrableReader {

    public static Cube generateCubeFromString(String text) {
        Pattern pattern = Pattern.compile("[ULFRBD][123]");
        Matcher matcher = pattern.matcher(text);
        List<String> allMatches = new ArrayList<>();

        while (matcher.find()) {
            allMatches.add(matcher.group());
        }

        Cube scrambleCube = new Cube(CubeType.Solved);

        for (String s: allMatches) {
            if (s.charAt(1) == '1') {
                scrambleCube.rotateSlice(slice(s.charAt(0)), RotationDirection.CCW);
            } else if (s.charAt(1) == '2') {
                scrambleCube.rotateSlice(slice(s.charAt(0)), RotationDirection.CCW);
                scrambleCube.rotateSlice(slice(s.charAt(0)), RotationDirection.CCW);
            } else if (s.charAt(1) == '3') {
                scrambleCube.rotateSlice(slice(s.charAt(0)), RotationDirection.ACW);
            }
        }

        return scrambleCube;
    }

    public static ArrayList<SliceRotatationResult> generateMoveSetFromString(String text) {
        Pattern pattern = Pattern.compile("[ULFRBD][123]");
        Matcher matcher = pattern.matcher(text);
        List<String> allMatches = new ArrayList<>();

        while (matcher.find()) {
            allMatches.add(matcher.group());
        }

        ArrayList<SliceRotatationResult> list = new ArrayList<>();

        for (String s: allMatches) {
            if (s.charAt(1) == '1') {
                list.add(new SliceRotatationResult(slice(s.charAt(0)), RotationDirection.CCW));
            } else if (s.charAt(1) == '2') {
                list.add(new SliceRotatationResult(slice(s.charAt(0)), RotationDirection.CCW));
                list.add(new SliceRotatationResult(slice(s.charAt(0)), RotationDirection.CCW));
            } else if (s.charAt(1) == '3') {
                list.add(new SliceRotatationResult(slice(s.charAt(0)), RotationDirection.ACW));
            }
        }

        return list;
    }

    private static Position slice(char pos){
        switch (pos) {
            case 'L':
                return Position.Left;
            case 'R':
                return Position.Right;
            case 'F':
                return Position.Front;
            case 'B':
                return Position.Back;
            case 'D':
                return Position.Bottom;
            case 'U':
                return Position.Top;
            default:
                throw new InternalError("Unidentified char: " + pos);
        }
    }
}
