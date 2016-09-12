package pl.hypeapp.episodie.base;

import android.app.Activity;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.load.Transformation;
import com.tinmegali.mvp.mvp.ContextView;

public interface BaseActivityView extends ContextView{

    Activity getActivity();

    void setTextLogoFont(TextView textView);

    void loadImageFromAssetsIntoView(ImageView view, String path);

    void loadImageFromAssetsIntoView(ImageView view, String path, Transformation... transformations);

    void loadImageFromResourcesIntoView(ImageView view, int drawable);

    void loadImageFromResourcesIntoView(ImageView view, int drawable, Transformation... transformations);
}
