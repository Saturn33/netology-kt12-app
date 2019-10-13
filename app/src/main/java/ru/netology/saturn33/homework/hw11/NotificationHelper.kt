package ru.netology.saturn33.homework.hw11

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import ru.netology.saturn33.homework.hw11.ui.FeedActivity

object NotificationHelper {
    private val COMEBACK_NOTIFY_ID = 0
    private val RETURN_NOTIFY_ID = 1
    private val COMEBACK_CHANNEL_ID = "comeback_chanel_id"
    private var channelCreated = false
    private fun createNotificationChannel(context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "Сообщение о возвращении"
            val descriptionText =
                "Уведомления о возвращении в приложение после первого выхода и периодические уведомления о том, что появился новый контент"
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
            .setContentText("Возвращайтесь к нам скорее, у нас скоро будет очень много нового и интересного...")
            .setStyle(NotificationCompat.BigTextStyle())
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            builder.priority = NotificationManager.IMPORTANCE_HIGH
        }
        NotificationManagerCompat.from(context).notify(COMEBACK_NOTIFY_ID, builder.build())
    }

    fun returnToAppNotification(context: Context) {
        val intent = Intent(context, FeedActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(
            context,
            COMEBACK_NOTIFY_ID, intent,
            COMEBACK_NOTIFY_ID
        )

        createNotificationChannelIfNotCreated(context)
        val builder = NotificationCompat.Builder(context, COMEBACK_CHANNEL_ID)
            .setSmallIcon(R.mipmap.ic_launcher_round).setContentTitle("Новый контент!")
            .setContentText("Проверь, что нового появилось, пока тебя не было!")
            .setStyle(NotificationCompat.BigTextStyle())
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            builder.priority = NotificationManager.IMPORTANCE_HIGH
        }
        NotificationManagerCompat.from(context).notify(RETURN_NOTIFY_ID, builder.build())
    }
}
