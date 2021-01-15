package com.example.tinyrouter

import android.app.Application
import com.tinyrouter.api.TinyRouter

public class App : Application() {

    override fun onCreate() {
        super.onCreate()
        TinyRouter.getInstance().init(this)
    }
}