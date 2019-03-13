package org.team930.bears.wears;

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

import static android.app.AlertDialog.THEME_HOLO_LIGHT;

@SuppressWarnings("ALL")
public class Settings extends AppCompatActivity {
    LinearLayout stillEnabler, deleteBar, adminUnlock;
    ToggleButton stillFRC;
    Button deleteData, submitPassword;
    EditText password;
    TextView revokeStill;
    ImageView disappearStill, disappearAdmin, disappearDelete;
    Spinner scouterPos;

    Integer previousAttempt, adminPassword;
    String stillPreferences, matchDataPreferences, otherPreferences, numMatchesStored;

    SharedPreferences matchData, stillEnabled, otherSettings;
    MediaPlayer Still;
    ContextThemeWrapper ctw;
    AlertDialog.Builder delete, adminNow, noStill;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        previousAttempt = 0;
        adminPassword = getResources().getInteger(R.integer.adminPassword);

        ctw = new ContextThemeWrapper(this, THEME_HOLO_LIGHT);
        adminNow = new AlertDialog.Builder(ctw);
        delete = new AlertDialog.Builder(ctw);
        noStill = new AlertDialog.Builder(ctw);

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
        scouterPos = findViewById(R.id.scouterPosition);
        adminUnlock = findViewById(R.id.adminUnlock);
        deleteData = findViewById(R.id.deleteData);
        deleteBar = findViewById(R.id.deleteBar);
        submitPassword = findViewById(R.id.submitPassword);
        password = findViewById(R.id.password);
        revokeStill = findViewById(R.id.revokeStill);
        disappearStill = findViewById(R.id.disappearStill);
        disappearAdmin = findViewById(R.id.disappearAdmin);
        disappearDelete = findViewById(R.id.disappearDelete);

        scouterPos.setSelection(Integer.parseInt(otherSettings.getString("scouterPos", "0")));
        scouterPos.setPopupBackgroundResource(R.drawable.spinner_bg);


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
            disappearAdmin.setVisibility(View.GONE);
            adminUnlock.setVisibility(View.GONE);
        } else {
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

                        SPOS.putString("scannedMatches", "");
                        SPOS.putInt("scannedID", 0);
                        SPOS.putBoolean("csVisible", false);

                        SPOS.putBoolean("dataAvailable", false);
                        SPOS.putBoolean("multipleQR", false);

                        SPOS.apply();

                        SharedPreferences.Editor SPMD = matchData.edit();

                        SPMD.putString("teamNum", "0");
                        SPMD.putString("matchNum", "0");
                        SPMD.putString("sHabLine", "0");

                        SPMD.putString("sCsHt", "0");
                        SPMD.putString("sCsCg", "0");
                        SPMD.putString("sRtHt", "0");
                        SPMD.putString("sRtCg", "0");

                        SPMD.putString("tCsHtSc", "0");
                        SPMD.putString("tCsCgSc", "0");
                        SPMD.putString("tRtHtSc", "0");
                        SPMD.putString("tRtCgSc", "0");

                        SPMD.putString("tRtHtFl", "0");
                        SPMD.putString("tRtCgFl", "0");
                        SPMD.putString("tRtHtFl", "0");
                        SPMD.putString("tRtCgFl", "0");

                        //ENDGAME
                        SPMD.putString("habStatus", "0");
                        SPMD.putString("disabled", "0");
                        SPMD.putString("comments", "0");

                        SPMD.putString("preMatchVals", "");
                        SPMD.putString("autonTeleopVals", "");
                        SPMD.putString("postMatchVals", "");


                        SPMD.putString("firstQR", "");
                        SPMD.putString("secondQR", "");

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

    public void onPause() {
        super.onPause();
        SharedPreferences.Editor SPOS = otherSettings.edit();

        SPOS.putString("scouterPos", Integer.toString(scouterPos.getSelectedItemPosition()));
        SPOS.apply();
    }


}


