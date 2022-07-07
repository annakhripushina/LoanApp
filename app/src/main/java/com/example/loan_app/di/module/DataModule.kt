package com.example.loan_app.di.module

import android.app.Application
import android.content.Context
import com.example.loan_app.data.network.ApiService
import com.example.loan_app.data.room.LoansDao
import com.example.loan_app.data.room.LoansDataBase
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import javax.inject.Singleton


@Module
class DataModule(val application: Application) {
    companion object {
        var BASE_URL = "https://shiftlab.cft.ru:7777/"
    }

    @Singleton
    @Provides
    fun provideRetrofitInterface(): Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(ScalarsConverterFactory.create())
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    @Singleton
    @Provides
    fun provideApiService(retrofit: Retrofit): ApiService =
        retrofit.create(ApiService::class.java)


    @Singleton
    @Provides
    fun getLoansDAO(loansDataBase: LoansDataBase): LoansDao =
        loansDataBase.loansDao

    @Singleton
    @Provides
    fun getLoansDataBaseInstance(): LoansDataBase =
        LoansDataBase.getDatabase(provideAppContext())


    @Singleton
    @Provides
    fun provideAppContext(): Context =
        application.applicationContext


}