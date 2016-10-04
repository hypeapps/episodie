package pl.hypeapp.episodie.presenter;

import net.grandcentrix.thirtyinch.TiLifecycleObserver;
import net.grandcentrix.thirtyinch.TiPresenter;

import pl.hypeapp.episodie.view.LoginView;


public class LoginPresenter extends TiPresenter<LoginView> implements TiLifecycleObserver {

    @Override
    protected void onWakeUp() {
        super.onWakeUp();
        getView().enterActivityLogoTransition();
    }

    @Override
    public void onChange(State state, boolean beforeLifecycleEvent) {

    }
}
