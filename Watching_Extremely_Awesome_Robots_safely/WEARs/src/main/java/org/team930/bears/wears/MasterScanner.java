package org.team930.bears.wears;


import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.media.MediaScannerConnection;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.ContextThemeWrapper;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import static android.app.AlertDialog.THEME_HOLO_LIGHT;


@SuppressWarnings("ALL")
public class MasterScanner extends AppCompatActivity {
    Button generateCSV, scanQRCode;
    TextView numDataSets;

    String otherPreferences, prevScan;
    byte[] fullCSVExport;

    SharedPreferences otherSettings;
    FileOutputStream fos;
    File path, dir, file;
    ContextThemeWrapper ctw;
    AlertDialog.Builder csvCreated;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_master_scanner);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);


        ctw = new ContextThemeWrapper(this, THEME_HOLO_LIGHT);
        csvCreated = new AlertDialog.Builder(ctw);

        otherPreferences = getString(R.string.otherPreferences);
        otherSettings = getSharedPreferences(otherPreferences, 0);

        generateCSV = findViewById(R.id.generateCSV);
        scanQRCode = findViewById(R.id.scanQRCode);
        numDataSets = findViewById(R.id.numDataSets);

        prevScan = null;

        SharedPreferences.Editor SPOS = otherSettings.edit();


        if (otherSettings.getInt("scannedID", 0) == 0) {
            SPOS.putInt("scannedID", 0);
            SPOS.apply();
        }

        if (otherSettings.getBoolean("csVisible", false)) {
            generateCSV.setVisibility(View.VISIBLE);
        }
        numDataSets.setText(String.format(Locale.ENGLISH, "%d", otherSettings.getInt("scannedID", 6)));


    }

    public void setGenerateCSV(View v) {


        if (!checkWriteExternalPermission()) {
            if (Build.VERSION.SDK_INT < 23) {
                Toast.makeText(this, "Go into your phone Settings and give access to storage", Toast.LENGTH_SHORT).show();
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

                DateFormat df = new SimpleDateFormat("HH:mm:ss a");
                String time = df.format(Calendar.getInstance().getTime());
                file = new File(dir, "MatchData(" + time + ").csv");

                file.setWritable(true);
                file.setReadable(true);
                file.setExecutable(true);

                if (file.exists()) {
                    file.delete();
                    //noinspection ResultOfMethodCallIgnored
                }
                file.createNewFile();
                fos = new FileOutputStream(file);

                fullCSVExport = otherSettings.getString("scannedMatches", "No Data").getBytes();
                fos.write(fullCSVExport);
                fos.close();

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


    public void setScanQRCode(View v) {
        IntentIntegrator integrator = new IntentIntegrator(this);
        integrator.initiateScan();

    }

    @SuppressLint("SetTextI18n")
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {

        generateCSV.setVisibility(View.VISIBLE);

        IntentResult scanResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
        if (resultCode == RESULT_CANCELED) {
            Toast.makeText(this, "Scan Cancelled", Toast.LENGTH_SHORT).show();

        } else if (scanResult != null || scanResult.getContents() == "0") {
            // handle scan result


            SharedPreferences.Editor SPOS = otherSettings.edit();

            SPOS.putString("scannedMatches", otherSettings.getString("scannedMatches", "") + scanResult.getContents());
            SPOS.apply();
            SPOS.putInt("qrScanned", otherSettings.getInt("qrScanned", 0) + 1);
            SPOS.putInt("scannedID", otherSettings.getInt("scannedID", 5) + 1);
            SPOS.putBoolean("deleteData", true);
            SPOS.putBoolean("csVisible", true);
            SPOS.apply();

            prevScan = scanResult.getContents();
            numDataSets.setText(String.format(Locale.ENGLISH, "%d", otherSettings.getInt("scannedID", 6)));

        } else {
            Toast.makeText(this, "Null Scan. Please Retry", Toast.LENGTH_SHORT).show();

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