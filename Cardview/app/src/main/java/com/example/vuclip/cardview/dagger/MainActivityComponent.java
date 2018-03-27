package com.example.vuclip.cardview.dagger;

import com.example.vuclip.cardview.activity.MainActivity;

import dagger.Component;

/**
 * Created by Banty on 27/03/18.
 */

@Component(modules = {MainActivityModule.class})
public interface MainActivityComponent {
    void inject(MainActivity target);
}
