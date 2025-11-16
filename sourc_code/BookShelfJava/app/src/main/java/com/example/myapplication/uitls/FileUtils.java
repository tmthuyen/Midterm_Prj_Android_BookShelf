package com.example.myapplication.uitls;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class FileUtils {
    public static String saveBitmapToInternalStorage(Context context, Bitmap bitmap, String fileName) {
        File dir = new File(context.getFilesDir(), "thumbnails");
        if (!dir.exists()) {
            dir.mkdirs();
        }

        File file = new File(dir, fileName + ".jpg");
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 90, fos);
            fos.flush();
            return file.getAbsolutePath();   // trả về path để lưu vào Room
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException ignored) {
                }
            }
        }
    }

    public static String saveBitmapToExternalAppFolder(Context context, Bitmap bitmap, String fileName) {
        // Folder: /storage/emulated/0/Android/data/<package>/files/thumbnails
        File dir = new File(context.getExternalFilesDir("thumbnails"), "");
        if (!dir.exists()) {
            dir.mkdirs();
        }

        File file = new File(dir, fileName + ".jpg");
        Log.d("FileUtils", "saveBitmapToExternalAppFolder: " + file.getAbsolutePath());
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 90, fos);
            fos.flush();

            return file.getAbsolutePath();   // lưu path này vào Room
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException ignored) {
                }
            }
        }
    }


}
