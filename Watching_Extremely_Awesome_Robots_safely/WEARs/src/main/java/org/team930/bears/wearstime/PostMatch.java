package org.team930.bears.wearstime;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextThemeWrapper;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ToggleButton;

import java.util.Locale;

import static android.app.AlertDialog.THEME_HOLO_LIGHT;

@SuppressWarnings("ALL")
public class PostMatch extends AppCompatActivity {
    ToggleButton disabled;
    Button submitData;
    EditText comments;

    Integer mDisabled;
    String matchDataPreferences, otherPreferences, numStoredMatches;
    String scouterIDFinal, teamNumFinal, matchNumFinal, aAutolineFinal, aSwitchAttemptsFinal, aScaleAttemptsFinal, tSwitchAttemptsFinal, tScaleAttemptsFinal, tOSwitchAttemptsFinal, aSwitchScoredFinal, aScaleScoredFinal, tSwitchScoredFinal, tScaleScoredFinal, tOSwitchScoredFinal,  tVaultScoredFinal, tParkedFinal, tElevatedFinal, disabledFinal, commentsFinal, aSwitchAccuracy, aScaleAccuracy, tSwitchAccuracy, tScaleAccuracy, tOSwitchAccuracy;

    AlertDialog.Builder submit, moreComments, backPressed;
    ContextThemeWrapper ctw;
    SharedPreferences matchData, otherSettings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_match);
        setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        ctw = new ContextThemeWrapper(this, THEME_HOLO_LIGHT);
        submit = new AlertDialog.Builder(ctw);
        moreComments = new AlertDialog.Builder(ctw);
        backPressed = new AlertDialog.Builder(ctw);

        numStoredMatches = getString(R.string.numStoredMatches);
        mDisabled = 0;

        matchDataPreferences = getString(R.string.matchDataPreferences);
        matchData = getSharedPreferences(matchDataPreferences, 0);

        otherPreferences = getString(R.string.otherPreferences);
        otherSettings = getSharedPreferences(otherPreferences, 0);

        disabled = findViewById(R.id.disabled);
        comments = findViewById(R.id.comments);
        submitData = findViewById(R.id.submitData);
//TODO do you like this robot? Why in Comments
    }

    public void setDisabled(View v){

        if(disabled.isChecked()){
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

                            String disabledPassable = String.format(Locale.ENGLISH, "%d", mDisabled);
                            String commentsPassable = comments.getText().toString();

                            SPMD.putString("disabled", disabledPassable);
                            SPMD.putString("comments", commentsPassable);
                            SPMD.apply();

                            scouterIDFinal = matchData.getString("scouterID", "Low'a");
                            teamNumFinal = matchData.getString("teamNum", "420");
                            matchNumFinal = matchData.getString("matchNum", "The muthaFLowers D-O double G");

                            aAutolineFinal = matchData.getString("aAutoline", "0");
                            aSwitchAttemptsFinal = matchData.getString("aSwitchAttempts", "0");
                            aScaleAttemptsFinal = matchData.getString("aScaleAttempts", "0");
                            tSwitchAttemptsFinal = matchData.getString("tSwitchAttempts", "0");
                            tScaleAttemptsFinal = matchData.getString("tScaleAttempts", "0");
                            tOSwitchAttemptsFinal = matchData.getString("tOSwitchAttempts", "0");

                            aSwitchScoredFinal = matchData.getString("aSwitchScored", "0");
                            aScaleScoredFinal = matchData.getString("aScaleScored", "0");
                            tSwitchScoredFinal = matchData.getString("tSwitchScored", "0");
                            tScaleScoredFinal = matchData.getString("tScaleScored", "0");
                            tOSwitchScoredFinal = matchData.getString("tOSwitchScored", "0");
                            tVaultScoredFinal = matchData.getString("tVaultScored", "0");


                            if(Integer.parseInt(aSwitchAttemptsFinal) != 0) {
                                aSwitchAccuracy = String.format(Locale.ENGLISH, "%d", Integer.parseInt(aSwitchScoredFinal) / Integer.parseInt(aSwitchAttemptsFinal));
                            } else {
                                aSwitchAccuracy = "N/A";
                            }

                            if(Integer.parseInt(aScaleAttemptsFinal) !=0) {
                                aScaleAccuracy = String.format(Locale.ENGLISH, "%d", Integer.parseInt(aScaleScoredFinal) / Integer.parseInt(aScaleAttemptsFinal));
                            } else {
                                aScaleAccuracy = "N/A";
                            }

                            if(Integer.parseInt(tSwitchAttemptsFinal) != 0) {
                                tSwitchAccuracy = String.format(Locale.ENGLISH, "%d", Integer.parseInt(tSwitchScoredFinal) / Integer.parseInt(tSwitchAttemptsFinal));
                            } else {
                                tSwitchAccuracy = "N/A";
                            }

                            if(Integer.parseInt(tScaleAttemptsFinal) !=0) {
                                tScaleAccuracy = String.format(Locale.ENGLISH, "%d", Integer.parseInt(tScaleScoredFinal) / Integer.parseInt(tScaleAttemptsFinal));
                            } else {
                                tScaleAccuracy = "N/A";
                            }

                            if(Integer.parseInt(tOSwitchAttemptsFinal) != 0) {
                                tOSwitchAccuracy = String.format(Locale.ENGLISH, "%d", Integer.parseInt(tOSwitchScoredFinal) / Integer.parseInt(tOSwitchAttemptsFinal));
                            } else {
                                tOSwitchAccuracy = "N/A";
                            }


                            tParkedFinal = matchData.getString("tParked", "0");
                            tElevatedFinal = matchData.getString("tElevated", "0");

                            disabledFinal = matchData.getString("disabled", "0");
                            commentsFinal = matchData.getString("comments", "you no maka da comments, you suffer da consequence");

                            String fullMatchData = scouterIDFinal + "," + teamNumFinal + "," + matchNumFinal + "," + aAutolineFinal + "," +
                                    aSwitchScoredFinal + "," + aSwitchAccuracy + "," +
                                    aScaleScoredFinal + "," + aScaleAccuracy + "," +
                                    tOSwitchScoredFinal + "," + tOSwitchAccuracy + "," +
                                    tSwitchScoredFinal + "," + tSwitchAccuracy + "," +
                                    tScaleScoredFinal + "," + tScaleAccuracy + "," +
                                    tVaultScoredFinal + "," + tParkedFinal + "," + tElevatedFinal + "," + disabledFinal + "," + commentsFinal + "\n";


                            switch (otherSettings.getInt(numStoredMatches, 5)) {
                                case 0:
                                    SPMD.putString("match1", fullMatchData);
                                    SPMD.apply();
                                    break;
                                case 1:
                                    SPMD.putString("match2", fullMatchData);
                                    SPMD.apply();
                                    break;
                                case 2:
                                    SPMD.putString("match3", fullMatchData);
                                    SPMD.apply();
                                    break;
                                case 3:
                                    SPMD.putString("match4", fullMatchData);
                                    SPMD.apply();
                                    break;
                                case 4:
                                    SPMD.putString("match5", fullMatchData);
                                    SPMD.apply();
                                    break;
                                case 5:
                                    SPMD.putString("match6", fullMatchData);
                                    SPMD.apply();
                                    break;
                                default:
                                    SPMD.putString("match6", fullMatchData);
                                    SPMD.apply();

                            }
                            SharedPreferences.Editor SPOS = otherSettings.edit();

                            SPOS.putInt(numStoredMatches, otherSettings.getInt(numStoredMatches, 5) + 1);
                            SPOS.apply();

                            SPMD.putString("teamNum", "0");
                            SPMD.putString("matchNum", "0");
                            SPMD.putString("aAutoline", "0");

                            SPMD.putString("aSwitchAttempts", "0");
                            SPMD.putString("aScaleAttempts", "0");
                            SPMD.putString("tSwitchAttempts", "0");
                            SPMD.putString("tScaleAttempts", "0");
                            SPMD.putString("tOSwitchAttempts", "0");

                            SPMD.putString("aSwitchScored", "0");
                            SPMD.putString("aScaleScored", "0");
                            SPMD.putString("tSwitchScored", "0");
                            SPMD.putString("tScaleScored", "0");
                            SPMD.putString("tOSwitchScored", "0");
                            SPMD.putString("tVaultScored", "0");

                            SPMD.putString("tParked", "0");
                            SPMD.putString("tElevated", "0");
                            SPMD.putString("disabled", "0");

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
