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
    TextView sCgLDs, sHtLDs, sHtMDs, sHtHDs, tCgLDs, tCgMDs, tCgHDs, tHtLDs, tHtMDs, tHtHDs;

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

//SANDSTORM LOW CARGO
    public void setSCgLSub(View v) {
        if (sCgL > 0) {
            sCgL -= 1;
            sCgLDs.setText(String.format(Locale.ENGLISH, "%d", sCgL));
        }
    }

    public void setSCgLAdd(View v) {
        sCgL += 1;
        sCgLDs.setText(String.format(Locale.ENGLISH, "%d", sCgL));
        }


//SANDSTORM LOW HATCHES
    public void setSHtLSub(View v) {

        if (sHtL > 0) {
            sHtL -= 1;
            sHtLDs.setText(String.format(Locale.ENGLISH, "%d", sHtL));
        }
    }

    public void setSHtLAdd(View v) {
        sHtL += 1;
        sHtLDs.setText(String.format(Locale.ENGLISH, "%d", sHtL));
    }

//SANDSTORM MID HATCHES
    public void setSHtMSub(View v) {
        if (sHtM > 0) {
            sHtM -= 1;
            sHtMDs.setText(String.format(Locale.ENGLISH, "%d", sHtM));
        }
    }

    public void setSHtMAdd(View v) {
            sHtM += 1;
            sHtMDs.setText(String.format(Locale.ENGLISH, "%d", sHtM));
        }

//SANDSTORM HIGH HATCHES
    public void setSHtHSub(View v) {

        if (sHtH > 0) {
            sHtH -= 1;
            sHtHDs.setText(String.format(Locale.ENGLISH, "%d", sHtH));
        }
    }

    public void setSHtHAdd(View v) {
        sHtH += 1;
        sHtHDs.setText(String.format(Locale.ENGLISH, "%d", sHtH));
    }



//TELEOP LOW CARGO
    public void setTCgLSub(View v) {
        if (tCgL > 0) {
            tCgL -= 1;
            tCgLDs.setText(String.format(Locale.ENGLISH, "%d", tCgL));
        }
    }

    public void setTCgLAdd(View v) {
        tCgL += 1;
        tCgLDs.setText(String.format(Locale.ENGLISH, "%d", tCgL));
    }

//TELEOP MID CARGO
    public void setTCgMSub(View v) {

        if (tCgM > 0) {
            tCgM -= 1;
            tCgMDs.setText(String.format(Locale.ENGLISH, "%d", tCgM));
        }
    }

    public void setTCgMAdd(View v) {
        tCgM += 1;
        tCgMDs.setText(String.format(Locale.ENGLISH, "%d", tCgM));
    }

//TELEOP HIGH CARGO
    public void setTCgHSub(View v) {
        if (tCgH > 0) {
            tCgH -= 1;
            tCgHDs.setText(String.format(Locale.ENGLISH, "%d", tCgH));
        }
    }

    public void setTCgHAdd(View v) {
        tCgH += 1;
        tCgHDs.setText(String.format(Locale.ENGLISH, "%d", tCgH));
    }



//TELEOP LOW HATCHES
    public void setTHtLSub(View v) {
        if (tHtL > 0) {
            tHtL -= 1;
            tHtLDs.setText(String.format(Locale.ENGLISH, "%d", tHtL));
        }
    }

    public void setTHtLAdd(View v) {
        tHtL += 1;
        tHtLDs.setText(String.format(Locale.ENGLISH, "%d", tHtL));
    }

//TELEOP MID HATCHES
    public void setTHtMSub(View v) {

        if (tHtM > 0) {
            tHtM -= 1;
            tHtMDs.setText(String.format(Locale.ENGLISH, "%d", tHtM));
        }
    }

    public void setTHtMAdd(View v) {
        tHtM += 1;
        tHtMDs.setText(String.format(Locale.ENGLISH, "%d", tHtM));
    }

//TELEOP HIGH HATCHES
    public void setTHtHSub(View v) {
        if (tHtH > 0) {
            tHtH -= 1;
            tHtHDs.setText(String.format(Locale.ENGLISH, "%d", tHtH));
        }
    }

    public void setTHtHAdd(View v) {
        tHtH += 1;
        tHtHDs.setText(String.format(Locale.ENGLISH, "%d", tHtH));
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



