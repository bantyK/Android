package example.banty.com.instagramclone.utils;

public class StringMethods {

    public static String expandUsername(String username) {
        return username.replace("."," ");
    }

    public static String condenseUsername(String username) {
        return username.replace(" ",".");
    }
}
