package com.example.capstone;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;

import com.google.firebase.messaging.RemoteMessage;

import java.util.Map;

import kr.co.ilg.activity.login.SplashActivity;

public class MyFirebaseMessagingService extends com.google.firebase.messaging.FirebaseMessagingService {
    private static final String TAG = "FirebaseMsgService"; // [START receive_message]
    String title, message;
    int k=0;
     @RequiresApi(api = Build.VERSION_CODES.O)
     @Override public void onMessageReceived(RemoteMessage remoteMessage) {
         //추가한것
         String message = "";
         String title = "";

         // Notifition 항목이 있을때.
         if (remoteMessage.getNotification() != null) {
             message = remoteMessage.getNotification().getBody();
             title = remoteMessage.getNotification().getTitle();
         }

         // Data 항목이 있을때.
         Map<String, String> data = remoteMessage.getData();
         String messageData = data.get("body");
         String titleData = data.get("title");

         sendNotification(titleData,messageData);
     }
     @RequiresApi(api = Build.VERSION_CODES.O)
     private void sendNotification(String title, String message) {
         Intent intent;
         PendingIntent pendingIntent;

         intent = new Intent(this, SplashActivity.class);

         intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
         pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent, PendingIntent.FLAG_ONE_SHOT);


         Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

         NotificationCompat.Builder notificationBuilder;
         NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

         //SDK26부터 푸쉬에 채널항목에 대한 세팅이 필요하다.
         if (Build.VERSION.SDK_INT >= 26) {

             String channelId = "test push";
             String channelName = "test Push Message";
             String channelDescription = "New test Information";
             NotificationChannel channel = new NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_DEFAULT);
             channel.setDescription(channelDescription);
             //각종 채널에 대한 설정
             channel.enableLights(true);
             channel.setLightColor(Color.RED);
             channel.enableVibration(true);
             channel.setVibrationPattern(new long[]{100, 200, 300});
             notificationManager.createNotificationChannel(channel);
             //channel이 등록된 builder
             notificationBuilder = new NotificationCompat.Builder(this, channelId);
         } else {
             notificationBuilder = new NotificationCompat.Builder(this);
         }

         notificationBuilder.setSmallIcon(R.drawable.app_icon)
                 .setContentTitle(title)
                 .setStyle(new NotificationCompat.BigTextStyle().bigText(message))
                 .setAutoCancel(true)
                 .setSound(defaultSoundUri)
                 .setContentIntent(pendingIntent)
                 .setContentText(message);

         notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());
     }
}

