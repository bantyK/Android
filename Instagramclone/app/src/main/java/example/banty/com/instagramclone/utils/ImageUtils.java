package example.banty.com.instagramclone.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Created by banty on 27/08/17.
 */

public class ImageUtils {
    private static final String TAG = "ImageUtils";

    public static Bitmap getBitmap(String imageURL) {
        File imageFile = new File(imageURL);
        FileInputStream fin = null;
        Bitmap bitmap = null;

        try {
            fin = new FileInputStream(imageFile);
            bitmap = BitmapFactory.decodeStream(fin);
        } catch (FileNotFoundException e) {
            Log.e(TAG, "getBitmap: error while creating file input stream : " + e.getMessage());
        }

        try {
            fin.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return bitmap;
    }

    public static byte[] getByteFromBitmap(Bitmap bitmap, int quality) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, quality, byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }
}
