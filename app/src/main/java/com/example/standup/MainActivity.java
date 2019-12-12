package com.example.standup;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.SystemClock;
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
    //private static final long NOTIFICATION_INTERVAL_MS=AlarmManager.INTERVAL_FIFTEEN_MINUTES;
    private static final long NOTIFICATION_INTERVAL_MS=15000L;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toggleButton = findViewById(R.id.alarmToggle);

        final AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

        Intent notifyIntent = new Intent(this, AlarmReceiver.class);
        final PendingIntent notifyPendingIntent = PendingIntent.getBroadcast
                (this, NOTIFICATION_ID, notifyIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        boolean alarmUp = (PendingIntent.getBroadcast(this, NOTIFICATION_ID, notifyIntent,
                PendingIntent.FLAG_NO_CREATE) != null);
        toggleButton.setChecked(alarmUp);


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
                if (false){
                    alarmManager.setRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP,
                            SystemClock.elapsedRealtime(),
                            NOTIFICATION_INTERVAL_MS,
                            notifyPendingIntent);
                }

                alarmManager.setAlarmClock(
                        new AlarmManager.AlarmClockInfo(System.currentTimeMillis()+NOTIFICATION_INTERVAL_MS, notifyPendingIntent)
                        , notifyPendingIntent);

                //alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP,System.currentTimeMillis(),notifyPendingIntent);

                //alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP,System.currentTimeMillis(),notifyPendingIntent);

            } else {
                //Set the toast message for the "off" case.
                toastMessage = "Stand Up Alarm Off!";
                mNotificationManager.cancelAll();
                if (alarmManager != null) {
                    alarmManager.cancel(notifyPendingIntent);
                }
            }
            //Show a toast to say the alarm is turned on or off.
            Toast.makeText(MainActivity.this, toastMessage,Toast.LENGTH_SHORT)
                    .show();
        });

        createNotificationChannel();
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
