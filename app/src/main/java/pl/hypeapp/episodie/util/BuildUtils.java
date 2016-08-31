package pl.hypeapp.episodie.util;

import android.os.Build;

public class BuildUtils {

    public static boolean isMinApi21() {
        return Build.VERSION.SDK_INT >= 21;
    }
}