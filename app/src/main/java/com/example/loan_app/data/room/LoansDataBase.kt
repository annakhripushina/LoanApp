package com.example.loan_app.data.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.loan_app.data.model.LoanResponse

@Database(entities = [LoanResponse::class], version = 1)
abstract class LoansDataBase : RoomDatabase() {

    abstract val loansDao: LoansDao

    companion object {
        private var instance: LoansDataBase? = null

        fun getDatabase(context: Context): LoansDataBase {
            if (instance == null) {
                instance = Room.databaseBuilder(
                    context.applicationContext,
                    LoansDataBase::class.java,
                    "loans-db"
                )
                    .allowMainThreadQueries()
                    .build()
            }
            return instance as LoansDataBase
        }
    }
}