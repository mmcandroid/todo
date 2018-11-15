package com.example.malek.todo

import android.app.Application
import android.support.multidex.MultiDexApplication
import timber.log.Timber



class TodoApplication : MultiDexApplication(){
    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }

    }
}