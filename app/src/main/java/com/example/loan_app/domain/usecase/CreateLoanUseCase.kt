package com.example.loan_app.domain.usecase

import com.example.loan_app.data.model.LoanRequest
import com.example.loan_app.data.model.LoanResponse
import com.example.loan_app.data.repository.EntityResult
import com.example.loan_app.domain.repository.LoanRepository
import javax.inject.Inject

class CreateLoanUseCase @Inject constructor(
    private val loanRepository: LoanRepository
) {
    suspend fun createLoan(loan: LoanRequest): EntityResult<LoanResponse> =
        loanRepository.createLoan(loan)

}