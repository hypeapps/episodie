package pl.hypeapp.episodie.util;


import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.util.Log;

import java.util.HashMap;
import java.util.Map;

public class FontManager {
    private static final String TAG = FontManager.class.getName();

    private static FontManager instance;

    private AssetManager assetManager;

    private Map<String, Typeface> fonts;

    private FontManager(AssetManager assetManager) {
        this.assetManager = assetManager;
        this.fonts = new HashMap<>();
    }

    public static FontManager getInstance(AssetManager assetManager) {
        if (instance == null) {
            instance = new FontManager(assetManager);
        }
        return instance;
    }

    public Typeface getFont(String asset) {
        if (fonts.containsKey(asset))
            return fonts.get(asset);

        Typeface font = null;

        try {
            font = Typeface.createFromAsset(assetManager, asset);
            fonts.put(asset, font);
        } catch (RuntimeException e) {
            Log.e(TAG, "getFont: Can't create font from asset.", e);
        }

        return font;
    }
}