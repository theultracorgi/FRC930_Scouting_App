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
    String matchDataPreferences, otherPreferences, allianceColor;
    String startPosVal;

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



        switch (startPos.getPos()) {
            case 0:
                map.setImageResource(R.drawable.s0);
                allianceColor = "b";
                break;
            case 1:
                map.setImageResource(R.drawable.s1);
                allianceColor = "b";
                break;
            case 2:
                map.setImageResource(R.drawable.s2);
                allianceColor = "b";
                break;
            case 3:
                map.setImageResource(R.drawable.s3);
                allianceColor = "r";
                break;
            case 4:
                map.setImageResource(R.drawable.s4);
                allianceColor = "r";
                break;
            case 5:
                map.setImageResource(R.drawable.s5);
                allianceColor = "r";
                break;
            default:
                allianceColor = "b";
                map.setImageResource(R.drawable.field);
                break;


        }


    }

    long prevTime = 0;
    public void setPGoToMatch(View v) {

        switch (otherSettings.getInt("scouterPos", 0)) {
            case 1:
            case 4:
                startPosVal = "Middle";
                break;
            case 2:
            case 3:
                startPosVal = "Left";
                break;
            default:
                startPosVal = "Right";
                break;
        }


        if (isValidField()) {
            long thisTime = Calendar.getInstance().getTimeInMillis();
            if (prevTime < thisTime) {
                if ((thisTime - prevTime) <= 1000) {//1 SEC

                    SharedPreferences.Editor SPMD = matchData.edit();
                    SPMD.putString("preMatchVals", teamNum.getText() + "," + matchNum.getText() + allianceColor + "," + startPosVal + ",");
                    SPMD.apply();

                    Intent goToAuton = new Intent(PreMatch.this, AutonTeleop.class);
                    startActivity(goToAuton);
                } else {
                    //first tap
                    Toast.makeText(this, "Press Again To Go to Post Match", Toast.LENGTH_SHORT).show();
                    prevTime = thisTime;
                }
            }
        }

    }

    private boolean isValidField() {
        if (teamNum.getText().length() == 0 || matchNum.getText().length() == 0) {
            Toast.makeText(this, "Please Complete All Fields", Toast.LENGTH_SHORT).show();
            return false;
        } else if (!isNumeric(teamNum.getText()) || !isNumeric(matchNum.getText())) {
            Toast.makeText(this, "those ain't numbers my dude", Toast.LENGTH_SHORT).show();
            return false;
        } else if (Integer.parseInt(teamNum.getText()) == 0 || Integer.parseInt(matchNum.getText()) == 0) {
            Toast.makeText(this, "you gave us values but they suck. try again", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            return true;
        }
    }

    public static boolean isNumeric(String strNum) {
        if (strNum == null) {
            return false;
        }
        try {
            double d = Double.parseDouble(strNum);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }

    long backPrevTime = 0;

    @Override
    public void onBackPressed() {

        long thisTime = Calendar.getInstance().getTimeInMillis();
        if ((thisTime - backPrevTime) <= 1000) {//1 SEC

            super.onBackPressed();
        } else {
            Toast.makeText(this, "Press Again to Go Back", Toast.LENGTH_LONG).show();
            backPrevTime = thisTime;
        }

    }

}
