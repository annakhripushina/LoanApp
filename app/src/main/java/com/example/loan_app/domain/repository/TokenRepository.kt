package com.example.loan_app.domain.repository

interface TokenRepository {
    fun getToken(): String?

    fun setToken(value: String)

    fun removeToken()
}