package com.pyassasins.alarmic;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.gcm.GoogleCloudMessaging;

import org.json.JSONException;
import org.json.JSONObject;


public class GCMMessageHandler extends IntentService {

    static final String MSG_TT = "";
    static final String MSG_UT = "";
    static String[] days = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday"};
    String[] times;
    int notifyID = 1154;

    String mes;

    public GCMMessageHandler() {
        super("GCMMessageHandler");
    }

    @Override
    public void onCreate() {
        // TODO Auto-generated method stub
        super.onCreate();

    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Bundle extras = intent.getExtras();
        GoogleCloudMessaging gcm = GoogleCloudMessaging.getInstance(this);
        times = new String[9];

        String messageType = gcm.getMessageType(intent);

        if (!extras.isEmpty()) {
            if (GoogleCloudMessaging.MESSAGE_TYPE_SEND_ERROR
                    .equals(messageType)) {
                sendNotification(0, "Send error: " + extras.toString());
            } else if (GoogleCloudMessaging.MESSAGE_TYPE_DELETED
                    .equals(messageType)) {
                sendNotification(0, "Deleted messages on server: "
                        + extras.toString());
            } else if (GoogleCloudMessaging.MESSAGE_TYPE_MESSAGE
                    .equals(messageType)) {

                if (extras.get(MSG_TT) != null) {
                    try {
                        JSONObject js = new JSONObject((String) extras.get(MSG_TT));

                        sendNotification(0, "Timetable Updated");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                if (extras.get(MSG_UT) != null) {
                    try {
                        JSONObject js = new JSONObject((String) extras.get(MSG_UT));

                        sendNotification(1, "Upcoming Timetable Updated");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        GCMReceiver.completeWakefulIntent(intent);
    }
    private void sendNotification(int type,String msg) {
        switch (type){
            case 0:


                NotificationCompat.Builder mNotifyBuilder;
                NotificationManager mNotificationManager;

                mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

                mNotifyBuilder = new NotificationCompat.Builder(this)
                        .setContentTitle("Alert")
                        .setContentText("You've received new message.")
                        .setSmallIcon(R.drawable.common_signin_btn_icon_dark);
                // Set pending intent

                // Set Vibrate, Sound and Light
                int defaults = 0;
                defaults = defaults | Notification.DEFAULT_LIGHTS;
                defaults = defaults | Notification.DEFAULT_VIBRATE;
                defaults = defaults | Notification.DEFAULT_SOUND;

                mNotifyBuilder.setDefaults(defaults);
                // Set the content for Notification
                mNotifyBuilder.setContentText("New message from Server");
                // Set autocancel
                mNotifyBuilder.setAutoCancel(true);
                // Post a notification
                mNotificationManager.notify(notifyID, mNotifyBuilder.build());
                break;
            case 1:


                // Set Vibrate, Sound and Light
                defaults = 0;
                defaults = defaults | Notification.DEFAULT_LIGHTS;
                defaults = defaults | Notification.DEFAULT_VIBRATE;
                defaults = defaults | Notification.DEFAULT_SOUND;


                break;

        }

    }
}