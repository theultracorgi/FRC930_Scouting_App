package org.team930.bears.wearstime;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.ContextThemeWrapper;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.ToggleButton;


import java.util.Locale;

import static android.app.AlertDialog.THEME_HOLO_LIGHT;

@SuppressWarnings("ALL")
public class AutonTeleop extends AppCompatActivity {
    Button goToPostMatch;
    ToggleButton autoline;
    RadioButton onField, parked, elevated;
    TextView aSwitchADisplay, aScaleADisplay, aSwitchSDisplay, aScaleSDisplay, tSwitchADisplay, tSwitchSDisplay, tScaleADisplay, tScaleSDisplay, tOSwitchADisplay, tOSwitchSDisplay, tVaultSDisplay;

    Integer aAutoLine, aSwitchAttempts, aScaleAttempts, tSwitchAttempts, tScaleAttempts, tOSwitchAttempts, aSwitchScored, aScaleScored, tSwitchScored, tScaleScored, tOSwitchScored, tVaultScored, tParked, tElevated;
    String matchDataPreferences, otherPreferences;
    Boolean scoredAddsAttempt, addAScoredCube, addTScoredCube;
    String aAutoLinePassable, aSwitchAttemptsPassable, aScaleAttemptsPassable, tSwitchAttemptsPassable, tScaleAttemptsPassable, tOSwitchAttemptsPassable, aSwitchScoredPassable, aScaleScoredPassable, tSwitchScoredPassable, tScaleScoredPassable, tOSwitchScoredPassable, tVaultScoredPassable, tParkedPassable, tElevatedPassable;

    SharedPreferences matchData, otherSettings;
    MediaPlayer Still;
    AlertDialog.Builder submit;
    ContextThemeWrapper ctw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_autonteleop);

        //CUBE LIMITS
        addAScoredCube = true;
        addTScoredCube = true;

        //POPUPS
        ctw = new ContextThemeWrapper(this, THEME_HOLO_LIGHT);
        submit = new AlertDialog.Builder(ctw);

        //OHNINE
        Still = MediaPlayer.create(this, R.raw.ohnine_playa);


        //SHARED PREFERENCES
        matchDataPreferences = getString(R.string.matchDataPreferences);
        otherPreferences = getString(R.string.otherPreferences);

        matchData = getSharedPreferences(matchDataPreferences, 0);
        otherSettings = getSharedPreferences(otherPreferences, 0);

        scoredAddsAttempt = otherSettings.getBoolean("scoredAddsAttempt", false);

        //AUTOLINE

        //BINARIES
        aAutoLine = Integer.parseInt(matchData.getString("aAutoline", "0")); //binary

        //VIEWS
        autoline = findViewById(R.id.autoline);

        if(aAutoLine ==0) {
            autoline.setChecked(false);
        } else {
            autoline.setChecked(true);
        }

        //ATTEMPTS

        //COUNTS
        aSwitchAttempts = Integer.parseInt(matchData.getString("aSwitchAttempts", "0"));
        aScaleAttempts = Integer.parseInt(matchData.getString("aScaleAttempts", "0"));
        tSwitchAttempts = Integer.parseInt(matchData.getString("tSwitchAttempts", "0"));
        tScaleAttempts = Integer.parseInt(matchData.getString("tScaleAttempts", "0"));
        tOSwitchAttempts = Integer.parseInt(matchData.getString("tSwitchAttempts", "0"));


        //VIEWS
        aSwitchADisplay = findViewById(R.id.aSwitchDisplayA);
        aScaleADisplay = findViewById(R.id.aScaleDisplayA);
        tSwitchADisplay = findViewById(R.id.tSwitchDisplayA);
        tScaleADisplay = findViewById(R.id.tScaleDisplayA);
        tOSwitchADisplay = findViewById(R.id.tOSwitchDisplayA);

        aSwitchADisplay.setText(String.format(Locale.ENGLISH, "%d", aSwitchAttempts));
        aScaleADisplay.setText(String.format(Locale.ENGLISH, "%d", aScaleAttempts));
        tSwitchADisplay.setText(String.format(Locale.ENGLISH, "%d", tSwitchAttempts));
        tScaleADisplay.setText(String.format(Locale.ENGLISH, "%d", tScaleAttempts));
        tOSwitchADisplay.setText(String.format(Locale.ENGLISH, "%d", tOSwitchAttempts));

        //SCORED

        //COUNTS
        aSwitchScored = Integer.parseInt(matchData.getString("aSwitchScored", "0"));
        aScaleScored = Integer.parseInt(matchData.getString("aScaleScored", "0"));
        tSwitchScored = Integer.parseInt(matchData.getString("tSwitchScored", "0"));
        tScaleScored = Integer.parseInt(matchData.getString("tScaleScored", "0"));
        tOSwitchScored = Integer.parseInt(matchData.getString("tSwitchScored", "0"));
        tVaultScored = Integer.parseInt(matchData.getString("tVaultScored", "0"));

        //VIEWS
        aSwitchSDisplay = findViewById(R.id.aSwitchDisplayS);
        aScaleSDisplay = findViewById(R.id.aScaleDisplayS);
        tSwitchSDisplay = findViewById(R.id.tSwitchDisplayS);
        tScaleSDisplay = findViewById(R.id.tScaleDisplayS);
        tOSwitchSDisplay = findViewById(R.id.tOSwitchDisplayS);
        tVaultSDisplay = findViewById(R.id.tVaultDisplayS);

        aSwitchSDisplay.setText(String.format(Locale.ENGLISH, "%d", aSwitchScored));
        aScaleSDisplay.setText(String.format(Locale.ENGLISH, "%d", aScaleScored));
        tSwitchSDisplay.setText(String.format(Locale.ENGLISH, "%d", tSwitchScored));
        tScaleSDisplay.setText(String.format(Locale.ENGLISH, "%d", tScaleScored));
        tOSwitchSDisplay.setText(String.format(Locale.ENGLISH, "%d", tOSwitchScored));
        tVaultSDisplay.setText(String.format(Locale.ENGLISH, "%d", tVaultScored));


        //ENDGAME

        //BINARIES
        tParked = Integer.parseInt(matchData.getString("tParked", "0")); //binary
        tElevated = Integer.parseInt(matchData.getString("tElevated", "0")); //binary

        //VIEWS
        onField = findViewById(R.id.onField);
        parked = findViewById(R.id.parked);
        elevated = findViewById(R.id.elevated);

        if(tParked == 1){
            onField.setChecked(false);
            parked.setChecked(true);
            elevated.setChecked(false);
        } else if(tElevated == 1){
            onField.setChecked(false);
            parked.setChecked(false);
            elevated.setChecked(true);
        } else {
            onField.setChecked(true);
            parked.setChecked(false);
            elevated.setChecked(false);
        }

        //NEXT
        goToPostMatch = findViewById(R.id.gotoPostMatch);


    }


    //AUTON AUTOLINE
    public void setAutoline(View v) {

        if (autoline.isChecked()) {
            aAutoLine = 1;

        } else {
            aAutoLine = 0;

        }
    }

//AUTON SWITCH

    //ATTEMPTS
    public void setASwitchSubtractA(View v) {
        if (aSwitchAttempts > 0 && aSwitchAttempts > aSwitchScored) {
            aSwitchAttempts -= 1;
            aSwitchADisplay.setText(String.format(Locale.ENGLISH, "%d", aSwitchAttempts));
        }
    }

    public void setASwitchAddA(View v) {
        aSwitchAttempts += 1;
        aSwitchADisplay.setText(String.format(Locale.ENGLISH, "%d", aSwitchAttempts));
    }

    //SCORED
    public void setASwitchSubtractS(View v) {

        if (aSwitchScored > 0) {
            aSwitchScored -= 1;
            aSwitchSDisplay.setText(String.format(Locale.ENGLISH, "%d", aSwitchScored));
        }
    }

    public void setASwitchAddS(View v) {
        if (!(aSwitchScored + aScaleScored >= 19)) {

            if (scoredAddsAttempt) {
                aSwitchScored += 1;
                aSwitchSDisplay.setText(String.format(Locale.ENGLISH, "%d", aSwitchScored));
                aSwitchAttempts += 1;
                aSwitchADisplay.setText(String.format(Locale.ENGLISH, "%d", aSwitchAttempts));
            } else if (aSwitchScored < aSwitchAttempts) {
                aSwitchScored += 1;
                aSwitchSDisplay.setText(String.format(Locale.ENGLISH, "%d", aSwitchScored));
            }
        }
    }

//AUTON SCALE

    //ATTEMPTS
    public void setAScaleSubtractA(View v) {
        if (aScaleAttempts > 0 && aScaleAttempts > aScaleScored) {
            aScaleAttempts -= 1;
            aScaleADisplay.setText(String.format(Locale.ENGLISH, "%d", aScaleAttempts));
        }
    }

    public void setAScaleAddA(View v) {

        aScaleAttempts += 1;
        aScaleADisplay.setText(String.format(Locale.ENGLISH, "%d", aScaleAttempts));
    }

    //SCORED
    public void setAScaleSubtractS(View v) {
        if (aScaleScored > 0) {

            aScaleScored -= 1;
            aScaleSDisplay.setText(String.format(Locale.ENGLISH, "%d", aScaleScored));
        }
    }

    public void setAScaleAddS(View v) {
        if (!(aSwitchScored + aScaleScored >= 19)) {

            if (scoredAddsAttempt) {
                aScaleScored += 1;
                aScaleSDisplay.setText(String.format(Locale.ENGLISH, "%d", aScaleScored));
                aScaleAttempts += 1;
                aScaleADisplay.setText(String.format(Locale.ENGLISH, "%d", aScaleAttempts));
            } else if (aScaleScored < aScaleAttempts) {
                aScaleScored += 1;
                aScaleSDisplay.setText(String.format(Locale.ENGLISH, "%d", aScaleScored));
            }
        }
    }

//TELEOP SWITCH

    //ATTEMPTS
    public void setTSwitchSubtractA(View v) {
        if (tSwitchAttempts > 0 && tSwitchAttempts > tSwitchScored) {
            tSwitchAttempts -= 1;
            tSwitchADisplay.setText(String.format(Locale.ENGLISH, "%d", tSwitchAttempts));
        }
    }

    public void setTSwitchAddA(View v) {
        tSwitchAttempts += 1;
        tSwitchADisplay.setText(String.format(Locale.ENGLISH, "%d", tSwitchAttempts));
    }

    //SCORED
    public void setTSwitchSubtractS(View v) {

        if (tSwitchScored > 0) {
            tSwitchScored -= 1;
            tSwitchSDisplay.setText(String.format(Locale.ENGLISH, "%d", tSwitchScored));
        }
    }

    public void setTSwitchAddS(View v) {
        if (!(tOSwitchScored + tSwitchScored + tScaleScored + tVaultScored >= 60 - (aSwitchScored + aScaleScored))) {

            if (scoredAddsAttempt) {
                tSwitchScored += 1;
                tSwitchSDisplay.setText(String.format(Locale.ENGLISH, "%d", tSwitchScored));
                tSwitchAttempts += 1;
                tSwitchADisplay.setText(String.format(Locale.ENGLISH, "%d", tSwitchAttempts));
            } else if (tSwitchScored < tSwitchAttempts) {
                tSwitchScored += 1;
                tSwitchSDisplay.setText(String.format(Locale.ENGLISH, "%d", tSwitchScored));
            }
        }
    }

//TELEOP SCALE

    //ATTEMPTS
    public void setTScaleSubtractA(View v) {
        if (tScaleAttempts > 0 && tScaleAttempts > tScaleScored) {
            tScaleAttempts -= 1;
            tScaleADisplay.setText(String.format(Locale.ENGLISH, "%d", tScaleAttempts));
        }
    }

    public void setTScaleAddA(View v) {
        //if(add 1 to both fields)
        tScaleAttempts += 1;
        tScaleADisplay.setText(String.format(Locale.ENGLISH, "%d", tScaleAttempts));
    }

    //SCORED
    public void setTScaleSubtractS(View v) {
        if (tScaleScored > 0) {
            tScaleScored -= 1;
            tScaleSDisplay.setText(String.format(Locale.ENGLISH, "%d", tScaleScored));
        }
    }

    public void setTScaleAddS(View v) {
        if (!(tOSwitchScored + tSwitchScored + tScaleScored + tVaultScored >= 60 - (aSwitchScored + aScaleScored))) {

            if (scoredAddsAttempt) {
                tScaleScored += 1;
                tScaleSDisplay.setText(String.format(Locale.ENGLISH, "%d", tScaleScored));
                tScaleAttempts += 1;
                tScaleADisplay.setText(String.format(Locale.ENGLISH, "%d", tScaleAttempts));
            } else if (tScaleScored < tScaleAttempts) {
                tScaleScored += 1;
                tScaleSDisplay.setText(String.format(Locale.ENGLISH, "%d", tScaleScored));
            }
        }
    }

//TELEOP SWITCH

    //ATTEMPTS
    public void setTOSwitchSubtractA(View v) {
        if (tOSwitchAttempts > 0 && tOSwitchAttempts > tOSwitchScored) {
            tOSwitchAttempts -= 1;
            tOSwitchADisplay.setText(String.format(Locale.ENGLISH, "%d", tOSwitchAttempts));
        }
    }

    public void setTOSwitchAddA(View v) {
        tOSwitchAttempts += 1;
        tOSwitchADisplay.setText(String.format(Locale.ENGLISH, "%d", tOSwitchAttempts));
    }

    //SCORED
    public void setTOSwitchSubtractS(View v) {

        if (tOSwitchScored > 0) {
            tOSwitchScored -= 1;
            tOSwitchSDisplay.setText(String.format(Locale.ENGLISH, "%d", tOSwitchScored));
        }
    }

    public void setTOSwitchAddS(View v) {
        if (!(tOSwitchScored + tSwitchScored + tScaleScored + tVaultScored >= 60 - (aSwitchScored + aScaleScored))) {

            if (scoredAddsAttempt) {
                tOSwitchScored += 1;
                tOSwitchSDisplay.setText(String.format(Locale.ENGLISH, "%d", tOSwitchScored));
                tOSwitchAttempts += 1;
                tOSwitchADisplay.setText(String.format(Locale.ENGLISH, "%d", tOSwitchAttempts));
            } else if (tOSwitchScored < tOSwitchAttempts) {
                tOSwitchScored += 1;
                tOSwitchSDisplay.setText(String.format(Locale.ENGLISH, "%d", tOSwitchScored));
            }
        }
    }

//TELEOP VAULT

    //SCORED
    public void setTVaultSubtractS(View v) {
        if (tVaultScored > 0) {
            tVaultScored -= 1;
            tVaultSDisplay.setText(String.format(Locale.ENGLISH, "%d", tVaultScored));
        }
    }

    public void setTVaultAddS(View v) {
        if (!(tOSwitchScored + tSwitchScored + tScaleScored + tVaultScored >= 60 - (aSwitchScored + aScaleScored))) {

            tVaultScored += 1;
            tVaultSDisplay.setText(String.format(Locale.ENGLISH, "%d", tVaultScored));
        }
    }

//ENDGAME
    public void setOnField(View v) {
        tParked = 0;
        tElevated = 0;

    }

    public void setParked(View v) {
        tParked = 1;
        tElevated = 0;

    }

    public void setElevated(View v) {
        tParked = 0;
        tElevated = 1;

    }

//NEXT
    public void setGoToPostMatch(View v) {

        //PASSABLE STRING CREATION

        //AUTOLINE
        aAutoLinePassable = String.format(Locale.ENGLISH, "%d", aAutoLine);

        //ATTEMPTS
        aSwitchAttemptsPassable = String.format(Locale.ENGLISH, "%d", aSwitchAttempts);
        aScaleAttemptsPassable = String.format(Locale.ENGLISH, "%d", aScaleAttempts);
        tSwitchAttemptsPassable = String.format(Locale.ENGLISH, "%d", tSwitchAttempts);
        tScaleAttemptsPassable = String.format(Locale.ENGLISH, "%d", tScaleAttempts);
        tOSwitchAttemptsPassable = String.format(Locale.ENGLISH, "%d", tOSwitchAttempts);

        //SCORED
        aSwitchScoredPassable = String.format(Locale.ENGLISH, "%d", aSwitchScored);
        aScaleScoredPassable = String.format(Locale.ENGLISH, "%d", aScaleScored);
        tSwitchScoredPassable = String.format(Locale.ENGLISH, "%d", tSwitchScored);
        tScaleScoredPassable = String.format(Locale.ENGLISH, "%d", tScaleScored);
        tOSwitchScoredPassable = String.format(Locale.ENGLISH, "%d", tOSwitchScored);
        tVaultScoredPassable = String.format(Locale.ENGLISH, "%d", tVaultScored);


        //ENDGAME
        tParkedPassable = String.format(Locale.ENGLISH, "%d", tParked);
        tElevatedPassable = String.format(Locale.ENGLISH, "%d", tElevated);

        //SAVING PASSABLES
        SharedPreferences.Editor SPMD = matchData.edit();

        //AUTOLINE
        SPMD.putString("aAutoline", aAutoLinePassable);

        //ATTEMPTS
        SPMD.putString("aSwitchAttempts", aSwitchAttemptsPassable);
        SPMD.putString("aScaleAttempts", aScaleAttemptsPassable);
        SPMD.putString("tSwitchAttempts", tSwitchAttemptsPassable);
        SPMD.putString("tScaleAttempts", tScaleAttemptsPassable);
        SPMD.putString("tOSwitchAttempts", tOSwitchAttemptsPassable);

        //SCORED
        SPMD.putString("aSwitchScored", aSwitchScoredPassable);
        SPMD.putString("aScaleScored", aScaleScoredPassable);
        SPMD.putString("tSwitchScored", tSwitchScoredPassable);
        SPMD.putString("tScaleScored", tScaleScoredPassable);
        SPMD.putString("tOSwitchScored", tOSwitchScoredPassable);
        SPMD.putString("tVaultScored", tVaultScoredPassable);

        //ENDGAME
        SPMD.putString("tParked", tParkedPassable);
        SPMD.putString("tElevated", tElevatedPassable);
        SPMD.apply();

        //NEXT
        Intent goToPostMatch = new Intent(AutonTeleop.this, PostMatch.class);
        startActivity(goToPostMatch);
    }

    public void onPause() {
        super.onPause();
        //AUTOLINE
        aAutoLinePassable = String.format(Locale.ENGLISH, "%d", aAutoLine);

        //ATTEMPTS
        aSwitchAttemptsPassable = String.format(Locale.ENGLISH, "%d", aSwitchAttempts);
        aScaleAttemptsPassable = String.format(Locale.ENGLISH, "%d", aScaleAttempts);
        tSwitchAttemptsPassable = String.format(Locale.ENGLISH, "%d", tSwitchAttempts);
        tScaleAttemptsPassable = String.format(Locale.ENGLISH, "%d", tScaleAttempts);
        tOSwitchAttemptsPassable = String.format(Locale.ENGLISH, "%d", tOSwitchAttempts);

        //SCORED
        aSwitchScoredPassable = String.format(Locale.ENGLISH, "%d", aSwitchScored);
        aScaleScoredPassable = String.format(Locale.ENGLISH, "%d", aScaleScored);
        tSwitchScoredPassable = String.format(Locale.ENGLISH, "%d", tSwitchScored);
        tScaleScoredPassable = String.format(Locale.ENGLISH, "%d", tScaleScored);
        tOSwitchScoredPassable = String.format(Locale.ENGLISH, "%d", tOSwitchScored);
        tVaultScoredPassable = String.format(Locale.ENGLISH, "%d", tVaultScored);


        //ENDGAME
        tParkedPassable = String.format(Locale.ENGLISH, "%d", tParked);
        tElevatedPassable = String.format(Locale.ENGLISH, "%d", tElevated);

        //SAVING PASSABLES
        SharedPreferences.Editor SPMD = matchData.edit();

        //AUTOLINE
        SPMD.putString("aAutoline", aAutoLinePassable);

        //ATTEMPTS
        SPMD.putString("aSwitchAttempts", aSwitchAttemptsPassable);
        SPMD.putString("aScaleAttempts", aScaleAttemptsPassable);
        SPMD.putString("tSwitchAttempts", tSwitchAttemptsPassable);
        SPMD.putString("tScaleAttempts", tScaleAttemptsPassable);
        SPMD.putString("tOSwitchAttempts", tOSwitchAttemptsPassable);

        //SCORED
        SPMD.putString("aSwitchScored", aSwitchScoredPassable);
        SPMD.putString("aScaleScored", aScaleScoredPassable);
        SPMD.putString("tSwitchScored", tSwitchScoredPassable);
        SPMD.putString("tScaleScored", tScaleScoredPassable);
        SPMD.putString("tOSwitchScored", tOSwitchScoredPassable);
        SPMD.putString("tVaultScored", tVaultScoredPassable);

        //ENDGAME
        SPMD.putString("tParked", tParkedPassable);
        SPMD.putString("tElevated", tElevatedPassable);
        SPMD.apply();

    }

    public void onResume() {
        super.onResume();


        //BINARIES
        aAutoLine = Integer.parseInt(matchData.getString("aAutoline", "0")); //binary

        //VIEWS
        autoline = findViewById(R.id.autoline);

        if(aAutoLine ==0) {
            autoline.setChecked(false);
        } else {
            autoline.setChecked(true);
        }

        //ATTEMPTS

        //COUNTS
        aSwitchAttempts = Integer.parseInt(matchData.getString("aSwitchAttempts", "0"));
        aScaleAttempts = Integer.parseInt(matchData.getString("aScaleAttempts", "0"));
        tSwitchAttempts = Integer.parseInt(matchData.getString("tSwitchAttempts", "0"));
        tScaleAttempts = Integer.parseInt(matchData.getString("tScaleAttempts", "0"));
        tOSwitchAttempts = Integer.parseInt(matchData.getString("tSwitchAttempts", "0"));


        //VIEWS
        aSwitchADisplay = findViewById(R.id.aSwitchDisplayA);
        aScaleADisplay = findViewById(R.id.aScaleDisplayA);
        tSwitchADisplay = findViewById(R.id.tSwitchDisplayA);
        tScaleADisplay = findViewById(R.id.tScaleDisplayA);
        tOSwitchADisplay = findViewById(R.id.tOSwitchDisplayA);

        aSwitchADisplay.setText(String.format(Locale.ENGLISH, "%d", aSwitchAttempts));
        aScaleADisplay.setText(String.format(Locale.ENGLISH, "%d", aScaleAttempts));
        tSwitchADisplay.setText(String.format(Locale.ENGLISH, "%d", tSwitchAttempts));
        tScaleADisplay.setText(String.format(Locale.ENGLISH, "%d", tScaleAttempts));
        tOSwitchADisplay.setText(String.format(Locale.ENGLISH, "%d", tOSwitchAttempts));

        //SCORED

        //COUNTS
        aSwitchScored = Integer.parseInt(matchData.getString("aSwitchScored", "0"));
        aScaleScored = Integer.parseInt(matchData.getString("aScaleScored", "0"));
        tSwitchScored = Integer.parseInt(matchData.getString("tSwitchScored", "0"));
        tScaleScored = Integer.parseInt(matchData.getString("tScaleScored", "0"));
        tOSwitchScored = Integer.parseInt(matchData.getString("tSwitchScored", "0"));
        tVaultScored = Integer.parseInt(matchData.getString("tVaultScored", "0"));

        //VIEWS
        aSwitchSDisplay = findViewById(R.id.aSwitchDisplayS);
        aScaleSDisplay = findViewById(R.id.aScaleDisplayS);
        tSwitchSDisplay = findViewById(R.id.tSwitchDisplayS);
        tScaleSDisplay = findViewById(R.id.tScaleDisplayS);
        tOSwitchSDisplay = findViewById(R.id.tOSwitchDisplayS);
        tVaultSDisplay = findViewById(R.id.tVaultDisplayS);

        aSwitchSDisplay.setText(String.format(Locale.ENGLISH, "%d", aSwitchScored));
        aScaleSDisplay.setText(String.format(Locale.ENGLISH, "%d", aScaleScored));
        tSwitchSDisplay.setText(String.format(Locale.ENGLISH, "%d", tSwitchScored));
        tScaleSDisplay.setText(String.format(Locale.ENGLISH, "%d", tScaleScored));
        tOSwitchSDisplay.setText(String.format(Locale.ENGLISH, "%d", tOSwitchScored));
        tVaultSDisplay.setText(String.format(Locale.ENGLISH, "%d", tVaultScored));


        //ENDGAME

        //BINARIES
        tParked = Integer.parseInt(matchData.getString("tParked", "0")); //binary
        tElevated = Integer.parseInt(matchData.getString("tElevated", "0")); //binary

        //VIEWS
        onField = findViewById(R.id.onField);
        parked = findViewById(R.id.parked);
        elevated = findViewById(R.id.elevated);

        if(tParked == 1){
            onField.setChecked(false);
            parked.setChecked(true);
            elevated.setChecked(false);
        } else if(tElevated == 1){
            onField.setChecked(false);
            parked.setChecked(false);
            elevated.setChecked(true);
        } else {
            onField.setChecked(true);
            parked.setChecked(false);
            elevated.setChecked(false);
        }
    }
}



