package org.team930.bears.watching_extremely_awesome_robots;

import android.content.SharedPreferences;

import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.ToggleButton;

public class Settings extends AppCompatActivity {

    Spinner scouterList;
    LinearLayout stillEnabler;
    ToggleButton stillFRC;

    String stillPreferences, matchDataPreferences;

    SharedPreferences matchData, stillEnabled;
    MediaPlayer Still;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        matchDataPreferences = getString(R.string.matchDataPreferences);
        stillPreferences = getString(R.string.stillPreferences);

        matchData = getSharedPreferences(matchDataPreferences, 0);
        stillEnabled = getSharedPreferences(stillPreferences, 0);

        Still = MediaPlayer.create(this, R.raw.still_frc_mixdown);
        Still.setLooping(true);

        stillEnabler = findViewById(R.id.stillEnabler);
        stillFRC = findViewById(R.id.stillFRC);
        scouterList = findViewById(R.id.scouterList);

        if (stillEnabled.getBoolean("stillEnabled", false)) {

            stillEnabler.setVisibility(View.VISIBLE);
        }

    }

    public void setStillFRC(View v) {

        if (stillFRC.isChecked()) {
            Still.start();

        } else {
            Still.pause();

        }
    }

    protected void onStop() {
        // call the superclass method first
        super.onStop();


        String selectedScouterPassable = scouterList.getSelectedItem().toString();

        SharedPreferences.Editor SPMD = matchData.edit();
        SPMD.putString("scouterID", selectedScouterPassable);
        SPMD.commit();


    }

}


