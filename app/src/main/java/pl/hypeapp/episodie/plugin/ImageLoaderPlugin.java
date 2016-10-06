package pl.hypeapp.episodie.plugin;

import android.graphics.Bitmap;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.Transformation;
import com.pascalwelsch.compositeandroid.activity.ActivityPlugin;

public class ImageLoaderPlugin extends ActivityPlugin {

    public void loadImageFromResourcesIntoView(ImageView view, int drawable, Transformation<Bitmap>... transformations) {
        Glide.with(getActivity()).load(drawable).bitmapTransform(transformations).into(view);
    }

    public void loadImageFromResourcesIntoView(ImageView view, int drawable) {
        Glide.with(getActivity()).load(drawable).into(view);
    }

//    public void loadImageFromResourcesIntoView(ImageView view, int drawable){
//        Glide.with(getActivity()).load(drawable).into(view);
//    }
//
//    public void loadImageFromAssetsIntoView(ImageView view, String path, Transformation... transformations) {
//
//    }
//
//    public void loadImageFromAssetsIntoView(ImageView view, String path){
//        Glide.with(getActivity()).load(path)
//                .bitmapTransform(transformations)
//                .into(view);
//    }
}
