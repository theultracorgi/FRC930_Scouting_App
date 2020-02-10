package org.team930.bears.wears;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Handler;
import android.view.ContextThemeWrapper;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.ToggleButton;


import static android.app.AlertDialog.THEME_HOLO_LIGHT;

@SuppressWarnings("ALL")
public class PreMatch extends AppCompatActivity {
    EditText teamNum, matchNum;
    ToggleButton allianceColor;
    ImageView map;
    Spinner startPos;

    String matchDataPreferences, otherPreferences;
    String teamNumPassable, matchNumPassable, startPosPassable;
    char alliance;
    Integer greenAlliance;

    SharedPreferences matchData, otherSettings;


    boolean doubleBackToExitPressedOnce = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pre_match);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
/*

        alliance = 'b';
        greenAlliance = 0;
        
        matchDataPreferences = getString(R.string.matchDataPreferences);
        matchData = getSharedPreferences(matchDataPreferences, 0);
*/
        otherPreferences = getString(R.string.otherPreferences);
        otherSettings = getSharedPreferences(otherPreferences, 0);

        SharedPreferences.Editor SPOS = otherSettings.edit();

        SPOS.apply();
/*
        teamNum = findViewById(R.id.teamNum);
        matchNum = findViewById(R.id.matchNum);
        startPos = findViewById(R.id.startingPos);
        allianceColor = findViewById(R.id.allianceToggle);*/
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
/*
    public void setGoToAuton(View v) {

        if ((teamNum.getText().toString()).length() == 0 || (matchNum.getText().toString()).length() == 0) {

            Toast.makeText(this, "Please Complete All Fields", Toast.LENGTH_SHORT).show();


        } else if (Integer.parseInt(teamNum.getText().toString()) == 0 || Integer.parseInt(matchNum.getText().toString()) == 0) {
            Toast.makeText(this, "you gave us values but they suck. try again", Toast.LENGTH_SHORT).show();
        } else {
            if (allianceColor.isChecked()) {
                alliance = 'r';
            } else {
                alliance = 'b';
            }

            teamNumPassable = teamNum.getText().toString();
            matchNumPassable = matchNum.getText().toString() + alliance;
            startPosPassable = Integer.toString(startPos.getSelectedItemPosition() + 1);

            SharedPreferences.Editor SPMD = matchData.edit();
            SPMD.putString("preMatchVals", teamNumPassable + "," + matchNumPassable + "," + startPosPassable + ",");
            SPMD.apply();


            Intent goToAuton = new Intent(PreMatch.this, AutonTeleop.class);
            startActivity(goToAuton);
        }
    }

    public void setAllianceColor(View v) {
        greenAlliance += 1;
        if (greenAlliance >= 50) {
            allianceColor.setBackgroundColor(getResources().getColor(R.color.stillGreen));
        } else {

        }
    }
    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);
    }

    public final static String PACKAGE = "..."; // insert your package name

    private int getDrawable(String name) {
        return getId(name, "drawable");
    }

    private int getId(String name, String type) {
        return getResources().getIdentifier(name, type, PACKAGE);
    }*/
}
