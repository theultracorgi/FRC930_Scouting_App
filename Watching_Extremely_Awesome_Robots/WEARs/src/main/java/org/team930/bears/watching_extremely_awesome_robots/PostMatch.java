package org.team930.bears.watching_extremely_awesome_robots;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextThemeWrapper;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ToggleButton;

import static android.app.AlertDialog.THEME_HOLO_LIGHT;

public class PostMatch extends AppCompatActivity {
    ToggleButton disabled;
    Button submitData;
    EditText comments;

    Integer mDisabled;
    String matchDataPreferences, otherPreferences, numStoredMatches;
    String scouterIDFinal, teamNumFinal, matchNumFinal, aAutolineFinal, aSwitchFinal, aScaleFinal, tSwitchFinal, tScaleFinal, tVaultFinal, tParkedFinal, tElevatedFinal, disabledFinal, commentsFinal;

    AlertDialog.Builder submit, moreComments;
    ContextThemeWrapper ctw;
    SharedPreferences matchData, otherSettings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_match);

        ctw = new ContextThemeWrapper(this, THEME_HOLO_LIGHT);
        submit = new AlertDialog.Builder(ctw);
        moreComments = new AlertDialog.Builder(ctw);

        numStoredMatches = getString(R.string.numStoredMatches);
        mDisabled = 0;

        matchDataPreferences = getString(R.string.matchDataPreferences);
        matchData = getSharedPreferences(matchDataPreferences, 0);

        otherPreferences = getString(R.string.otherPreferences);
        otherSettings = getSharedPreferences(otherPreferences, 0);

        disabled = findViewById(R.id.disabled);
        comments = findViewById(R.id.comments);
        submitData = findViewById(R.id.submitData);

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

                            String disabledPassable = Integer.toString(mDisabled) + ",";
                            String commentsPassable = comments.getText().toString() + ",";

                            SPMD.putString("disabled", disabledPassable);
                            SPMD.putString("comments", commentsPassable);
                            SPMD.commit();

                            scouterIDFinal = matchData.getString("scouterID", "Low'a,");
                            teamNumFinal = matchData.getString("teamNum", "420,");
                            matchNumFinal = matchData.getString("matchNum", "snoop DOOOOOOOOGGG,");
                            aAutolineFinal = matchData.getString("aAutoline", "0,");
                            aSwitchFinal = matchData.getString("aSwitch", "0,");
                            aScaleFinal = matchData.getString("aScale", "0,");
                            tSwitchFinal = matchData.getString("tSwitch", "0,");
                            tScaleFinal = matchData.getString("tScale", "0,");
                            tVaultFinal = matchData.getString("tVault", "0,");
                            tParkedFinal = matchData.getString("tParked", "0,");
                            tElevatedFinal = matchData.getString("tElevated", "0,");
                            disabledFinal = matchData.getString("disabled", "0,");
                            commentsFinal = matchData.getString("comments", "you no maka da comments, you suffer da consequence\n");

                            String fullMatchData = scouterIDFinal + teamNumFinal + matchNumFinal + aAutolineFinal + aSwitchFinal + aScaleFinal +
                                    tSwitchFinal + tScaleFinal + tVaultFinal + tParkedFinal + tElevatedFinal + disabledFinal + commentsFinal + "\n";


                            switch (otherSettings.getInt(numStoredMatches, 5)) {
                                case 0:
                                    SPMD.putString("match1", fullMatchData);
                                    SPMD.commit();
                                    break;
                                case 1:
                                    SPMD.putString("match2", fullMatchData);
                                    SPMD.commit();
                                    break;
                                case 2:
                                    SPMD.putString("match3", fullMatchData);
                                    SPMD.commit();
                                    break;
                                case 3:
                                    SPMD.putString("match4", fullMatchData);
                                    SPMD.commit();
                                    break;
                                case 4:
                                    SPMD.putString("match5", fullMatchData);
                                    SPMD.commit();
                                    break;
                                case 5:
                                    SPMD.putString("match6", fullMatchData);
                                    SPMD.commit();
                                    break;
                                default:
                                    SPMD.putString("match6", fullMatchData);
                                    SPMD.commit();

                            }
                            SharedPreferences.Editor SPOS = otherSettings.edit();

                            SPOS.putInt(numStoredMatches, otherSettings.getInt(numStoredMatches, 5) + 1);
                            SPOS.commit();


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


}
