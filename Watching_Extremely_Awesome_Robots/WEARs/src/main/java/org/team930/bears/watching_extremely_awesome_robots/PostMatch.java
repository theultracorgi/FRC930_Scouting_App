package org.team930.bears.watching_extremely_awesome_robots;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ToggleButton;

import java.util.Set;
import java.util.TreeSet;

public class PostMatch extends AppCompatActivity {
    ToggleButton disabled;
    Button submitData;
    EditText comments;
    Integer mDisabled;
    String matchDataPreferences;
    SharedPreferences matchData;
    String scouterIDFinal, teamNumFinal, matchNumFinal, aAutolineFinal, aSwitchFinal, aScaleFinal, tSwitchFinal, tScaleFinal, tVaultFinal, tParkedFinal, tElevatedFinal, disabledFinal, commentsFinal;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_match);

        matchDataPreferences = getString(R.string.matchDataPreferences);

        matchData = getSharedPreferences(matchDataPreferences, 0);

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

        SharedPreferences.Editor SPE = matchData.edit();

        String disabledPassable = Integer.toString(mDisabled);

        String commentsPassable = comments.getText().toString();

        SPE.putString("disabled", disabledPassable);
        SPE.putString("comments", commentsPassable);
        SPE.commit();

        scouterIDFinal = matchData.getString("scouterID", "te fol skween");
        teamNumFinal = matchData.getString("teamNum", "420");
        matchNumFinal = matchData.getString("matchNum", "snoop DOOOOOOOOGGG");
        aAutolineFinal = matchData.getString("aAutoline", "0");
        aSwitchFinal = matchData.getString("aSwitch", "0");
        aScaleFinal = matchData.getString("aScale", "0");
        tSwitchFinal = matchData.getString("tSwitch", "0");
        tScaleFinal = matchData.getString("tScale", "0");
        tVaultFinal = matchData.getString("tVault", "0");
        tParkedFinal = matchData.getString("tParked", "0");
        tElevatedFinal = matchData.getString("tElevated", "0");
        disabledFinal = matchData.getString("disabled", "0");
        commentsFinal = matchData.getString("comments", "Somebody once told me the world is gonna roll me I ain't the sharpest tool in the shed She was looking kind of dumb with her finger and her thumb In the shape of an L on her forehead\n");


        Intent submitData = new Intent(this, Home_Screen.class);
        startActivity(submitData);
    }


}
