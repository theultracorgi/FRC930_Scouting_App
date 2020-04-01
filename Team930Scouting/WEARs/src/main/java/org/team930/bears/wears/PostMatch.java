package org.team930.bears.wears;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

@SuppressWarnings("ALL")
public class PostMatch extends AppCompatActivity {

    int disabled;
    String matchDataPreferences, otherPreferences, numStoredMatches;
    SharedPreferences matchData, otherSettings;

    SeekbarView passingEffectivenss, boundaryEffectiveness, defendedEffectiveness, defenseEffectivness, defenseAggressiveness;
    TextboxView secondsDisabled, reasonDisabled, struggles, otherComments, whyPick;
    ToggleView passedTo, gettingInWay, pushed, pushing, worthPicking;
    CheckboxView penalties;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_match);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        numStoredMatches = getString(R.string.numStoredMatches);
        disabled = 0;

        matchDataPreferences = getString(R.string.matchDataPreferences);
        matchData = getSharedPreferences(matchDataPreferences, 0);

        otherPreferences = getString(R.string.otherPreferences);
        otherSettings = getSharedPreferences(otherPreferences, 0);

        //Finding Views
        passingEffectivenss = findViewById(R.id.pPassingEffectiveness);
        boundaryEffectiveness = findViewById(R.id.pBarCrossing);
        defendedEffectiveness = findViewById(R.id.pGettingDefended);
        defenseEffectivness = findViewById(R.id.pDefenseEffectiveness);
        defenseAggressiveness = findViewById(R.id.pDefenseAggressiveness);

        secondsDisabled = findViewById(R.id.pSecondsDisabled);
        reasonDisabled = findViewById(R.id.pReasonDisabled);
        struggles = findViewById(R.id.pStruggles);
        otherComments = findViewById(R.id.pOtherComments);
        whyPick = findViewById(R.id.pWhyPick);

        passedTo = findViewById(R.id.pPassedTo);
        gettingInWay = findViewById(R.id.pGettingInTheWay);
        pushed = findViewById(R.id.pDefensePushed);
        pushing = findViewById(R.id.pDefensePushing);
        worthPicking = findViewById(R.id.pWorthPicking);

        penalties = findViewById(R.id.pPenalties);

    }

    long prevTime = 0;

    public void setSubmitData(View v) {

        if (whyPick.getText().length() <= 10 || !whyPick.getText().toString().contains(" ")) {
            Toast.makeText(this, "cool kids make more comments", Toast.LENGTH_SHORT).show();

        } else if (secondsDisabled.getText().length() > 0 && reasonDisabled.getText().length() < 5) {
            Toast.makeText(this, "y were they disabled tho???", Toast.LENGTH_SHORT).show();
        } else { //1

            int[] penaltyStates = penalties.getCheckBoxCheckedArray();
            if (secondsDisabled.getText().length() > 0) {
                    disabled = 1;
            }

            SharedPreferences.Editor SPMD = matchData.edit();

            SPMD.putString(getString(R.string.postMatchVals), passingEffectivenss.getProgress() + "," + passedTo.getState() + "," +
                    boundaryEffectiveness.getProgress() + "," + disabled + "," + secondsDisabled.getText() + "," + reasonDisabled.getText() + "," +
                    gettingInWay.getState() + "," +
                    defendedEffectiveness.getProgress() + "," + pushed.getState() + "," +
                    defenseEffectivness.getProgress() + "," + defenseAggressiveness.getProgress() + "," + pushing.getState() + "," +
                    penaltyStates[0] + "," + penaltyStates[1] + "," + penaltyStates[2] + "," + penaltyStates[3] + "," + penaltyStates[4] + "," +
                    struggles.getText() + "," + otherComments.getText() + "," + worthPicking.getState() + "," + whyPick.getText() + "," +
                    matchData.getString("scouterName", "") + "\n");
            SPMD.apply();

            long thisTime = java.util.Calendar.getInstance().getTimeInMillis();
            if (prevTime < thisTime) { //2
                if ((thisTime - prevTime) <= 1000) {//1 SEC

                    SharedPreferences.Editor SPOS = otherSettings.edit();
                    SPOS.putInt(numStoredMatches, otherSettings.getInt(numStoredMatches, 2) + 1);
                    SPMD.putString(getString(R.string.sendableData), matchData.getString(getString(R.string.sendableData), "sendable not found,") +
                            matchData.getString(getString(R.string.preMatchVals), "shared preference not found,") +
                            matchData.getString(getString(R.string.autonTeleopVals), "shared preference not found,") +
                            matchData.getString(getString(R.string.postMatchVals), "shared preference not found,"));
                    SPMD.apply();
                    SPOS.apply();

                    Intent submitData = new Intent(PostMatch.this, HomeScreen.class);
                    startActivity(submitData);

                } else { //3
                    //first tap
                    Toast.makeText(this, "Press Again To Go to Submit Data", Toast.LENGTH_SHORT).show();
                    prevTime = thisTime;
                } //3

            } //2
        } //1

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
