package example.banty.com.quizapp.model;

import java.util.HashMap;
import java.util.Set;

public class SpinnerData {

    public static String[] getDifficultySpinnerData() {
        return new String[]{"Easy", "Medium", "Hard"};
    }

    public static String[] getTypeSpinnerData() {
        return new String[] {"Multiple Choice", "True/False"};
    }

    public static String[] getCategorySpinnerData() {
        HashMap<String, Integer> categoryIdMap = setUpCategoryHashMap();
        Set<String> categoryKeySet = categoryIdMap.keySet();
        return categoryKeySet.toArray(new String[categoryKeySet.size()]);
    }

    private static HashMap<String, Integer> setUpCategoryHashMap() {
        //https://opentdb.com/api_category.php

        HashMap<String, Integer> categoryIdMap = new HashMap<>();

        categoryIdMap.put("General Knowledge", 9);
        categoryIdMap.put("Entertainment: Books", 10);
        categoryIdMap.put("Entertainment: Film", 11);
        categoryIdMap.put("Entertainment: Music", 12);
        categoryIdMap.put("Entertainment: Musicals & Theatres", 13);
        categoryIdMap.put("Entertainment: Television", 14);
        categoryIdMap.put("Entertainment: Video Games", 15);
        categoryIdMap.put("Entertainment: Board Games", 16);
        categoryIdMap.put("Science & Nature", 17);
        categoryIdMap.put("Science: Computers", 18);
        categoryIdMap.put("Science: Mathematics", 19);
        categoryIdMap.put("Mythology", 20);
        categoryIdMap.put("Sports", 21);
        categoryIdMap.put("Geography", 22);
        categoryIdMap.put("History", 23);
        categoryIdMap.put("Politics", 24);
        categoryIdMap.put("Arts", 25);
        categoryIdMap.put("Celebrities", 26);
        categoryIdMap.put("Animals", 27);
        categoryIdMap.put("Vechiles", 28);
        categoryIdMap.put("Entertainment: Comics", 29);
        categoryIdMap.put("Science: Gadgets", 30);
        categoryIdMap.put("Entertainment: Japanese Anime & Manga", 31);
        categoryIdMap.put("Entertainment: Cartoon & Animations", 32);

        return categoryIdMap;
    }
}
