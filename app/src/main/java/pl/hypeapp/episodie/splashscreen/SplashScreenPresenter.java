package pl.hypeapp.episodie.splashscreen;

import net.grandcentrix.thirtyinch.TiPresenter;

public class SplashScreenPresenter extends TiPresenter<SplashScreenView> {

    public void onWindowFocus() {
        getView().startTextLogoAnimation();
    }

    public void onAnimationLogoEnd(){
        getView().startActivityDelayed(2200);
    }
}
