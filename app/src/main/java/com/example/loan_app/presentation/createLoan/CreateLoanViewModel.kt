package com.example.loan_app.presentation.createLoan

import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.loan_app.App
import com.example.loan_app.data.model.LoanConditionsResponse
import com.example.loan_app.data.model.LoanRequest
import com.example.loan_app.data.model.LoanResponse
import com.example.loan_app.data.repository.RepositoryError
import com.example.loan_app.domain.usecase.CreateLoanUseCase
import com.example.loan_app.domain.usecase.GetConditionsUseCase
import com.example.loan_app.extensions.SingleLiveEvent
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.math.ceil
import kotlin.math.pow

class CreateLoanViewModel @Inject constructor(
    private val createLoanUseCase: CreateLoanUseCase,
    private val getConditionsUseCase: GetConditionsUseCase,
) : AndroidViewModel(App()) {

    private var _conditions: MutableLiveData<LoanConditionsResponse> = SingleLiveEvent()
    val conditions: LiveData<LoanConditionsResponse> = _conditions
    private var _loan: MutableLiveData<LoanResponse> = SingleLiveEvent()
    val loan: LiveData<LoanResponse> = _loan

    private var _error: MutableLiveData<RepositoryError> = SingleLiveEvent()
    val error: LiveData<RepositoryError> = _error

    fun getLoanConditions() {
        viewModelScope.launch {
            val result = getConditionsUseCase.getLoanConditions()
            if (result.entity != null) {
                _conditions.value = result.entity
            }
            if (result.error != null) {
                _error.value = result.error
            }
        }
    }

    fun createLoan(
        amount: Double,
        firstName: String,
        lastName: String,
        phone: String
    ) {
        viewModelScope.launch {
            val result = _conditions.value?.let {
                LoanRequest(
                    amount,
                    firstName,
                    lastName,
                    it.percent,
                    it.period,
                    phone
                )
            }
                ?.let { createLoanUseCase.createLoan(it) }

            if (result != null) {
                if (result.entity != null) {
                    _loan.value = result.entity
                }
                if (result.error != null) {
                    _error.value = result.error
                }
            }
        }
    }

    fun onGetPayment(amount: Double, percent: Double, period: Int): String {
        val monthPercent = percent / (12 * 100)
        val numberPayments = ceil(period.toDouble() / 30)
        val ratio =
            (monthPercent * ((1 + monthPercent).pow(numberPayments)) / ((1 + monthPercent).pow(
                numberPayments
            ) - 1))
        return String.format("%.2f", (amount * ratio))
    }

    fun checkAmount(maxAmount: String, amount: String): Boolean =
        amount.toDouble() > maxAmount.toInt()

    fun checkInput(
        lastName: String?,
        firstName: String?,
        phone: String?,
        amount: String?,
    ): Boolean =
        lastName.isNullOrEmpty() || firstName.isNullOrEmpty() || phone.isNullOrEmpty() || amount.isNullOrEmpty()

}