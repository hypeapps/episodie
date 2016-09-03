package pl.hypeapp.episodie.util.animation;

import android.view.View;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;

public class YoYoAnimator {
    private static YoYoAnimator ourInstance = new YoYoAnimator();

    public static YoYoAnimator getInstance() {
        return ourInstance;
    }

    private YoYoAnimator() {}

    public void playDelayed(View view, Techniques techniques, long delayInMilis){
        YoYo.with(techniques).delay(delayInMilis).playOn(view);
    }

    public void play(Techniques techniques, View view) {
        YoYo.with(techniques).playOn(view);
    }
}
