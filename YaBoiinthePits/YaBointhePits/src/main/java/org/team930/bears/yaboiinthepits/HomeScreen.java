package org.team930.bears.yaboiinthepits;

import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;



import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.media.MediaScannerConnection;
import android.os.Environment;
import android.view.ContextThemeWrapper;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Locale;

import static android.app.AlertDialog.THEME_HOLO_LIGHT;

public class HomeScreen extends AppCompatActivity {


    String dataKey;
    byte[] fullCSVExport;

    SharedPreferences data;
    FileOutputStream fos;
    File path, dir, file;
    ContextThemeWrapper ctw;
    AlertDialog.Builder csvCreated;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);

        dataKey = getString(R.string.data);
        data = getSharedPreferences(dataKey, 0);

        if(data.getInt("numFiles", 0) ==0) {
            SharedPreferences.Editor SPD = data.edit();

            SPD.putInt("numFiles", 1);
            SPD.apply();
        }

        ctw = new ContextThemeWrapper(this, THEME_HOLO_LIGHT);
        csvCreated = new AlertDialog.Builder(ctw);

    }

    public void setGoToPitScouter(View v){


        Intent goToPostMatch = new Intent(this, PitScouter.class);
        startActivity(goToPostMatch);

    }

    public void setGenerateCSV(View v) {


        if (!checkWriteExternalPermission()) {
            if(Build.VERSION.SDK_INT <23) {
                Toast.makeText(this, "Go into Settings and give access to storage", Toast.LENGTH_SHORT).show();
            } else {
                requestPermissions(new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                requestPermissions(new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
            }
        } else {

            try {
                path = Environment.getExternalStorageDirectory();
                dir = new File(path.getAbsolutePath() + "/FRCMATCHDATA");
                //noinspection ResultOfMethodCallIgnored
                dir.mkdirs();


                file = new File(dir, "PitData" + data.getInt("numFiles", 0) + ".txt");

                file.setWritable(true);
                file.setReadable(true);
                file.setExecutable(true);

                if (file.exists()) {
                    file.delete();
                    //noinspection ResultOfMethodCallIgnored
                }
                file.createNewFile();
                fos = new FileOutputStream(file, true);

                fullCSVExport = data.getString("allData", "No Data").getBytes();
                fos.write(fullCSVExport);
                fos.close();

                SharedPreferences.Editor SPD = data.edit();

                SPD.putString("oldData", data.getString("oldData", "") + data.getString("allData", ""));
                SPD.putString("allData", "");
                SPD.putInt("numFiles", data.getInt("numFiles", 0) + 1);
                SPD.apply();

                csvCreated.setTitle("Data Added Successfully");
                csvCreated.setCancelable(true);
                csvCreated.setNeutralButton(
                        "OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
                AlertDialog alert = csvCreated.create();
                alert.show();

                MediaScannerConnection.scanFile(this, new String[]{file.toString()}, null, null);

            } catch (FileNotFoundException e) {
                Toast.makeText(this, "FileNotFound", Toast.LENGTH_SHORT).show();

            } catch (IOException e) {

                Toast.makeText(this, "IOException", Toast.LENGTH_SHORT).show();
            }
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

}





