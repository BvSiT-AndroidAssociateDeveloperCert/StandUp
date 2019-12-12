package com.example.standup;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.Toast;
import android.widget.ToggleButton;

public class MainActivity extends AppCompatActivity {

    private ToggleButton toggleButton;
    private NotificationManager mNotificationManager;
    private static final String PRIMARY_CHANNEL_ID = "primary_notification_channel";
    private static final int NOTIFICATION_ID = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toggleButton = findViewById(R.id.alarmToggle);

        /* Common method to create a listener
        toggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                String toastMessage;
                if(isChecked){
                    //Set the toast message for the "on" case.
                    toastMessage = "Stand Up Alarm On!";
                    deliverNotification(getApplicationContext());
                } else {
                    //Set the toast message for the "off" case.
                    toastMessage = "Stand Up Alarm Off!";
                }

                //Show a toast to say the alarm is turned on or off.
                Toast.makeText(MainActivity.this, toastMessage,Toast.LENGTH_SHORT)
                        .show();
            }
        });
        */

        //Or with lambda expression. See also https://stackoverflow.com/questions/52859802/setting-oncheckedchangelistener-with-a-lambda
        //toggleButton.setOnCheckedChangeListener((view,b)->setAlarm(b));

        //Or lambda without separate method:
        toggleButton.setOnCheckedChangeListener((view,isChecked)-> {
            String toastMessage;
            if(isChecked){
                //Set the toast message for the "on" case.
                toastMessage = "Stand Up Alarm On!";
                deliverNotification(this);
            } else {
                //Set the toast message for the "off" case.
                toastMessage = "Stand Up Alarm Off!";
            }
            //Show a toast to say the alarm is turned on or off.
            Toast.makeText(MainActivity.this, toastMessage,Toast.LENGTH_SHORT)
                    .show();
        });

        createNotificationChannel();
    }

    private void setAlarm(boolean isChecked){
        String toastMessage;
        if(isChecked){
            //Set the toast message for the "on" case.
            toastMessage = "Stand Up Alarm On!";
        } else {
            //Set the toast message for the "off" case.
            toastMessage = "Stand Up Alarm Off!";
        }
        //Show a toast to say the alarm is turned on or off.
        Toast.makeText(MainActivity.this, toastMessage,Toast.LENGTH_SHORT)
                .show();
    }

    private void deliverNotification(Context context) {
        Intent contentIntent = new Intent(this,MainActivity.class);
        PendingIntent contentPendingIntent = PendingIntent.getActivity(context,NOTIFICATION_ID,contentIntent,PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, PRIMARY_CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_standup)
                .setContentTitle(getString(R.string.notification_content_title))
                .setContentText(getString(R.string.notification_content_text))
                .setContentIntent(contentPendingIntent)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setAutoCancel(true)
                .setDefaults(NotificationCompat.DEFAULT_ALL);
        mNotificationManager.notify(NOTIFICATION_ID,builder.build());
    }

    private void createNotificationChannel() {
        // Create a notification manager object.
        mNotificationManager =
                (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        // Notification channels are only available in OREO and higher.
        // So, add a check on SDK version.
        if (android.os.Build.VERSION.SDK_INT >=
                android.os.Build.VERSION_CODES.O) {

            // Create the NotificationChannel with all the parameters.
            NotificationChannel notificationChannel = new NotificationChannel
                    (PRIMARY_CHANNEL_ID,
                            "Stand up notification",
                            NotificationManager.IMPORTANCE_HIGH);

            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.RED);
            notificationChannel.enableVibration(true);
            notificationChannel.setDescription
                    ("Notifies every 15 minutes to stand up and walk");
            mNotificationManager.createNotificationChannel(notificationChannel);
        }
    }
}
