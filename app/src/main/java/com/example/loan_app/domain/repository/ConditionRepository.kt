package com.example.loan_app.domain.repository

import com.example.loan_app.data.model.LoanConditionsResponse
import com.example.loan_app.data.repository.EntityResult

interface ConditionRepository {

    suspend fun getConditions(): EntityResult<LoanConditionsResponse>
}