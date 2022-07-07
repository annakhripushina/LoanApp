package com.example.loan_app.data.network

import com.example.loan_app.data.model.*
import retrofit2.http.*

interface ApiService {
    @POST("/login")
    suspend fun postLogin(
        @Body loginRequest: AuthRequest
    ): String

    @POST("/registration")
    suspend fun postRegistration(
        @Body registrationRequest: AuthRequest
    ): RegistrationResponse

    @POST("/loans")
    suspend fun postLoan(
        @Body loan: LoanRequest,
        @Header("Authorization") token: String
    ): LoanResponse

    @GET("/loans/all")
    suspend fun getAllLoans(
        @Header("Authorization") token: String
    ): List<LoanResponse>

    @GET("/loans/conditions")
    suspend fun getConditions(
        @Header("Authorization") token: String
    ): LoanConditionsResponse

    @GET("/loans/{id}")
    suspend fun getLoan(
        @Header("Authorization") token: String,
        @Path("id") id: String
    ): LoanResponse
}