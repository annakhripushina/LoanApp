package com.example.loan_app.domain.usecase

import com.example.loan_app.domain.repository.AuthRepository
import javax.inject.Inject

class LogOutUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    fun logOutUser() {
        authRepository.logOutUser()
    }

}