package com.example.loan_app.di

import com.example.loan_app.di.module.DataModule
import com.example.loan_app.di.module.DomainModule
import com.example.loan_app.ui.MainActivity
import com.example.loan_app.ui.auth.AuthFragment
import com.example.loan_app.ui.createLoan.CreateLoanFragment
import com.example.loan_app.ui.detailLoan.DetailLoanFragment
import com.example.loan_app.ui.history.HistoryFragment
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [DataModule::class, DomainModule::class])
interface AppComponent {
    fun inject(authFragment: AuthFragment)
    fun inject(historyFragment: HistoryFragment)
    fun inject(createLoanFragment: CreateLoanFragment)
    fun inject(detailLoanFragment: DetailLoanFragment)
    fun inject(mainActivity: MainActivity)

}