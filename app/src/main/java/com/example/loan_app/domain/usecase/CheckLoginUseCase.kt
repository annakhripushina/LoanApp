package com.example.loan_app.domain.usecase

import com.example.loan_app.domain.repository.TokenRepository
import javax.inject.Inject

class CheckLoginUseCase @Inject constructor(
    private val tokenRepository: TokenRepository
) {
    fun getToken(): String? =
        tokenRepository.getToken()
}