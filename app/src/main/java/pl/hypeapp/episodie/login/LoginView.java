package pl.hypeapp.episodie.login;

import net.grandcentrix.thirtyinch.TiView;
import net.grandcentrix.thirtyinch.callonmainthread.CallOnMainThread;

public interface LoginView extends TiView {
    @CallOnMainThread
    void enterActivityLogoTransition();
}
