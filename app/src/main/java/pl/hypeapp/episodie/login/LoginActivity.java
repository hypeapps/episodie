package pl.hypeapp.episodie.login;

import android.os.Bundle;
import android.transition.TransitionManager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Space;
import android.widget.TextView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.google.firebase.auth.FirebaseAuth;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import pl.hypeapp.episodie.App;
import pl.hypeapp.episodie.R;
import pl.hypeapp.episodie.base.BaseMVPActivity;


public class LoginActivity extends
        BaseMVPActivity<LoginMVP.RequiredViewOps, LoginMVP.ProvidedPresenterOps, LoginPresenter>
        implements LoginMVP.RequiredViewOps {

    @BindView(R.id.iv_login_background)
    ImageView loginBackgroundImageView;
    @BindView(R.id.rl_logo)
    ViewGroup logoLayout;
    @BindView(R.id.iv_logo)
    ImageView logoIcon;
    @BindView(R.id.tv_logo)
    TextView logoText;
    @BindView(R.id.center)
    Space space;
    @Inject
    FirebaseAuth firebaseAuth;

    App app;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        super.onCreate(LoginPresenter.class, this);

        String backgroundImageUrl = getString(R.string.image_background_url);
        getPresenter().loadImageFromUrlIntoView(loginBackgroundImageView, backgroundImageUrl);
        setTextLogoFont(logoText);
        enterActivityLogoTransition();

        app = (App) getApplication();
        app.getAuthComponent().inject(this);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }



    public void enterActivityLogoTransition() {
        logoIcon.postDelayed(logoTransitionRun(logoIcon, logoText, logoLayout), 1400);
    }

    private Runnable logoTransitionRun(final View logoIcon, final View logoText, final ViewGroup logoLayout) {
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

    public FirebaseAuth getFirebaseAuth() {
        return firebaseAuth;
    }

    public void onCompleteLogin() {
    }
}
