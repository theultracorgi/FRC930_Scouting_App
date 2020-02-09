package org.team930.bears.wears;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.media.MediaPlayer;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
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

import org.frc2834.bluealliance.v2.BlueAlliance;

import java.util.List;


import static android.app.AlertDialog.THEME_HOLO_LIGHT;

@SuppressWarnings("ALL")
public class Settings extends AppCompatActivity {
    LinearLayout deleteBar, adminUnlock;
    Button deleteData, submitPassword;
    EditText password;
    ImageView disappearAdmin, disappearDelete;
    Spinner scouterPos;

    Integer previousAttempt, adminPassword;
    String matchDataPreferences, otherPreferences, numMatchesStored, TBA_AUTH_KEY;

    SharedPreferences matchData, otherSettings;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        previousAttempt = 0;
        adminPassword = getResources().getInteger(R.integer.adminPassword);

        numMatchesStored = getString(R.string.numStoredMatches);


        matchDataPreferences = getString(R.string.matchDataPreferences);
        matchData = getSharedPreferences(matchDataPreferences, 0);


        otherPreferences = getString(R.string.otherPreferences);
        otherSettings = getSharedPreferences(otherPreferences, 0);
        scouterPos = findViewById(R.id.scouterPosition);
        adminUnlock = findViewById(R.id.adminUnlock);
        deleteData = findViewById(R.id.deleteData);
        deleteBar = findViewById(R.id.deleteBar);
        submitPassword = findViewById(R.id.submitPassword);
        password = findViewById(R.id.password);
        disappearAdmin = findViewById(R.id.disappearAdmin);

        scouterPos.setSelection(Integer.parseInt(otherSettings.getString("scouterPos", "0")));

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



    public void setDeleteData(View v) {


                        SharedPreferences.Editor SPOS = otherSettings.edit();

                        SPOS.putBoolean("deleteData", false);
                        SPOS.putInt(numMatchesStored, 0);

                        SPOS.putString("scannedMatches", "");
                        SPOS.putInt("scannedID", 0);
                        SPOS.putBoolean("csVisible", false);

                        SPOS.putBoolean("dataAvailable", false);

                        SPOS.apply();

                        SharedPreferences.Editor SPMD = matchData.edit();

                        SPMD.putString("teamNum", "0");
                        SPMD.putString("matchNum", "0");
                        SPMD.putString("startPos", "1");
                        SPMD.putString("sHabLine", "0");

                        SPMD.putString("sCgL", "0");
                        SPMD.putString("sHtL", "0");
                        SPMD.putString("sHtM", "0");
                        SPMD.putString("sHtH", "0");

                        SPMD.putString("tCgL", "0");
                        SPMD.putString("tCgM", "0");
                        SPMD.putString("tCgH", "0");
                        SPMD.putString("tHtL", "0");
                        SPMD.putString("tHtM", "0");
                        SPMD.putString("tHtH", "0");


                        SPMD.putString("habStatus", "0");
                        SPMD.putString("disabled", "0");
                        SPMD.putString("defense", "0");
                        SPMD.putString("comments", "");

                        SPMD.putString("preMatchVals", "");
                        SPMD.putString("autonTeleopVals", "");
                        SPMD.putString("postMatchVals", "");


                        SPMD.putString("firstQR", "");
                        SPMD.putString("secondQR", "");

                        SPMD.apply();

                        Settings.this.recreate();
    }



    public void onPause() {
        super.onPause();
        SharedPreferences.Editor SPOS = otherSettings.edit();

        SPOS.putString("scouterPos", Integer.toString(scouterPos.getSelectedItemPosition()));
        SPOS.apply();
    }


}


