package org.team930.bears.watching_extremely_awesome_robots;

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

import static android.app.AlertDialog.THEME_HOLO_LIGHT;

public class AutonTeleop extends AppCompatActivity {
    Button goToPostMatch;
    ToggleButton autoline;
    RadioButton onField, parked, elevated;
    TextView aSwitchADisplay, aScaleADisplay, aSwitchSDisplay, aScaleSDisplay, tSwitchADisplay, tSwitchSDisplay, tScaleADisplay, tScaleSDisplay, tVaultSDisplay;

    Integer aAutoLine, aSwitchAttempts, aScaleAttempts, tSwitchAttempts, tScaleAttempts, aSwitchScored, aScaleScored, tSwitchScored, tScaleScored, tVaultScored, tParked, tElevated;
    String matchDataPreferences, otherPreferences;
    Boolean scoredAddsAttempt, addAScoredCube, addTScoredCube;
    String aAutoLinePassable, aSwitchAttemptsPassable, aScaleAttemptsPassable, tSwitchAttemptsPassable, tScaleAttemptsPassable, aSwitchScoredPassable, aScaleScoredPassable, tSwitchScoredPassable, tScaleScoredPassable, tVaultScoredPassable, tParkedPassable, tElevatedPassable;

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

        //VIEWS
        aSwitchADisplay = findViewById(R.id.aSwitchDisplayA);
        aScaleADisplay = findViewById(R.id.aScaleDisplayA);
        tSwitchADisplay = findViewById(R.id.tSwitchDisplayA);
        tScaleADisplay = findViewById(R.id.tScaleDisplayA);

        aSwitchADisplay.setText(Integer.toString(aSwitchAttempts));
        aScaleADisplay.setText(Integer.toString(aScaleAttempts));
        tSwitchADisplay.setText(Integer.toString(tSwitchAttempts));
        tScaleADisplay.setText(Integer.toString(tScaleAttempts));

        //SCORED

        //COUNTS
        aSwitchScored = Integer.parseInt(matchData.getString("aSwitchScored", "0"));
        aScaleScored = Integer.parseInt(matchData.getString("aScaleScored", "0"));
        tSwitchScored = Integer.parseInt(matchData.getString("tSwitchScored", "0"));
        tScaleScored = Integer.parseInt(matchData.getString("tScaleScored", "0"));
        tVaultScored = Integer.parseInt(matchData.getString("tVaultScored", "0"));;

        //VIEWS
        aSwitchSDisplay = findViewById(R.id.aSwitchDisplayS);
        aScaleSDisplay = findViewById(R.id.aScaleDisplayS);
        tSwitchSDisplay = findViewById(R.id.tSwitchDisplayS);
        tScaleSDisplay = findViewById(R.id.tScaleDisplayS);
        tVaultSDisplay = findViewById(R.id.tVaultDisplayS);

        aSwitchSDisplay.setText(Integer.toString(aSwitchScored));
        aScaleSDisplay.setText(Integer.toString(aScaleScored));
        tSwitchSDisplay.setText(Integer.toString(tSwitchScored));
        tScaleSDisplay.setText(Integer.toString(tScaleScored));
        tVaultSDisplay.setText(Integer.toString(tVaultScored));

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
            aSwitchADisplay.setText(Integer.toString(aSwitchAttempts));
        }
    }

    public void setASwitchAddA(View v) {
        aSwitchAttempts += 1;
        aSwitchADisplay.setText(Integer.toString(aSwitchAttempts));
    }

    //SCORED
    public void setASwitchSubtractS(View v) {

        if (aSwitchScored > 0) {
            aSwitchScored -= 1;
            aSwitchSDisplay.setText(Integer.toString(aSwitchScored));
        }
    }

    public void setASwitchAddS(View v) {
        if (!(aSwitchScored + aScaleScored >= 19)) {

            if (scoredAddsAttempt) {
                aSwitchScored += 1;
                aSwitchSDisplay.setText(Integer.toString(aSwitchScored));
                aSwitchAttempts += 1;
                aSwitchADisplay.setText(Integer.toString(aSwitchAttempts));
            } else if (aSwitchScored < aSwitchAttempts) {
                aSwitchScored += 1;
                aSwitchSDisplay.setText(Integer.toString(aSwitchScored));
            }
        }
    }

//AUTON SCALE

    //ATTEMPTS
    public void setAScaleSubtractA(View v) {
        if (aScaleAttempts > 0 && aScaleAttempts > aScaleScored) {
            aScaleAttempts -= 1;
            aScaleADisplay.setText(Integer.toString(aScaleAttempts));
        }
    }

    public void setAScaleAddA(View v) {

        aScaleAttempts += 1;
        aScaleADisplay.setText(Integer.toString(aScaleAttempts));
    }

    //SCORED
    public void setAScaleSubtractS(View v) {
        if (aScaleScored > 0) {

            aScaleScored -= 1;
            aScaleSDisplay.setText(Integer.toString(aScaleScored));
        }
    }

    public void setAScaleAddS(View v) {
        if (!(aSwitchScored + aScaleScored >= 19)) {

            if (scoredAddsAttempt) {
                aScaleScored += 1;
                aScaleSDisplay.setText(Integer.toString(aScaleScored));
                aScaleAttempts += 1;
                aScaleADisplay.setText(Integer.toString(aScaleAttempts));
            } else if (aScaleScored < aScaleAttempts) {
                aScaleScored += 1;
                aScaleSDisplay.setText(Integer.toString(aScaleScored));
            }
        }
    }

//TELEOP SWITCH

    //ATTEMPTS
    public void setTSwitchSubtractA(View v) {
        if (tSwitchAttempts > 0 && tSwitchAttempts > tSwitchScored) {
            tSwitchAttempts -= 1;
            tSwitchADisplay.setText(Integer.toString(tSwitchAttempts));
        }
    }

    public void setTSwitchAddA(View v) {
        tSwitchAttempts += 1;
        tSwitchADisplay.setText(Integer.toString(tSwitchAttempts));
    }

    //SCORED
    public void setTSwitchSubtractS(View v) {

        if (tSwitchScored > 0) {
            tSwitchScored -= 1;
            tSwitchSDisplay.setText(Integer.toString(tSwitchScored));
        }
    }

    public void setTSwitchAddS(View v) {
        if (!(tSwitchScored + tScaleScored + tVaultScored >= 60 - (aSwitchScored + aScaleScored))) {

            if (scoredAddsAttempt) {
                tSwitchScored += 1;
                tSwitchSDisplay.setText(Integer.toString(tSwitchScored));
                tSwitchAttempts += 1;
                tSwitchADisplay.setText(Integer.toString(tSwitchAttempts));
            } else if (tSwitchScored < tSwitchAttempts) {
                tSwitchScored += 1;
                tSwitchSDisplay.setText(Integer.toString(tSwitchScored));
            }
        }
    }

//TELEOP SCALE

    //ATTEMPTS
    public void setTScaleSubtractA(View v) {
        if (tScaleAttempts > 0 && tScaleAttempts > tScaleScored) {
            tScaleAttempts -= 1;
            tScaleADisplay.setText(Integer.toString(tScaleAttempts));
        }
    }

    public void setTScaleAddA(View v) {
        //if(add 1 to both fields)
        tScaleAttempts += 1;
        tScaleADisplay.setText(Integer.toString(tScaleAttempts));
    }

    //SCORED
    public void setTScaleSubtractS(View v) {
        if (tScaleScored > 0) {
            tScaleScored -= 1;
            tScaleSDisplay.setText(Integer.toString(tScaleScored));
        }
    }

    public void setTScaleAddS(View v) {
        if (!(tSwitchScored + tScaleScored + tVaultScored >= 60 - (aSwitchScored + aScaleScored))) {

            if (scoredAddsAttempt) {
                tScaleScored += 1;
                tScaleSDisplay.setText(Integer.toString(tScaleScored));
                tScaleAttempts += 1;
                tScaleADisplay.setText(Integer.toString(tScaleAttempts));
            } else if (tScaleScored < tScaleAttempts) {
                tScaleScored += 1;
                tScaleSDisplay.setText(Integer.toString(tScaleScored));
            }
        }
    }

//TELEOP VAULT

    //SCORED
    public void setTVaultSubtractS(View v) {
        if (tVaultScored > 0) {
            tVaultScored -= 1;
            tVaultSDisplay.setText(Integer.toString(tVaultScored));
        }
    }

    public void setTVaultAddS(View v) {
        if (!(tSwitchScored + tScaleScored + tVaultScored >= 60 - (aSwitchScored + aScaleScored))) {

            tVaultScored += 1;
            tVaultSDisplay.setText(Integer.toString(tVaultScored));
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
        aAutoLinePassable = Integer.toString(aAutoLine);

        //ATTEMPTS
        aSwitchAttemptsPassable = Integer.toString(aSwitchAttempts);
        aScaleAttemptsPassable = Integer.toString(aScaleAttempts);
        tSwitchAttemptsPassable = Integer.toString(tSwitchAttempts);
        tScaleAttemptsPassable = Integer.toString(tScaleAttempts);

        //SCORED
        aSwitchScoredPassable = Integer.toString(aSwitchScored);
        aScaleScoredPassable = Integer.toString(aScaleScored);
        tSwitchScoredPassable = Integer.toString(tSwitchScored);
        tScaleScoredPassable = Integer.toString(tScaleScored);
        tVaultScoredPassable = Integer.toString(tVaultScored);


        //ENDGAME
        tParkedPassable = Integer.toString(tParked);
        tElevatedPassable = Integer.toString(tElevated);

        //SAVING PASSABLES
        SharedPreferences.Editor SPMD = matchData.edit();

        //AUTOLINE
        SPMD.putString("aAutoline", aAutoLinePassable);

        //ATTEMPTS
        SPMD.putString("aSwitchAttempts", aSwitchAttemptsPassable);
        SPMD.putString("aScaleAttempts", aScaleAttemptsPassable);
        SPMD.putString("tSwitchAttempts", tSwitchAttemptsPassable);
        SPMD.putString("tScaleAttempts", tScaleAttemptsPassable);

        //SCORED
        SPMD.putString("aSwitchScored", aSwitchScoredPassable);
        SPMD.putString("aScaleScored", aScaleScoredPassable);
        SPMD.putString("tSwitchScored", tSwitchScoredPassable);
        SPMD.putString("tScaleScored", tScaleScoredPassable);
        SPMD.putString("tVaultScored", tVaultScoredPassable);

        //ENDGAME
        SPMD.putString("tParked", tParkedPassable);
        SPMD.putString("tElevated", tElevatedPassable);
        SPMD.commit();

        //NEXT
        Intent goToPostMatch = new Intent(AutonTeleop.this, PostMatch.class);
        startActivity(goToPostMatch);
    }

    public void onPause() {
        super.onPause();
        //AUTOLINE
        aAutoLinePassable = Integer.toString(aAutoLine);

        //ATTEMPTS
        aSwitchAttemptsPassable = Integer.toString(aSwitchAttempts);
        aScaleAttemptsPassable = Integer.toString(aScaleAttempts);
        tSwitchAttemptsPassable = Integer.toString(tSwitchAttempts);
        tScaleAttemptsPassable = Integer.toString(tScaleAttempts);

        //SCORED
        aSwitchScoredPassable = Integer.toString(aSwitchScored);
        aScaleScoredPassable = Integer.toString(aScaleScored);
        tSwitchScoredPassable = Integer.toString(tSwitchScored);
        tScaleScoredPassable = Integer.toString(tScaleScored);
        tVaultScoredPassable = Integer.toString(tVaultScored);


        //ENDGAME
        tParkedPassable = Integer.toString(tParked);
        tElevatedPassable = Integer.toString(tElevated);

        //SAVING PASSABLES
        SharedPreferences.Editor SPMD = matchData.edit();

        //AUTOLINE
        SPMD.putString("aAutoline", aAutoLinePassable);

        //ATTEMPTS
        SPMD.putString("aSwitchAttempts", aSwitchAttemptsPassable);
        SPMD.putString("aScaleAttempts", aScaleAttemptsPassable);
        SPMD.putString("tSwitchAttempts", tSwitchAttemptsPassable);
        SPMD.putString("tScaleAttempts", tScaleAttemptsPassable);

        //SCORED
        SPMD.putString("aSwitchScored", aSwitchScoredPassable);
        SPMD.putString("aScaleScored", aScaleScoredPassable);
        SPMD.putString("tSwitchScored", tSwitchScoredPassable);
        SPMD.putString("tScaleScored", tScaleScoredPassable);
        SPMD.putString("tVaultScored", tVaultScoredPassable);

        //ENDGAME
        SPMD.putString("tParked", tParkedPassable);
        SPMD.putString("tElevated", tElevatedPassable);
        SPMD.commit();

    }

    public void onResume() {
        super.onResume();

        autoline = findViewById(R.id.autoline);

        if(aAutoLine ==0) {
            autoline.setChecked(false);
        } else {
            autoline.setChecked(true);
        }


        aSwitchAttempts = Integer.parseInt(matchData.getString("aSwitchAttempts", "0"));
        aScaleAttempts = Integer.parseInt(matchData.getString("aScaleAttempts", "0"));
        tSwitchAttempts = Integer.parseInt(matchData.getString("tSwitchAttempts", "0"));
        tScaleAttempts = Integer.parseInt(matchData.getString("tScaleAttempts", "0"));

        aSwitchADisplay.setText(Integer.toString(aSwitchAttempts));
        aScaleADisplay.setText(Integer.toString(aScaleAttempts));
        tSwitchADisplay.setText(Integer.toString(tSwitchAttempts));
        tScaleADisplay.setText(Integer.toString(tScaleAttempts));


        aSwitchScored = Integer.parseInt(matchData.getString("aSwitchScored", "0"));
        aScaleScored = Integer.parseInt(matchData.getString("aScaleScored", "0"));
        tSwitchScored = Integer.parseInt(matchData.getString("tSwitchScored", "0"));
        tScaleScored = Integer.parseInt(matchData.getString("tScaleScored", "0"));
        tVaultScored = Integer.parseInt(matchData.getString("tVaultScored", "0"));;

        aSwitchSDisplay.setText(Integer.toString(aSwitchScored));
        aScaleSDisplay.setText(Integer.toString(aScaleScored));
        tSwitchSDisplay.setText(Integer.toString(tSwitchScored));
        tScaleSDisplay.setText(Integer.toString(tScaleScored));
        tVaultSDisplay.setText(Integer.toString(tVaultScored));


        tParked = Integer.parseInt(matchData.getString("tParked", "0")); //binary
        tElevated = Integer.parseInt(matchData.getString("tElevated", "0")); //binary


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



