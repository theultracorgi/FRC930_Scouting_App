package org.team930.bears.wears;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;

import androidx.appcompat.app.AppCompatActivity;

import android.icu.util.Calendar;
import android.os.Bundle;
import android.os.Handler;
import android.view.ContextThemeWrapper;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.util.Locale;

import static android.app.AlertDialog.THEME_HOLO_LIGHT;

@SuppressWarnings("ALL")
public class PostMatch extends AppCompatActivity {

    int disabled;
    String matchDataPreferences, otherPreferences, numStoredMatches;
    SharedPreferences matchData, otherSettings;

    SeekbarView passingEffectivenss, boundaryEffectiveness, defendedEffectiveness, defenseEffectivness, defenseAggressiveness;
    TextboxView secondsDisabled, reasonDisabled, struggles, otherComments, whyPick, scouter;
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
        scouter = findViewById(R.id.pScouter);

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
        } else {
            long thisTime = java.util.Calendar.getInstance().getTimeInMillis();
            if (prevTime < thisTime) {
                if ((thisTime - prevTime) <= 1000) {//1 SEC

                    if (secondsDisabled.getText().length() > 0) {
                        if (Integer.parseInt(secondsDisabled.getText()) > 0) {
                            disabled = 1;
                        }
                    }

                    int[] penaltyStates = penalties.getCheckBoxCheckedArray();


                    SharedPreferences.Editor SPMD = matchData.edit();
                    SharedPreferences.Editor SPOS = otherSettings.edit();

                    SPMD.putString("postMatchVals", passingEffectivenss.getProgress() + "," + passedTo.getState() + "," +
                            boundaryEffectiveness.getProgress() + "," + disabled + "," + reasonDisabled.getText() + "," +
                            gettingInWay.getState() + "," +
                            defendedEffectiveness.getProgress() + "," + pushed.getState() + "," +
                            defenseEffectivness.getProgress() + "," + defenseAggressiveness.getProgress() + "," + pushing.getState() + "," +
                            penaltyStates[0] + "," + penaltyStates[1] + "," + penaltyStates[2] + "," + penaltyStates[3] + "," +
                            struggles.getText() + "," + otherComments.getText() + "," + worthPicking.getState() + "," + whyPick.getText() + "," + scouter.getText()
                    );
                    SPMD.apply();

                    SPOS.putInt(numStoredMatches, otherSettings.getInt(numStoredMatches, 7) + 1);
                    SPOS.apply();

                    if (otherSettings.getInt(numStoredMatches, 8) <= 4) {
                        SPMD.putString("firstQR", matchData.getString("firstQR", "") + matchData.getString("preMatchVals", "") + matchData.getString("autonTeleopVals", "") + matchData.getString("postMatchVals", "") + "\n");
                    } else {
                        SPMD.putString("secondQR", matchData.getString("secondQR", "") + matchData.getString("preMatchVals", "") + matchData.getString("autonTeleopVals", "") + matchData.getString("postMatchVals", "") + "\n");
                    }
                    SPOS.apply();
                    SPMD.apply();

                    Intent submitData = new Intent(PostMatch.this, HomeScreen.class);
                    startActivity(submitData);

                } else {
                    //first tap
                    Toast.makeText(this, "Press Again To Go to Submit Data", Toast.LENGTH_SHORT).show();
                    prevTime = thisTime;
                }

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
