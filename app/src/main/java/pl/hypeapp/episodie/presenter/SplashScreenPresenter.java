package pl.hypeapp.episodie.presenter;

import net.grandcentrix.thirtyinch.TiPresenter;

import pl.hypeapp.episodie.view.SplashScreenView;

public class SplashScreenPresenter extends TiPresenter<SplashScreenView> {

    public void onWindowFocus() {
        getView().startTextLogoAnimation();
    }

    public void onAnimationLogoEnd(){
        getView().startActivityDelayed(2200);
    }
}
