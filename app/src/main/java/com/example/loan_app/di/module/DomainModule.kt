package com.example.loan_app.di.module

import com.example.loan_app.data.repository.AuthRepositoryImpl
import com.example.loan_app.data.repository.ConditionRepositoryImpl
import com.example.loan_app.data.repository.LoanRepositoryImpl
import com.example.loan_app.data.repository.TokenRepositoryImpl
import com.example.loan_app.domain.repository.AuthRepository
import com.example.loan_app.domain.repository.ConditionRepository
import com.example.loan_app.domain.repository.LoanRepository
import com.example.loan_app.domain.repository.TokenRepository
import com.example.loan_app.domain.usecase.*
import dagger.Binds
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


@Module
abstract class DomainModule {

    companion object {
        @Singleton
        @Provides
        fun provideLoginUserUseCase(
            authRepository: AuthRepository
        ): LoginUserUseCase =
            LoginUserUseCase(authRepository)

        @Singleton
        @Provides
        fun provideRegistrationUserUseCase(authRepository: AuthRepository): RegistrationUserUseCase =
            RegistrationUserUseCase(authRepository)

        @Singleton
        @Provides
        fun provideLogOutUseCase(authRepository: AuthRepository): LogOutUseCase =
            LogOutUseCase(authRepository)

        @Singleton
        @Provides
        fun provideCheckLoginUseCase(tokenRepository: TokenRepository): CheckLoginUseCase =
            CheckLoginUseCase(tokenRepository)

        @Singleton
        @Provides
        fun provideCreateLoanUseCase(loanRepository: LoanRepository): CreateLoanUseCase =
            CreateLoanUseCase(loanRepository)

        @Singleton
        @Provides
        fun provideGetConditionsUseCase(
            conditionRepository: ConditionRepository
        ): GetConditionsUseCase =
            GetConditionsUseCase(conditionRepository)

        @Singleton
        @Provides
        fun provideGetDetailLoanUseCase(
            loanRepository: LoanRepository
        ): GetDetailLoanUseCase =
            GetDetailLoanUseCase(loanRepository)

        @Singleton
        @Provides
        fun provideGetLoanListUseCase(
            loanRepository: LoanRepository
        ): GetLoanListUseCase =
            GetLoanListUseCase(loanRepository)

    }

    @Singleton
    @Binds
    abstract fun bindLoanRepository(impl: LoanRepositoryImpl): LoanRepository

    @Singleton
    @Binds
    abstract fun bindConditionRepository(impl: ConditionRepositoryImpl): ConditionRepository

    @Singleton
    @Binds
    abstract fun bindAuthRepository(impl: AuthRepositoryImpl): AuthRepository

    @Singleton
    @Binds
    abstract fun bindTokenRepository(impl: TokenRepositoryImpl): TokenRepository

}