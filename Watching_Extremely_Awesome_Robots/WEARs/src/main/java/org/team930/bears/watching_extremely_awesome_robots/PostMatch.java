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
    SharedPreferences matchData, otherSettings;
    String scouterIDFinal, teamNumFinal, matchNumFinal, aAutolineFinal, aSwitchFinal, aScaleFinal, tSwitchFinal, tScaleFinal, tVaultFinal, tParkedFinal, tElevatedFinal, disabledFinal, commentsFinal;
    AlertDialog.Builder builder;
    ContextThemeWrapper ctw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_match);

        ctw = new ContextThemeWrapper(this, THEME_HOLO_LIGHT);
        builder = new AlertDialog.Builder(ctw);

        matchDataPreferences = getString(R.string.matchDataPreferences);
        otherPreferences = getString(R.string.otherPreferences);
        numStoredMatches = getString(R.string.numStoredMatches);

        matchData = getSharedPreferences(matchDataPreferences, 0);
        otherSettings = getSharedPreferences(otherPreferences, 0);

        mDisabled = 0;

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
        builder.setTitle("Submit Match Data");
        builder.setMessage("Are you sure you want to submit this data?");
        builder.setCancelable(true);

        builder.setPositiveButton(
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
                                tSwitchFinal + tScaleFinal + tVaultFinal + tParkedFinal + tElevatedFinal + disabledFinal + commentsFinal  + "\n";



                        switch(otherSettings.getInt(numStoredMatches, 5)){
                            case 0: SPMD.putString("match1", fullMatchData);
                                SPMD.commit();
                                break;
                            case 1: SPMD.putString("match2", fullMatchData);
                                SPMD.commit();
                                break;
                            case 2: SPMD.putString("match3", fullMatchData);
                                SPMD.commit();
                                break;
                            case 3: SPMD.putString("match4", fullMatchData);
                                SPMD.commit();
                                break;
                            case 4: SPMD.putString("match5", fullMatchData);
                                SPMD.commit();
                                break;
                            case 5: SPMD.putString("match6", fullMatchData);
                                SPMD.commit();
                                break;
                            default:  SPMD.putString("match6", fullMatchData);
                                SPMD.commit();

                        }
                        SharedPreferences.Editor SPOS = otherSettings.edit();

                        SPOS.putBoolean("dataAvailable", true);
                        SPOS.putInt(numStoredMatches, otherSettings.getInt(numStoredMatches, 5) + 1);
                        SPOS.commit();


                        Intent submitData = new Intent(PostMatch.this, HomeScreen.class);
                        startActivity(submitData);

                    }
                });

        builder.setNegativeButton(
                "No",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        AlertDialog alert = builder.create();
        alert.show();

    }


}
