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
    ToggleButton disabled;
    Button submitData;
    EditText comments;
    SeekBar defense;
    TextView dProgress;

    boolean doubleBackToExitPressedOnce = false;
    Integer mDisabled, defenseProgress;
    String matchDataPreferences, otherPreferences, numStoredMatches, fullMatchData;
    String disabledPassable, defensePassable, commentsPassable;

    SharedPreferences matchData, otherSettings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_match);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);


        numStoredMatches = getString(R.string.numStoredMatches);
        mDisabled = 0;

        matchDataPreferences = getString(R.string.matchDataPreferences);
        matchData = getSharedPreferences(matchDataPreferences, 0);
        defenseProgress = 0;

        otherPreferences = getString(R.string.otherPreferences);
        otherSettings = getSharedPreferences(otherPreferences, 0);
/*
        disabled = findViewById(R.id.disabled);
        comments = findViewById(R.id.comments);
        submitData = findViewById(R.id.submitData);
        defense = findViewById(R.id.defense);
        dProgress = findViewById(R.id.progress);
*/

//TODO do you like this robot? Why in Comments
    }

    public void setDisabled(View v) {

        if (disabled.isChecked()) {
            mDisabled = 1;
        } else {
            mDisabled = 0;
        }
    }

    public void setSubmitData(View v) {

        if ((comments.getText().toString()).length() <= 10 || !comments.getText().toString().contains(" ")) {

            Toast.makeText(this, "cool kids make more comments", Toast.LENGTH_SHORT).show();


        } else {


            SharedPreferences.Editor SPMD = matchData.edit();
            SharedPreferences.Editor SPOS = otherSettings.edit();

            SPMD.putString("postMatchVals", disabledPassable + "," + defensePassable + "," + commentsPassable);
            SPMD.apply();

            fullMatchData = matchData.getString("preMatchVals", "") + matchData.getString("autonTeleopVals", "") + matchData.getString("postMatchVals", "") + "\n";


            SPOS.putInt(numStoredMatches, otherSettings.getInt(numStoredMatches, 3) + 1);
            SPOS.apply();

            if (otherSettings.getInt(numStoredMatches, 4) % 2 == 0) {
                SPMD.putString("secondQR", matchData.getString("secondQR", "") + fullMatchData);
            } else {
                SPMD.putString("firstQR", matchData.getString("firstQR", "") + fullMatchData);
            }
            SPOS.apply();

            Intent submitData = new Intent(PostMatch.this, HomeScreen.class);
            startActivity(submitData);

        }


    }
    long prevTime = 0;
    @Override
    public void onBackPressed() {

        long thisTime = Calendar.getInstance().getTimeInMillis();
        if ((thisTime - prevTime) <= 1000) {//1 SEC

            super.onBackPressed();
        } else {
            Toast.makeText(this, "Press Again to Go Back", Toast.LENGTH_LONG).show();
            prevTime = thisTime;
        }

    }
}
