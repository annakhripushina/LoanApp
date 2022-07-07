package com.example.loan_app.data.repository

import com.example.loan_app.data.model.LoanRequest
import com.example.loan_app.data.model.LoanResponse
import com.example.loan_app.data.network.ApiService
import com.example.loan_app.data.room.LoansDao
import com.example.loan_app.domain.repository.LoanRepository
import com.example.loan_app.domain.repository.TokenRepository
import retrofit2.HttpException
import javax.inject.Inject

class LoanRepositoryImpl @Inject constructor(
    private val loansDao: LoansDao,
    private val apiService: ApiService,
    private val tokenRepository: TokenRepository
) : LoanRepository {

    override suspend fun getLoanList(): EntityResult<List<LoanResponse>> {
        try {
            loansDao.deleteLoans()
            tokenRepository.getToken()?.let {
                apiService.getAllLoans(it)
            }?.forEach { loan ->
                loansDao.insertLoan(loan)
            }
        } catch (http: HttpException) {
            return EntityResult(loansDao.getAllLoans(), RepositoryError.ERRORHTTPDB)
        } catch (e: Exception) {
            return EntityResult(loansDao.getAllLoans(), RepositoryError.ERRORNETWORKDB)
        }

        return EntityResult(loansDao.getAllLoans(), null)
    }

    override suspend fun getLoan(id: Long): EntityResult<LoanResponse> {
        try {
            tokenRepository.getToken()?.let {
                loansDao.updateLoan(apiService.getLoan(it, id.toString()))
            }
        } catch (http: HttpException) {
            return EntityResult(loansDao.getLoan(id), RepositoryError.ERRORHTTPDB)
        } catch (e: Exception) {
            return EntityResult(loansDao.getLoan(id), RepositoryError.ERRORNETWORKDB)
        }

        return EntityResult(loansDao.getLoan(id), null)
    }

    override suspend fun createLoan(loan: LoanRequest): EntityResult<LoanResponse> {
        val response: LoanResponse?
        try {
            response = tokenRepository.getToken()?.let {
                apiService.postLoan(loan, it)
            }
        } catch (http: HttpException) {
            return EntityResult(null, RepositoryError.ERRORHTTP)
        } catch (e: Exception) {
            return EntityResult(null, RepositoryError.ERRORNETWORK)
        }

        return EntityResult(response, null)
    }

}