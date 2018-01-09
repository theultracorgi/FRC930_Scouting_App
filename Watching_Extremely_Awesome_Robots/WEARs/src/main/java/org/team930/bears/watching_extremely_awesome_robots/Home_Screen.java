package org.team930.bears.watching_extremely_awesome_robots;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class Home_Screen extends AppCompatActivity {
   Intent nextScreen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home__screen);
    }

    public void setPreMatch(View v){

        nextScreen = new Intent(Home_Screen.this,PreMatch.class);
        startActivity(nextScreen);

    }

    public void setTransferData(View v){

        nextScreen = new Intent(Home_Screen.this,AutonTeleop.class);
        startActivity(nextScreen);

    }

    public void setSettings(View v){

        nextScreen = new Intent(Home_Screen.this,AutonTeleop.class);
        startActivity(nextScreen);

    }
}
