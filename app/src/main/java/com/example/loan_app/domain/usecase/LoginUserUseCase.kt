package com.example.loan_app.domain.usecase

import com.example.loan_app.data.model.AuthRequest
import com.example.loan_app.data.repository.EntityResult
import com.example.loan_app.domain.repository.AuthRepository
import javax.inject.Inject

class LoginUserUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    suspend fun loginUser(authRequest: AuthRequest): EntityResult<String> =
        authRepository.loginUser(authRequest)

}