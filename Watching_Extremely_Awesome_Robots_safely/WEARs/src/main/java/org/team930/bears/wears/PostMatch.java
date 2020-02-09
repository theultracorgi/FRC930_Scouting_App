package org.team930.bears.wears;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextThemeWrapper;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;
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

    Integer mDisabled, defenseProgress;
    String matchDataPreferences, otherPreferences, numStoredMatches, fullMatchData;
    String disabledPassable, defensePassable, commentsPassable;

    AlertDialog.Builder submit, moreComments, backPressed;
    ContextThemeWrapper ctw;
    SharedPreferences matchData, otherSettings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_match);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        ctw = new ContextThemeWrapper(this, THEME_HOLO_LIGHT);
        submit = new AlertDialog.Builder(ctw);
        moreComments = new AlertDialog.Builder(ctw);
        backPressed = new AlertDialog.Builder(ctw);

        numStoredMatches = getString(R.string.numStoredMatches);
        mDisabled = 0;

        matchDataPreferences = getString(R.string.matchDataPreferences);
        matchData = getSharedPreferences(matchDataPreferences, 0);
        defenseProgress = 0;

        otherPreferences = getString(R.string.otherPreferences);
        otherSettings = getSharedPreferences(otherPreferences, 0);

        disabled = findViewById(R.id.disabled);
        comments = findViewById(R.id.comments);
        submitData = findViewById(R.id.submitData);
        defense = findViewById(R.id.defense);
        dProgress = findViewById(R.id.progress);

        defense.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                int val = (progress * (seekBar.getWidth() - 2 * seekBar.getThumbOffset())) / seekBar.getMax();
                dProgress.setText("" + progress);
                dProgress.setX(seekBar.getX() + val + seekBar.getThumbOffset() / 2);

                defenseProgress = defense.getProgress();

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
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

            moreComments.setTitle("Make more comments");
            moreComments.setCancelable(true);
            moreComments.setNeutralButton(
                    "OK",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });
            AlertDialog alert = moreComments.create();
            alert.show();


        } else {

            submit.setTitle("Submit Match Data");
            submit.setMessage("Are you sure you want to submit this data?");
            submit.setCancelable(true);

            submit.setPositiveButton(
                    "Yes",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();


                            SharedPreferences.Editor SPMD = matchData.edit();
                            SharedPreferences.Editor SPOS = otherSettings.edit();

                            disabledPassable = String.format(Locale.ENGLISH, "%d", mDisabled);
                            defensePassable = String.format(Locale.ENGLISH, "%d", defenseProgress);
                            commentsPassable = comments.getText().toString();

                            SPMD.putString("disabled", disabledPassable);
                            SPMD.putString("defense", defensePassable);
                            SPMD.putString("comments", commentsPassable);
                            SPMD.putString("postMatchVals", disabledPassable + "," + defensePassable + "," + commentsPassable);
                            SPMD.apply();

                            fullMatchData = matchData.getString("preMatchVals", "") + matchData.getString("autonTeleopVals", "") + matchData.getString("postMatchVals", "") + "\n";


                            SPOS.putInt(numStoredMatches, otherSettings.getInt(numStoredMatches, 5) + 1);
                            SPOS.apply();

                            if (otherSettings.getInt(numStoredMatches, 6) % 2 == 0) {
                                SPMD.putString("secondQR", matchData.getString("secondQR", "") + fullMatchData);
                            } else {
                                SPMD.putString("firstQR", matchData.getString("firstQR", "") + fullMatchData);
                            }
                            SPOS.apply();


                            SPMD.putString("teamNum", "0");
                            SPMD.putString("matchNum", "0");
                            SPMD.putString("startPos", "1");
                            SPMD.putString("sHabLine", "0");

                            SPMD.putString("sCgL", "0");
                            SPMD.putString("sHtL", "0");
                            SPMD.putString("sHtM", "0");
                            SPMD.putString("sHtH", "0");

                            SPMD.putString("tCgL", "0");
                            SPMD.putString("tCgM", "0");
                            SPMD.putString("tCgH", "0");
                            SPMD.putString("tHtL", "0");
                            SPMD.putString("tHtM", "0");
                            SPMD.putString("tHtH", "0");

                            //ENDGAME
                            SPMD.putString("habStatus", "0");
                            SPMD.putString("disabled", "0");
                            SPMD.putString("defense", "0");
                            SPMD.putString("comments", "0");

                            SPMD.apply();

                            Intent submitData = new Intent(PostMatch.this, HomeScreen.class);
                            startActivity(submitData);

                        }
                    });

            submit.setNegativeButton(
                    "No",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });

            AlertDialog alert = submit.create();
            alert.show();
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            backPressed.setTitle("Go Back");
            backPressed.setMessage("Are you sure you want to go back? All data on this form will be lost.");
            backPressed.setCancelable(true);

            backPressed.setPositiveButton(
                    "Yes",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                            Intent home = new Intent(PostMatch.this, AutonTeleop.class);
                            startActivity(home);
                        }
                    });

            backPressed.setNegativeButton(
                    "No",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();

                        }
                    });

            AlertDialog alert = backPressed.create();
            alert.show();
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }
}
