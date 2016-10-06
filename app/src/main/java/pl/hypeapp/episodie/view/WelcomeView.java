package pl.hypeapp.episodie.view;

import android.view.View;

import net.grandcentrix.thirtyinch.TiView;
import net.grandcentrix.thirtyinch.callonmainthread.CallOnMainThread;

public interface WelcomeView extends TiView {

    @CallOnMainThread
    void animatePagerTransition(final boolean forward);
}
