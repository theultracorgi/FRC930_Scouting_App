package org.team930.bears.watching_extremely_awesome_robots;


import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.media.MediaScannerConnection;
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
import java.util.Locale;

import static android.app.AlertDialog.THEME_HOLO_LIGHT;


@SuppressWarnings("ALL")
public class MasterScanner extends AppCompatActivity {
    Button generateCSV, scanQRCode;
    TextView numDataSets;

    String otherPreferences;


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
        setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);


        ctw = new ContextThemeWrapper(this, THEME_HOLO_LIGHT);
        csvCreated = new AlertDialog.Builder(ctw);

        otherPreferences = getString(R.string.otherPreferences);
        otherSettings = getSharedPreferences(otherPreferences, 0);



        generateCSV = findViewById(R.id.generateCSV);
        scanQRCode = findViewById(R.id.scanQRCode);
        numDataSets = findViewById(R.id.numDataSets);

        SharedPreferences.Editor SPOS = otherSettings.edit();

        changeButtonTextOnBoolean(generateCSV,otherSettings.getBoolean("hasPerms", false), "Generate .XLSX File" , "Give Me Permissions");

        if (otherSettings.getInt("scannedID", 0) == 0) {


            SPOS.putInt("scannedID", 0);
            SPOS.apply();
        }

        if (otherSettings.getBoolean("csVisible", false)) {
            generateCSV.setVisibility(View.VISIBLE);
        }

        if (otherSettings.getInt("scannedID", 6) == 6) {
            numDataSets.setTextSize(45);
            numDataSets.setText("Generate A CSV");
            scanQRCode.setVisibility(View.INVISIBLE);

        } else {
            numDataSets.setTextSize(100);
            numDataSets.setText(String.format(Locale.ENGLISH, "%d", otherSettings.getInt("scannedID", 6)));

        }

    }

    public void setGenerateCSV(View v) {

        SharedPreferences.Editor SPOS = otherSettings.edit();
        if(checkWriteExternalPermission()) {
            SPOS.putBoolean("hasPerms", true);
            SPOS.apply();
            generateCSV.setText("Generate .XLSX File");
        } else {
            SPOS.putBoolean("hasPerms", false);
            SPOS.apply();
        }

        if (!otherSettings.getBoolean("hasPerms", false)) {

            requestPermissions(new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
            requestPermissions(new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE}, 1);

        } else {

            String fullCSVExport = otherSettings.getString("csvMatch1", "") + otherSettings.getString("csvMatch2", "") +
                    otherSettings.getString("csvMatch3", "") + otherSettings.getString("csvMatch4", "") +
                    otherSettings.getString("csvMatch5", "") + otherSettings.getString("csvMatch6", "");
            try {
                path = Environment.getExternalStorageDirectory();
                dir = new File(path.getAbsolutePath() + "/FRCMATCHDATA");
                //noinspection ResultOfMethodCallIgnored
                dir.mkdirs();


                file = new File(dir, "MatchData.xlsx");


                if (file.exists()) {
                    fos = new FileOutputStream(file, true);
                    fos.write(fullCSVExport.getBytes());
                } else {
                    fos = new FileOutputStream(file);
                    //noinspection ResultOfMethodCallIgnored
                    file.createNewFile();
                    fos.write(fullCSVExport.getBytes());

                }
                fos.flush();
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
        if (scanResult != null) {
            // handle scan result
            SharedPreferences.Editor SPOS = otherSettings.edit();

            switch (otherSettings.getInt("scannedID", 5)) {
                case 0:
                    SPOS.putString("csvMatch1", scanResult.getContents());
                    SPOS.apply();
                    break;
                case 1:
                    SPOS.putString("csvMatch2", scanResult.getContents());
                    SPOS.apply();
                    break;
                case 2:
                    SPOS.putString("csvMatch3", scanResult.getContents());
                    SPOS.apply();
                    break;
                case 3:
                    SPOS.putString("csvMatch4", scanResult.getContents());
                    SPOS.apply();
                    break;
                case 4:
                    SPOS.putString("csvMatch5", scanResult.getContents());
                    SPOS.apply();
                    break;
                case 5:
                    SPOS.putString("csvMatch6", scanResult.getContents());
                    SPOS.apply();
                    break;
                default:
                    SPOS.putString("csvMatch6", scanResult.getContents());
                    SPOS.apply();
            }

            SPOS.putInt("qrScanned", otherSettings.getInt("qrScanned", 0) + 1);
            SPOS.apply();
            SPOS.putInt("scannedID", otherSettings.getInt("scannedID", 5) + 1);
            SPOS.putBoolean("deleteData", true);
            SPOS.putBoolean("csVisible", true);
            SPOS.apply();


            if (otherSettings.getInt("scannedID", 6) == 6) {
                numDataSets.setTextSize(45);
                numDataSets.setText("Generate A CSV");
                scanQRCode.setVisibility(View.INVISIBLE);


            } else {
                numDataSets.setText(String.format(Locale.ENGLISH, "%d", otherSettings.getInt("scannedID", 6)));
            }
        }
    }

    private boolean checkWriteExternalPermission() {

        String writePerm = "android.permission.WRITE_EXTERNAL_STORAGE";
        String readPerm = "android.permission.READ_EXTERNAL_STORAGE";
        int write = this.checkCallingOrSelfPermission(writePerm);
        int read = this.checkCallingOrSelfPermission(readPerm);

        boolean permsGranted;

        if(write == PackageManager.PERMISSION_GRANTED && read == PackageManager.PERMISSION_GRANTED) {
            permsGranted = true;
        } else {
            permsGranted = false;
        }

        return permsGranted;


    }

    public void changeButtonTextOnBoolean(Button textApplication, boolean test, String trueText, String falseText){
        if(test){
            textApplication.setText(trueText);
        } else {
            textApplication.setText(falseText);
        }
    }
}