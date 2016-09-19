package pl.hypeapp.episodie.util;


import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.util.Log;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Map;

public class FontUtil {
    private static final String TAG = FontUtil.class.getName();

    private static FontUtil instance;

    private AssetManager assetManager;

    private Map<String, Typeface> fonts;

    private FontUtil(AssetManager assetManager) {
        this.assetManager = assetManager;
        this.fonts = new HashMap<>();
    }

    public static FontUtil getInstance(AssetManager assetManager) {
        if (instance == null) {
            instance = new FontUtil(assetManager);
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

    public void setTextViewTypeface(TextView textView, String asset){
        textView.setTypeface(getFont(asset));
    }
}