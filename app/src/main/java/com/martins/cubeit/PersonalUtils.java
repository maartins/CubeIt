package com.martins.cubeit;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.os.Environment;
import android.util.Log;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;

public final class PersonalUtils {
    private static final String TAG = "PersonalUtils";
    private static final File STORAGE_DIR = new File(Environment.getExternalStorageDirectory()
                                                        + "/Android/data/"
                                                        + "com.martins.cubeit"
                                                        + "/Files");


    public static Resources resources;

    private static File getOutputMediaFile(String fileName){
        if (!STORAGE_DIR.exists())
            if (!STORAGE_DIR.mkdirs())
                return null;

        return new File(STORAGE_DIR.getPath() + "/" + fileName + ".png");
    }

    public static InputStream getFileInputStream(String fileName) throws FileNotFoundException {
        return new FileInputStream(new File(STORAGE_DIR.getPath() + "/" + fileName));
    }

    public static void storeImage(Bitmap source, String fileName) throws IOException {
        File pictureFile = getOutputMediaFile(fileName);

        if (pictureFile == null) {
            Log.e(TAG, "Error creating media file, check storage permissions: ");
            return;
        }

        FileOutputStream fos = new FileOutputStream(pictureFile);
        source.compress(Bitmap.CompressFormat.PNG, 100, fos);
        fos.close();
    }

    public static boolean fileExists(String fileName) {
        File testFile = new File(STORAGE_DIR.getPath() + "/" + fileName);
        return (testFile.exists() && !testFile.isDirectory());
    }

    public static boolean deleteFile(String fileName) {
        File testFile = new File(STORAGE_DIR.getPath() + "/" + fileName);
        return testFile.delete();
    }

    public static String loadRawString(int rawId) throws Exception{
        InputStream is = resources.openRawResource(rawId);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] buf = new byte[1024];
        int len;
        while((len = is.read(buf))!= -1){
            baos.write(buf, 0, len);
        }
        return baos.toString();
    }

    public static void writeJSON(String fileName, JSONObject source) throws IOException {
        if (!STORAGE_DIR.exists())
            if (!STORAGE_DIR.mkdirs())
                Log.e(TAG, "FAILED to create directory");

        Log.d(TAG, "Writing JSON to: " + STORAGE_DIR.getPath() + "/" + fileName + ".json");

        FileWriter fw = new FileWriter(STORAGE_DIR.getPath() + "/" + fileName + ".json");
        fw.write(source.toString());
        fw.close();
    }

    public static void writeJSON(String fileName, String source) throws IOException {
        if (!STORAGE_DIR.exists())
            if (!STORAGE_DIR.mkdirs())
                Log.e(TAG, "FAILED to create directory");

        Log.d(TAG, "Writing JSON to: " + STORAGE_DIR.getPath() + "/" + fileName + ".json");

        FileWriter fw = new FileWriter(STORAGE_DIR.getPath() + "/" + fileName + ".json");
        fw.write(source);
        fw.close();
    }

    public static String readJSON(String fileName) throws IOException {
        Log.d(TAG, "Reading JSON : " + STORAGE_DIR.getPath() + "/" + fileName + ".json");
        try (BufferedReader br = new BufferedReader(
                new FileReader(STORAGE_DIR.getPath() + "/" + fileName + ".json"))) {
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();

            while (line != null) {
                sb.append(line);
                sb.append("\n");
                line = br.readLine();
            }
            return sb.toString();
        } catch (Exception e) {
            Log.e(TAG, "Reading internal JSON file failed.");
            e.printStackTrace();
            return null;
        }
    }

    public static void displaySquareMatrix(float[] matrix){
        int len = matrix.length;
        int size = (int) Math.sqrt(len);
        StringBuilder s = new StringBuilder();

        int counter = 0;
        for (int i = 0; i < size; i++){
            for (int j = 0; j < size; j++){
                s.append(matrix[counter]).append(", ");
                counter++;
            }
            s.append("\n");
        }
        Log.d(TAG, "START");
        Log.d(TAG, s.toString());
        Log.d(TAG, "END");
    }

    public static void displaySquareMatrix(float[] matrix, String id){
        int len = matrix.length;
        int size = (int) Math.sqrt(len);
        StringBuilder s = new StringBuilder();

        int counter = 0;
        for (int i = 0; i < size; i++){
            for (int j = 0; j < size; j++){
                s.append(matrix[counter]).append(", ");
                counter++;
            }
            s.append("\n");
        }
        Log.d(TAG, "START " + id);
        Log.d(TAG, s.toString());
        Log.d(TAG, "END");
    }

    public static void displaySquareMatrix(int[] matrix){
        int len = matrix.length;
        int size = (int) Math.sqrt(len);
        StringBuilder s = new StringBuilder();

        int counter = 0;
        for (int i = 0; i < size; i++){
            for (int j = 0; j < size; j++){
                s.append(matrix[counter]).append(", ");
                counter++;
            }
            s.append("\n");
        }
        Log.d(TAG, "START");
        Log.d(TAG, s.toString());
        Log.d(TAG, "END");
    }

    public static void displaySquareMatrix(int[] matrix, String id){
        int len = matrix.length;
        int size = (int) Math.sqrt(len);
        StringBuilder s = new StringBuilder();

        int counter = 0;
        for (int i = 0; i < size; i++){
            for (int j = 0; j < size; j++){
                s.append(matrix[counter]).append(", ");
                counter++;
            }
            s.append("\n");
        }
        Log.d(TAG, "START " + id);
        Log.d(TAG, s.toString());
        Log.d(TAG, "END");
    }
}
