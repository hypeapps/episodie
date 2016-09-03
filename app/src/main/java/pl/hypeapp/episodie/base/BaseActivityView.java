package pl.hypeapp.episodie.base;

import android.app.Activity;
import android.widget.TextView;

import com.tinmegali.mvp.mvp.ContextView;

public interface BaseActivityView extends ContextView{

    Activity getActivity();

    void setTextLogoFont(TextView textView);
}
