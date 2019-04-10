package org.team930.bears.wears;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.ContextThemeWrapper;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.ToggleButton;

import java.util.Locale;

import static android.app.AlertDialog.THEME_HOLO_LIGHT;

@SuppressWarnings("ALL")
public class AutonTeleop extends AppCompatActivity {
    Button goToPostMatch;
    ToggleButton habLine;
    RadioButton onField, lvlOne, lvlTwo, lvlThree;
    TextView sCgLDs, sHtLDs, sHtMDs, sHtHDs, tCgLDs, tCgMDs, tCgHDs, tHtLDs, tHtMDs, tHtHDs,

    Integer sHabLine, sCgL, sHtL, sHtM, sHtH, tCgL, tCgM, tCgH, tHtL, tHtM, tHtH, habStatus;
    String matchDataPreferences, otherPreferences;
    String sHabLinePass, sCgLPass, sHtLPass, sHtMPass, sHtHPass, tCgLPass, tCgMPass, tCgHPass, tHtLPass, tHtMPass, tHtHPass, habStatusPass;
    Boolean attemptsOutput;


    SharedPreferences matchData, otherSettings;
    MediaPlayer Still;
    AlertDialog.Builder backPressed;
    ContextThemeWrapper ctw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_autonteleop);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        //POPUPS
        ctw = new ContextThemeWrapper(this, THEME_HOLO_LIGHT);
        backPressed = new AlertDialog.Builder(ctw);

        //OHNINE
        Still = MediaPlayer.create(this, R.raw.ohnine_playa);

        attemptsOutput = false;


        //SHARED PREFERENCES
        matchDataPreferences = getString(R.string.matchDataPreferences);
        otherPreferences = getString(R.string.otherPreferences);

        matchData = getSharedPreferences(matchDataPreferences, 0);
        otherSettings = getSharedPreferences(otherPreferences, 0);

        //BINARIES
        sHabLine = Integer.parseInt(matchData.getString("sHabline", "0")); //binary

        //VIEWS
        habLine = findViewById(R.id.habLine);

        if (sHabLine == 0) {
            habLine.setChecked(false);
        } else {
            habLine.setChecked(true);
        }
//SANDSTORM SETTERS
        //COUNTS
        sCgL = Integer.parseInt(matchData.getString("sCgL", "0"));
        sHtL = Integer.parseInt(matchData.getString("sHtL", "0"));
        sHtM = Integer.parseInt(matchData.getString("sHtM", "0"));
        sHtH = Integer.parseInt(matchData.getString("sHtH", "0"));


        //VIEWS
        sCgLDs = findViewById(R.id.sCgLDs);
        sHtLDs = findViewById(R.id.sHtLDs);
        sHtMDs = findViewById(R.id.sHtMDs);
        sHtHDs = findViewById(R.id.sHtHDs);

        sCgLDs.setText(String.format(Locale.ENGLISH, "%d", sCgL));
        sHtLDs.setText(String.format(Locale.ENGLISH, "%d", sHtL));
        sHtMDs.setText(String.format(Locale.ENGLISH, "%d", sHtM));
        sHtHDs.setText(String.format(Locale.ENGLISH, "%d", sHtH));

        //TELEOP SETTERS

        //COUNTS
        tCgL = Integer.parseInt(matchData.getString("tCgL", "0"));
        tCgM = Integer.parseInt(matchData.getString("tCgM", "0"));
        tCgH = Integer.parseInt(matchData.getString("tCGH", "0"));
        tHtL = Integer.parseInt(matchData.getString("tHtL", "0"));
        tHtM = Integer.parseInt(matchData.getString("tHtM", "0"));
        tHtH = Integer.parseInt(matchData.getString("tHtH", "0"));

        //VIEWS
        tCgLDs = findViewById(R.id.tCgLDs);
        tCgMDs = findViewById(R.id.tCgMDs);
        tCgHDs = findViewById(R.id.tCgHDs);
        tHtLDs = findViewById(R.id.tHtLDs);
        tHtMDs = findViewById(R.id.tHtMDs);
        tHtHDs = findViewById(R.id.tHtHDs);

        tCgLDs.setText(String.format(Locale.ENGLISH, "%d", tCgL));
        tCgMDs.setText(String.format(Locale.ENGLISH, "%d", tCgM));
        tCgHDs.setText(String.format(Locale.ENGLISH, "%d",  tCgH));
        tHtLDs.setText(String.format(Locale.ENGLISH, "%d", tHtL));
        tHtMDs.setText(String.format(Locale.ENGLISH, "%d", tHtM));
        tHtHDs.setText(String.format(Locale.ENGLISH, "%d", tHtH));

        //ENDGAME

        //BINARIES
        habStatus = Integer.parseInt(matchData.getString("tParked", "0")); //binary

        //VIEWS
        onField = findViewById(R.id.onField);
        lvlOne = findViewById(R.id.lvlOne);
        lvlTwo = findViewById(R.id.lvlTwo);
        lvlThree = findViewById(R.id.lvlThree);

        if (habStatus == 0) {
            onField.setChecked(true);
            lvlOne.setChecked(false);
            lvlTwo.setChecked(false);
            lvlThree.setChecked(false);
        } else if (habStatus == 1) {
            onField.setChecked(false);
            lvlOne.setChecked(true);
            lvlTwo.setChecked(false);
            lvlThree.setChecked(false);
        } else if (habStatus == 2) {
            onField.setChecked(false);
            lvlOne.setChecked(false);
            lvlTwo.setChecked(true);
            lvlThree.setChecked(false);
        } else {
            onField.setChecked(false);
            lvlOne.setChecked(false);
            lvlTwo.setChecked(false);
            lvlThree.setChecked(true);
        }

        //NEXT
        goToPostMatch = findViewById(R.id.gotoPostMatch);


    }


    //SANDSTORM HABLINE
    public void setHabLine(View v) {

        if (habLine.isChecked()) {
            sHabLine = 1;

        } else {
            sHabLine = 0;
        }
    }

    //SANDSTORM CARGO SHIP
    //Hatches
    public void setSCsHtSub(View v) {
        if (sCsHt > 0) {
            sCsHt -= 1;
            sCsHtDs.setText(String.format(Locale.ENGLISH, "%d", sCsHt));
        }
    }

    public void setSCsHtAdd(View v) {

        if (sCsHt + sRtHt <= 4) {
            sCsHt += 1;
            sCsHtDs.setText(String.format(Locale.ENGLISH, "%d", sCsHt));
        }
    }

    //Cargo
    public void setSCsCgSub(View v) {

        if (sCsCg > 0) {
            sCsCg -= 1;
            sCsCgDs.setText(String.format(Locale.ENGLISH, "%d", sCsCg));
        }
    }

    public void setSCsCgAdd(View v) {
        sCsCg += 1;
        sCsCgDs.setText(String.format(Locale.ENGLISH, "%d", sCsCg));
    }

//SANDSTORM ROCKET SHIP

    //Hatches
    public void setSRtHtSub(View v) {
        if (sRtHt > 0) {
            sRtHt -= 1;
            sRtHtDs.setText(String.format(Locale.ENGLISH, "%d", sRtHt));
        }
    }

    public void setSRtHtAdd(View v) {
        if (sCsHt + sRtHt <= 4) {
            sRtHt += 1;
            sRtHtDs.setText(String.format(Locale.ENGLISH, "%d", sRtHt));
        }
    }

    //Cargo
    public void setSRtCgSub(View v) {

        if (sRtCg > 0) {
            sRtCg -= 1;
            sRtCgDs.setText(String.format(Locale.ENGLISH, "%d", sRtCg));
        }
    }

    public void setSRtCgAdd(View v) {
        sRtCg += 1;
        sRtCgDs.setText(String.format(Locale.ENGLISH, "%d", sRtCg));
    }

//TELEOP CARGO SHIP HATCHES

    //ATTEMPTS
    public void setTCsHtSubSc(View v) {
        if (tCsHtSc > 0) {
            tCsHtSc -= 1;
            tCsHtDsSc.setText(String.format(Locale.ENGLISH, "%d", tCsHtSc));
        }
    }

    public void setTCsHtAddSc(View v) {
        tCsHtSc += 1;
        tCsHtDsSc.setText(String.format(Locale.ENGLISH, "%d", tCsHtSc));
    }

    //SCORED
    public void setTCsHtSubFl(View v) {

        if (tCsHtFl > 0) {
            tCsHtFl -= 1;
            tCsHtDsFl.setText(String.format(Locale.ENGLISH, "%d", tCsHtFl));
        }
    }

    public void setTCsHtAddFl(View v) {
        tCsHtFl += 1;
        tCsHtDsFl.setText(String.format(Locale.ENGLISH, "%d", tCsHtFl));
    }

//TELEOP CARGO SHIP CARGO

    //ATTEMPTS
    public void setTCsCgSubSc(View v) {
        if (tCsCgSc > 0) {
            tCsCgSc -= 1;
            tCsCgDsSc.setText(String.format(Locale.ENGLISH, "%d", tCsCgSc));
        }
    }

    public void setTCsCgAddSc(View v) {
        tCsCgSc += 1;
        tCsCgDsSc.setText(String.format(Locale.ENGLISH, "%d", tCsCgSc));
    }

    //SCORED

    public void setTCsCgSubFl(View v) {

        if (tCsCgFl > 0) {
            tCsCgFl -= 1;
            tCsCgDsFl.setText(String.format(Locale.ENGLISH, "%d", tCsCgFl));
        }
    }

    public void setTCsCgAddFl(View v) {
        tCsCgFl += 1;
        tCsCgDsFl.setText(String.format(Locale.ENGLISH, "%d", tCsCgFl));
    }

//TELEOP ROCKET SHIP HATCHES

    //ATTEMPTS
    public void setTRtHtSubSc(View v) {
        if (tRtHtSc > 0) {
            tRtHtSc -= 1;
            tRtHtDsSc.setText(String.format(Locale.ENGLISH, "%d", tRtHtSc));
        }
    }

    public void setTRtHtAddSc(View v) {
        tRtHtSc += 1;
        tRtHtDsSc.setText(String.format(Locale.ENGLISH, "%d", tRtHtSc));
    }

    //SCORED
    public void setTRtHtSubFl(View v) {

        if (tRtHtFl > 0) {
            tRtHtFl -= 1;
            tRtHtDsFl.setText(String.format(Locale.ENGLISH, "%d", tRtHtFl));
        }
    }

    public void setTRtHtAddFl(View v) {
        tRtHtFl += 1;
        tRtHtDsFl.setText(String.format(Locale.ENGLISH, "%d", tRtHtFl));
    }

//TELEOP ROCKET SHIP CARGO

    //ATTEMPTS
    public void setTRtCgSubSc(View v) {
        if (tRtCgSc > 0) {
            tRtCgSc -= 1;
            tRtCgDsSc.setText(String.format(Locale.ENGLISH, "%d", tRtCgSc));
        }
    }

    public void setTRtCgAddSc(View v) {
        tRtCgSc += 1;
        tRtCgDsSc.setText(String.format(Locale.ENGLISH, "%d", tRtCgSc));
    }

    //SCORED
    public void setTRtCgSubFl(View v) {

        if (tRtCgFl > 0) {
            tRtCgFl -= 1;
            tRtCgDsFl.setText(String.format(Locale.ENGLISH, "%d", tRtCgFl));
        }
    }

    public void setTRtCgAddFl(View v) {
        tRtCgFl += 1;
        tRtCgDsFl.setText(String.format(Locale.ENGLISH, "%d", tRtCgFl));
    }

    //ENDGAME
    public void setOnField(View v) {
        habStatus = 0;

    }

    public void setLvlOne(View v) {

        habStatus = 1;
    }

    public void setLvlTwo(View v) {

        habStatus = 2;
    }

    public void setLvlThree(View v) {

        habStatus = 3;
    }

    //NEXT
    public void setGoToPostMatch(View v) {

        //PASSABLE STRING CREATION

        //AUTOLINE
        sHabLinePass = String.format(Locale.ENGLISH, "%d", sHabLine);

        //ATTEMPTS
        sCgLPass = String.format(Locale.ENGLISH, "%d", sCgL);
        sHtLPass = String.format(Locale.ENGLISH, "%d", sHtL);
        sHtMPass = String.format(Locale.ENGLISH, "%d", sHtM);
        sHtHPass = String.format(Locale.ENGLISH, "%d", sHtH);

        tCgLPass = String.format(Locale.ENGLISH, "%d", tCgL);
        tCgMPass = String.format(Locale.ENGLISH, "%d", tCgM);
        tCgHPass = String.format(Locale.ENGLISH, "%d", tCgH);
        tHtLPass = String.format(Locale.ENGLISH, "%d", tHtL);
        tHtMPass = String.format(Locale.ENGLISH, "%d", tHtM);
        tHtHPass = String.format(Locale.ENGLISH, "%d", tHtH);


        //ENDGAME
        habStatusPass = String.format(Locale.ENGLISH, "%d", habStatus);


        //SAVING PASSABLES
        SharedPreferences.Editor SPMD = matchData.edit();

        //AUTOLINE
        SPMD.putString("sHabLine", sHabLinePass);

        //ATTEMPTS
        SPMD.putString("sCgL", sCgLPass);
        SPMD.putString("sHtL", sHtLPass);
        SPMD.putString("sHtM", sHtMPass);
        SPMD.putString("sHtH", sHtHPass);

        SPMD.putString("tCgL", tCgLPass);
        SPMD.putString("tCgM", tCgMPass);
        SPMD.putString("tCgH", tCgHPass);
        SPMD.putString("tHtL", tHtLPass);
        SPMD.putString("tHtM", tHtMPass);
        SPMD.putString("tHtH", tHtHPass);


        //ENDGAME
        SPMD.putString("habStatus", habStatusPass);
        SPMD.putString("autonTeleopVals", sHabLinePass + "," + sCgLPass + "," + sHtLPass + "," + sHtMPass + "," + sHtHPass + "," +
                tCgLPass + "," + tCgMPass + "," + tCgHPass + "," +
                tHtLPass + "," + tHtMPass + "," + tHtHPass + ","  +
                habStatusPass + ",");
        SPMD.apply();

        //NEXT
        Intent goToPostMatch = new Intent(AutonTeleop.this, PostMatch.class);
        startActivity(goToPostMatch);
    }

    public void onPause() {
        super.onPause();
        //AUTOLINE
        //PASSABLE STRING CREATION

        //AUTOLINE
        sHabLinePass = String.format(Locale.ENGLISH, "%d", sHabLine);

        //ATTEMPTS
        sCgLPass = String.format(Locale.ENGLISH, "%d", sCgL);
        sHtLPass = String.format(Locale.ENGLISH, "%d", sHtL);
        sHtMPass = String.format(Locale.ENGLISH, "%d", sHtM);
        sHtHPass = String.format(Locale.ENGLISH, "%d", sHtH);

        tCgLPass = String.format(Locale.ENGLISH, "%d", tCgL);
        tCgMPass = String.format(Locale.ENGLISH, "%d", tCgM);
        tCgHPass = String.format(Locale.ENGLISH, "%d", tCgH);
        tHtLPass = String.format(Locale.ENGLISH, "%d", tHtL);
        tHtMPass = String.format(Locale.ENGLISH, "%d", tHtM);
        tHtHPass = String.format(Locale.ENGLISH, "%d", tHtH);


        //ENDGAME
        habStatusPass = String.format(Locale.ENGLISH, "%d", habStatus);


        //SAVING PASSABLES
        SharedPreferences.Editor SPMD = matchData.edit();

        //AUTOLINE
        SPMD.putString("sHabLine", sHabLinePass);

        //ATTEMPTS
        SPMD.putString("sCgL", sCgLPass);
        SPMD.putString("sHtL", sHtLPass);
        SPMD.putString("sHtM", sHtMPass);
        SPMD.putString("sHtH", sHtHPass);

        SPMD.putString("tCgL", tCgLPass);
        SPMD.putString("tCgM", tCgMPass);
        SPMD.putString("tCgH", tCgHPass);
        SPMD.putString("tHtL", tHtLPass);
        SPMD.putString("tHtM", tHtMPass);
        SPMD.putString("tHtH", tHtHPass);


        //ENDGAME
        SPMD.putString("habStatus", habStatusPass);

        SPMD.putString("autonTeleopVals", sHabLinePass + "," + sCgLPass + "," + sHtLPass + "," + sHtMPass + "," + sHtHPass + "," +
                tCgLPass + "," + tCgMPass + "," + tCgHPass + "," +
                tHtLPass + "," + tHtMPass + "," + tHtHPass + ","  +
                habStatusPass + ",");
        SPMD.apply();

    }

    public void onResume() {
        super.onResume();


        //BINARIES
        sHabLine = Integer.parseInt(matchData.getString("sHabLine", "0")); //binary

        //VIEWS
        habLine = findViewById(R.id.habLine);

        if (sHabLine == 0) {
            habLine.setChecked(false);
        } else {
            habLine.setChecked(true);
        }
//SANDSTORM SETTERS
        //COUNTS
        sCgL = Integer.parseInt(matchData.getString("sCgL", "0"));
        sHtL = Integer.parseInt(matchData.getString("sHtL", "0"));
        sHtM = Integer.parseInt(matchData.getString("sHtM", "0"));
        sHtH = Integer.parseInt(matchData.getString("sHtH", "0"));


        //VIEWS
        sCgLDs = findViewById(R.id.sCgLDs);
        sHtLDs = findViewById(R.id.sHtLDs);
        sHtMDs = findViewById(R.id.sHtMDs);
        sHtHDs = findViewById(R.id.sHtHDs);

        sCgLDs.setText(String.format(Locale.ENGLISH, "%d", sCgL));
        sHtLDs.setText(String.format(Locale.ENGLISH, "%d", sHtL));
        sHtMDs.setText(String.format(Locale.ENGLISH, "%d", sHtM));
        sHtHDs.setText(String.format(Locale.ENGLISH, "%d", sHtH));

        //TELEOP SETTERS

        //COUNTS
        tCgL = Integer.parseInt(matchData.getString("tCgL", "0"));
        tCgM = Integer.parseInt(matchData.getString("tCgM", "0"));
        tCgH = Integer.parseInt(matchData.getString("tCGH", "0"));
        tHtL = Integer.parseInt(matchData.getString("tHtL", "0"));
        tHtM = Integer.parseInt(matchData.getString("tHtM", "0"));
        tHtH = Integer.parseInt(matchData.getString("tHtH", "0"));

        //VIEWS
        tCgLDs = findViewById(R.id.tCgLDs);
        tCgMDs = findViewById(R.id.tCgMDs);
        tCgHDs = findViewById(R.id.tCgHDs);
        tHtLDs = findViewById(R.id.tHtLDs);
        tHtMDs = findViewById(R.id.tHtMDs);
        tHtHDs = findViewById(R.id.tHtHDs);

        tCgLDs.setText(String.format(Locale.ENGLISH, "%d", tCgL));
        tCgMDs.setText(String.format(Locale.ENGLISH, "%d", tCgM));
        tCgHDs.setText(String.format(Locale.ENGLISH, "%d",  tCgH));
        tHtLDs.setText(String.format(Locale.ENGLISH, "%d", tHtL));
        tHtMDs.setText(String.format(Locale.ENGLISH, "%d", tHtM));
        tHtHDs.setText(String.format(Locale.ENGLISH, "%d", tHtH));

        //ENDGAME

        //BINARIES
        habStatus = Integer.parseInt(matchData.getString("habStatus", "0")); //binary

        //VIEWS
        onField = findViewById(R.id.onField);
        lvlOne = findViewById(R.id.lvlOne);
        lvlTwo = findViewById(R.id.lvlTwo);
        lvlThree = findViewById(R.id.lvlThree);

        if (habStatus == 0) {
            onField.setChecked(true);
            lvlOne.setChecked(false);
            lvlTwo.setChecked(false);
            lvlThree.setChecked(false);
        } else if (habStatus == 1) {
            onField.setChecked(false);
            lvlOne.setChecked(true);
            lvlTwo.setChecked(false);
            lvlThree.setChecked(false);
        } else if (habStatus == 2) {
            onField.setChecked(false);
            lvlOne.setChecked(false);
            lvlTwo.setChecked(true);
            lvlThree.setChecked(false);
        } else {
            onField.setChecked(false);
            lvlOne.setChecked(false);
            lvlTwo.setChecked(false);
            lvlThree.setChecked(true);
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
                            Intent home = new Intent(AutonTeleop.this, PreMatch.class);
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



