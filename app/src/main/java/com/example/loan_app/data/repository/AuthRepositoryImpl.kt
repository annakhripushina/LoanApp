package com.example.loan_app.data.repository

import com.example.loan_app.data.model.AuthRequest
import com.example.loan_app.data.model.RegistrationResponse
import com.example.loan_app.data.network.ApiService
import com.example.loan_app.domain.repository.AuthRepository
import com.example.loan_app.domain.repository.TokenRepository
import retrofit2.HttpException
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val tokenRepository: TokenRepository
) : AuthRepository {

    override suspend fun loginUser(authRequest: AuthRequest): EntityResult<String> {
        val token: String?
        try {
            token = apiService.postLogin(authRequest)
            tokenRepository.setToken(token)
        } catch (http: HttpException) {
            return if (http.code() == 404)
                EntityResult(null, RepositoryError.ERROR404)
            else
                EntityResult(null, RepositoryError.ERRORHTTP)

        } catch (e: Exception) {
            return EntityResult(null, RepositoryError.ERRORNETWORK)
        }
        return EntityResult(token, null)
    }

    override suspend fun registrationUser(authRequest: AuthRequest): EntityResult<RegistrationResponse> {
        val response: RegistrationResponse?
        try {
            response = apiService.postRegistration(authRequest)
        } catch (http: HttpException) {
            return if (http.code() == 400)
                EntityResult(null, RepositoryError.ERROR400)
            else
                EntityResult(null, RepositoryError.ERRORHTTP)

        } catch (e: Exception) {
            return EntityResult(null, RepositoryError.ERRORNETWORK)
        }
        return EntityResult(response, null)
    }

    override fun logOutUser() {
        tokenRepository.removeToken()
    }


}