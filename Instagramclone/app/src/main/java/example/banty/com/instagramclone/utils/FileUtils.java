package example.banty.com.instagramclone.utils;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by banty on 20/08/17.
 */

public class FileUtils {

    //Returns the list of all the directories  which is inside the @param directory
    public static ArrayList<String> getDirectoryPath(String directory) {
        ArrayList<String> filePath = new ArrayList<>();
        File file = new File(directory);
        File[] filelist = file.listFiles();
        for (int i = 0; i < filelist.length; i++) {
            if(filelist[i].isDirectory()) {
                filePath.add(filelist[i].getAbsolutePath());
            }
        }
        return filePath;
    }

    //Returns the list of all the files  which is inside the @param directory
    public static ArrayList<String> getFilePaths (String directory) {
        ArrayList<String> filePath = new ArrayList<>();
        File file = new File(directory);
        File[] filelist = file.listFiles();
        if(filelist != null && filelist.length > 0) {
            for (int i = 0; i < filelist.length; i++) {
                if (filelist[i].isFile()) {
                    filePath.add(filelist[i].getAbsolutePath());
                }
            }
        }
        return filePath;
    }
}
