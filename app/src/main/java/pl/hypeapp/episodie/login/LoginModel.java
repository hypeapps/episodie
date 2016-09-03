package pl.hypeapp.episodie.login;

import android.content.Context;

import com.bumptech.glide.DrawableTypeRequest;
import com.bumptech.glide.Glide;
import com.tinmegali.mvp.mvp.GenericModel;

public class LoginModel extends GenericModel<LoginMVP.RequiredPresenterOps>
        implements LoginMVP.ProvidedModelOps {

    @Override
    public DrawableTypeRequest getBitmapFromUrl(String url, Context context) {
        return Glide.with(context).load(url);
    }
}
