package pl.hypeapp.episodie.presenter;

import net.grandcentrix.thirtyinch.TiPresenter;

import pl.hypeapp.episodie.view.WelcomeView;

public class WelcomePresenter extends TiPresenter<WelcomeView> {

    public void onNextPage(){
        getView().animatePagerTransition(true);
    }
}
