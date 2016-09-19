package pl.hypeapp.episodie.splashscreen;

import net.grandcentrix.thirtyinch.TiView;
import net.grandcentrix.thirtyinch.callonmainthread.CallOnMainThread;

public interface SplashScreenView extends TiView {

    @CallOnMainThread
    void startTextLogoAnimation();

    @CallOnMainThread
    void startActivityDelayed(long delayInMilis);
}
