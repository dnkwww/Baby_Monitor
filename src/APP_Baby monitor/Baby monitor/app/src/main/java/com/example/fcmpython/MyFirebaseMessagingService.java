package com.example.fcmpython;

import android.app.Activity;
import android.app.Service;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        // Get notification data
        String message = remoteMessage.getNotification().getBody();
        //紀錄收到通知的時間
        long timestamp = System.currentTimeMillis();

        /*接收python發送過來的危險狀況*/
        Class<? extends Activity> activityToOpen = MainActivity.class; // 預設為沒有事件發生的主頁面

        // 將通知添加到本地數據庫中
        SharedPreferences sharedPreferences = getSharedPreferences("NotificationHistory", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(String.valueOf(timestamp), message);
        editor.apply();


        if (message.contains("Your child is in the state of lying on belly!")) {  //趴睡
            activityToOpen = MainActivity5.class;
        }
        else if(message.contains("Your child is not covered with a quilt!")) {
            activityToOpen = MainActivity6.class;  //沒蓋被子
        }
        else if (message.contains("Your child face is being covered!")) {
            activityToOpen = MainActivity4.class;  //異物覆蓋
        }
        else if(message.contains("Your child is spitting up milk!")) {
            activityToOpen = MainActivity3.class;  //吐奶
        }
        else if(message.contains("Your child is in a high-decibel environment!")) {
            activityToOpen = MainActivity7.class;  //高分貝
        }

        Intent intent = new Intent(this, activityToOpen);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);

    }
}