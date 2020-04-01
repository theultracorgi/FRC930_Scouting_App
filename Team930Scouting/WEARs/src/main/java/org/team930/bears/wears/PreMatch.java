package org.team930.bears.wears;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.Toast;

import java.util.Calendar;


public class PreMatch extends AppCompatActivity {

    TextboxView scouter, teamNum, matchNum;
    LabelView scouterPos;
    SpinnerView startPos;

    String matchDataPreferences, otherPreferences, allianceColor;
    String startPosVal;

    SharedPreferences matchData, otherSettings;

    @SuppressLint("SourceLockedOrientationActivity")
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

        scouter = findViewById(R.id.pScouter);
        teamNum = findViewById(R.id.teamNum);
        matchNum = findViewById(R.id.matchNum);
        startPos = findViewById(R.id.startPos);
        scouterPos = findViewById(R.id.pScouterPos);

        switch (otherSettings.getInt("scouterPos", 0)) {
            case 0:
                scouterPos.setText("Blue Far");
                allianceColor = "b";
                break;
            case 1:
                scouterPos.setText("Blue Middle");
                allianceColor = "b";
                break;
            case 2:
                scouterPos.setText("Blue Close");
                allianceColor = "b";
                break;
            case 3:
                scouterPos.setText("Red Far");
                allianceColor = "r";
                break;
            case 4:
                scouterPos.setText("Red Middle");
                allianceColor = "r";
                break;
            default:
                scouterPos.setText("Red Close");
                allianceColor = "r";
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

            SharedPreferences.Editor SPMD = matchData.edit();
            SPMD.putString(getString(R.string.preMatchVals), teamNum.getText() + "," + matchNum.getText() + allianceColor + "," + startPosVal + ",");
            SPMD.putString("scouterName", scouter.getText());
            SPMD.apply();

            long thisTime = Calendar.getInstance().getTimeInMillis();
            if (prevTime < thisTime) {
                if ((thisTime - prevTime) <= 1000) {//1 SEC
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
        } else if (isNumeric(teamNum.getText()) || isNumeric(matchNum.getText())) {
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
            return true;
        }
        try {
            double d = Double.parseDouble(strNum);
        } catch (NumberFormatException nfe) {
            return true;
        }
        return false;
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
