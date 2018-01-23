package org.team930.bears.watching_extremely_awesome_robots;

import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.ToggleButton;

public class Settings extends AppCompatActivity {

    Spinner scouterList;
    LinearLayout stillEnabler, deleteBar;
    ToggleButton stillFRC;
    Button deleteData;

    String stillPreferences, matchDataPreferences, otherPreferences, numMatchesStored;

    SharedPreferences matchData, stillEnabled, otherSettings;
    MediaPlayer Still;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        matchDataPreferences = getString(R.string.matchDataPreferences);
        stillPreferences = getString(R.string.stillPreferences);
        otherPreferences = getString(R.string.otherPreferences);
        numMatchesStored = getString(R.string.numStoredMatches);

        matchData = getSharedPreferences(matchDataPreferences, 0);
        stillEnabled = getSharedPreferences(stillPreferences, 0);
        otherSettings = getSharedPreferences(otherPreferences, 0);

        Still = MediaPlayer.create(this, R.raw.still_frc_mixdown);
        Still.setLooping(true);

        stillEnabler = findViewById(R.id.stillEnabler);
        stillFRC = findViewById(R.id.stillFRC);
        scouterList = findViewById(R.id.scouterList);
        deleteData = findViewById(R.id.deleteData);
        deleteBar = findViewById(R.id.deleteBar);

        if (stillEnabled.getBoolean("stillEnabled", false)) {
            stillEnabler.setVisibility(View.VISIBLE);
        }
        if (otherSettings.getBoolean("deleteData", false)) {
            deleteBar.setVisibility(View.VISIBLE);
        }

    }

    public void setStillFRC(View v) {

        if (stillFRC.isChecked()) {
            Still.start();

        } else {
            Still.pause();

        }
    }

    public void setDeleteData(View v) {

        SharedPreferences.Editor SPOS = otherSettings.edit();

        SPOS.putBoolean("deleteData", false);
        SPOS.putInt(numMatchesStored, 0);
        SPOS.commit();


    }

    protected void onStop() {
        // call the superclass method first
        super.onStop();


        String selectedScouterPassable = scouterList.getSelectedItem().toString();

        SharedPreferences.Editor SPMD = matchData.edit();
        SPMD.putString("scouterID", selectedScouterPassable);
        SPMD.commit();

        SharedPreferences.Editor SPOS = otherSettings.edit();

        SPOS.putInt(numMatchesStored, 0);

        SPOS.commit();


    }

}


