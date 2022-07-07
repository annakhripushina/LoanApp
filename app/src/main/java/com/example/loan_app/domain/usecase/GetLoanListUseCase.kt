package com.example.loan_app.domain.usecase

import com.example.loan_app.data.model.LoanResponse
import com.example.loan_app.data.repository.EntityResult
import com.example.loan_app.domain.repository.LoanRepository
import javax.inject.Inject

class GetLoanListUseCase @Inject constructor(
    private val loanRepository: LoanRepository
) {
    suspend fun getLoanList(): EntityResult<List<LoanResponse>> =
        loanRepository.getLoanList()

}