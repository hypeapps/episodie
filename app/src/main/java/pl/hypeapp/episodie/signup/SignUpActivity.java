package pl.hypeapp.episodie.signup;

import android.animation.Animator;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.animation.AccelerateInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import pl.hypeapp.episodie.App;
import pl.hypeapp.episodie.R;
import pl.hypeapp.episodie.base.BaseMVPActivity;
import pl.hypeapp.episodie.util.BuildUtil;

public class SignUpActivity extends
        BaseMVPActivity<SignUpMVP.RequiredViewOps, SignUpMVP.ProvidedPresenterOps, SignUpPresenter>
        implements SignUpMVP.RequiredViewOps {

//    @BindView(R.id.bt_sign_up)
    Button signUpButton;
    @BindView(R.id.iv_signup_background)
    ImageView backgroundImageView;
    @BindView(R.id.iv_shared_background)
    ImageView sharedBackground;
    @BindView(R.id.sign_up_layout)
    RelativeLayout rootLayout;
    @Inject
    FirebaseAuth firebaseAuth;
    App app;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        ButterKnife.bind(this);
        super.onCreate(SignUpPresenter.class, this);
        loadImageFromResourcesIntoView(backgroundImageView, R.drawable.mrrobot_background);
        rootLayout.post(revealAnimationRun());

        app = (App) getApplication();
        app.getAuthComponent().inject(this);
    }

    private Runnable revealAnimationRun() {
        return new Runnable() {
            @Override
            public void run() {
                revealBackgroundFromCoordinates(backgroundImageView);
            }
        };
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    public FirebaseAuth getFirebaseAuth() {
        return firebaseAuth;
    }

    private Animator revealBackgroundFromCoordinates(final ImageView viewRoot) {
        Animator anim = null;
        long duration = getResources().getInteger(R.integer.anim_duration_long);
        if (BuildUtil.isMinApi21()) {

            int cx = (viewRoot.getLeft() + viewRoot.getRight()) / 2;
            int cy = (viewRoot.getTop() + viewRoot.getBottom()) / 2;
            int finalRadius = Math.max(viewRoot.getWidth(), viewRoot.getHeight());
            anim = ViewAnimationUtils.createCircularReveal(viewRoot, cx, cy, 0, finalRadius);
            anim.setDuration(duration);
            anim.setInterpolator(new AccelerateInterpolator());
            anim.start();
        }
        return anim;
    }

    @Override
    public void onCompleteSignUp() {
        Log.e("SIGN_UP_ACTIVITY", "SIGN_UP_COMPLETE");
    }

    @Override
    public void onFailureSignUp() {
        Log.e("SIGN_UP_ACTIVITY", "SIGN_UP_FAIL");
    }

    @Override
    public void onSuccessSignUp() {
        Log.e("SIGN_UP_ACTIVITY", "SIGN_UP_SUCCESS");
    }

//    @OnClick(R.id.bt_sign_up)
    public void registerUser(View view) {
//        Log.e("SIGN_UP_ACTIVITY", "button");
//        getPresenter().registerUser("register@test.pl", "test6liter");

    }
}
