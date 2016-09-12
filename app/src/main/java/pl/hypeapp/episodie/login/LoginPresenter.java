package pl.hypeapp.episodie.login;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.Transformation;
import com.tinmegali.mvp.mvp.GenericPresenter;

import pl.hypeapp.episodie.util.image.BlurTransformation;
import pl.hypeapp.episodie.util.image.GrayscaleTransformation;

public class LoginPresenter extends GenericPresenter<LoginMVP.RequiredPresenterOps, LoginMVP.ProvidedModelOps,
        LoginMVP.RequiredViewOps, LoginModel>
        implements
        LoginMVP.RequiredPresenterOps,
        LoginMVP.ProvidedPresenterOps {

    LoginActivity activity;

    @Override
    public void onCreate(LoginMVP.RequiredViewOps view) {
        super.onCreate(LoginModel.class, this);
        setView(view);
        activity = (LoginActivity) getView().getActivity();
    }

    @Override
    public void onConfigurationChanged(LoginMVP.RequiredViewOps view) {
        setView(view);
        activity = (LoginActivity) getView().getActivity();
    }

    @Override
    public void onDestroy(boolean isChangingConfiguration) {

    }

    @Override
    public void onBackPressed() {
    }
}
