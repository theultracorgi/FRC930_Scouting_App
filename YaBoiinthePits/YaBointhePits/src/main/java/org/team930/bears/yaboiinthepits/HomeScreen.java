package org.team930.bears.yaboiinthepits;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.view.ContextThemeWrapper;
import android.view.View;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import static android.app.AlertDialog.THEME_HOLO_LIGHT;

public class HomeScreen extends AppCompatActivity {


    String dataKey, dialogText;
    byte[] fullCSVExport;

    SharedPreferences data;
    FileOutputStream fos;
    File path, dir, file;
    ContextThemeWrapper ctw;
    AlertDialog.Builder csvCreated, permissions;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);

        dataKey = getString(R.string.data);
        data = getSharedPreferences(dataKey, 0);

        if (data.getInt("numFiles", 0) == 0) {
            SharedPreferences.Editor SPD = data.edit();

            SPD.putInt("numFiles", 1);
            SPD.apply();
        }

        ctw = new ContextThemeWrapper(this, THEME_HOLO_LIGHT);
        csvCreated = new AlertDialog.Builder(ctw);
        permissions = new AlertDialog.Builder(ctw);

    }
    Intent intent;
    public void setGoToPitScouter(View v) {
        if(!checkWriteExternalPermission() || !checkCameraPermission()) {

            permissions.setTitle("Give Me permissions");
            permissions.setMessage("Go into settings and give me permission to Camera and Storage");
            permissions.setCancelable(true);

            permissions.setPositiveButton(
                    "Go to Settings",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                            intent = new Intent();
                            intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
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
                            intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                            Uri uri = Uri.fromParts("package", getPackageName(), null);
                            intent.setData(uri);
                            startActivity(intent);
                        }
                    });

           AlertDialog alert = permissions.create();
            alert.show();


        } else {
            Intent goToPostMatch = new Intent(this, PitScouter.class);
            startActivity(goToPostMatch);
        }
    }



    private boolean checkWriteExternalPermission() {

        String writePerm = "android.permission.WRITE_EXTERNAL_STORAGE";
        String readPerm = "android.permission.READ_EXTERNAL_STORAGE";
        int write = this.checkCallingOrSelfPermission(writePerm);
        int read = this.checkCallingOrSelfPermission(readPerm);

        return  write == PackageManager.PERMISSION_GRANTED && read == PackageManager.PERMISSION_GRANTED;



    }

    private boolean checkCameraPermission() {

        String camPerm = "android.permission.CAMERA";
        int cam = this.checkCallingOrSelfPermission(camPerm);

        return cam == PackageManager.PERMISSION_GRANTED;


    }
}





