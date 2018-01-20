package org.team930.bears.watching_extremely_awesome_robots;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.preference.PreferenceManager;
import android.support.annotation.ColorInt;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.ToggleButton;
import android.support.v7.widget.Toolbar;

public class PreMatch extends AppCompatActivity {
    EditText teamNum, matchNum;
    ToggleButton allianceColor;
    String stillPreferences, matchDataPreferences;
    char alliance;
    Integer stillCount;
    SharedPreferences matchData, stillEnabled;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pre_match);

        stillPreferences = getString(R.string.stillPreferences);
        matchDataPreferences = getString(R.string.matchDataPreferences);

        matchData = getSharedPreferences(matchDataPreferences, 0);
        stillEnabled = getSharedPreferences(stillPreferences, 0);

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

        String teamNumPassable = teamNum.getText().toString();
        String matchNumPassable = matchNum.getText().toString() + alliance;

        SharedPreferences.Editor SPE = matchData.edit();

        SPE.putString("teamNum", teamNumPassable);
        SPE.putString("matchNum", matchNumPassable);
        SPE.commit();


        Intent goToAuton = new Intent(PreMatch.this, AutonTeleop.class);
        startActivity(goToAuton);
    }

    public void setAllianceColor(View v) {
        stillCount += 1;
        if (stillCount == 50) {
            allianceColor.setBackgroundColor(getResources().getColor(R.color.stillGreen));

            Toast.makeText(getApplicationContext(), "STILL Unlocked", Toast.LENGTH_LONG).show();


            SharedPreferences.Editor SPE = stillEnabled.edit();

            SPE.putBoolean("stillEnabled", true);
            SPE.commit();
        } else {

        }
    }

}

