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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import static android.app.AlertDialog.THEME_HOLO_LIGHT;


@SuppressWarnings("ALL")
public class HomeScreen extends AppCompatActivity {
    Button goToPreMatch, goToGenQR, goToSettings, goToMasterScanner;
    LinearLayout masterScanner;
    ImageView disappear;

    String otherPreferences, numStoredMatches, matchDataPreferences, numMatchesStored;
    Integer maxMatches;
    boolean showToast;

    SharedPreferences otherSettings, matchData;
    ContextThemeWrapper ctw;
    AlertDialog.Builder builder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home__screen);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        ctw = new ContextThemeWrapper(this, THEME_HOLO_LIGHT);
        builder = new AlertDialog.Builder(ctw);
        showToast = true;

        numStoredMatches = getString(R.string.numStoredMatches);
        maxMatches = getResources().getInteger(R.integer.maxMatches);

        otherPreferences = getString(R.string.otherPreferences);
        otherSettings = getSharedPreferences(otherPreferences, 0);

        numMatchesStored = getString(R.string.numStoredMatches);

        matchDataPreferences = getString(R.string.matchDataPreferences);
        matchData = getSharedPreferences(matchDataPreferences, 0);

        masterScanner = findViewById(R.id.masterScanner);

        goToPreMatch = findViewById(R.id.goToPreMatch);
        goToGenQR = findViewById(R.id.goToGenQR);
        goToSettings = findViewById(R.id.goToSettings);
        goToMasterScanner = findViewById(R.id.goToMaster);
        disappear = findViewById(R.id.disappearHomeScreen);

        SharedPreferences.Editor SPOS = otherSettings.edit();

        if (otherSettings.getBoolean("firstOpen", true)) {
            SPOS.putInt(numStoredMatches, 0);

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
            SPOS.apply();
        }
    }

    public void setGoToPreMatch(View v) {
        if (otherSettings.getBoolean("deleteData", false)) {
            builder.setTitle("Delete Your Data");
            builder.setMessage("You need to delete your data to continue scouting.");
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

        } else if (otherSettings.getInt(numStoredMatches, maxMatches) >= maxMatches) { //
            builder.setTitle("All Your Data Are Belong To Us.");
            builder.setMessage("You need to generate a QR code to continue scouting.");
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

        } else {
            Intent nextScreen = new Intent(this, PreMatch.class);
            startActivity(nextScreen);
        }
    }

    public void setGoToGenQR(View v) {


        Intent nextScreen = new Intent(HomeScreen.this, QRCreator.class);
        startActivity(nextScreen);

    }

    public void setGoToSettings(View v) {

        Intent nextScreen = new Intent(HomeScreen.this, Settings.class);
        startActivity(nextScreen);
    }

    public void setGoToMasterScanner(View v) {

        Intent nextScreen = new Intent(HomeScreen.this, MasterScanner.class);
        startActivity(nextScreen);

    }

    public void setRevokeAdmin(View v) {
        if (otherSettings.getBoolean("admin", false)) {

            builder.setTitle("Revoke Admin");
            builder.setMessage("Are you sure you don't want this device to be an Administrator?");
            builder.setCancelable(true);
            builder.setPositiveButton(
                    "Yes",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();

                            SharedPreferences.Editor SPOS = otherSettings.edit();
                            SPOS.putBoolean("admin", false);
                            SPOS.apply();
                            HomeScreen.this.recreate();

                        }
                    });

            builder.setNegativeButton(
                    "No",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });

            AlertDialog alert = builder.create();
            alert.show();

        } else {

        }
    }

    public void onResume() {
        super.onResume();  // Always call the superclass method first

        if (otherSettings.getBoolean("admin", false)) {
            masterScanner.setVisibility(View.VISIBLE);
            disappear.setVisibility(View.VISIBLE);
            goToMasterScanner.setBackground(getResources().getDrawable(R.color.lightBlue));
            goToSettings.setBackground(getResources().getDrawable(R.color.lighterBlue));
        } else {
            masterScanner.setVisibility(View.GONE);
            disappear.setVisibility(View.GONE);
            goToSettings.setBackground(getResources().getDrawable(R.color.lightBlue));
        }
    }

}
