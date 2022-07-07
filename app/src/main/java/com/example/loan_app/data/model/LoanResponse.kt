package com.example.loan_app.data.model

import androidx.room.Entity

@Entity(
    tableName = "loansTable",
    primaryKeys = ["id"]
)
data class LoanResponse(
    val amount: Double,
    val date: String,
    val firstName: String,
    val id: Long,
    val lastName: String,
    val percent: Double,
    val period: Int,
    val phoneNumber: String,
    val state: LoanState,
)
