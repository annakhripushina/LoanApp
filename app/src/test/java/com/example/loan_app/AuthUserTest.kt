package com.example.loan_app

import com.example.loan_app.data.model.AuthRequest
import com.example.loan_app.domain.usecase.CheckLoginUseCase
import com.example.loan_app.domain.usecase.LoginUserUseCase
import com.example.loan_app.domain.usecase.RegistrationUserUseCase
import com.example.loan_app.presentation.auth.AuthViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

class AuthUserTest {

    private lateinit var loginUserUseCase: LoginUserUseCase
    private lateinit var registrationUserUseCase: RegistrationUserUseCase
    private lateinit var checkLoginUseCase: CheckLoginUseCase
    private lateinit var authViewModel: AuthViewModel

    @Before
    fun setUp() {
        Dispatchers.setMain(UnconfinedTestDispatcher())
        loginUserUseCase = mock()
        registrationUserUseCase = mock()
        checkLoginUseCase = mock()
        authViewModel = AuthViewModel(loginUserUseCase, registrationUserUseCase, checkLoginUseCase)
    }

    @Test
    fun `WHEN get token viewModel EXPECT get token useCase`() {
        whenever(checkLoginUseCase.getToken()).thenReturn(
            "Bearer token"
        )

        val actual = authViewModel.getToken()
        val expected = "Bearer token"
        Assert.assertEquals(expected, actual)
    }

    @Test
    fun `WHEN registration user EXPECT registration user useCase`() {
        authViewModel.registrationUser("test", "123-456Aa")
        runTest {
            verify(registrationUserUseCase).registrationUser(AuthRequest("test", "123-456Aa"))
        }
    }

    @Test
    fun `WHEN login user EXPECT login user useCase`() {
        authViewModel.loginUser("test", "123-456Aa")
        runTest {
            verify(loginUserUseCase).loginUser(AuthRequest("test", "123-456Aa"))
        }
    }
}