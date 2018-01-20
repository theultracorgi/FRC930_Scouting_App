package org.team930.bears.watching_extremely_awesome_robots;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class Home_Screen extends AppCompatActivity {
    Button goToPreMatch, goToGenQR, goToSettings;

    SharedPreferences SP;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home__screen);

        SP = getSharedPreferences("SharedPreferences", 0);

        goToPreMatch = findViewById(R.id.goToPreMatch);
        goToGenQR = findViewById(R.id.goToGenQR);
        goToSettings = findViewById(R.id.goToSettings);


    }

    public void setGoToPreMatch(View v) {

        Intent nextScreen = new Intent(Home_Screen.this, PreMatch.class);
        startActivity(nextScreen);

    }

    public void setGoToGenQR(View v) {

        Intent nextScreen = new Intent(Home_Screen.this, AutonTeleop.class);
        startActivity(nextScreen);

    }

    public void setGoToSettings(View v) {

        Intent nextScreen = new Intent(Home_Screen.this, Settings.class);
        startActivity(nextScreen);

    }
}
