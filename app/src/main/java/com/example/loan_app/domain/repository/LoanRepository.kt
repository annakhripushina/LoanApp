package com.example.loan_app.domain.repository

import com.example.loan_app.data.model.LoanRequest
import com.example.loan_app.data.model.LoanResponse
import com.example.loan_app.data.repository.EntityResult

interface LoanRepository {
    suspend fun getLoanList(): EntityResult<List<LoanResponse>>

    suspend fun getLoan(id: Long): EntityResult<LoanResponse>

    suspend fun createLoan(loan: LoanRequest): EntityResult<LoanResponse>

}