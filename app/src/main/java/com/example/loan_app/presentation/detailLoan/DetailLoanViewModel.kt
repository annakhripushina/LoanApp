package com.example.loan_app.presentation.detailLoan

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.loan_app.data.model.LoanResponse
import com.example.loan_app.data.repository.RepositoryError
import com.example.loan_app.domain.usecase.GetDetailLoanUseCase
import com.example.loan_app.extensions.SingleLiveEvent
import kotlinx.coroutines.launch
import javax.inject.Inject

class DetailLoanViewModel @Inject constructor(
    private val detailLoanUseCase: GetDetailLoanUseCase
) : AndroidViewModel(Application()) {

    private var _loan: MutableLiveData<LoanResponse> = SingleLiveEvent()
    private var _error: MutableLiveData<RepositoryError> = SingleLiveEvent()
    val loan: LiveData<LoanResponse> = _loan
    val error: LiveData<RepositoryError> = _error

    fun getLoan(id: Long) {
        viewModelScope.launch {
            val result = detailLoanUseCase.getLoan(id)
            if (result.entity != null) {
                _loan.value = result.entity
            }
            if (result.error != null) {
                _error.value = result.error
            }
        }
    }
}