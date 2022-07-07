package com.example.loan_app.data.repository

import android.content.Context
import com.example.loan_app.domain.repository.TokenRepository
import javax.inject.Inject

class TokenRepositoryImpl @Inject constructor(private val context: Context) : TokenRepository {
    companion object {
        private const val TOKEN_NAME = "TOKEN_NAME"
        private const val TOKEN_KEY = "TOKEN_KEY"
    }

    override fun getToken(
    ): String? {
        return context.getSharedPreferences(
            TOKEN_NAME,
            Context.MODE_PRIVATE
        ).getString(TOKEN_KEY, "")
    }

    override fun setToken(value: String) {
        context.getSharedPreferences(TOKEN_NAME, Context.MODE_PRIVATE)
            .edit()
            .putString(TOKEN_KEY, value)
            .apply()
    }

    override fun removeToken() {
        context.getSharedPreferences(TOKEN_NAME, Context.MODE_PRIVATE)
            .edit()
            .clear()
            .apply()
    }

}