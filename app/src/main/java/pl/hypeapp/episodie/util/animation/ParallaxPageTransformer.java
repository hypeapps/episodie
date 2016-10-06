package pl.hypeapp.episodie.util.animation;

import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;

import pl.hypeapp.episodie.R;

public class ParallaxPageTransformer implements ViewPager.PageTransformer {
    @Override
    public void transformPage(View page, float position) {
        int pageWidth = page.getWidth();
        ImageView background = (ImageView) page.findViewById(R.id.parallax_background);
        if (position < -1) {
            page.setAlpha(1);
        } else if (position <= 1) {
            background.setTranslationX(-position * (pageWidth / 2)); //Half the normal speed
        } else {
            page.setAlpha(1);
        }
    }
}
