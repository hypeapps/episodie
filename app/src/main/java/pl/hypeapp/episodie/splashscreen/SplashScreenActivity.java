package pl.hypeapp.episodie.splashscreen;

import android.os.Bundle;
import android.support.v4.util.Pair;
import android.view.View;
import android.widget.ImageView;

import com.daimajia.androidanimations.library.Techniques;
import com.hanks.htextview.HTextView;
import com.hanks.htextview.HTextViewType;

import butterknife.BindView;
import butterknife.ButterKnife;
import pl.hypeapp.episodie.R;
import pl.hypeapp.episodie.base.BaseMVPActivity;
import pl.hypeapp.episodie.util.StartActivityUtil;
import pl.hypeapp.episodie.util.animation.HTextViewAnimator;
import pl.hypeapp.episodie.util.animation.YoYoAnimator;
import pl.hypeapp.episodie.util.image.BlurTransformation;
import pl.hypeapp.episodie.util.image.GrayscaleTransformation;

public class SplashScreenActivity extends
        BaseMVPActivity<SplashScreenMVP.RequiredViewOps, SplashScreenMVP.ProvidedPresenterOps, SplashScreenPresenter>
        implements SplashScreenMVP.RequiredViewOps {

    @BindView(R.id.iv_background_splash_screen)
    ImageView backgroundImageView;
    @BindView(R.id.iv_text_logo)
    ImageView textLogo;
    @BindView(R.id.tv_logo)
    HTextView hTextView;
    HTextViewAnimator hTextViewAnimator;
    final YoYoAnimator yoyoAnimator = YoYoAnimator.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        ButterKnife.bind(this);
        super.onCreate(SplashScreenPresenter.class, this);

        loadImageFromResourcesIntoView(backgroundImageView, R.drawable.breaking_bad_background,
                new GrayscaleTransformation(this), new BlurTransformation(this, 12));

        String[] textsToAnimate = getResources().getStringArray(R.array.splash_screen_texts);

        setTextLogoFont(hTextView);
        hTextView.animateText(getString(R.string.app_name));

        hTextViewAnimator = new HTextViewAnimator(hTextView, textsToAnimate, HTextViewType.FALL);
        hTextViewAnimator.setListener(new HTextViewAnimator.Listener() {
            @Override
            public void onAnimationEnd() {
                yoyoAnimator.play(Techniques.Bounce, textLogo);
                yoyoAnimator.playDelayed(hTextView, Techniques.ZoomOut, 1500);
                getPresenter().runActivityWithDelay(2200);
            }
        });
        hTextViewAnimator.playSequenceAnimation();
    }

    public void runActivity(Class startActivityClass) {
        View sharedLogo = textLogo;
        View sharedBackground = backgroundImageView;
        Pair<View, String> sharedLogoPair = Pair.
                create(sharedLogo, getResources().getString(R.string.shared_name_logo));
        Pair<View, String> sharedBackgroundPair = Pair.
                create(sharedBackground, getResources().getString(R.string.shared_name_background));
        StartActivityUtil startActivityUtil = StartActivityUtil.getInstance(getActivity());
        startActivityUtil.runActivityWithTransition(startActivityClass, sharedLogoPair, sharedBackgroundPair);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
