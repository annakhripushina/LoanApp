package com.example.loan_app.domain.usecase

import com.example.loan_app.data.model.AuthRequest
import com.example.loan_app.data.model.RegistrationResponse
import com.example.loan_app.data.repository.EntityResult
import com.example.loan_app.domain.repository.AuthRepository
import javax.inject.Inject

class RegistrationUserUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    suspend fun registrationUser(authRequest: AuthRequest): EntityResult<RegistrationResponse> =
        authRepository.registrationUser(authRequest)

}