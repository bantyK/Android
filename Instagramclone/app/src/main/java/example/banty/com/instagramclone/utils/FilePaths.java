package example.banty.com.instagramclone.utils;

import android.os.Environment;

/**
 * Created by banty on 20/08/17.
 */

public class FilePaths {
    //Path ->  /storage/emulated/0
    public static String ROOT_DIR = Environment.getExternalStorageDirectory().getPath();
    public static String CAMERA_DIR = ROOT_DIR + "/DCIM/Camera";
    public static String PICTURES = ROOT_DIR + "/Pictures";
    public static String SCREENSHOTS = ROOT_DIR + "/DCIM/Screenshots";
}
