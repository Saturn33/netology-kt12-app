package ru.netology.saturn33.homework.hw11

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat

object NotificationHelper {
    private val COMEBACK_CHANNEL_ID = "comeback_chanel_id"
    private var channelCreated = false
    private fun createNotificationChannel(context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "Comeback message"
            val descriptionText = "Notifies when user first time exits application"
            val importance = NotificationManager.IMPORTANCE_HIGH
            val channel = NotificationChannel(COMEBACK_CHANNEL_ID, name, importance).apply {
                description = descriptionText
            }
            val notificationManager: NotificationManager =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    private fun createNotificationChannelIfNotCreated(context: Context) {
        if (!channelCreated)
            createNotificationChannel(context)
        channelCreated = true
    }

    fun comeBackNotification(context: Context) {
        createNotificationChannelIfNotCreated(context)
        val builder = NotificationCompat.Builder(context, COMEBACK_CHANNEL_ID)
            .setSmallIcon(R.mipmap.ic_launcher_round).setContentTitle("Come back...")
            .setContentText("Возвращайтесь к нам скорее, у нас очень много нового и интересного...")
            .setStyle(NotificationCompat.BigTextStyle())
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            builder.priority = NotificationManager.IMPORTANCE_HIGH
        }
        NotificationManagerCompat.from(context).notify(0, builder.build())
    }
}
