package pl.hypeapp.episodie.base;

import android.app.Activity;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.DrawableTypeRequest;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.Transformation;
import com.tinmegali.mvp.mvp.GenericMVPActivity;
import com.tinmegali.mvp.mvp.PresenterOps;

import pl.hypeapp.episodie.util.FontManager;

public abstract class BaseMVPActivity<RequiredViewOps, ProvidedPresenterOps, PresenterType extends PresenterOps<RequiredViewOps>>
        extends GenericMVPActivity<RequiredViewOps, ProvidedPresenterOps, PresenterType>
        implements BaseActivityView {

    @Override
    public Activity getActivity() {
        return this;
    }

    @Override
    public void setTextLogoFont(TextView textView) {
        textView.setTypeface(FontManager.getInstance(getAssets()).getFont("fonts/coolvetica.ttf"));
    }

    @Override
    public void loadImageFromResourcesIntoView(ImageView view, int drawable, Transformation... transformations){
        Glide.with(getActivity()).load(drawable).bitmapTransform(transformations).into(view);
    }

    @Override
    public void loadImageFromResourcesIntoView(ImageView view, int drawable){
        Glide.with(getActivity()).load(drawable).into(view);
    }

    @Override
    public void loadImageFromAssetsIntoView(ImageView view, String path, Transformation... transformations) {

    }

    @Override
    public void loadImageFromAssetsIntoView(ImageView view, String path){
//        Glide.with(getActivity()).load(path)
//                .bitmapTransform(transformations)
//                .into(view);
    }
}
