package com.example.loan_app.extensions

import android.content.Context
import android.content.res.Configuration
import android.view.ContextThemeWrapper
import java.util.*


object LocaleHelper {
    private const val SELECTED_LANGUAGE = "SELECTED_LANGUAGE"

    fun persist(context: Context, language: String) {
        context.getSharedPreferences(SELECTED_LANGUAGE, Context.MODE_PRIVATE)
            .edit()
            .putString(SELECTED_LANGUAGE, language)
            .apply()
    }

    fun getPersistedData(context: Context): String? =
        context.getSharedPreferences(SELECTED_LANGUAGE, Context.MODE_PRIVATE)
            .getString(SELECTED_LANGUAGE, "")


    fun updateConfig(wrapper: ContextThemeWrapper, locale: Locale) {
        if (locale == Locale(""))
            return
        Locale.setDefault(locale)
        val configuration = Configuration()
        configuration.setLocale(locale)
        wrapper.applyOverrideConfiguration(configuration)
    }

}