package pl.hypeapp.episoder.ui.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.transition.TransitionManager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Space;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.hannesdorfmann.mosby.mvp.MvpActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import pl.hypeapp.episoder.R;
import pl.hypeapp.episoder.animation.BlurTransformation;
import pl.hypeapp.episoder.animation.GrayscaleTransformation;
import pl.hypeapp.episoder.ui.presenter.LoginPresenter;
import pl.hypeapp.episoder.util.FontManager;
import pl.hypeapp.episoder.view.LoginView;

public class LoginActivity extends MvpActivity<LoginView, LoginPresenter>
        implements LoginView {
    @BindView(R.id.iv_login_background)
    ImageView loginBackground;
    @BindView(R.id.rl_logo)
    ViewGroup logoLayout;
    @BindView(R.id.iv_logo)
    ImageView logoIcon;
    @BindView(R.id.tv_logo)
    TextView logoText;
    @BindView(R.id.center)
    Space space;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        presenter.loadBackgroundImage(loginBackground);

        setTextLogoFont(logoText);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            enterActivityLogoTransition(logoIcon, logoText, logoLayout, 800);
        }
    }

    @NonNull
    @Override
    public LoginPresenter createPresenter() {
        return new LoginPresenter();
    }

    private void setTextLogoFont(TextView logoText) {
        logoText.setTypeface(FontManager.getInstance(getAssets()).getFont("fonts/LeagueGothic-Regular.ttf"));
    }

    private void enterActivityLogoTransition(View logoIcon, View logoText, ViewGroup rootGroup, long delayInMilis) {
        logoIcon.postDelayed(logoTransitionRun(logoIcon, logoText, rootGroup), delayInMilis);
    }

    Runnable logoTransitionRun(final View logoIcon, final View logoText, final ViewGroup logoLayout) {
        return new Runnable() {
            @Override
            public void run() {
                TransitionManager.beginDelayedTransition(logoLayout);

                RelativeLayout.LayoutParams rl = (RelativeLayout.LayoutParams) logoIcon.getLayoutParams();
                rl.addRule(RelativeLayout.LEFT_OF, space.getId());
                logoIcon.setLayoutParams(rl);

                logoText.setVisibility(View.VISIBLE);
                YoYo.with(Techniques.SlideInRight).playOn(logoText);
            }
        };
    }

    @Override
    public void loadImageFromUrlIntoView(ImageView view, String url) {
        Glide.with(this).load(url)
                .bitmapTransform(new BlurTransformation(this, 12), new GrayscaleTransformation(this))
                .into(view);
    }
}
