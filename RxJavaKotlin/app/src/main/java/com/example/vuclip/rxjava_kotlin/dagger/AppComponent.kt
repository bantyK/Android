package com.example.vuclip.rxjava_kotlin.dagger

import com.example.vuclip.rxjava_kotlin.MainActivity
import dagger.Component

/**
 * Created by Banty on 31/03/18.
 */
@Component(modules = [(NetworkModule::class)])
interface AppComponent {
    fun inject(activity: MainActivity)
}