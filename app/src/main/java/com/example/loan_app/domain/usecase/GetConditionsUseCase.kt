package com.example.loan_app.domain.usecase

import com.example.loan_app.data.model.LoanConditionsResponse
import com.example.loan_app.data.repository.EntityResult
import com.example.loan_app.domain.repository.ConditionRepository
import javax.inject.Inject

class GetConditionsUseCase @Inject constructor(
    private val conditionRepository: ConditionRepository
) {
    suspend fun getLoanConditions(): EntityResult<LoanConditionsResponse> =
        conditionRepository.getConditions()

}