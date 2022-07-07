package com.example.loan_app.service

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.loan_app.R
import com.example.loan_app.ui.MainActivity

class PushReceiver : BroadcastReceiver() {
    companion object {
        const val CHANNEL_ID = "CHANNEL_LOAN"
        const val CHANNEL_NAME = "LOAN_PAY"
        const val CHANNEL_DESCRIPTION = "Push loan pay"
        const val BUNDLE_NAME = "BUNDLE_NAME"
        const val LOAN_ID = "LOAN_ID"
        const val NOTIFICATION_PUSH = "NOTIFICATION_PUSH"
    }

    @SuppressLint("UnspecifiedImmutableFlag")
    override fun onReceive(context: Context, intent: Intent) {
        val bundle = intent.getBundleExtra(BUNDLE_NAME)
        val loanId = bundle?.getLong(LOAN_ID)
        val intent = Intent(context, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        intent.putExtra(NOTIFICATION_PUSH, loanId)
        val pendingIntent =
            PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)

        val importance = NotificationManager.IMPORTANCE_DEFAULT
        val channel = NotificationChannel(CHANNEL_ID, CHANNEL_NAME, importance)
        channel.description = CHANNEL_DESCRIPTION
        context.getSystemService(NotificationManager::class.java)
            .createNotificationChannel(channel)

        val builder = NotificationCompat.Builder(context, CHANNEL_ID)
            .setContentTitle(context.getString(R.string.pushTitle))
            .setContentText(context.getString(R.string.pushText))
            .setSmallIcon(R.drawable.ic_baseline_card_travel_green)
            .setPriority(NotificationCompat.PRIORITY_LOW)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)

        if (loanId != null) {
            NotificationManagerCompat.from(context).notify(loanId.toInt(), builder.build())
        }
    }

}