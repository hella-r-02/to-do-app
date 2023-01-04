package com.src.todo

import android.app.Application
import com.src.todo.di.AppComponent
import com.src.todo.di.AppModule
import com.src.todo.di.DaggerAppComponent

class App : Application() {
    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent
            .builder()
            .appModule(AppModule(context = this))
            .build()
    }
}