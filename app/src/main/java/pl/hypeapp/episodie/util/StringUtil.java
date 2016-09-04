package pl.hypeapp.episodie.util;

import java.util.ArrayList;
import java.util.List;

public final class StringUtil {

    public static String[] upperCaseArray(String[] textArray){
        List<String> list = new ArrayList();
        for (String text : textArray) {
            list.add(text.toUpperCase());
        }
        return list.toArray(new String[list.size()]);
    }

    public static String[] lowerCaseArray(String[] textArray){
        List<String> list = new ArrayList();
        for (String text : textArray) {
            list.add(text.toLowerCase());
        }
        return list.toArray(new String[list.size()]);
    }
}
