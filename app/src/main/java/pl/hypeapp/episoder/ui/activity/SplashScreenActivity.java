package pl.hypeapp.episoder.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.hanks.htextview.HTextView;
import com.hanks.htextview.HTextViewType;

import butterknife.BindView;
import butterknife.ButterKnife;
import pl.hypeapp.episoder.R;
import pl.hypeapp.episoder.animation.BlurTransformation;
import pl.hypeapp.episoder.animation.GrayscaleTransformation;
import pl.hypeapp.episoder.animation.HTextViewAnimator;
import pl.hypeapp.episoder.animation.YoYoAnimator;
import pl.hypeapp.episoder.util.FontManager;
import pl.hypeapp.episoder.util.StringUtil;


public class SplashScreenActivity extends AppCompatActivity {
    @BindView(R.id.iv_background_splash_screen)
    ImageView backgroundImage;
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

        Glide.with(this).load("http://www.goliath.com/wp-content/uploads/2015/12/1035x776-breakingbad-1800-1404309469.jpg")
                .bitmapTransform(new BlurTransformation(this, 12), new GrayscaleTransformation(this))
                .into(backgroundImage);

        String[] textsToAnimate = StringUtil.upperCaseArray(getResources().getStringArray(R.array.splash_screen_texts));

        hTextView.setTypeface(FontManager.getInstance(getAssets()).getFont("fonts/LeagueGothic-Regular.ttf"));
        hTextView.animateText(getString(R.string.app_name));
        hTextViewAnimator = new HTextViewAnimator(hTextView, textsToAnimate, HTextViewType.FALL);
        hTextViewAnimator.setListener(new HTextViewAnimator.Listener() {
            @Override
            public void onAnimationEnd() {
                YoYo.with(Techniques.Bounce).playOn(textLogo);
                yoyoAnimator.playDelayed(hTextView, Techniques.ZoomOut, 1600);
                hTextView.postDelayed(runActivity(), 2000);
            }
        });
        hTextViewAnimator.PlaySequenceAnimation();
    }

    Runnable runActivity(){
        return new Runnable() {
            @Override
            public void run() {
                View sharedLogo = textLogo;
                View sharedBackground = backgroundImage;
                Pair<View, String> sharedElement = Pair.
                        create(sharedLogo, getResources().getString(R.string.shared_name_logo));
                Pair<View, String> sharedElement1 = Pair.
                        create(sharedBackground, getResources().getString(R.string.shared_name_background));
                ActivityOptionsCompat transitionActivityOptions = null;
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                    transitionActivityOptions = ActivityOptionsCompat.
                            makeSceneTransitionAnimation(SplashScreenActivity.this, sharedElement, sharedElement1);
                }
                startActivity(new Intent(SplashScreenActivity.this, LoginActivity.class), transitionActivityOptions.toBundle());
            }
        };
    }

}
