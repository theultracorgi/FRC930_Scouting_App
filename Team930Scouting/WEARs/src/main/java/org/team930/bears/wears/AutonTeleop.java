package org.team930.bears.wears;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.os.Handler;

import android.view.View;
import android.widget.Button;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;

@SuppressWarnings("ALL")
public class AutonTeleop extends AppCompatActivity {

    String matchDataPreferences, otherPreferences, aFailTrue;
    SharedPreferences matchData, otherSettings;

    Button tGoToPostmatch;
    ToggleView initiationLine, aFail, grabbedBalls, penaltiesGood, rotationAttempt, positionAttempt, otherClimbs;
    CounterView aBottom, aOuter, aInner,
            z1Outer, z1Inner, z1Missed,
            z2Outer, z2Inner, z2Bottom, z2Missed,
            z3Outer, z3Inner, z3Missed,
            z4Outer, z4Inner, z4Missed,
            z5Outer, z5Inner, z5Missed,
            passesMade, penaltiesBad;
    SpinnerView aFailReason, endgameState;
    ChronometerView rotationTime, positionTime, endgameTime;
    TextboxView endgameFailReason, endgameOther;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_autonteleop);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        matchDataPreferences = getString(R.string.matchDataPreferences);
        otherPreferences = getString(R.string.otherPreferences);

        matchData = getSharedPreferences(matchDataPreferences, 0);
        otherSettings = getSharedPreferences(otherPreferences, 0);

        //Finding Views
        tGoToPostmatch = findViewById(R.id.goToPostMatch);

        initiationLine = findViewById(R.id.aInitiationLine);
        aFail = findViewById(R.id.aFail);
        grabbedBalls = findViewById(R.id.aBallsNotShot);
        penaltiesGood = findViewById(R.id.tPenaltiesAccrued);
        rotationAttempt = findViewById(R.id.tRotationAttempt);
        positionAttempt = findViewById(R.id.tPositionAttempt);
        otherClimbs = findViewById(R.id.tOtherClimbs);

        aBottom = findViewById(R.id.aBottomPort);
        aOuter = findViewById(R.id.aOuterPort);
        aInner = findViewById(R.id.aInnerPort);

        z1Outer = findViewById(R.id.tZone1Outer);
        z1Inner = findViewById(R.id.tZone1Inner);
        z1Missed = findViewById(R.id.tZone1Missed);

        z2Outer = findViewById(R.id.tZone2Outer);
        z2Inner = findViewById(R.id.tZone2Inner);
        z2Missed = findViewById(R.id.tZone2Missed);
        z2Bottom = findViewById(R.id.tZone2Bottom);

        z3Outer = findViewById(R.id.tZone3Outer);
        z3Inner = findViewById(R.id.tZone3Inner);
        z3Missed = findViewById(R.id.tZone3Missed);

        z4Outer = findViewById(R.id.tZone4Outer);
        z4Inner = findViewById(R.id.tZone4Inner);
        z4Missed = findViewById(R.id.tZone4Missed);

        z5Outer = findViewById(R.id.tZone5Outer);
        z5Inner = findViewById(R.id.tZone5Inner);
        z5Missed = findViewById(R.id.tZone5Missed);

        passesMade = findViewById(R.id.tPasses);
        penaltiesBad = findViewById(R.id.tPenaltiesIncurred);

        aFailReason = findViewById(R.id.aFailReason);
        endgameState = findViewById(R.id.tEndgameState);

        rotationTime = findViewById(R.id.tRotationTime);
        positionTime = findViewById(R.id.tPositionTime);
        endgameTime = findViewById(R.id.tEndgameTime);

        endgameFailReason = findViewById(R.id.tClimbFailDetails);
        endgameOther = findViewById(R.id.tOtherClimbDetails);


    }

    long prevTime = 0;

    public void setTGoToPostmatch(View v) {

        if (aFail.getState() == 1) {
            aFailTrue = aFailReason.getSelection();
        } else {
            aFailTrue = "";
        }
        int rotationBool = 0;
        int positionBool = 0;
        if(rotationAttempt.getState()==1 || rotationTime.getChonometerReading() != 0) {
            rotationBool = 1;
        }
        if(positionAttempt.getState()==1 || positionTime.getChonometerReading() != 0) {
            rotationBool = 1;
        }

        long thisTime = java.util.Calendar.getInstance().getTimeInMillis();
        if (prevTime < thisTime) {
            if ((thisTime - prevTime) <= 1000) {//1 SEC
                SharedPreferences.Editor SPMD = matchData.edit();
                SPMD.putString("autonTeleopVals", initiationLine.getState() + "," +
                        aBottom.getCount() + "," + aOuter.getCount() + "," + aInner.getCount() + "," +
                        aFailTrue + "," + grabbedBalls.getState() + "," +
                        z1Inner.getCount() + "," + z1Outer.getCount() + "," + z1Missed.getCount() + "," +
                        z2Inner.getCount() + "," + z2Outer.getCount() + "," + z2Bottom.getCount() + "," + z2Missed.getCount() + "," +
                        z3Inner.getCount() + "," + z3Outer.getCount() + "," + z3Missed.getCount() + "," +
                        z4Inner.getCount() + "," + z4Outer.getCount() + "," + z4Missed.getCount() + "," +
                        z5Inner.getCount() + "," + z5Outer.getCount() + "," + z5Missed.getCount() + "," +
                        rotationTime.getChonometerReading() + "," + rotationBool + "," +
                        positionTime.getChonometerReading() + "," + positionBool + "," +
                        passesMade.getCount() + "," + penaltiesBad.getCount() + "," + penaltiesGood.getState() + "," +
                        endgameState.getSelection() + "," + otherClimbs.getState() + "," + endgameTime.getChonometerReading() + "," +
                        endgameFailReason.getText() + "," + endgameOther.getText() + ","
                );

                SPMD.apply();

                Intent goToAuton = new Intent(this, PostMatch.class);
                startActivity(goToAuton);

            } else {
                //first tap
                Toast.makeText(this, "Press Again To Go to Submit Data", Toast.LENGTH_SHORT).show();
                prevTime = thisTime;
            }

        }

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

