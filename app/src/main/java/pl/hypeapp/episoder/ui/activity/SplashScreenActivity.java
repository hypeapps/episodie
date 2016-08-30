package pl.hypeapp.episoder.ui.activity;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.util.Pair;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.DrawableTypeRequest;
import com.bumptech.glide.Glide;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.hanks.htextview.HTextView;
import com.hanks.htextview.HTextViewType;
import com.hannesdorfmann.mosby.mvp.MvpActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import pl.hypeapp.episoder.R;
import pl.hypeapp.episoder.animation.BlurTransformation;
import pl.hypeapp.episoder.animation.GrayscaleTransformation;
import pl.hypeapp.episoder.animation.HTextViewAnimator;
import pl.hypeapp.episoder.animation.YoYoAnimator;
import pl.hypeapp.episoder.ui.presenter.SplashScreenPresenter;
import pl.hypeapp.episoder.util.FontManager;
import pl.hypeapp.episoder.util.StartActivityUtil;
import pl.hypeapp.episoder.util.StringUtil;
import pl.hypeapp.episoder.view.SplashScreenView;


public class SplashScreenActivity extends MvpActivity<SplashScreenView, SplashScreenPresenter>
    implements SplashScreenView {
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

        presenter.loadBackgroundImage(backgroundImageView);

        String[] textsToAnimate = StringUtil.upperCaseArray(getResources().getStringArray(R.array.splash_screen_texts));

        hTextView.setTypeface(FontManager.getInstance(getAssets()).getFont("fonts/LeagueGothic-Regular.ttf"));
        hTextView.animateText(getString(R.string.app_name));

        hTextViewAnimator = new HTextViewAnimator(hTextView, textsToAnimate, HTextViewType.FALL);
        hTextViewAnimator.setListener(new HTextViewAnimator.Listener() {
            @Override
            public void onAnimationEnd() {
                yoyoAnimator.play(Techniques.Bounce, textLogo);
                yoyoAnimator.playDelayed(hTextView, Techniques.ZoomOut, 1500);
                presenter.runActivityWithDelay(2100);
            }
        });
        hTextViewAnimator.PlaySequenceAnimation();
    }


    @NonNull
    @Override
    public SplashScreenPresenter createPresenter() {
        return new SplashScreenPresenter();
    }

    public Activity getActivity(){
        return this;
    }

    @Override
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
    public DrawableTypeRequest loadImageFromUrl(String url){
        return Glide.with(this).load(url);
    }

    @Override
    public void loadImageIntoView(ImageView view, DrawableTypeRequest drawableTypeRequest) {
        drawableTypeRequest.bitmapTransform(new BlurTransformation(this, 12), new GrayscaleTransformation(this))
                .into(view);
    }
}
