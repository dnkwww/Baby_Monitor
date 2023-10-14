package com.example.fcmpython

import android.util.Log
import com.google.firebase.iid.FirebaseInstanceId
import com.google.firebase.messaging.EnhancedIntentService
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class MyFirebaseInstanceIDService : FirebaseMessagingService() {
companion object{
    const val TAG = "PUSH"
}
    override fun onNewToken(refreshedToken: String) {
        super.onNewToken(refreshedToken)
        // Get updated InstanceID token.
        val token: String? = FirebaseInstanceId.getInstance().getToken()
        Log.d(TAG, "Refreshed token: $token")
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {

        // TODO(developer): Handle FCM messages here.
        // Not getting messages here? See why this may be: https://goo.gl/39bRNJ
        Log.d(TAG, "From: " + remoteMessage.from)

        // Check if message contains a data payload.
        if (remoteMessage.data.size > 0) {
            Log.d(TAG, "Message data payload: " + remoteMessage.data)
            if ( /* Check if data needs to be processed by long running job */true) {
                // For long-running tasks (10 seconds or more) use Firebase Job Dispatcher.
            // scheduleJob()
            } else {
                // Handle message within 10 seconds
            }
        }

        // Check if message contains a notification payload.
        if (remoteMessage.notification != null) {
            //print push message which we will send from our sever
            Log.d(TAG,"Message Notification Body: " + remoteMessage.notification!!
                    .body
            )
        }

    }
}