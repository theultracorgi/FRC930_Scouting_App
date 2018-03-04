package org.team930.bears.yaboiinthepits;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class HomeScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);

    }

    public void setGoToPitScouter(View v){


        Intent goToPostMatch = new Intent(this, PitScouter.class);
        startActivity(goToPostMatch);

    }

}
