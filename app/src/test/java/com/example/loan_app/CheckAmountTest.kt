package com.example.loan_app

import com.example.loan_app.domain.usecase.CreateLoanUseCase
import com.example.loan_app.domain.usecase.GetConditionsUseCase
import com.example.loan_app.presentation.createLoan.CreateLoanViewModel
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Parameterized
import org.mockito.kotlin.mock

@RunWith(Parameterized::class)
class CheckAmountTest(
    private val maxAmount: String,
    private val amount: String,
    private val expected: Boolean
) {

    companion object {
        @JvmStatic
        @Parameterized.Parameters
        fun loanData() = listOf(
            arrayOf("10000", "9000", false),
            arrayOf("100000", "100001", true),
            arrayOf("10000", "0", false)
        )
    }

    @Test
    fun `WHEN check amount EXPECT correct result`() {
        val createLoanUseCase: CreateLoanUseCase = mock()
        val getConditionsUseCase: GetConditionsUseCase = mock()

        val createLoanViewModel =
            CreateLoanViewModel(createLoanUseCase, getConditionsUseCase)

        val actual =
            createLoanViewModel.checkAmount(maxAmount, amount)

        Assert.assertEquals(expected, actual)

    }

}