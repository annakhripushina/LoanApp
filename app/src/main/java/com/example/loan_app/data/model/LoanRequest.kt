package com.example.loan_app.data.model

data class LoanRequest(
    val amount: Double,
    val firstName: String,
    val lastName: String,
    val percent: Double,
    val period: Int,
    val phoneNumber: String,
)