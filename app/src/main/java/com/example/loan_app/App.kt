package com.example.loan_app

import android.app.Application
import com.example.loan_app.di.AppComponent
import com.example.loan_app.di.DaggerAppComponent
import com.example.loan_app.di.module.DataModule
import com.example.loan_app.extensions.LocaleHelper
import com.example.loan_app.ui.MainActivity
import java.util.*

class App : Application() {
    val appComponent: AppComponent =
        DaggerAppComponent.builder()
            .dataModule(DataModule(this))
            .build()

    override fun onCreate() {
        super.onCreate()

        val language = LocaleHelper.getPersistedData(this)
        MainActivity.locale = Locale(language)
    }

}