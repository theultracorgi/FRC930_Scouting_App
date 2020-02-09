package org.team930.bears.wears;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.ContextThemeWrapper;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

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


            SPMD.putString("preMatchVals", "");
            SPMD.putString("autonTeleopVals", "");
            SPMD.putString("postMatchVals", "");


            SPMD.putString("firstQR", "");
            SPMD.putString("secondQR", "");


            SPMD.apply();
            SPOS.apply();
        }
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
