package org.team930.bears.watching_extremely_awesome_robots;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.ContextThemeWrapper;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.util.Timer;

import static android.app.AlertDialog.THEME_HOLO_LIGHT;

public class Settings extends AppCompatActivity {

    Spinner scouterList;
    LinearLayout stillEnabler, deleteBar, nerdStatsLayout;
    ToggleButton stillFRC, scoredAddsAttempt;
    Button deleteData, submitPassword, statsForNerds;
    EditText password;
    TextView revokeStill;
    ImageView disappear;

    Integer previousAttempt, adminPassword;
    String stillPreferences, matchDataPreferences, otherPreferences, numMatchesStored;

    SharedPreferences matchData, stillEnabled, otherSettings;
    MediaPlayer Still;
    ContextThemeWrapper ctw;
    AlertDialog.Builder delete, adminNow, noStill, nerdStats;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);


        previousAttempt = 0;
        adminPassword = getResources().getInteger(R.integer.adminPassword);

        ctw = new ContextThemeWrapper(this, THEME_HOLO_LIGHT);
        adminNow = new AlertDialog.Builder(ctw);
        delete = new AlertDialog.Builder(ctw);
        noStill = new AlertDialog.Builder(ctw);
        nerdStats = new AlertDialog.Builder(ctw);

        numMatchesStored = getString(R.string.numStoredMatches);

        matchDataPreferences = getString(R.string.matchDataPreferences);
        matchData = getSharedPreferences(matchDataPreferences, 0);

        stillPreferences = getString(R.string.stillPreferences);
        stillEnabled = getSharedPreferences(stillPreferences, 0);

        otherPreferences = getString(R.string.otherPreferences);
        otherSettings = getSharedPreferences(otherPreferences, 0);

        Still = MediaPlayer.create(this, R.raw.still_frc_mixdown);
        Still.setLooping(true);

        stillEnabler = findViewById(R.id.stillEnabler);
        stillFRC = findViewById(R.id.stillFRC);
        scouterList = findViewById(R.id.scouterList);
        deleteData = findViewById(R.id.deleteData);
        deleteBar = findViewById(R.id.deleteBar);
        submitPassword = findViewById(R.id.submitPassword);
        password = findViewById(R.id.password);
        revokeStill = findViewById(R.id.revokeStill);
        disappear = findViewById(R.id.disappearSettings);
        nerdStatsLayout = findViewById(R.id.nerdStatsLayout);
        statsForNerds = findViewById(R.id.statsForNerds);
        scoredAddsAttempt = findViewById(R.id.scoredAddsAttempt);

        scoredAddsAttempt.setChecked( otherSettings.getBoolean("scoredAddsAttempt", false));



        scouterList.setSelection(otherSettings.getInt("scouterIDNum", 0));

        if (stillEnabled.getBoolean("stillEnabled", false)) {
            stillEnabler.setVisibility(View.VISIBLE);
        }
        if (otherSettings.getBoolean("deleteData", false)) {
            deleteBar.setVisibility(View.VISIBLE);
        }
        if(stillEnabled.getBoolean("stillEnabled", false) && otherSettings.getBoolean("deleteData", false) == false) {
            disappear.setVisibility(View.GONE);
        }
        if(otherSettings.getBoolean("admin", false)) {
            nerdStatsLayout.setVisibility(View.VISIBLE);
        }
        }

    public void setSubmitPassword(View v) {
        if(password.getText().toString().length() == 0 || !TextUtils.isDigitsOnly(password.getText().toString())) {

        } else {
            SharedPreferences.Editor SPOS = otherSettings.edit();
            if (otherSettings.getBoolean("admin", false)) {

                adminNow.setTitle("You are already Admin!");
                adminNow.setCancelable(true);
                adminNow.setNeutralButton(
                        "OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
                AlertDialog alert = adminNow.create();
                alert.show();



            } else if (Integer.parseInt(password.getText().toString()) == adminPassword ) {

                adminNow.setTitle("You are now Admin!");
                adminNow.setCancelable(true);
                adminNow.setNeutralButton(
                        "Yay!",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
                AlertDialog alert = adminNow.create();
                alert.show();

                SPOS.putBoolean("admin", true);
                SPOS.commit();


            } else if (Integer.parseInt(password.getText().toString()) != adminPassword && Integer.parseInt(password.getText().toString()) != previousAttempt ) {
                Toast.makeText(this, "Password is Incorrect", Toast.LENGTH_SHORT).show();
                previousAttempt = Integer.parseInt(password.getText().toString());
            }
        }
    }

    public void setScoredAddsAttempt(View v) {
        SharedPreferences.Editor SPOS = otherSettings.edit();

        if(scoredAddsAttempt.isChecked()) {
            SPOS.putBoolean("scoredAddsAttempt", true);

        } else {
            SPOS.putBoolean("scoredAddsAttempt", false);
        }
        SPOS.commit();
    }

    public void setStillFRC(View v) {

        if (stillFRC.isChecked()) {
            Still.start();

        } else {
            Still.pause();

        }
    }

    public void setDeleteData(View v) {

        delete.setTitle("Delete Data");
        delete.setMessage("This will delete all match data you have on the device");
        delete.setCancelable(true);


        delete.setPositiveButton(
                "Yes",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                        SharedPreferences.Editor SPOS = otherSettings.edit();

                        SPOS.putBoolean("deleteData", false);
                        SPOS.putInt(numMatchesStored, 0);

                        SPOS.putString("csvMatch1", "");
                        SPOS.putString("csvMatch2", "");
                        SPOS.putString("csvMatch3", "");
                        SPOS.putString("csvMatch4", "");
                        SPOS.putString("csvMatch5", "");
                        SPOS.putString("csvMatch6", "");
                        SPOS.putInt("scannedID", 0);
                        SPOS.putBoolean("csVisible", false);

                        SPOS.putBoolean("dataAvailable", false);

                        SPOS.commit();

                        SharedPreferences.Editor SPMD = matchData.edit();

                        SPMD.putString("teamNum", "");
                        SPMD.putString("matchNum", "");
                        SPMD.putString("aAutoline", "");
                        SPMD.putString("aSwitch", "");
                        SPMD.putString("aScale", "");
                        SPMD.putString("tSwitch", "");
                        SPMD.putString("tScale", "");
                        SPMD.putString("tVault", "");
                        SPMD.putString("tParked", "");
                        SPMD.putString("tElevated", "");
                        SPMD.putString("disabled", "");
                        SPMD.putString("comments", "");

                        SPMD.putString("match1", "");
                        SPMD.putString("match2", "");
                        SPMD.putString("match3", "");
                        SPMD.putString("match4", "");
                        SPMD.putString("match5", "");
                        SPMD.putString("match6", "");

                        SPMD.commit();

                        Settings.this.recreate();

                    }
                });

        delete.setNegativeButton(
                "No",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        AlertDialog alert = delete.create();
        alert.show();

    }

    public void setRevokeStill(View v){
        if (stillEnabled.getBoolean("stillEnabled", false)) {

            noStill.setTitle("We're sorry to see you go");
            noStill.setMessage("Are you sure you don't want to listen to our song?");
            noStill.setCancelable(true);
            noStill.setPositiveButton(
                    "Yes",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();

                            SharedPreferences.Editor SPSE = stillEnabled.edit();
                            SPSE.putBoolean("stillEnabled", false);
                            SPSE.commit();

                            Settings.this.recreate();

                        }
                    });

            noStill.setNegativeButton(
                    "No",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });

            AlertDialog alert = noStill.create();
            alert.show();

        } else {

        }
    }

    public void setStatsForNerds(View v) {


            nerdStats.setTitle("Stats For Nerds");
            nerdStats.setMessage("CSVs Created: " + otherSettings.getInt("csvCreated", 0) + "\nQR Codes Scanned: " + otherSettings.getInt("qrScanned", 0));
            nerdStats.setCancelable(true);
            nerdStats.setNeutralButton(
                    "Yeah Playa",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();

                            Settings.this.recreate();

                        }
                    });



            AlertDialog alert = nerdStats.create();
            alert.show();

    }

    protected void onStop() {
        // call the superclass method first
        super.onStop();

        SharedPreferences.Editor SPMD = matchData.edit();

        switch (scouterList.getSelectedItemPosition()) {
            case 0:
                SPMD.putString("scouterID", "ARr005");
                SPMD.commit();
                break;
            case 1:
                SPMD.putString("scouterID", "AMk003");
                SPMD.commit();
                break;
            case 2:
                SPMD.putString("scouterID", "ASr006");
                SPMD.commit();
                break;
            case 3:
                SPMD.putString("scouterID", "ABr001");
                SPMD.commit();
                break;
            case 4:
                SPMD.putString("scouterID", "AJu002");
                SPMD.commit();
                break;
            case 5:
                SPMD.putString("scouterID", "AWk007");
                SPMD.commit();
                break;
            case 6:
                SPMD.putString("scouterID", "AOk004");
                SPMD.commit();
                break;
            case 7:
                SPMD.putString("scouterID", "AAx000");
                SPMD.commit();
                break;
            case 8:
                SPMD.putString("scouterID", "Low'a");
                SPMD.commit();
                break;
            case 9:
                SPMD.putString("scouterID", "DMb010");
                SPMD.commit();
                break;
            case 10:
                SPMD.putString("scouterID", "Low'a");
                SPMD.commit();
                break;
            case 11:
                SPMD.putString("scouterID", "DEu009");
                SPMD.commit();
                break;
            case 12:
                SPMD.putString("scouterID", "EEx011");
                SPMD.commit();
                break;
            case 13:
                SPMD.putString("scouterID", "EZu013");
                SPMD.commit();
                break;
            case 14:
                SPMD.putString("scouterID", "EZr012");
                SPMD.commit();
                break;
            case 15:
                SPMD.putString("scouterID", "JMr017");
                SPMD.commit();
                break;
            case 16:
                SPMD.putString("scouterID", "JDk015");
                SPMD.commit();
                break;
            case 17:
                SPMD.putString("scouterID", "JPx018");
                SPMD.commit();
                break;
            case 18:
                SPMD.putString("scouterID", "JWr021");
                SPMD.commit();
                break;
            case 19:
                SPMD.putString("scouterID", "JKw016");
                SPMD.commit();
                break;
            case 20:
                SPMD.putString("scouterID", "JWr020");
                SPMD.commit();
                break;
            case 21:
                SPMD.putString("scouterID", "JVb019");
                SPMD.commit();
                break;
            case 22:
                SPMD.putString("scouterID", "KKr022");
                SPMD.commit();
                break;
            case 23:
                SPMD.putString("scouterID", "Low'a");
                SPMD.commit();
                break;
            case 24:
                SPMD.putString("scouterID", "KMx023");
                SPMD.commit();
                break;
            case 25:
                SPMD.putString("scouterID", "KSk024");
                SPMD.commit();
                break;
            case 26:
                SPMD.putString("scouterID", "KSu025");
                SPMD.commit();
                break;
            case 27:
                SPMD.putString("scouterID", "MMr028");
                SPMD.commit();
                break;
            case 28:
                SPMD.putString("scouterID", "MRk029");
                SPMD.commit();
                break;
            case 29:
                SPMD.putString("scouterID", "MRb030");
                SPMD.commit();
                break;
            case 30:
                SPMD.putString("scouterID", "MHb027");
                SPMD.commit();
                break;
            case 31:
                SPMD.putString("scouterID", "NBr031");
                SPMD.commit();
                break;
            case 32:
                SPMD.putString("scouterID", "NKk032");
                SPMD.commit();
                break;
            case 33:
                SPMD.putString("scouterID", "NRr034");
                SPMD.commit();
                break;
            case 34:
                SPMD.putString("scouterID", "SUPAHOTFIREx048");
                SPMD.commit();
                break;
            case 35:
                SPMD.putString("scouterID", "NNb033");
                SPMD.commit();
                break;
            case 36:
                SPMD.putString("scouterID", "NFx047");
                SPMD.commit();
                break;
            case 37:
                SPMD.putString("scouterID", "PRo036");
                SPMD.commit();
                break;
            case 38:
                SPMD.putString("scouterID", "SRr039");
                SPMD.commit();
                break;
            case 39:
                SPMD.putString("scouterID", "SBr037");
                SPMD.commit();
                break;
            case 40:
                SPMD.putString("scouterID", "SGi038");
                SPMD.commit();
                break;
            case 41:
                SPMD.putString("scouterID", "Low'a");
                SPMD.commit();
                break;
            case 42:
                SPMD.putString("scouterID", "SSk040");
                SPMD.commit();
                break;
            case 43:
                SPMD.putString("scouterID", "TSr043");
                SPMD.commit();
                break;
            case 44:
                SPMD.putString("scouterID", "TGk042");
                SPMD.commit();
                break;
            case 45:
                SPMD.putString("scouterID", "TBk041");
                SPMD.commit();
                break;
            case 46:
                SPMD.putString("scouterID", "VKb044");
                SPMD.commit();
                break;
            case 47:
                SPMD.putString("scouterID", "Low'a");
                SPMD.commit();
                break;
            case 48:
                SPMD.putString("scouterID", "ZAk045");
                SPMD.commit();
                break;
            case 49:
                SPMD.putString("scouterID", "ZYb046");
                SPMD.commit();
                break;
            default:
                SPMD.putString("scouterID", "Low'a");
                SPMD.commit();


        }
        SPMD.putString("scouterID", matchData.getString("scouterID", "Low'a"));

        SharedPreferences.Editor SPOS = otherSettings.edit();

        SPOS.putInt("scouterIDNum", scouterList.getSelectedItemPosition());

        SPOS.commit();


    }



}


