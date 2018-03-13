package org.team930.bears.wearstime;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
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

import org.team930.bears.wearstime.R;

import static android.app.AlertDialog.THEME_HOLO_LIGHT;

@SuppressWarnings("ALL")
public class Settings extends AppCompatActivity {

    Spinner scouterList;
    LinearLayout stillEnabler, deleteBar, nerdStatsLayout, adminUnlock;
    ToggleButton stillFRC, scoredAddsAttempt;
    Button deleteData, submitPassword, statsForNerds;
    EditText password;
    TextView revokeStill;
    ImageView disappearStill, disappearAdmin, disappearDelete, disappearStats;

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
        setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);


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

        Still = MediaPlayer.create(this, R.raw.eight_bit_dungeon_boss);
        Still.setLooping(true);

        stillEnabler = findViewById(R.id.stillEnabler);
        stillFRC = findViewById(R.id.stillFRC);
        scouterList = findViewById(R.id.scouterList);
        adminUnlock = findViewById(R.id.adminUnlock);
        deleteData = findViewById(R.id.deleteData);
        deleteBar = findViewById(R.id.deleteBar);
        submitPassword = findViewById(R.id.submitPassword);
        password = findViewById(R.id.password);
        revokeStill = findViewById(R.id.revokeStill);
        disappearStill = findViewById(R.id.disappearStill);
        disappearAdmin = findViewById(R.id.disappearAdmin);
        disappearDelete = findViewById(R.id.disappearDelete);
        disappearStats = findViewById(R.id.disappearStatsForNerds);
        nerdStatsLayout = findViewById(R.id.nerdStatsLayout);
        statsForNerds = findViewById(R.id.statsForNerds);
        scoredAddsAttempt = findViewById(R.id.scoredAddsAttempt);

        scoredAddsAttempt.setChecked(otherSettings.getBoolean("scoredAddsAttempt", false));


        scouterList.setSelection(otherSettings.getInt("scouterIDNum", 0));

        if (stillEnabled.getBoolean("stillEnabled", false)) {
            stillEnabler.setVisibility(View.VISIBLE);
            disappearStill.setVisibility(View.VISIBLE);
        } else {
            stillEnabler.setVisibility(View.GONE);
            disappearStill.setVisibility(View.GONE);
        }

        if (otherSettings.getBoolean("deleteData", false)) {
            deleteBar.setVisibility(View.VISIBLE);
            disappearDelete.setVisibility(View.VISIBLE);
        } else {
            deleteBar.setVisibility(View.GONE);
            disappearDelete.setVisibility(View.GONE);
        }

        if (otherSettings.getBoolean("admin", false)) {
            nerdStatsLayout.setVisibility(View.VISIBLE);
            disappearStats.setVisibility(View.VISIBLE);
            disappearAdmin.setVisibility(View.GONE);
            adminUnlock.setVisibility(View.GONE);
        } else {
            nerdStatsLayout.setVisibility(View.GONE);
            disappearStats.setVisibility(View.GONE);
            disappearAdmin.setVisibility(View.VISIBLE);
            adminUnlock.setVisibility(View.VISIBLE);
        }
    }

    public void setSubmitPassword(View v) {
        if (password.getText().toString().length() != 0 && TextUtils.isDigitsOnly(password.getText().toString())) {
            SharedPreferences.Editor SPOS = otherSettings.edit();
             if (Integer.parseInt(password.getText().toString()) == adminPassword) {

               Toast.makeText(this, "You are now Admin!", Toast.LENGTH_SHORT).show();

                disappearAdmin.setVisibility(View.GONE);
                adminUnlock.setVisibility(View.GONE);
                nerdStatsLayout.setVisibility(View.VISIBLE);
                disappearStats.setVisibility(View.VISIBLE);

                SPOS.putBoolean("admin", true);
                SPOS.apply();
                this.recreate();


            } else if (Integer.parseInt(password.getText().toString()) != adminPassword && Integer.parseInt(password.getText().toString()) != previousAttempt) {
                Toast.makeText(this, "Password is Incorrect", Toast.LENGTH_SHORT).show();
                previousAttempt = Integer.parseInt(password.getText().toString());
            }
        } else {
            //invalid password
        }
    }

    public void setScoredAddsAttempt(View v) {
        SharedPreferences.Editor SPOS = otherSettings.edit();

        if (scoredAddsAttempt.isChecked()) {
            SPOS.putBoolean("scoredAddsAttempt", true);

        } else {
            SPOS.putBoolean("scoredAddsAttempt", false);
        }
        SPOS.apply();
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

                        SPOS.apply();

                        SharedPreferences.Editor SPMD = matchData.edit();

                        SPMD.putString("teamNum", "0");
                        SPMD.putString("matchNum", "0");
                        SPMD.putString("aAutoline", "0");

                        SPMD.putString("aSwitchAttempts", "0");
                        SPMD.putString("aScaleScored", "0");
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

                        SPMD.putString("match1", "");
                        SPMD.putString("match2", "");
                        SPMD.putString("match3", "");
                        SPMD.putString("match4", "");
                        SPMD.putString("match5", "");
                        SPMD.putString("match6", "");

                        SPMD.apply();

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

    public void setRevokeStill(View v) {
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
                            SPSE.apply();

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

                break;
            case 1:
                SPMD.putString("scouterID", "AMk003");

                break;
            case 2:
                SPMD.putString("scouterID", "ASr006");

                break;
            case 3:
                SPMD.putString("scouterID", "ABr001");

                break;
            case 4:
                SPMD.putString("scouterID", "AJu002");

                break;
            case 5:
                SPMD.putString("scouterID", "AWk007");

                break;
            case 6:
                SPMD.putString("scouterID", "AOk004");

                break;
            case 7:
                SPMD.putString("scouterID", "AAx000");

                break;
            case 8:
                SPMD.putString("scouterID", "Low'a");

                break;
            case 9:
                SPMD.putString("scouterID", "DMb010");

                break;
            case 10:
                SPMD.putString("scouterID", "Low'a");

                break;
            case 11:
                SPMD.putString("scouterID", "DEu009");

                break;
            case 12:
                SPMD.putString("scouterID", "EEx011");

                break;
            case 13:
                SPMD.putString("scouterID", "EZu013");

                break;
            case 14:
                SPMD.putString("scouterID", "EZr012");

                break;
            case 15:
                SPMD.putString("scouterID", "JMr017");

                break;
            case 16:
                SPMD.putString("scouterID", "JDk015");

                break;
            case 17:
                SPMD.putString("scouterID", "JPx018");

                break;
            case 18:
                SPMD.putString("scouterID", "JWr021");

                break;
            case 19:
                SPMD.putString("scouterID", "JKw016");

                break;
            case 20:
                SPMD.putString("scouterID", "JWr020");

                break;
            case 21:
                SPMD.putString("scouterID", "JVb019");

                break;
            case 22:
                SPMD.putString("scouterID", "KKr022");

                break;
            case 23:
                SPMD.putString("scouterID", "Low'a");

                break;
            case 24:
                SPMD.putString("scouterID", "KMx023");

                break;
            case 25:
                SPMD.putString("scouterID", "KSk024");

                break;
            case 26:
                SPMD.putString("scouterID", "KSu025");

                break;
            case 27:
                SPMD.putString("scouterID", "MMr028");

                break;
            case 28:
                SPMD.putString("scouterID", "MRk029");

                break;
            case 29:
                SPMD.putString("scouterID", "MRb030");

                break;
            case 30:
                SPMD.putString("scouterID", "MHb027");

                break;
            case 31:
                SPMD.putString("scouterID", "NBr031");

                break;
            case 32:
                SPMD.putString("scouterID", "NKk032");

                break;
            case 33:
                SPMD.putString("scouterID", "NRr034");

                break;
            case 34:
                SPMD.putString("scouterID", "SUPAHOTFIREx048");

                break;
            case 35:
                SPMD.putString("scouterID", "NNb033");

                break;
            case 36:
                SPMD.putString("scouterID", "NFx047");

                break;
            case 37:
                SPMD.putString("scouterID", "PRo036");

                break;
            case 38:
                SPMD.putString("scouterID", "SRr039");

                break;
            case 39:
                SPMD.putString("scouterID", "SBr037");

                break;
            case 40:
                SPMD.putString("scouterID", "SGi038");

                break;
            case 41:
                SPMD.putString("scouterID", "Low'a");

                break;
            case 42:
                SPMD.putString("scouterID", "SSk040");

                break;
            case 43:
                SPMD.putString("scouterID", "TSr043");

                break;
            case 44:
                SPMD.putString("scouterID", "TGk042");

                break;
            case 45:
                SPMD.putString("scouterID", "TBk041");

                break;
            case 46:
                SPMD.putString("scouterID", "VKb044");

                break;
            case 47:
                SPMD.putString("scouterID", "Low'a");

                break;
            case 48:
                SPMD.putString("scouterID", "ZAk045");

                break;
            case 49:
                SPMD.putString("scouterID", "ZYb046");

                break;
            default:
                SPMD.putString("scouterID", "Low'a");
        }
        SPMD.apply();

        SharedPreferences.Editor SPOS = otherSettings.edit();

        SPOS.putInt("scouterIDNum", scouterList.getSelectedItemPosition());

        SPOS.apply();


    }


}


