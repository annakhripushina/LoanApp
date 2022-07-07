package com.example.loan_app.presentation.history

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.loan_app.data.model.LoanResponse
import com.example.loan_app.data.repository.RepositoryError
import com.example.loan_app.domain.usecase.GetLoanListUseCase
import com.example.loan_app.domain.usecase.LogOutUseCase
import com.example.loan_app.extensions.SingleLiveEvent
import kotlinx.coroutines.launch
import javax.inject.Inject

class HistoryViewModel @Inject constructor(
    private val getLoanListUseCase: GetLoanListUseCase,
    private val logOutUseCase: LogOutUseCase
) : AndroidViewModel(Application()) {

    private var _loanList: MutableLiveData<List<LoanResponse>> = SingleLiveEvent()
    val loanList: LiveData<List<LoanResponse>> = _loanList

    private var _error: MutableLiveData<RepositoryError> = SingleLiveEvent()
    val error: LiveData<RepositoryError> = _error

    fun logOut() {
        logOutUseCase.logOutUser()
    }

    fun getLoanList() {
        viewModelScope.launch {
            val result = getLoanListUseCase.getLoanList()
            if (!result.entity.isNullOrEmpty()) {
                _loanList.value = result.entity
            }
            if (result.error != null) {
                _error.value = result.error
            }
        }
    }


}