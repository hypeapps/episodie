package pl.hypeapp.episodie.login;

import net.grandcentrix.thirtyinch.TiLifecycleObserver;
import net.grandcentrix.thirtyinch.TiPresenter;


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
