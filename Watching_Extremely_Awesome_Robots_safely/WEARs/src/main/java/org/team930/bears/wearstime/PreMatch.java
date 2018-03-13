package org.team930.bears.wearstime;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.ContextThemeWrapper;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.ToggleButton;

import org.team930.bears.wearstime.R;

import static android.app.AlertDialog.THEME_HOLO_LIGHT;

@SuppressWarnings("ALL")
public class PreMatch extends AppCompatActivity {
    EditText teamNum, matchNum;
    ToggleButton allianceColor;

    String stillPreferences, matchDataPreferences, otherPreferences;
    char alliance;
    Integer stillCount;

    SharedPreferences matchData, stillEnabled, otherSettings;
    AlertDialog.Builder builder;
    ContextThemeWrapper ctw;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pre_match);
        setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        ctw = new ContextThemeWrapper(this, THEME_HOLO_LIGHT);
        builder = new AlertDialog.Builder(ctw);



        alliance = 'b';
        stillCount = 0;


        stillPreferences = getString(R.string.stillPreferences);
        stillEnabled = getSharedPreferences(stillPreferences, 0);

        matchDataPreferences = getString(R.string.matchDataPreferences);
        matchData = getSharedPreferences(matchDataPreferences, 0);

        otherPreferences = getString(R.string.otherPreferences);
        otherSettings = getSharedPreferences(otherPreferences, 0);

        SharedPreferences.Editor SPOS = otherSettings.edit();

        SPOS.putBoolean("firstOpen", false);


        SPOS.apply();

        teamNum = findViewById(R.id.teamNum);
        matchNum = findViewById(R.id.matchNum);
        allianceColor = findViewById(R.id.allianceToggle);

    }

    public void setGoToAuton(View v) {

        if((teamNum.getText().toString()).length() ==0 || (matchNum.getText().toString()).length() ==0 ) {

            builder.setTitle("Fill out all Fields");
            builder.setCancelable(true);
            builder.setNeutralButton(
                    "OK",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });
            AlertDialog alert = builder.create();
            alert.show();


        } else if(Integer.parseInt(teamNum.getText().toString()) ==0 || Integer.parseInt(matchNum.getText().toString()) ==0) {
            builder.setTitle("Give Us Valid Values");
            builder.setCancelable(true);
            builder.setNeutralButton(
                    "OK",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });
            AlertDialog alert = builder.create();
            alert.show();
        }else {
            if (allianceColor.isChecked()) {
                alliance = 'r';
            } else {
                alliance = 'b';
            }

            String teamNumPassable = teamNum.getText().toString();
            String matchNumPassable = matchNum.getText().toString() + alliance;

            SharedPreferences.Editor SPMD = matchData.edit();

            SPMD.putString("teamNum", teamNumPassable);
            SPMD.putString("matchNum", matchNumPassable);
            SPMD.apply();


            Intent goToAuton = new Intent(PreMatch.this, AutonTeleop.class);
            startActivity(goToAuton);
        }
    }

    public void setAllianceColor(View v) {
        stillCount += 1;
        if (stillCount == 50) {
            allianceColor.setBackgroundColor(getResources().getColor(R.color.stillGreen));
            Toast.makeText(getApplicationContext(), "Not Dis Way!", Toast.LENGTH_LONG).show();


            SharedPreferences.Editor SPSE = stillEnabled.edit();

            SPSE.putBoolean("stillEnabled", true);
            SPSE.apply();
        } else {

        }
    }

}

