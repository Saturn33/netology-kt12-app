package ru.netology.saturn33.homework.hw12.service

import com.auth0.android.jwt.JWT
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.netology.saturn33.homework.hw12.NotificationHelper
import ru.netology.saturn33.homework.hw12.Utils
import ru.netology.saturn33.homework.hw12.repositories.Repository

class FCMService : FirebaseMessagingService() {
    override fun onMessageReceived(message: RemoteMessage) {
        println(message)
        val recipientId = message.data["recipientId"]?.toLong() ?: 0
        val title = message.data["title"] ?: ""
        val text = message.data["text"] ?: ""

        println(recipientId)
        println(title)

        val token = Utils.getToken(baseContext) ?: return

        val jwt = JWT(token)
        if (jwt.getClaim("id").asLong() == recipientId) {
            NotificationHelper.simpleNotification(baseContext, title, text)
        } else {
            kotlinx.coroutines.CoroutineScope(Dispatchers.Main).launch {
                Repository.unregisterPushToken(recipientId)
            }
        }
    }

    override fun onNewToken(token: String) {
        println(token)
    }
}
