package com.example.loan_app.data.room

import androidx.room.*
import com.example.loan_app.data.model.LoanResponse


@Dao
interface LoansDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertLoan(loan: LoanResponse)

    @Query("SELECT * FROM loansTable")
    suspend fun getAllLoans(): List<LoanResponse>

    @Query("SELECT * FROM loansTable WHERE id = :id")
    suspend fun getLoan(id: Long): LoanResponse

    @Query("DELETE FROM loansTable")
    suspend fun deleteLoans()

    @Update
    fun updateLoan(loan: LoanResponse)
}