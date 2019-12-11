package com.example.standup;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.Toast;
import android.widget.ToggleButton;

public class MainActivity extends AppCompatActivity {

    private ToggleButton toggleButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toggleButton = findViewById(R.id.alarmToggle);

        toggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
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
        });

        //Or with lambda expression. See also https://stackoverflow.com/questions/52859802/setting-oncheckedchangelistener-with-a-lambda
        //toggleButton.setOnCheckedChangeListener((view,b)->setAlarm(b));

        //Or lambda without separate method:
        toggleButton.setOnCheckedChangeListener((view,isChecked)-> {
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
        });
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
}
