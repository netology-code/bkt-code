package ru.netology.kotlin.ncraftmedia.service

import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class FCMService : FirebaseMessagingService() {
    override fun onMessageReceived(message: RemoteMessage) {
        val recipientId = message.data["recipientId"]
        val title = message.data["title"]

        println(recipientId)
        println(title)
        // TODO: show notification
    }

    override fun onNewToken(token: String) {
        println(token)
    }
}