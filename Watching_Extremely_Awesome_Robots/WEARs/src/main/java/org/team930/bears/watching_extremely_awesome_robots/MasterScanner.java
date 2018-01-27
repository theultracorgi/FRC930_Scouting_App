package org.team930.bears.watching_extremely_awesome_robots;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;


public class MasterScanner extends AppCompatActivity {
    Button generateCSV, scanQRCode;
    String[] matchDataArray;
    SharedPreferences otherSettings;
    String otherPreferences;



    FileOutputStream fWriter;
    File findDir, directory, fileName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_master_scanner);



        matchDataArray = new String[6];

        generateCSV = findViewById(R.id.generateCSV);
        scanQRCode = findViewById(R.id.scanQRCode);

        otherPreferences = getString(R.string.otherPreferences);

        otherSettings = getSharedPreferences(otherPreferences, 0);

        if (otherSettings.getInt("scannedID", 0) == 0) {
            SharedPreferences.Editor SPOS = otherSettings.edit();

            SPOS.putInt("scannedID", 0);
            SPOS.commit();

        }

        if(otherSettings.getBoolean("csVisible", false)){
            generateCSV.setVisibility(View.VISIBLE);
        }

    }

    public void setGenerateCSV(View v) {

        String fullCSVExport = otherSettings.getString("csvMatch1", "") + otherSettings.getString("csvMatch2", "") +
                otherSettings.getString("csvMatch3", "") + otherSettings.getString("csvMatch4", "") +
                otherSettings.getString("csvMatch5", "") + otherSettings.getString("csvMatch6", "");
        try {
            File sdCard = Environment.getExternalStorageDirectory();
            File dir = new File(sdCard.getAbsolutePath() + "/FRCMATCHDATA");
            dir.mkdirs();
            File file = new File(dir, "MatchData.csv");

            if(file.exists()){
                file.delete();
            } else {
                file.createNewFile();
            }

            FileOutputStream f = new FileOutputStream(file);

            f.write(fullCSVExport.getBytes());
            f.flush();
            f.close();
        } catch(FileNotFoundException e) {
            Toast.makeText(this, "FileNotFound", Toast.LENGTH_SHORT).show();

        } catch(IOException e ) {

            Toast.makeText(this, "IO", Toast.LENGTH_SHORT).show();
        }
        Toast.makeText(this, "CSV created, playa", Toast.LENGTH_SHORT).show();
    }

    public void setScanQRCode(View v) {
        IntentIntegrator integrator = new IntentIntegrator(this);
        integrator.initiateScan();
    }

    public void onActivityResult(int requestCode, int resultCode, Intent intent) {

        generateCSV.setVisibility(View.VISIBLE);


        IntentResult scanResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
        if (scanResult != null) {
            // handle scan result


            SharedPreferences.Editor SPOS = otherSettings.edit();

            switch(otherSettings.getInt("scannedID", 5)){
                case 0: SPOS.putString("csvMatch1", scanResult.getContents());
                    SPOS.commit();
                    break;
                case 1: SPOS.putString("csvMatch2", scanResult.getContents());
                    SPOS.commit();
                    break;
                case 2: SPOS.putString("csvMatch3", scanResult.getContents());
                    SPOS.commit();
                    break;
                case 3: SPOS.putString("csvMatch4", scanResult.getContents());
                    SPOS.commit();
                    break;
                case 4: SPOS.putString("csvMatch5", scanResult.getContents());
                    SPOS.commit();
                    break;
                case 5: SPOS.putString("csvMatch6", scanResult.getContents());
                    SPOS.commit();
                    break;
                default:  SPOS.putString("csvMatch6", scanResult.getContents());
                    SPOS.commit();

            }

            SPOS.putInt("scannedID", otherSettings.getInt("scannedID", 5) + 1);
            SPOS.putBoolean("deleteData", true);
            SPOS.putBoolean("csVisible", true);
            SPOS.commit();

            if(otherSettings.getInt("scannedID", 6) == 6) {
                Toast.makeText(this, "Generate a CSV homie", Toast.LENGTH_SHORT).show();
            }

        }

    }
}
