package pl.hypeapp.episodie.ui.fragment;

import android.support.annotation.NonNull;

import net.grandcentrix.thirtyinch.TiFragment;

import pl.hypeapp.episodie.presenter.RegistrationMethodPresenter;
import pl.hypeapp.episodie.view.RegistrationMethodView;

public class RegistrationMethodFragment extends TiFragment<RegistrationMethodPresenter, RegistrationMethodView> {

    @NonNull
    @Override
    public RegistrationMethodPresenter providePresenter() {
        return null;
    }
}