package com.example.loan_app.data.model

data class LoanConditionsResponse(
    val maxAmount: Int,
    val percent: Double,
    val period: Int,
)