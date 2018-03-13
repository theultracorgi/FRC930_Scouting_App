package org.team930.bears.wearstime;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextThemeWrapper;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import org.team930.bears.wearstime.R;

import static android.app.AlertDialog.THEME_HOLO_LIGHT;


@SuppressWarnings("ALL")
public class HomeScreen extends AppCompatActivity {
    Button goToPreMatch, goToGenQR, goToSettings, goToMasterScanner;
    LinearLayout masterScanner;
    ImageView disappear;

    String otherPreferences, numStoredMatches, matchDataPreferences;
    Integer maxMatches;
    boolean showToast;

    SharedPreferences otherSettings, matchData;
    ContextThemeWrapper ctw;
    AlertDialog.Builder builder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home__screen);
        setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        ctw = new ContextThemeWrapper(this, THEME_HOLO_LIGHT);
        builder = new AlertDialog.Builder(ctw);
        showToast = true;

        numStoredMatches = getString(R.string.numStoredMatches);
        maxMatches = getResources().getInteger(R.integer.maxMatches);

        otherPreferences = getString(R.string.otherPreferences);
        otherSettings = getSharedPreferences(otherPreferences, 0);

        matchDataPreferences = getString(R.string.matchDataPreferences);
        matchData = getSharedPreferences(matchDataPreferences,0);

        masterScanner = findViewById(R.id.masterScanner);

        goToPreMatch = findViewById(R.id.goToPreMatch);
        goToGenQR = findViewById(R.id.goToGenQR);
        goToSettings = findViewById(R.id.goToSettings);
        goToMasterScanner = findViewById(R.id.goToMaster);
        disappear = findViewById(R.id.disappearHomeScreen);

        SharedPreferences.Editor SPOS = otherSettings.edit();

        if (otherSettings.getBoolean("firstOpen", true)) {
            SPOS.putInt(numStoredMatches, 0);
            SPOS.apply();
        }
    }

    public void setGoToPreMatch(View v) {

        if(otherSettings.getInt(numStoredMatches, maxMatches) >= maxMatches) { //

            if(otherSettings.getBoolean("deleteData", false)) {
                builder.setTitle("Delete Your Data");
                builder.setMessage("You need to delete your data to continue scouting.");
            } else {
                builder.setTitle("All Your Data Are Belong To Us.");
                builder.setMessage("You need to generate a QR code to continue scouting.");
            }
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

        if (otherSettings.getBoolean("admin", false) ) {
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