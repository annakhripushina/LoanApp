package com.example.loan_app.presentation.auth

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.loan_app.data.model.AuthRequest
import com.example.loan_app.data.model.RegistrationResponse
import com.example.loan_app.data.repository.RepositoryError
import com.example.loan_app.domain.usecase.CheckLoginUseCase
import com.example.loan_app.domain.usecase.LoginUserUseCase
import com.example.loan_app.domain.usecase.RegistrationUserUseCase
import com.example.loan_app.extensions.SingleLiveEvent
import kotlinx.coroutines.launch
import javax.inject.Inject

class AuthViewModel @Inject constructor(
    private val loginUserUseCase: LoginUserUseCase,
    private val registrationUserUseCase: RegistrationUserUseCase,
    private val checkLoginUseCase: CheckLoginUseCase
) : AndroidViewModel(Application()) {

    private var _token: MutableLiveData<String> = SingleLiveEvent()
    private var _error: MutableLiveData<RepositoryError> = SingleLiveEvent()
    val token: LiveData<String> = _token
    val error: LiveData<RepositoryError> = _error
    private var _regResponse: MutableLiveData<RegistrationResponse> = SingleLiveEvent()
    var regResponse: LiveData<RegistrationResponse> = _regResponse

    fun loginUser(name: String, password: String) {
        viewModelScope.launch {
            val result = loginUserUseCase.loginUser(AuthRequest(name, password))
            if (result.entity != null) {
                _token.value = result.entity
            }
            if (result.error != null) {
                _error.value = result.error
            }
        }
    }

    fun getToken(): String? = checkLoginUseCase.getToken()

    fun registrationUser(name: String, password: String) {
        viewModelScope.launch {
            val result = registrationUserUseCase.registrationUser(AuthRequest(name, password))
            if (result.entity != null) {
                _regResponse.value = result.entity
            }
            if (result.error != null) {
                _error.value = result.error
            }
        }
    }

    fun checkLoginData(login: String, password: String): AuthInputError? =
        when {
            login.isEmpty() -> {
                AuthInputError.ERRORLOGIN
            }
            password.isEmpty() -> AuthInputError.ERRORPASSWORD
            else -> null
        }

    fun checkRegData(login: String, password: String): AuthInputError? =
        when {
            login.isEmpty() -> {
                AuthInputError.ERRORLOGIN
            }
            password.isEmpty() -> {
                AuthInputError.ERRORPASSWORD
            }
            password.length < 8 -> {
                AuthInputError.ERRORLENGTH
            }
            !password.matches(".*[A-Z].*".toRegex()) -> {
                AuthInputError.ERRORUPPER
            }
            !password.matches(".*[a-z].*".toRegex()) -> {
                AuthInputError.ERRORLOWER
            }
            !password.matches(".*[@#\$%^+=-].*".toRegex()) -> {
                AuthInputError.ERRORSYMBOL
            }
            else -> null
        }


}