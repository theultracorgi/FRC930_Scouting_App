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
    TextView sCsHtDs, sCsCgDs, sRtHtDs, sRtCgDs, tCsHtDsSc, tCsHtDsFl, tCsCgDsSc, tCsCgDsFl, tRtHtDsSc, tRtHtDsFl, tRtCgDsSc, tRtCgDsFl;

    Integer sHabLine, sCsHt, sCsCg, sRtHt, sRtCg, tCsHtSc, tCsHtFl, tCsCgSc, tCsCgFl, tRtHtSc, tRtHtFl, tRtCgSc, tRtCgFl, habStatus;
    String matchDataPreferences, otherPreferences;
    String sHabLinePass, sCsHtPass, sCsCgPass, sRtHtPass, sRtCgPass, tCsHtScPass, tCsHtFlPass, tCsCgScPass, tCsCgFlPass, tRtHtScPass, tRtHtFlPass, tRtCgScPass, tRtCgFlPass, habStatusPass;
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
        sCsHt = Integer.parseInt(matchData.getString("sCsHt", "0"));
        sCsCg = Integer.parseInt(matchData.getString("sCsCg", "0"));
        sRtHt = Integer.parseInt(matchData.getString("sRtHt", "0"));
        sRtCg = Integer.parseInt(matchData.getString("sRtCg", "0"));


        //VIEWS
        sCsHtDs = findViewById(R.id.sCsHtDs);
        sCsCgDs = findViewById(R.id.sCsCgDs);
        sRtHtDs = findViewById(R.id.sRtHtDs);
        sRtCgDs = findViewById(R.id.sRtCgDs);

        sCsHtDs.setText(String.format(Locale.ENGLISH, "%d", sCsHt));
        sCsCgDs.setText(String.format(Locale.ENGLISH, "%d", sCsCg));
        sRtHtDs.setText(String.format(Locale.ENGLISH, "%d", sRtHt));
        sRtCgDs.setText(String.format(Locale.ENGLISH, "%d", sRtCg));

        //TELEOP SETTERS

        //COUNTS
        tCsHtSc = Integer.parseInt(matchData.getString("tCsHtSc", "0"));
        tCsHtFl = Integer.parseInt(matchData.getString("tCsHtFl", "0"));
        tCsCgSc = Integer.parseInt(matchData.getString("tCsCgSc", "0"));
        tCsCgFl = Integer.parseInt(matchData.getString("tCsCgFl", "0"));
        tRtHtSc = Integer.parseInt(matchData.getString("tRtHtSc", "0"));
        tRtHtFl = Integer.parseInt(matchData.getString("tRtHtFl", "0"));
        tRtCgSc = Integer.parseInt(matchData.getString("tRtCgSc", "0"));
        tRtCgFl = Integer.parseInt(matchData.getString("tRtCgFl", "0"));

        //VIEWS
        tCsHtDsSc = findViewById(R.id.tCsHtDsSc);
        tCsHtDsFl = findViewById(R.id.tCsHtDsFl);
        tCsCgDsSc = findViewById(R.id.tCsCgDsSc);
        tCsCgDsFl = findViewById(R.id.tCsCgDsFl);
        tRtHtDsSc = findViewById(R.id.tRtHtDsSc);
        tRtHtDsFl = findViewById(R.id.tRtHtDsFl);
        tRtCgDsSc = findViewById(R.id.tRtCgDsSc);
        tRtCgDsFl = findViewById(R.id.tRtCgDsFl);


        tCsHtDsSc.setText(String.format(Locale.ENGLISH, "%d", tCsHtSc));
        tCsHtDsFl.setText(String.format(Locale.ENGLISH, "%d", tCsHtFl));
        tCsCgDsSc.setText(String.format(Locale.ENGLISH, "%d", tCsCgSc));
        tCsCgDsFl.setText(String.format(Locale.ENGLISH, "%d", tCsCgFl));
        tRtHtDsSc.setText(String.format(Locale.ENGLISH, "%d", tRtHtSc));
        tRtHtDsFl.setText(String.format(Locale.ENGLISH, "%d", tRtHtFl));
        tRtCgDsSc.setText(String.format(Locale.ENGLISH, "%d", tRtCgSc));
        tRtCgDsFl.setText(String.format(Locale.ENGLISH, "%d", tRtCgFl));

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
        sCsHtPass = String.format(Locale.ENGLISH, "%d", sCsHt);
        sCsCgPass = String.format(Locale.ENGLISH, "%d", sCsCg);
        sRtHtPass = String.format(Locale.ENGLISH, "%d", sCsHt);
        sRtCgPass = String.format(Locale.ENGLISH, "%d", sCsCg);

        tCsHtScPass = String.format(Locale.ENGLISH, "%d", tCsHtSc);
        tCsCgScPass = String.format(Locale.ENGLISH, "%d", tCsCgSc);
        tRtHtScPass = String.format(Locale.ENGLISH, "%d", tRtHtSc);
        tRtCgScPass = String.format(Locale.ENGLISH, "%d", tRtCgSc);

        if (attemptsOutput) {
            tCsHtFlPass = String.format(Locale.ENGLISH, "%d", tCsHtFl + tCsHtSc);
            tCsCgFlPass = String.format(Locale.ENGLISH, "%d", tCsCgFl + tCsCgSc);
            tRtHtFlPass = String.format(Locale.ENGLISH, "%d", tRtHtFl + tRtHtSc);
            tRtCgFlPass = String.format(Locale.ENGLISH, "%d", tRtCgFl + tRtCgSc);
        } else {
            tCsHtFlPass = String.format(Locale.ENGLISH, "%d", tCsHtFl);
            tCsCgFlPass = String.format(Locale.ENGLISH, "%d", tCsCgFl);
            tRtHtFlPass = String.format(Locale.ENGLISH, "%d", tRtHtFl);
            tRtCgFlPass = String.format(Locale.ENGLISH, "%d", tRtCgFl);
        }


        //ENDGAME
        habStatusPass = String.format(Locale.ENGLISH, "%d", habStatus);


        //SAVING PASSABLES
        SharedPreferences.Editor SPMD = matchData.edit();

        //AUTOLINE
        SPMD.putString("sHabLine", sHabLinePass);

        //ATTEMPTS
        SPMD.putString("sCsHt", sCsHtPass);
        SPMD.putString("sCsCg", sCsCgPass);
        SPMD.putString("sRtHt", sRtHtPass);
        SPMD.putString("sRtCg", sRtCgPass);

        SPMD.putString("tCsHtSc", tCsHtScPass);
        SPMD.putString("tCsCgSc", tCsCgScPass);
        SPMD.putString("tRtHtSc", tRtHtScPass);
        SPMD.putString("tRtCgSc", tRtCgScPass);

        SPMD.putString("tRtHtFl", tCsHtFlPass);
        SPMD.putString("tRtCgFl", tCsCgFlPass);
        SPMD.putString("tRtHtFl", tRtHtFlPass);
        SPMD.putString("tRtCgFl", tRtCgFlPass);


        //ENDGAME
        SPMD.putString("habStatus", habStatusPass);
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
        sCsHtPass = String.format(Locale.ENGLISH, "%d", sCsHt);
        sCsCgPass = String.format(Locale.ENGLISH, "%d", sCsCg);
        sRtHtPass = String.format(Locale.ENGLISH, "%d", sRtHt);
        sRtCgPass = String.format(Locale.ENGLISH, "%d", sRtCg);


        tCsHtScPass = String.format(Locale.ENGLISH, "%d", tCsHtSc);
        tCsCgScPass = String.format(Locale.ENGLISH, "%d", tCsCgSc);
        tRtHtScPass = String.format(Locale.ENGLISH, "%d", tRtHtSc);
        tRtCgScPass = String.format(Locale.ENGLISH, "%d", tRtCgSc);

        tCsHtFlPass = String.format(Locale.ENGLISH, "%d", tCsHtFl);
        tCsCgFlPass = String.format(Locale.ENGLISH, "%d", tCsCgFl);
        tRtHtFlPass = String.format(Locale.ENGLISH, "%d", tRtHtFl);
        tRtCgFlPass = String.format(Locale.ENGLISH, "%d", tRtCgFl);


        //ENDGAME
        habStatusPass = String.format(Locale.ENGLISH, "%d", habStatus);


        //SAVING PASSABLES
        SharedPreferences.Editor SPMD = matchData.edit();

        //AUTOLINE
        SPMD.putString("sHabLine", sHabLinePass);

        //ATTEMPTS
        SPMD.putString("sCsHt", sCsHtPass);
        SPMD.putString("sCsCg", sCsCgPass);
        SPMD.putString("sRtHt", sRtHtPass);
        SPMD.putString("sRtCg", sRtCgPass);

        SPMD.putString("tCsHtSc", tCsHtScPass);
        SPMD.putString("tCsCgSc", tCsCgScPass);
        SPMD.putString("tRtHtSc", tRtHtScPass);
        SPMD.putString("tRtCgSc", tRtCgScPass);

        SPMD.putString("tRtHtFl", tCsHtFlPass);
        SPMD.putString("tRtCgFl", tCsCgFlPass);
        SPMD.putString("tRtHtFl", tRtHtFlPass);
        SPMD.putString("tRtCgFl", tRtCgFlPass);


        //ENDGAME
        SPMD.putString("habStatus", habStatusPass);
        SPMD.putString("autonTeleopVals", sHabLinePass + "," + sCsHtPass + "," + sCsCgPass + "," + sRtHtPass + "," + sRtCgPass + "," +
                tCsHtScPass + "," + tCsHtFlPass + "," + tCsCgScPass + "," + tCsCgFlPass + "," +
                tRtHtScPass + "," + tRtHtFlPass + "," + tRtCgScPass + "," + tRtCgFlPass + "," + habStatusPass + ",");
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
        sCsHt = Integer.parseInt(matchData.getString("sCsHt", "0"));
        sCsCg = Integer.parseInt(matchData.getString("sCsCg", "0"));
        sRtHt = Integer.parseInt(matchData.getString("sRtHt", "0"));
        sRtCg = Integer.parseInt(matchData.getString("sRtCg", "0"));


        //VIEWS
        sCsHtDs = findViewById(R.id.sCsHtDs);
        sCsCgDs = findViewById(R.id.sCsCgDs);
        sRtHtDs = findViewById(R.id.sRtHtDs);
        sRtCgDs = findViewById(R.id.sRtCgDs);

        sCsHtDs.setText(String.format(Locale.ENGLISH, "%d", sCsHt));
        sCsCgDs.setText(String.format(Locale.ENGLISH, "%d", sCsCg));
        sRtHtDs.setText(String.format(Locale.ENGLISH, "%d", sRtHt));
        sRtCgDs.setText(String.format(Locale.ENGLISH, "%d", sRtCg));

        //TELEOP SETTERS

        //COUNTS
        tCsHtSc = Integer.parseInt(matchData.getString("tCsHtSc", "0"));
        tCsHtFl = Integer.parseInt(matchData.getString("tCsHtFl", "0"));
        tCsCgSc = Integer.parseInt(matchData.getString("tCsCgSc", "0"));
        tCsCgFl = Integer.parseInt(matchData.getString("tCsCgFl", "0"));
        tRtHtSc = Integer.parseInt(matchData.getString("tRtHtSc", "0"));
        tRtHtFl = Integer.parseInt(matchData.getString("tRtHtFl", "0"));
        tRtCgSc = Integer.parseInt(matchData.getString("tRtCgSc", "0"));
        tRtCgFl = Integer.parseInt(matchData.getString("tRtCgFl", "0"));

        //VIEWS
        tCsHtDsSc = findViewById(R.id.tCsHtDsSc);
        tCsHtDsFl = findViewById(R.id.tCsHtDsFl);
        tCsCgDsSc = findViewById(R.id.tCsCgDsSc);
        tCsCgDsFl = findViewById(R.id.tCsCgDsFl);
        tRtHtDsSc = findViewById(R.id.tRtHtDsSc);
        tRtHtDsFl = findViewById(R.id.tRtHtDsFl);
        tRtCgDsSc = findViewById(R.id.tRtCgDsSc);
        tRtCgDsFl = findViewById(R.id.tRtCgDsFl);


        tCsHtDsSc.setText(String.format(Locale.ENGLISH, "%d", tCsHtSc));
        tCsHtDsFl.setText(String.format(Locale.ENGLISH, "%d", tCsHtFl));
        tCsCgDsSc.setText(String.format(Locale.ENGLISH, "%d", tCsCgSc));
        tCsCgDsFl.setText(String.format(Locale.ENGLISH, "%d", tCsCgFl));
        tRtHtDsSc.setText(String.format(Locale.ENGLISH, "%d", tRtHtSc));
        tRtHtDsFl.setText(String.format(Locale.ENGLISH, "%d", tRtHtFl));
        tRtCgDsSc.setText(String.format(Locale.ENGLISH, "%d", tRtCgSc));
        tRtCgDsFl.setText(String.format(Locale.ENGLISH, "%d", tRtCgFl));

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



