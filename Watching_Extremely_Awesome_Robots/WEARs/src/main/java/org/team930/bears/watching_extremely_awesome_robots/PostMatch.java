package org.team930.bears.watching_extremely_awesome_robots;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ToggleButton;

public class PostMatch extends AppCompatActivity {
    ToggleButton disabled;
    Button submitData;
    EditText comments;
    Integer mDisabled;
    String matchDataPreferences, otherPreferences, numStoredMatches;
    SharedPreferences matchData, otherSettings;
    String scouterIDFinal, teamNumFinal, matchNumFinal, aAutolineFinal, aSwitchFinal, aScaleFinal, tSwitchFinal, tScaleFinal, tVaultFinal, tParkedFinal, tElevatedFinal, disabledFinal, commentsFinal;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_match);

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

        SharedPreferences.Editor SPMD = matchData.edit();

        String disabledPassable = Integer.toString(mDisabled);
        String commentsPassable = comments.getText().toString();

        SPMD.putString("disabled", disabledPassable);
        SPMD.putString("comments", commentsPassable);
        SPMD.commit();

            scouterIDFinal = matchData.getString("scouterID,", "te fol skween,");
            teamNumFinal = matchData.getString("teamNum,", "420,");
            matchNumFinal = matchData.getString("matchNum,", "snoop DOOOOOOOOGGG,");
            aAutolineFinal = matchData.getString("aAutoline,", "0,");
            aSwitchFinal = matchData.getString("aSwitch,", "0,");
            aScaleFinal = matchData.getString("aScale,", "0,");
            tSwitchFinal = matchData.getString("tSwitch,", "0,");
            tScaleFinal = matchData.getString("tScale,", "0,");
            tVaultFinal = matchData.getString("tVault,", "0,");
            tParkedFinal = matchData.getString("tParked,", "0,");
            tElevatedFinal = matchData.getString("tElevated,", "0,");
            disabledFinal = matchData.getString("disabled,", "0,");
            commentsFinal = matchData.getString("comments,", "you no maka da comments, or you suffer da consequence\n");

        String fullMatchData = scouterIDFinal + teamNumFinal + matchNumFinal + aAutolineFinal + aSwitchFinal + aScaleFinal +
                tSwitchFinal + tScaleFinal + tVaultFinal + tParkedFinal + tElevatedFinal + disabledFinal + commentsFinal  + "\n";

        SharedPreferences.Editor SPOS = otherSettings.edit();

        switch(otherSettings.getInt(numStoredMatches, 5)){
            case 0: SPOS.putString("match1", fullMatchData);
                    SPOS.commit();
                break;
            case 1: SPOS.putString("match2", fullMatchData);
                SPOS.commit();
                break;
            case 2: SPOS.putString("match3", fullMatchData);
                SPOS.commit();
                break;
            case 3: SPOS.putString("match4", fullMatchData);
                SPOS.commit();
                break;
            case 4: SPOS.putString("match5", fullMatchData);
                SPOS.commit();
                break;
            case 5: SPOS.putString("match6", fullMatchData);
                SPOS.commit();
                break;
            default:  SPOS.putString("match6", fullMatchData);
                SPOS.commit();

        }
        SPOS.putInt(numStoredMatches, otherSettings.getInt(numStoredMatches, 5) + 1);
        SPOS.commit();


                Intent submitData = new Intent(this, HomeScreen.class);
        startActivity(submitData);
    }


}
