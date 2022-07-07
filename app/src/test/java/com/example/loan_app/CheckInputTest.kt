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
class CheckInputTest(
    private val lastName: String?,
    private val firstName: String?,
    private val phone: String?,
    private val amount: String?,
    private val expected: Boolean
) {

    companion object {
        @JvmStatic
        @Parameterized.Parameters
        fun loanData() = listOf(
            arrayOf("", "", "", "", true),
            arrayOf(null, null, null, null, true),
            arrayOf(null, null, null, "10000", true),
            arrayOf("LastName", "FirstName", "911", "10000", false)
        )
    }

    @Test
    fun `WHEN check input EXPECT get correct result`() {
        val createLoanUseCase: CreateLoanUseCase = mock()
        val getConditionsUseCase: GetConditionsUseCase = mock()

        val createLoanViewModel =
            CreateLoanViewModel(createLoanUseCase, getConditionsUseCase)

        val actual =
            createLoanViewModel.checkInput(lastName, firstName, phone, amount)

        Assert.assertEquals(expected, actual)

    }

}