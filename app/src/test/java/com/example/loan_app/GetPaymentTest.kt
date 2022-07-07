package com.example.loan_app

import com.example.loan_app.domain.usecase.CreateLoanUseCase
import com.example.loan_app.domain.usecase.GetConditionsUseCase
import com.example.loan_app.presentation.createLoan.CreateLoanViewModel
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Parameterized
import org.mockito.kotlin.mock

@RunWith(Parameterized::class)
class GetPaymentTest(
    private val amount: Double,
    private val percent: Double,
    private val period: Int,
    private val expected: String
) {

    companion object {
        @JvmStatic
        @Parameterized.Parameters
        fun loanData() = listOf(
            arrayOf(10000, 5.35, 75, "3363,10"),
            arrayOf(100000, 9.14, 120, "25477,85"),
            arrayOf(15500, 8.11, 15, "15604,75")
        )
    }

    @Test
    fun `WHEN computation payment EXPECT get correct result`() {
        val createLoanUseCase: CreateLoanUseCase = mock()
        val getConditionsUseCase: GetConditionsUseCase = mock()

        val createLoanViewModel =
            CreateLoanViewModel(createLoanUseCase, getConditionsUseCase)

        val actual =
            createLoanViewModel.onGetPayment(amount, percent, period)

        assertEquals(expected, actual)

    }

}