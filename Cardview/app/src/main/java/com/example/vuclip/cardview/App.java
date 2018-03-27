package com.example.vuclip.cardview;

import android.app.Application;

import com.example.vuclip.cardview.dagger.DaggerMainActivityComponent;
import com.example.vuclip.cardview.dagger.MainActivityComponent;
import com.example.vuclip.cardview.dagger.MainActivityModule;

/**
 * Created by Banty on 27/03/18.
 */

public class App extends Application {

    MainActivityComponent component;

    @Override
    public void onCreate() {
        super.onCreate();

        component = DaggerMainActivityComponent.builder()
                .mainActivityModule(new MainActivityModule())
                .build();
    }

    public MainActivityComponent getComponent() {
        return component;
    }

}
