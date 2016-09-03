package pl.hypeapp.episodie.signup;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import pl.hypeapp.episodie.App;
import pl.hypeapp.episodie.R;
import pl.hypeapp.episodie.base.BaseMVPActivity;


public class SignUpActivity extends
        BaseMVPActivity<SignUpMVP.RequiredViewOps, SignUpMVP.ProvidedPresenterOps, SignUpPresenter>
        implements SignUpMVP.RequiredViewOps {

    @BindView(R.id.bt_sign_up)
    Button signUpButton;
    @Inject
    FirebaseAuth firebaseAuth;

    App app;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        ButterKnife.bind(this);

        super.onCreate(SignUpPresenter.class, this);

        String backgroundImageUrl = getString(R.string.image_background_url);
//        getPresenter().loadImageFromUrlIntoView(loginBackgroundImageView, backgroundImageUrl);
//        setTextLogoFont(logoText);
//        enterActivityLogoTransition();

        app = (App) getApplication();
        app.getAuthComponent().inject(this);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

//    public void enterActivityTitleTransition() {
////        logoIcon.postDelayed(logoTransitionRun(logoIcon, logoText, logoLayout), 1400);
//    }
//
//    private Runnable logoTransitionRun(final View logoIcon, final View logoText, final ViewGroup logoLayout) {
//        return new Runnable() {
//            @Override
//            public void run() {
//                TransitionManager.beginDelayedTransition(logoLayout);
//
//                RelativeLayout.LayoutParams rl = (RelativeLayout.LayoutParams) logoIcon.getLayoutParams();
//                rl.addRule(RelativeLayout.LEFT_OF, space.getId());
//                logoIcon.setLayoutParams(rl);
//
//                logoText.setVisibility(View.VISIBLE);
//                YoYo.with(Techniques.SlideInRight).playOn(logoText);
//            }
//        };
//    }

    @Override
    public FirebaseAuth getFirebaseAuth() {
        return firebaseAuth;
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

    @OnClick(R.id.bt_sign_up)
    public void registerUser(View view) {
        Log.e("SIGN_UP_ACTIVITY", "button");
        getPresenter().registerUser("register@test.pl", "test6liter");
//        presenter.registerUserWithPassword("pszem.szym@gmail.com", "222");
    }
}
