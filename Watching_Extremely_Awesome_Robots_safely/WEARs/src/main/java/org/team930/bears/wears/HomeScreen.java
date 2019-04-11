package org.team930.bears.wears;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
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
    AlertDialog.Builder builder, permissions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home__screen);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        ctw = new ContextThemeWrapper(this, THEME_HOLO_LIGHT);
        builder = new AlertDialog.Builder(ctw);
        permissions = new AlertDialog.Builder(ctw);
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
        SharedPreferences.Editor SPMD = matchData.edit();

        if (otherSettings.getBoolean("firstOpen", true)) {
            SPOS.putInt(numStoredMatches, 0);

            SPOS.putBoolean("deleteData", false);
            SPOS.putInt(numMatchesStored, 0);

            SPOS.putString("scannedMatches", "Null");
            SPOS.putInt("scannedID", 0);
            SPOS.putBoolean("csVisible", false);

            SPOS.putBoolean("dataAvailable", false);
            SPOS.putString("scouterPos", "0");
            SPOS.putBoolean("firstOpen", false);

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
            SPMD.putString("comments", "0");

            SPMD.putString("preMatchVals", "");
            SPMD.putString("autonTeleopVals", "");
            SPMD.putString("postMatchVals", "");


            SPMD.putString("firstQR", "");
            SPMD.putString("secondQR", "");


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

    Intent intent;

    public void setGoToMasterScanner(View v) {

        if (!checkWriteExternalPermission() || !checkCameraPermission()) {

            permissions.setTitle("Give Me permissions");
            permissions.setMessage("Go into settings and give me permission to Camera and Storage");
            permissions.setCancelable(true);

            permissions.setPositiveButton(
                    "Go to Settings",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                            intent = new Intent();
                            intent.setAction(android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                            Uri uri = Uri.fromParts("package", getPackageName(), null);
                            intent.setData(uri);
                            startActivity(intent);
                        }
                    });

            permissions.setNegativeButton(
                    "No",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                            intent = new Intent();
                            intent.setAction(android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                            Uri uri = Uri.fromParts("package", getPackageName(), null);
                            intent.setData(uri);
                            startActivity(intent);
                        }
                    });

            AlertDialog alert = permissions.create();
            alert.show();


        } else {
            Intent goToPostMatch = new Intent(this, MasterScanner.class);
            startActivity(goToPostMatch);
        }
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

    private boolean checkWriteExternalPermission() {

        String writePerm = "android.permission.WRITE_EXTERNAL_STORAGE";
        String readPerm = "android.permission.READ_EXTERNAL_STORAGE";
        int write = this.checkCallingOrSelfPermission(writePerm);
        int read = this.checkCallingOrSelfPermission(readPerm);

        boolean permsGranted;

        if (write == PackageManager.PERMISSION_GRANTED && read == PackageManager.PERMISSION_GRANTED) {
            permsGranted = true;
        } else {
            permsGranted = false;
        }

        return permsGranted;


    }

    private boolean checkCameraPermission() {

        String camPerm = "android.permission.CAMERA";
        int cam = this.checkCallingOrSelfPermission(camPerm);

        boolean permsGranted;

        if (cam == PackageManager.PERMISSION_GRANTED) {
            permsGranted = true;
        } else {
            permsGranted = false;
        }

        return permsGranted;


    }
}
