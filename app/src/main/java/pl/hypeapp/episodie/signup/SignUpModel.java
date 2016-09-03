package pl.hypeapp.episodie.signup;

import android.content.Context;

import com.bumptech.glide.DrawableTypeRequest;
import com.tinmegali.mvp.mvp.GenericModel;


public class SignUpModel extends GenericModel<SignUpMVP.RequiredPresenterOps>
        implements SignUpMVP.ProvidedModelOps {
    @Override
    public DrawableTypeRequest getBitmapFromUrl(String url, Context context) {
        return null;
    }
}
