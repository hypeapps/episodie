package pl.hypeapp.episodie.ui.activity;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.util.Pair;
import android.view.View;
import android.widget.ImageView;

import com.daimajia.androidanimations.library.Techniques;
import com.hanks.htextview.HTextView;
import com.hanks.htextview.HTextViewType;
import com.pascalwelsch.compositeandroid.activity.CompositeActivity;

import net.grandcentrix.thirtyinch.internal.TiPresenterProvider;
import net.grandcentrix.thirtyinch.plugin.TiActivityPlugin;

import butterknife.BindView;
import butterknife.ButterKnife;
import pl.hypeapp.episodie.R;
import pl.hypeapp.episodie.plugin.ImageLoaderPlugin;
import pl.hypeapp.episodie.presenter.SplashScreenPresenter;
import pl.hypeapp.episodie.util.FontUtil;
import pl.hypeapp.episodie.util.StartActivityUtil;
import pl.hypeapp.episodie.util.animation.HtextViewAnimator;
import pl.hypeapp.episodie.util.animation.YoYoAnimator;
import pl.hypeapp.episodie.view.SplashScreenView;

public class SplashScreenActivity extends CompositeActivity implements SplashScreenView {
    private final ImageLoaderPlugin mImageLoaderPlugin = new ImageLoaderPlugin();
    private static YoYoAnimator sYoyoAnimator = YoYoAnimator.getInstance();
    private static HtextViewAnimator sHtextViewAnimator;

    private final TiActivityPlugin<SplashScreenPresenter, SplashScreenView> mPresenterPlugin =
            new TiActivityPlugin<>(new TiPresenterProvider<SplashScreenPresenter>() {
                @NonNull
                @Override
                public SplashScreenPresenter providePresenter() {
                    return new SplashScreenPresenter();
                }
            });

    private SplashScreenPresenter mPresenter;

    @BindView(R.id.iv_background_splash_screen)
    ImageView backgroundImageView;
    @BindView(R.id.iv_text_logo)
    ImageView textLogo;
    @BindView(R.id.tv_logo)
    HTextView hTextView;

    public SplashScreenActivity() {
        addPlugin(mPresenterPlugin);
        addPlugin(mImageLoaderPlugin);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        ButterKnife.bind(this);
        loadBackground();
        FontUtil sFontUtil = FontUtil.getInstance(getAssets());
        sFontUtil.setTextViewTypeface(hTextView, "fonts/coolvetica.ttf");
        mPresenter = mPresenterPlugin.getPresenter();
    }

    @Override
    public void onStop() {
        super.onStop();
        sHtextViewAnimator.stopSequenceAnimation();
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            mPresenter.onWindowFocus();
        }
    }

    @Override
    public void startTextLogoAnimation() {
        String[] textsToAnimate = getResources().getStringArray(R.array.splash_screen_texts);
        hTextView.animateText(getString(R.string.app_name));
        sHtextViewAnimator = new HtextViewAnimator(hTextView, textsToAnimate, HTextViewType.FALL);
        sHtextViewAnimator.setListener(new HtextViewAnimator.Listener() {
            @Override
            public void onAnimationEnd() {
                sYoyoAnimator.play(Techniques.Bounce, textLogo);
                sYoyoAnimator.playDelayed(hTextView, Techniques.ZoomOut, 1500);
                mPresenter.onAnimationLogoEnd();
            }
        });
        sHtextViewAnimator.playSequenceAnimation();
    }

    @Override
    public void startActivityDelayed(long delayInMilis) {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                runActivity(LoginActivity.class);
            }
        }, delayInMilis);
    }

    public void loadBackground() {
        mImageLoaderPlugin.loadImageFromResourcesIntoView(backgroundImageView, R.drawable.breaking_bad_backgorund);
    }

    private void runActivity(Class startActivityClass) {
        View sharedLogo = textLogo;
        View sharedBackground = backgroundImageView;
        Pair<View, String> sharedLogoPair = Pair.
                create(sharedLogo, getResources().getString(R.string.shared_name_logo));
        Pair<View, String> sharedBackgroundPair = Pair.
                create(sharedBackground, getResources().getString(R.string.shared_name_background));
        StartActivityUtil startActivityUtil = StartActivityUtil.getInstance(this);
        startActivityUtil.runActivityWithTransition(startActivityClass, sharedLogoPair, sharedBackgroundPair);
    }
}
