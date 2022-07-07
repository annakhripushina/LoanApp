package com.example.loan_app.domain.repository

import com.example.loan_app.data.model.AuthRequest
import com.example.loan_app.data.model.RegistrationResponse
import com.example.loan_app.data.repository.EntityResult

interface AuthRepository {
    suspend fun loginUser(authRequest: AuthRequest): EntityResult<String>

    suspend fun registrationUser(authRequest: AuthRequest): EntityResult<RegistrationResponse>

    fun logOutUser()
}