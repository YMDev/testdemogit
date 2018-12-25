package mobile.a3tech.com.a3tech;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.RemoteInput;
import android.util.Log;


import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import org.codehaus.jackson.smile.Tool;

import java.util.Random;

import mobile.a3tech.com.a3tech.activity.A3techWelcomPageActivity;
import mobile.a3tech.com.a3tech.activity.MessageGcmActivity;


/**
 * Created by user on 2017/4/12.
 */

public class MyFirebaseMessagingService extends FirebaseMessagingService {
    private NotificationManager mNotificationManager;
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        Log.d("FCM", "onMessageReceived:"+remoteMessage.getFrom());
        if (remoteMessage.getData().size() > 0) {
            Log.d("FCM","Message data payload: " + remoteMessage.getData());
        }
        // Check if message contains a notification payload.
        if (remoteMessage.getNotification() != null) {
            String title = remoteMessage.getNotification().getTitle();
            String body = remoteMessage.getNotification().getBody();
            Log.d("FCM", "Message Notification Title: " + title);
            Log.d("FCM", "Message Notification Body: " + body);


            NotificationCompat.Builder mBuilder =
                    new NotificationCompat.Builder(this.getApplicationContext(), "notify_001");
            Intent ii = new Intent(this.getApplicationContext(), A3techWelcomPageActivity.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, ii, 0);

            NotificationCompat.BigTextStyle bigText = new NotificationCompat.BigTextStyle();
            bigText.bigText(body);
            bigText.setBigContentTitle(title);
            bigText.setSummaryText(body);

            mBuilder.setContentIntent(pendingIntent);
            mBuilder.setSmallIcon(R.mipmap.a3tech_logo_2);
            mBuilder.setContentTitle(title);
            mBuilder.setContentText(body);
            mBuilder.setPriority(Notification.PRIORITY_MAX);
            mBuilder.setStyle(bigText);
            mBuilder.setAutoCancel(true);

            mNotificationManager =
                    (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);


            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                String channelId = "YOUR_CHANNEL_ID";
                NotificationChannel channel = new NotificationChannel(channelId,
                        "Channel human readable title",
                        NotificationManager.IMPORTANCE_DEFAULT);
                mNotificationManager.createNotificationChannel(channel);
                mBuilder.setChannelId(channelId);
            }

            mNotificationManager.notify(0, mBuilder.build());
        }
    }
    private static final String KEY_TEXT_REPLY = "key_text_reply";
    @Override
    public void onNewToken(String s) {
        Log.e("NEW_TOKEN", s);
    }

    private static void sendNotification(Context context, String title, String message, String idMission,
                                         String idOffre, String  userId, String  isMessage) {
        int icon = R.drawable.ic_launcher;
        long when = System.currentTimeMillis();
        NotificationCompat.Builder nBuilder;
        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        nBuilder = new NotificationCompat.Builder(context)
                .setSmallIcon(icon)
                .setContentTitle(title)
                .setLights(Color.RED, 500, 500).setContentText(message)
                .setAutoCancel(true).setTicker(title)
                .setWhen(when)
                .setVibrate(new long[] { 100, 250, 100, 250, 100, 250 })
                .setSound(alarmSound);

        Intent resultIntent = new Intent(context, MessageGcmActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("idMissionNot", idMission);
        bundle.putString("idOffreNot", idOffre);
        bundle.putString("userIdNot", userId);
        bundle.putString("isMessageNot", isMessage);
        resultIntent.putExtras(bundle);

        int m = (new Random()).nextInt(9999 - 1000) + 1000;
        PendingIntent resultPendingIntent = PendingIntent.getActivity(context,m, resultIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        nBuilder.setContentIntent(resultPendingIntent);
        NotificationManager nNotifyMgr = (NotificationManager) context.getSystemService(context.NOTIFICATION_SERVICE);
        nNotifyMgr.notify(m + 1, nBuilder.build());
    }

}