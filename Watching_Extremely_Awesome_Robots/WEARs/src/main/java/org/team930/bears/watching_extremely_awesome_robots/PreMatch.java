package org.team930.bears.watching_extremely_awesome_robots;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.ToggleButton;

public class PreMatch extends AppCompatActivity {
    EditText teamNum, matchNum;
    ToggleButton allianceColor;
    String stillPreferences, matchDataPreferences, otherPreferences;
    char alliance;
    Integer stillCount;
    SharedPreferences matchData, stillEnabled, otherSettings;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pre_match);

        stillPreferences = getString(R.string.stillPreferences);
        matchDataPreferences = getString(R.string.matchDataPreferences);
        otherPreferences = getString(R.string.otherPreferences);
        alliance = 'b';

        stillEnabled = getSharedPreferences(stillPreferences, 0);
        matchData = getSharedPreferences(matchDataPreferences, 0);
        otherSettings = getSharedPreferences(otherPreferences, 0);

        SharedPreferences.Editor SPOS = otherSettings.edit();

        SPOS.putBoolean("firstOpen", false);

        SPOS.commit();

        stillCount = 0;

        teamNum = findViewById(R.id.teamNum);
        matchNum = findViewById(R.id.matchNum);

        allianceColor = findViewById(R.id.allianceToggle);

    }

    public void setGoToAuton(View v) {
        if (allianceColor.isChecked()) {
            alliance = 'r';
        } else {
            alliance = 'b';
        }

        String teamNumPassable = teamNum.getText().toString()  + ",";
        String matchNumPassable = matchNum.getText().toString() + alliance  + ",";

        SharedPreferences.Editor SPMD = matchData.edit();

        SPMD.putString("teamNum", teamNumPassable);
        SPMD.putString("matchNum", matchNumPassable);
        SPMD.commit();


        Intent goToAuton = new Intent(PreMatch.this, AutonTeleop.class);
        startActivity(goToAuton);
    }

    public void setAllianceColor(View v) {
        stillCount += 1;
        if (stillCount == 50) {
            allianceColor.setBackgroundColor(getResources().getColor(R.color.stillGreen));

            Toast.makeText(getApplicationContext(), "STILL Unlocked", Toast.LENGTH_LONG).show();


            SharedPreferences.Editor SPSE = stillEnabled.edit();

            SPSE.putBoolean("stillEnabled", true);
            SPSE.commit();
        } else {

        }
    }

}

