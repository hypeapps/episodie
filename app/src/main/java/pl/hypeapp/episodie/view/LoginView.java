package pl.hypeapp.episodie.view;

import net.grandcentrix.thirtyinch.TiView;
import net.grandcentrix.thirtyinch.callonmainthread.CallOnMainThread;

public interface LoginView extends TiView {

    @CallOnMainThread
    void enterActivityLogoTransition();
}
