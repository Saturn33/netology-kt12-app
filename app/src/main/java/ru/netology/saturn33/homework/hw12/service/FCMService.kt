package ru.netology.saturn33.homework.hw12.service

import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import ru.netology.saturn33.homework.hw12.NotificationHelper

class FCMService : FirebaseMessagingService() {
    override fun onMessageReceived(message: RemoteMessage) {
        println(message)
        val recipientId = message.data["recipientId"]
        val title = message.data["title"] ?: ""
        val text = message.data["text"] ?: ""

        println(recipientId)
        println(title)
        NotificationHelper.simpleNotification(baseContext, title, text)
    }

    override fun onNewToken(token: String) {
        println(token)
    }
}
