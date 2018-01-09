package org.team930.bears.watching_extremely_awesome_robots;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class PreMatch extends AppCompatActivity {
    Intent goToAuton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pre_match);
    }

    public void goToAuton(View v){

        goToAuton = new Intent(PreMatch.this, AutonTeleop.class);
        startActivity(goToAuton);
        }

    }

