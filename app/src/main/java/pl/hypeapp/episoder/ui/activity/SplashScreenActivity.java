package pl.hypeapp.episoder.ui.activity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.hanks.htextview.HTextView;
import com.hanks.htextview.HTextViewType;

import butterknife.BindView;
import butterknife.ButterKnife;
import pl.hypeapp.episoder.R;
import pl.hypeapp.episoder.animation.BlurTransformation;
import pl.hypeapp.episoder.animation.GrayscaleTransformation;
import pl.hypeapp.episoder.util.FontManager;


public class SplashScreenActivity extends AppCompatActivity {

    @BindView(R.id.iv_background_splash_screen)
    ImageView backgroundImage;
    @BindView(R.id.iv_text_logo)
    ImageView textLogo;
    @BindView(R.id.tv_logo)
    HTextView hTextView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        ButterKnife.bind(this);
        Glide.with(this).load("http://www.goliath.com/wp-content/uploads/2015/12/1035x776-breakingbad-1800-1404309469.jpg")
                .bitmapTransform(new BlurTransformation(this, 12), new GrayscaleTransformation(this))
                .into(backgroundImage);

        hTextView.setTypeface(FontManager.getInstance(getAssets()).getFont("fonts/LeagueGothic-Regular.ttf"));
        hTextView.setAnimateType(HTextViewType.FALL);
        hTextView.animateText("Episodie");
        String[] textsToAnimate = {"SEASONS", "EPISODES", "TV SHOWS", "YOUR TV SHOW MANAGER"};
        handleTextsAnimation(textsToAnimate,hTextView, 0);


    }

    public void handleTextsAnimation(final String[] textsToAnimate, final HTextView hTextViewToAnimate, final int textIndex) {
        final int textToAnimateLength = textsToAnimate.length;
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                hTextViewToAnimate.animateText(textsToAnimate[textIndex]);
                if(textIndex < textToAnimateLength - 1){
                    handleTextsAnimation(textsToAnimate, hTextViewToAnimate, textIndex + 1);
                }else {
//                    YoYo.with(Techniques.Bounce).playOn(textLogo);
                    View sharedView = textLogo;
                    View sharedView2 = backgroundImage;
                    String transitionName = "logo";
                    Pair<View, String> sharedElement = Pair.create(sharedView, "logo");
                    Pair<View, String> sharedElement1 = Pair.create(sharedView2, "background");
                    ActivityOptionsCompat transitionActivityOptions = null;
                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                        transitionActivityOptions = ActivityOptionsCompat.makeSceneTransitionAnimation(SplashScreenActivity.this, sharedElement, sharedElement1);
                    }
                    startActivity(new Intent(SplashScreenActivity.this, LoginActivity.class), transitionActivityOptions.toBundle());
                }
            }
        }, 1500);
    }
}
