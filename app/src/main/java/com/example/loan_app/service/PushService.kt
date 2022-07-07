package com.example.loan_app.service

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent

import android.os.Bundle
import com.example.loan_app.service.PushReceiver.Companion.BUNDLE_NAME
import com.example.loan_app.service.PushReceiver.Companion.LOAN_ID


class PushService(private val context: Context) {
    private val alarmManager by lazy { context.getSystemService(Context.ALARM_SERVICE) as AlarmManager }

    fun setExactAlarm(loanId: Long, time: Long, requestCode: Int) {
        setAlarm(time, getPendingIntent(getIntent().apply {
            val bundle = Bundle()
            bundle.putLong(LOAN_ID, loanId)
            putExtra(BUNDLE_NAME, bundle)
        }, requestCode))
    }

    private fun setAlarm(time: Long, pendingIntent: PendingIntent) {
        alarmManager.let {
            alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, time, pendingIntent)
        }
    }

    private fun getIntent(): Intent = Intent(context, PushReceiver::class.java)

    @SuppressLint("UnspecifiedImmutableFlag")
    private fun getPendingIntent(intent: Intent, requestCode: Int) =
        PendingIntent.getBroadcast(
            context,
            requestCode,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )
}