package org.team930.bears.wears;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.Calendar;



public class PreMatch extends AppCompatActivity {

    TextboxView teamNum, matchNum;
    SpinnerView startPos;

    ImageView map;
    String matchDataPreferences, otherPreferences;

    SharedPreferences matchData, otherSettings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pre_match);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        matchDataPreferences = getString(R.string.matchDataPreferences);
        matchData = getSharedPreferences(matchDataPreferences, 0);

        otherPreferences = getString(R.string.otherPreferences);
        otherSettings = getSharedPreferences(otherPreferences, 0);

        SharedPreferences.Editor SPOS = otherSettings.edit();

        SPOS.apply();

        teamNum = findViewById(R.id.teamNum);
        matchNum = findViewById(R.id.matchNum);
        startPos = findViewById(R.id.startPos);
        map = findViewById(R.id.map);

        switch (otherSettings.getInt("scouterPos", 0)) {
            case 0:
                map.setImageResource(R.drawable.s0);
                break;
            case 1:
                map.setImageResource(R.drawable.s1);
                break;
            case 2:
                map.setImageResource(R.drawable.s2);
                break;
            case 3:
                map.setImageResource(R.drawable.s3);
                break;
            case 4:
                map.setImageResource(R.drawable.s4);
                break;
            case 5:
                map.setImageResource(R.drawable.s5);
                break;
            default:
                map.setImageResource(R.drawable.field);
                break;


        }
    }

    public void setGoToAuton(View v) {

        if ((teamNum.getText().toString()).length() == 0 || (matchNum.getText().toString()).length() == 0) {

            Toast.makeText(this, "Please Complete All Fields", Toast.LENGTH_SHORT).show();


        } else if (Integer.parseInt(teamNum.getText().toString()) == 0 || Integer.parseInt(matchNum.getText().toString()) == 0) {
            Toast.makeText(this, "you gave us values but they suck. try again", Toast.LENGTH_SHORT).show();
        } else {


            SharedPreferences.Editor SPMD = matchData.edit();
            SPMD.putString("preMatchVals", ",");
            SPMD.apply();


            Intent goToAuton = new Intent(PreMatch.this, AutonTeleop.class);
            startActivity(goToAuton);
        }
    }

    long prevTime = 0;
   @Override
    public void onBackPressed() {

        long thisTime = Calendar.getInstance().getTimeInMillis();
        if ((thisTime - prevTime) <= 1000) {//1 SEC

            super.onBackPressed();
        } else {
             Toast.makeText(this, "Press Again to Go Back", Toast.LENGTH_LONG).show();
            prevTime = thisTime;
        }

    }

}
