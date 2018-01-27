package org.team930.bears.watching_extremely_awesome_robots;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


public class HomeScreen extends AppCompatActivity {
    Button goToPreMatch, goToGenQR, goToSettings, goToMasterScanner;

    SharedPreferences otherSettings;

    String otherPreferences, numStoredMatches;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home__screen);

        numStoredMatches = getString(R.string.numStoredMatches);
        otherPreferences = getString(R.string.otherPreferences);

        otherSettings = getSharedPreferences(otherPreferences, 0);

        SharedPreferences.Editor SPOS = otherSettings.edit();

        if(otherSettings.getBoolean("firstOpen", true)){
            SPOS.putInt(numStoredMatches, 0);

            SPOS.commit();
        }


        goToPreMatch = findViewById(R.id.goToPreMatch);
        goToGenQR = findViewById(R.id.goToGenQR);
        goToSettings = findViewById(R.id.goToSettings);
        goToMasterScanner = findViewById(R.id.goToMaster);


    }

    public void setGoToPreMatch(View v) {

        if(otherSettings.getInt(numStoredMatches, 6) >= 6) { //
            Toast.makeText(this, "You must generate a QR code to continue scouting", Toast.LENGTH_LONG).show();

        } else {

            Intent nextScreen = new Intent(HomeScreen.this, PreMatch.class);
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
}
