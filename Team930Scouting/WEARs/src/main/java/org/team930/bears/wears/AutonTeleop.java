package org.team930.bears.wears;

import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.os.Handler;

import android.widget.Button;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;

@SuppressWarnings("ALL")
public class AutonTeleop extends AppCompatActivity {
    Button goToPostMatch;

    String matchDataPreferences, otherPreferences;

    boolean doubleBackToExitPressedOnce = false;

    SharedPreferences matchData, otherSettings;
    long prevTime = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_autonteleop);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        matchDataPreferences = getString(R.string.matchDataPreferences);
        otherPreferences = getString(R.string.otherPreferences);

        matchData = getSharedPreferences(matchDataPreferences, 0);
        otherSettings = getSharedPreferences(otherPreferences, 0);

    }


    @Override
    public void onBackPressed() {

        long thisTime = Calendar.getInstance().getTimeInMillis();
        if ((thisTime - prevTime) <= 1000) {//1 SEC
            Toast.makeText(this, "DOUBLE TAP DETECTED!!!", Toast.LENGTH_LONG).show();
            super.onBackPressed();
        } else {
            //first tap
            prevTime = thisTime;
        }

    }


}

