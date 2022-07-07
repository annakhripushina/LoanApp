package com.example.loan_app.data.repository

import com.example.loan_app.data.model.LoanConditionsResponse
import com.example.loan_app.data.network.ApiService
import com.example.loan_app.domain.repository.ConditionRepository
import com.example.loan_app.domain.repository.TokenRepository
import retrofit2.HttpException
import javax.inject.Inject

class ConditionRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val tokenRepository: TokenRepository
) : ConditionRepository {

    override suspend fun getConditions(): EntityResult<LoanConditionsResponse> {
        val conditions: LoanConditionsResponse?
        try {
            conditions = tokenRepository.getToken()?.let {
                apiService.getConditions(it)
            }
        } catch (http: HttpException) {
            return EntityResult(null, RepositoryError.ERRORHTTP)
        } catch (e: Exception) {
            return EntityResult(null, RepositoryError.ERRORNETWORK)
        }
        return EntityResult(conditions, null)
    }
}