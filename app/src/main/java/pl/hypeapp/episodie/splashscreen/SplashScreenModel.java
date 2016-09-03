package pl.hypeapp.episodie.splashscreen;

import android.content.Context;

import com.bumptech.glide.DrawableTypeRequest;
import com.bumptech.glide.Glide;
import com.tinmegali.mvp.mvp.GenericModel;

public class SplashScreenModel extends GenericModel<SplashScreenMVP.RequiredPresenterOps>
        implements SplashScreenMVP.ProvidedModelOps {

    @Override
    public DrawableTypeRequest getBitmapFromUrl(String url, Context context) {
        return Glide.with(context).load(url);
    }
}
